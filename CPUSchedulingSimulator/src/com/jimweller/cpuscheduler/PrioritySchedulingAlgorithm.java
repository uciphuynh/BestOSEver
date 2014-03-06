/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * @author: Team 32: Phong Huynh (21837531), Rachel Chu (41815538), Shannon Lewis (XXXXXXXX), Zachary Soohoo (XXXXXXXX)
 * Winter 2014
 *
 */
package com.jimweller.cpuscheduler;

import java.util.Vector;
/*
 * My implementation of the class does NOT keep any active job in the queue;
 * the active job is popped before being run.
 */
public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
	private boolean preemptive;
	private Vector<Process> jobs;

	PrioritySchedulingAlgorithm(){
		activeJob = null;
		jobs = new Vector<Process>();
	}

	/** Add the new job to the correct queue.
	 * If there are no current jobs, the incoming job becomes the activeJob rather than entering the queue;
	 * If preemption is enabled and the incoming job's priority is higher (lower weight) than the running job,
	 * the incoming job is run;
	 * Else, the incoming job is inserted wherever it belongs in the queue.*/
	public void addJob(Process p){
		if(jobs.size() == 0){
			activeJob = p;
		}
		else{
			if (isPreemptive() && p.getPriorityWeight() < activeJob.getPriorityWeight()) {
				jobs.add (0, activeJob);
				activeJob = p;

			}
			else {
				for (int i = 0; i < jobs.size(); i++) {
					if (p.getPriorityWeight() < jobs.get(i).getPriorityWeight()) {
						jobs.add(i, p);
						break;
					}
				}
			}
		}
	}

	/** Returns true if the job was present and was removed. 
	 * Also fetches the next job in the queue and runs it,
	 * if the removed job was the active one; not sure if
	 * this is desirable or not, but it's an easy fix
	 * if it's not!*/
	public boolean removeJob(Process p){
		if (activeJob.equals(p)){
			if(jobs.size() != 0){
				activeJob = jobs.remove(0);
			}
			else{
				activeJob = null;
			}
			return true;
		}
		return jobs.remove(p);
	}

	/** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		otherAlg.addJob(activeJob);
		activeJob = null;
		for(int i = 0; i < jobs.size(); i++){
			otherAlg.addJob(jobs.remove(i));
		}
	}


	/** Returns the next process that should be run by the CPU, null if none available.*/
	public Process getNextJob(long currentTime){
		if(jobs.size() == 0){
			return null;
		}
		activeJob = jobs.remove(0);
		return activeJob;
	}

	public String getName(){
		return "Single-queue Priority";
	}

	/**
	 * @return Value of preemptive.
	 */
	public boolean isPreemptive(){
		return preemptive;
	}

	/**
	 * @param v  Value to assign to preemptive.
	 */
	public void setPreemptive(boolean  v){
		preemptive = v;
	}
}