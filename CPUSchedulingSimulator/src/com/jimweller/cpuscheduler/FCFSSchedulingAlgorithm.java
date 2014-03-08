/** FCFSSchedulingAlgorithm.java
 * 
 * A first-come first-served scheduling algorithm.
 *
 * @author: Group 32: Shannon Lewis, Zach Soohoo, Phong Huynh, Rachel Chu
 * Winter 2014
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class FCFSSchedulingAlgorithm extends BaseSchedulingAlgorithm {

	LinkedList<Process> jobs;
    FCFSSchedulingAlgorithm()
    {
    	activeJob = null;
    	jobs = new LinkedList<Process>();

    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p)
    {
    	jobs.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p)
    {
    	return jobs.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) 
    {
    	otherAlg.addJob(activeJob);
    	for(Process job : jobs)
    	{
    		otherAlg.addJob(job);
    	}
    }


    public boolean shouldPreempt(long currentTime)
    {
    	return false;
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime)
    {
    	if(jobs.isEmpty())
    	{
    		return null;
    	}
    	else
    	{
    		return jobs.pop();	
    	}
    }

    public String getName(){
	return "First-come first-served";
    }
}