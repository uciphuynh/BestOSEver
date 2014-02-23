//a Multi-level queue algorithm where processes are sorted by priority
// Priority 0-3 => Queue 1 (Round robin, quantum of 10 or selected)
// Priority 4-6 => Queue 2 (Round robin, quantum of 2*Queue 1 quantum)
// Priority 7-9 => Queue 3 (First Come First Serve)

package com.jimweller.cpuscheduler;

import java.util.LinkedList;

public class MultiQueueSheduling extends RoundRobinSchedulingAlgorithm
implements OptionallyPreemptiveSchedulingAlgorithm
{
	//if canInterrupt is true, algorithm can be preemptive in that the moment a higher
	//level queue receives a process, lower level queue processes are put on hold
	private boolean canInterrupt;
	
	private LinkedList<Process> queue1;
	private LinkedList<Process> queue2;
	private LinkedList<Process> queue3;
	
	public boolean isPreemptive() {
		
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
		
		//check p's priority
		//add to appropriate queue
		
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
		
		return null;
	}

	@Override
	public String getName() {
		
		return "Multi-Queue";
	}

}
