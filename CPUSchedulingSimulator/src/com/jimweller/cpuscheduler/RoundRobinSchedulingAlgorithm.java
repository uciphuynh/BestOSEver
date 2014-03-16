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
	protected long quantumStart;
    protected LinkedList<Process> queue;
    
    public RoundRobinSchedulingAlgorithm() 
    {
    	activeJob = null;
    	queue = new LinkedList<Process>();
    	quantum = 0;
    }

    /** Add the new job to the correct queue. */
    public void addJob(Process p) 
    {
    	//queue adds P to the back of the queue
    	queue.add(p);
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
    	while(!queue.isEmpty())
    	{
    		otherAlg.addJob(queue.remove());
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
    	if(queue.isEmpty())
    	{
    		return null;
    	}
    	else if((currentTime - quantumStart >= quantum) || queue.get(0).isFinished() )
    	{
    		//if there is a current process running
    		//if quantum time is up or finished, call next job
    		//if not, return currently running process
	    	Process origRunning = queue.pop();
	    	//moved the process to the back of the queue
	    	queue.add(origRunning);
	    	quantumStart = currentTime;    	
    	}
    	return queue.get(0);
    }

    public String getName() {
	return "Round Robin";
    }
}