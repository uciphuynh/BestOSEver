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
	
	private int currentlyRunningQueue = 3;
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
			}
			else
			{
				//add to queue2
				queue2.add(p);
			}
		}
		else
		{
			queue3.add(p);
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
		
		//if I can't interrupt, I need to look to see if the quantum is fulfilled
		if(activeJob != null && !canInterrupt)
		{
			
			//check if the quantum time is done
			long psuedoQuant = currentTime - quantumStart;
			
			if(currentlyRunningQueue == 1 && (psuedoQuant < quantum))
			{
				return activeJob;
			}
			else if(currentlyRunningQueue == 2 && (psuedoQuant < 2*quantum))
			{
				return activeJob;
			}
			else if((currentlyRunningQueue == 3 && psuedoQuant < INFINITE_QUANTUM))
			{
				return activeJob;
			}
		}
		else if(queue.size() > 0)
		{
			//return queue 1 job and set method to queue1
			quantumStart = currentTime;
			currentlyRunningQueue = 1;
			return queue.get(0);
		}
		else if(queue2.size() > 0)
		{
			quantumStart = currentTime;
			currentlyRunningQueue = 2;
			return queue2.get(0);
		}
		else
		{
			quantumStart = currentTime;
			currentlyRunningQueue = 3;
			return queue3.get(0);
		}
		
		return null;
		
	}

	@Override
	public String getName() {
		
		return "Multi-Queue";
	}

}
