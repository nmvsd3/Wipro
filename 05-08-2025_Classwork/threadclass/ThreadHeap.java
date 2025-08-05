package com.example.threadclass;

//Example of stack and heap memory allocation where for each thread stack will allocated individually and heap will be shared

class SharedData
{
 int sharedValue =0;  // which will be in heap 	
}

 class UsingThreadHeap extends Thread  {

	private final SharedData data;
	
	public UsingThreadHeap(SharedData data )
	{
		//super(name);
	this.data = data;
	}
	
	public void run()
	{
		int localValue = 0 ; // this is a local variable  -- created in each thread's a different memory stack
		
		//String threadName = Thread.currentThread().getName();
		
		for(int i=0 ; i<=3 ; i++)
		{
			localValue++;
			data.sharedValue++;
			
			System.out.println( Thread.currentThread().getName() +  " at i :" + i + " the localValue is :" + localValue + " and sharedValue is from :" +  data.sharedValue);
		}
		
		
	}
	
}
public class ThreadHeap 
{
	public static void main(String[] args) throws InterruptedException {
		SharedData data = new SharedData(); // shared object and instance variable is stored in Heap
		
		
		UsingThreadHeap t1 = new UsingThreadHeap(data);  // same heap memory but different Stack area
		t1.setName("Thread 1");
		UsingThreadHeap t2 = new UsingThreadHeap(data );

		t1.start();	// will call run method 
		t2.setName("Thread 2");
		t2.start();
		
	
		
		
		}
	
	
}
