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
	private final int QUEUE_2_ALG = 5;
	private final int QUEUE_3_ALG = 0;
	
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
		
		//ignore the yellow since we're changing the constants to switch between modified
		//algorithms...
		if(QUEUE_1_ALG == 5)
		{
			RoundRobinSchedulingAlgorithm RR = (RoundRobinSchedulingAlgorithm) queue1Alg;
			RR.setQuantum(QUEUE_1_QUANTUM_MULT * quantum);
			queue1Alg = RR;
		}
		
		if(QUEUE_2_ALG == 5)
		{
			RoundRobinSchedulingAlgorithm RR2 = (RoundRobinSchedulingAlgorithm) queue2Alg;
			RR2.setQuantum(QUEUE_2_QUANTUM_MULT * quantum);
			queue2Alg = RR2;
		}
		
		if(QUEUE_3_ALG == 5)
		{
			RoundRobinSchedulingAlgorithm RR3 = (RoundRobinSchedulingAlgorithm) queue3Alg;
			RR3.setQuantum(QUEUE_3_QUANTUM_MULT * quantum);
			queue3Alg = RR3;
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
		
		if(p.getPriorityWeight() < QUEUE2_HIGHEST_PRIORITY)
		{
			queue1Alg.addJob(p);
		}
		else
		{
			if(p.getPriorityWeight() < QUEUE3_HIGHEST_PRIORITY)
			{
				queue2Alg.addJob(p);
			}
			else
			{
				queue3Alg.addJob(p);
			}
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
		
		//this one is going to be complicated
		
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Mix n' Match Multi-Queue";
	}

}
