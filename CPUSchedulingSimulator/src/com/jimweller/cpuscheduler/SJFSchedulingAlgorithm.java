/** SJFSchedulingAlgorithm.java
 * 
 * A shortest job first scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;


public class SJFSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    private boolean preemptive;
    private Vector<Process> jobs;
    //active job

    SJFSchedulingAlgorithm(){
    	activeJob = null;
    	preemptive = false;
    	jobs = new Vector<Process>();
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	Process cur;
    	if (isPreemptive()) {
    		for (int i = 0; i < jobs.size(); i++) {
        		cur = jobs.get(i);
        		if (p.getInitBurstTime() < cur.getBurstTime()) {	
        			jobs.add (i, p);
        		}		//BUGBUG change active job?
        	}
    	} else {
    		for (int i = 0; i < jobs.size(); i++) {
    			cur = jobs.get(i);
    			if (p.getInitBurstTime() < cur.getInitBurstTime()) {
    				jobs.add (i, p);
    			}
    		}
    	}
    	
    	jobs.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	return jobs.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
    	Process cur;
    	for (int i = jobs.size() - 1; i >= 0; i--) {
    		cur = jobs.get(i);
    		otherAlg.addJob(cur);
    		this.removeJob(cur);
    	}
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime) {
    	Process cur;
    	//Remove jobs that have completed
    	for (int i = jobs.size() - 1; i >= 0; i--) {
    		cur = jobs.get(i);
    		if (cur.getBurstTime() == 0) {
    			removeJob(cur);
    		} else {
    			break;
    		}
    	}
    	return jobs.get(0);
    }

    public String getName(){
    	return "Shortest job first";
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