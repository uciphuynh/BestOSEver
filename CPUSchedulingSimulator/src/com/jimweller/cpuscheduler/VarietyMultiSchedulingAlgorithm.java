package com.jimweller.cpuscheduler;

import java.util.ArrayList;

//in the documentation for the write up we use a variety of algorithms for each queue
//so when someone selects this one, I would like them to be able to choose the 3 algorithms
//to implement

//not too sure if I want the option of let 1 queue be preemptive and the others not...

//MultiQueueSheduling (forgot a 'c' sorry) already extends round robin

public class VarietyMultiSchedulingAlgorithm extends MultiQueueSheduling implements OptionallyPreemptiveSchedulingAlgorithm
{
	boolean canInterrupt;
	//private LinkedList queue;
	//private int quantum
	
	private final int QUEUE_1_ALG = 1;
	private final int QUEUE_2_ALG = 2;
	private final int QUEUE_3_ALG = 3;
	
	//all of the possible scheduling algorithm types are stored in this list
	//above ints repreesent the position in the arraylist below that the algorithm
	//that needs to be applied is.
	private ArrayList<BaseSchedulingAlgorithm> algorithmList;
	
	//multi queue is a Variety Queue with Queue2 multiplier set to 2 and queue3 multiplier
	//set to 100
	private final int QUEUE_2_QUANTUM_MULT = 1;
	private final int QUEUE_3_QUANTUM_MULT = 2;
	
	public VarietyMultiSchedulingAlgorithm()
	{
		//initialize the algorithm list with all possible algorithms (both preemptive and non
		//preemptive forms) not too sure if I want to do this manually...
	}

	@Override
	public boolean isPreemptive() {
		// TODO Auto-generated method stub
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
		
	}

	@Override
	public boolean removeJob(Process p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Process getNextJob(long currentTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Mix n' Match Multi-Queue";
	}

}
