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
public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
	private boolean preemptive;
	private Vector<Process> jobs;
	private Process aJob;			// Points to the currently running job.

	PrioritySchedulingAlgorithm(){
		jobs = new Vector<Process>();
		preemptive = false;
	}

	/** Add the new job to the correct queue.*/
	public void addJob(Process p){
		if(jobs.isEmpty()){
			jobs.add(p);
			aJob = p;
		}
		else{
			boolean added = false;
			for (int i = 0; i < jobs.size(); i++) {
				if (p.getPriorityWeight() < jobs.get(i).getPriorityWeight()) {
					jobs.add(i, p);
					added = true;
					break;
				}
			}
			if(!added){
				jobs.add(p);
			}
		}
	}

	/** Returns true if the job was present and was removed.*/
	public boolean removeJob(Process p){
		return jobs.remove(p);
	}

	/** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		while(!jobs.isEmpty()){
			otherAlg.addJob(jobs.remove(0));
		}
	}


	/** Returns the next process that should be run by the CPU, null if none available.*/
	public Process getNextJob(long currentTime){
		if(aJob != null && aJob.isFinished()){
			aJob = null;
		}
		if(jobs.isEmpty()){
			return null;
		}
		else if(isPreemptive() && aJob != null && jobs.get(0).getPriorityWeight() < aJob.getPriorityWeight()){
			aJob = jobs.get(0);
			return aJob;
		}
		else if(aJob != null){
			return aJob;
		}
		else {
			aJob = jobs.get(0);
			return aJob;
		}
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