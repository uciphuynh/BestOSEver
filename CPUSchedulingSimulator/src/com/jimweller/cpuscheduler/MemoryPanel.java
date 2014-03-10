package com.jimweller.cpuscheduler;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.*;

// creating a special panel to display memory stats
// copy and pasted the stats panel code and now modifying it to reflect what I
// actually want from the memory panel
/**
 * A simple panel for showing min/mean/max for any quantifier.
 */
class MemoryPanel extends JPanel{

    final static int width=170,height=80;

    JLabel maxMemoryLabel, maxMemory, freeMemoryLabel, freeMemory, allocatedMemoryLabel,
    allocatedMemory, suspendedProcessCountLabel, suspendedProcessCount;

    MemoryPanel(){
    }

    MemoryPanel(String title){
	TitledBorder tBorder =  BorderFactory.createTitledBorder(title);
	setBorder( tBorder);
	setLayout(new GridLayout(0,2));
	
	maxMemoryLabel = new JLabel("Max Mem");
	maxMemory = new JLabel(""+0);
	
	freeMemoryLabel = new JLabel("Free");
	freeMemory = new JLabel("" + 0);
	
	allocatedMemoryLabel = new JLabel("Taken");
	allocatedMemory = new JLabel(""+0);
	
	suspendedProcessCountLabel = new JLabel("Suspended");
	suspendedProcessCount = new JLabel("" + 0);

	add(maxMemoryLabel);
	add(maxMemory);
	add(freeMemoryLabel);
	add(freeMemory);
	add(allocatedMemoryLabel);
	add(allocatedMemory);
	add(suspendedProcessCountLabel);
	add(suspendedProcessCount);

	setSize(width,height);
	setMinimumSize(new Dimension(width,height));
    }

    
    /**
     * Update the statistic 
     */
    public void setStats(int max, int free, int taken, int count){
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	nf.setGroupingUsed(false);

	maxMemory.setText(Integer.toString(max));
	freeMemory.setText(Integer.toString(free));
	allocatedMemory.setText(Integer.toString(taken));
	suspendedProcessCount.setText(Integer.toString(count));
    }


     public Dimension getMinimumSize(){
 	return new Dimension(width,height);
     }

     public Dimension getPreferredSize(){
 	return new Dimension(width,height);
     }

}


