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
				//add to queue 1
				//and interrupt the current running process if the current running is not in
				//queue 1 
				
				if(canInterrupt)
				{
					
				}
			}
			else
			{
				//add to queue 2
				if(canInterrupt)
				{
					
				}
			}
		}
		else
		{
			//add to queue 3
		}
		
	}

	@Override
	public boolean removeJob(Process p) {
		// TODO Auto-generated method stub
		
		//check if p is done and remove p from its respective queue
		
		return false;
	}

	@Override
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Process getNextJob(long currentTime) {
		// TODO Auto-generated method stub
		
		//check through the higher level queues first
		
		if(queue.size() > 0)
		{
			//return queue 1 job and set method to queue1
		}
		
		if(queue2.size() > 0)
		{
			//return queue 2 job and set scheduling method to queue 2
		}
		
		//return queue 3 job
		
		return null;
	}

	@Override
	public String getName() {
		
		return "Multi-Queue";
	}

}
