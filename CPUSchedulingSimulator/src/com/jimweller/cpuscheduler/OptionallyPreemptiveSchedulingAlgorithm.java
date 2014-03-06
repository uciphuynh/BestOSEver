/** OptionallyPreemptiveSchedulingAlgorithm.java
 * 
 * An interface for algorithms that can be set to preempt processes running if a
 * 'higher priority' process appears.
 *
 * @author: Group 32: Shannon Lewis, Zach Soohoo, Phong Huynh, Rachel Chu
 * Winter 2014
 *
 */
package com.jimweller.cpuscheduler;

public interface OptionallyPreemptiveSchedulingAlgorithm extends SchedulingAlgorithm {
    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive();
    
    /**
     * @param v  Value to assign to preemptive.
     */
    public void setPreemptive(boolean  v);
}