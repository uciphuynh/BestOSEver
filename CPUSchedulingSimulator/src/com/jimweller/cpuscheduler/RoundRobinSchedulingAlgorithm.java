/** RoundRobinSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * @author: Group 32: Shannon Lewis, Zach Soohoo, Phong Huynh, Rachel Chu
 * Winter 2014
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm {

    /** the timeslice each process gets */
    //needs to be protected so MultiQueue can use this...
	protected int quantum;
    protected LinkedList<Process> queue;
    private LinkedList<Process> waiting;
    private LinkedList<Process> toAdd;
    public RoundRobinSchedulingAlgorithm() 
    {
    	activeJob = null;
    	queue = new LinkedList<Process>();
    	waiting = new LinkedList<Process>();
    	toAdd = new LinkedList<Process>();
    	quantum = 0;
    }

    /** Add the new job to the correct queue. */
    public void addJob(Process p) 
    {
    	waiting.add(p);
    }

    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p) 
    {
    	return queue.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) 
    {
    	otherAlg.addJob(activeJob);
    	for(Process job : queue)
    	{
    		otherAlg.addJob(job);
    	}
    	for(Process job : waiting)
    	{
    		otherAlg.addJob(job);
    	}
    	for(Process job : toAdd)
    	{
    		otherAlg.addJob(job);
    	}
    }

    /**
     * Get the value of quantum.
     * 
     * @return Value of quantum.
     */
    public int getQuantum() 
    {
	return quantum;
    }

    /**
     * Set the value of quantum.
     * 
     * @param v
     *            Value to assign to quantum.
     */
    public void setQuantum(int v) 
    {
	this.quantum = v;
    }

    /**
     * Returns the next process that should be run by the CPU, null if none
     * available.
     */
    public Process getNextJob(long currentTime) 
    {
    	if(queue.isEmpty() && waiting.isEmpty() && toAdd.isEmpty())
    	{
    		return null;
    	}
    	else
    	{
    		if(queue.isEmpty())
    		{
    			for(Process job : waiting)
    			{
    				queue.add(waiting.pop());
    			}
    			for(Process job : toAdd)
    			{
    				queue.add(toAdd.pop());
    			}
    		}
    		waiting.add(queue.pop());
    		return waiting.getLast();
    	}
    }

    public String getName() {
	return "Round Robin";
    }
}