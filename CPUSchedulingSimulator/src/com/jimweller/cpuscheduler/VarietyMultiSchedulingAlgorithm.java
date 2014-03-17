package com.jimweller.cpuscheduler;

import java.util.ArrayList;

//in the documentation for the write up we use a variety of algorithms for each queue
//so when someone selects this one, I would like them to be able to choose the 3 algorithms
//to implement

//not too sure if I want the option of let 1 queue be preemptive and the others not...

//MultiQueueSheduling (forgot a 'c' sorry) already extends round robin

public class VarietyMultiSchedulingAlgorithm extends RoundRobinSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm
{
	//included with Round Robin:
	//LinkedList queue
	
	private boolean canInterrupt;
	
	private final long QUEUE2_HIGHEST_PRIORITY = 4;
	private final long QUEUE3_HIGHEST_PRIORITY = 7;
	
	/*so order of algorithm list is: 
	// 0 FCFS 
	// 1 SJF
	// 2 SRTF (preemptive)
	 * 3 Priority
	 * 4 Priority (preemptive)
	 * 5 RR*/
	
	private final int QUEUE_1_ALG = 5;
	private final int QUEUE_2_ALG = 1;
	private final int QUEUE_3_ALG = 2;
	
	//all of the possible scheduling algorithm types are stored in this list
	//above ints represent the position in the arraylist below that the algorithm
	//that needs to be applied is.
	private ArrayList<BaseSchedulingAlgorithm> algorithmList;
	
	private BaseSchedulingAlgorithm queue1Alg;
	private BaseSchedulingAlgorithm queue2Alg;
	private BaseSchedulingAlgorithm queue3Alg;
	
	//multi queue is a Variety Queue with Queue2 multiplier set to 2 and queue3 multiplier
	//set to 100
	private final int QUEUE_1_QUANTUM_MULT = 1;
	private final int QUEUE_2_QUANTUM_MULT = 2;
	private final int QUEUE_3_QUANTUM_MULT = 10;
	
	public VarietyMultiSchedulingAlgorithm()
	{
		
		//manually adding instances of the algorithms into the algorithm list
		algorithmList = new ArrayList<BaseSchedulingAlgorithm>();
		algorithmList.add(new FCFSSchedulingAlgorithm());
		algorithmList.add(new SJFSchedulingAlgorithm());
		SJFSchedulingAlgorithm srtf = new SJFSchedulingAlgorithm();
		srtf.setPreemptive(true);
		algorithmList.add(srtf);
		algorithmList.add(new PrioritySchedulingAlgorithm());
		PrioritySchedulingAlgorithm preemptP = new PrioritySchedulingAlgorithm();
		preemptP.setPreemptive(true);
		algorithmList.add(preemptP);
		algorithmList.add(new RoundRobinSchedulingAlgorithm());
		
		queue1Alg = algorithmList.get(QUEUE_1_ALG);
		queue2Alg = algorithmList.get(QUEUE_2_ALG);
		queue3Alg = algorithmList.get(QUEUE_3_ALG);
		

	}
	
	public void setQuantum(int v)
	{
		super.setQuantum(v);
		
		//ignore the yellow since we're changing the constants to switch between modified
		//algorithms...
		if(QUEUE_1_ALG == 5)
		{
			((RoundRobinSchedulingAlgorithm) queue1Alg).setQuantum(QUEUE_1_QUANTUM_MULT * v);
		}
		
		if(QUEUE_2_ALG == 5)
		{
			((RoundRobinSchedulingAlgorithm) queue2Alg).setQuantum(QUEUE_2_QUANTUM_MULT * v);
			//System.out.println("queue 2's quantum: " + ((RoundRobinSchedulingAlgorithm) queue2Alg).getQuantum());
		}
		
		if(QUEUE_3_ALG == 5)
		{
			((RoundRobinSchedulingAlgorithm) queue3Alg).setQuantum(QUEUE_3_QUANTUM_MULT * v);
		}
	}
	

	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
		return canInterrupt;
	}

	@Override
	public void setPreemptive(boolean v) {
		// TODO Auto-generated method stub
		canInterrupt = v;
		
	}

	@Override
	public void addJob(Process p) {
		// TODO Auto-generated method stub
		
		if(p.getPriorityWeight() < QUEUE3_HIGHEST_PRIORITY)
		{
			if(p.getPriorityWeight() < QUEUE2_HIGHEST_PRIORITY)
			{
				queue1Alg.addJob(p);
			}
			else
			{
				queue2Alg.addJob(p);
			}
		}
		else
		{
			queue3Alg.addJob(p);
		}
		
	}

	@Override
	public boolean removeJob(Process p) {
		// TODO Auto-generated method stub
		if(p.getPriorityWeight() < QUEUE2_HIGHEST_PRIORITY)
		{
			return queue1Alg.removeJob(p);
		}
		else
		{
			if(p.getPriorityWeight() < QUEUE3_HIGHEST_PRIORITY)
			{
				return queue2Alg.removeJob(p);
			}
			else
			{
				return queue3Alg.removeJob(p);
			}
			
		}
	}

	@Override
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		// TODO Auto-generated method stub
		queue1Alg.transferJobsTo(otherAlg);
		queue2Alg.transferJobsTo(otherAlg);
		queue3Alg.transferJobsTo(otherAlg);
	}

	@Override
	public Process getNextJob(long currentTime) {
		// TODO Auto-generated method stub
		
		//fetch all the possible next jobs
		Process p1 = queue1Alg.getNextJob(currentTime);
		Process p2 = queue2Alg.getNextJob(currentTime);
		Process p3 = queue3Alg.getNextJob(currentTime);
		
		if(p1 == null && p2 == null && p3 == null)
		{
			return null;
		}
		else
		{
			//one of them is not null
			//find out which one is active and base decisions off of that
			if(p1 != null && p1.isActive())
			{
				return p1;
			}
			else if(p2 != null && p2.isActive())
			{
				if(canInterrupt && p1 != null)
				{
					return p1;
				}
				
				return p2;
			}
			else if(p3 != null && p3.isActive())
			{
				//p3 is not null and is active
				if(canInterrupt)
				{
					if(p1 != null)
					{
						return p1;
					}
					else if(p2 != null)
					{
						return p2;
					}
					
				}
				return p3;
			}
			else
			{
				//none are active... so find the one that's not null
				if(p1 != null)
				{
					return p1;
				}
				else if(p2 != null)
				{
					return p2;
				}
				else
				{
					return p3;
				}
			}
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Mix n' Match Multi-Queue";
	}

}
