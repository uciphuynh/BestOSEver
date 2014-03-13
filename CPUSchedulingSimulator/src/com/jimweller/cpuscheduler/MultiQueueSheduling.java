/*
 * @author: Group 32: Shannon Lewis, Zach Soohoo, Phong Huynh, Rachel Chu
 * Winter 2014
 */

//a Multi-level queue algorithm where processes are sorted by priority
// Priority 0-3 => Queue 1 (Round robin, quantum of 10 or selected)
// Priority 4-6 => Queue 2 (Round robin, quantum of 2*Queue 1 quantum)
// Priority 7-9 => Queue 3 (First Come First Serve)

package com.jimweller.cpuscheduler;

import java.util.LinkedList;

public class MultiQueueSheduling extends RoundRobinSchedulingAlgorithm
implements OptionallyPreemptiveSchedulingAlgorithm
{
	//private int quantum (get quantum and set quantum already here)
	
	//if canInterrupt is true, algorithm can be preemptive in that the moment a higher
	//level queue receives a process, lower level queue processes are put on hold
	private boolean canInterrupt;
	
	//private LinkedList<Process> queue1; //round robin with quantum (already provided with parent class)
	//so queue1 = queue.
	private LinkedList<Process> queue2;// round robin with 2*quantum
	private LinkedList<Process> queue3;// first come first serve (round robin with quantum of infinity)
	
	private final long QUEUE3_HIGHEST_PRIORITY = 7;
	private final long QUEUE2_HIGHEST_PRIORITY = 4;
	
	//since max burst time is 99, 100 is infinite
	private final int INFINITE_QUANTUM = 100;
	private long quantumStart;
	
	
	public MultiQueueSheduling()
	{
		super();
		queue2 = new LinkedList<Process>();
		queue3 = new LinkedList<Process>();
	}
	
	//preemption here means if a process from a higher queue enters, the currently
	//running process gets interrupted
	public boolean isPreemptive() {
		
		return canInterrupt;
	}
	
	@Override
	public void setPreemptive(boolean v) {
		// TODO Auto-generated method stub
		canInterrupt = v;
		
	}
	public void addJob(Process p) {
		
		//check p's priority
		//add to appropriate queue
		
		if(p.priority < QUEUE3_HIGHEST_PRIORITY)
		{
			if(p.priority < QUEUE2_HIGHEST_PRIORITY)
			{
				queue.add(p);
				//System.out.println("Process added to queue1");
			}
			else
			{
				//add to queue2
				queue2.add(p);
				//System.out.println("Process added to queue2");
			}
		}
		else
		{
			queue3.add(p);
			//System.out.println("Process added to queue3");
		}
		
	}

	@Override
	public boolean removeJob(Process p) {
		// TODO Auto-generated method stub
		
		//check if p is done and remove p from its respective queue
		if(p.getPriorityWeight() < QUEUE2_HIGHEST_PRIORITY)
		{
			return queue.remove(p);
		}
		else if(p.getPriorityWeight() < QUEUE3_HIGHEST_PRIORITY)
		{
			return queue2.remove(p);
		}
		else
		{
			return queue3.remove(p);
		}
	}

	@Override
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		// TODO Auto-generated method stub
		
		//transferring the jobs in the order of priority...ish...
		for(int i = 0; i < queue.size();i++)
		{
			otherAlg.addJob(queue.get(i));
		
		}
		
		for(int j = 0; j < queue2.size(); j++)
		{
			otherAlg.addJob(queue2.get(j));
			
		}
		
		for(int k = 0; k < queue3.size(); k++)
		{
			otherAlg.addJob(queue3.get(k));
		}
		
	}

	@Override
	public Process getNextJob(long currentTime) {
		// TODO Auto-generated method stub
		
		//if all the queues are empty, return null
		if(queue.size() == 0 && queue2.size() == 0 && queue3.size() == 0)
		{
			return null;
		}
		
		long pseudoTime = currentTime - quantumStart;
		//check which one is currently running process
		if(queue.size() != 0 && queue.get(0).isActive())
		{
			//since normal round robin is the parent class might as well
			//save typing 3 lines...
			//System.out.println("running queue1");
			return super.getNextJob(currentTime);
		}
		else if(queue2.size() != 0 && queue2.get(0).isActive())
		{
			//System.out.println("Pseudo time is: " + pseudoTime);
			if(queue.size() != 0 && canInterrupt)
			{
				//System.out.println("Queue2 interrupted to run queue1");
				quantumStart = currentTime;
				return queue.get(0);
			}
			else if(pseudoTime >= 2*quantum || queue2.get(0).isFinished())
			{
				//System.out.println("Changing head");
				Process origHead = queue2.pop();
				queue2.add(origHead);
				quantumStart = currentTime;
				if(queue.size() != 0)
				{
					//System.out.println("Running queue1 after queue2");
					return queue.get(0);
				}
			}
			//System.out.println("Running queue2");
			return queue2.get(0);
		}
		else if(queue3.size() != 0 && queue3.get(0).isActive())
		{
			if(queue2.size() != 0 || queue.size() != 0)
			{
				if(canInterrupt)
				{
					quantumStart = currentTime;
					if(queue.size() != 0)
					{
						//System.out.println("Running queue1 after interrupting queue3");
						return queue.get(0);
					}
					else
					{
						//System.out.println("Running queue2 after interrupting queue2");
						return queue2.get(0);
					}
				}
			}
			//System.out.println("running queue3");
			return queue3.get(0);
		}
		else
		{
			//System.out.println("Finding next process after idling");
			quantumStart = currentTime;
			//none of the queues have an active process but there is a queue that is non empty
			//find out which queue is non empty and return that process
			if(queue.size()!= 0)
			{
				return queue.get(0);
			}
			else if (queue2.size() != 0)
			{
				return queue2.get(0);
			}
			else
			{
				return queue3.get(0);
			}
		}
		
	}

	@Override
	public String getName() {
		
		return "Multi-Queue";
	}

}
