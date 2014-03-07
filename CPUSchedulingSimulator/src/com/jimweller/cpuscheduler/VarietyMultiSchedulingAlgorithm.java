package com.jimweller.cpuscheduler;

//in the documentation for the write up we use a variety of algorithms for each queue
//so when someone selects this one, I would like them to be able to choose the 3 algorithms
//to implement

//not too sure if I want the option of let 1 queue be preemptive and the others not...

public class VarietyMultiSchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm
{
	boolean canInterrupt;
	
	public VarietyMultiSchedulingAlgorithm()
	{
		
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
