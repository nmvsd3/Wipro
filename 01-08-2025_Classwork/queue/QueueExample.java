package com.example.queue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueExample {
	
	public static void main(String[] args) {
	/*
	Queue<String> q = new LinkedList<>(); //In one common queue or a general queue we can enqueue(adding) or dequeue(deleting)
	 q.offer("Madhu");// insersition from back
	 q.offer("N");
	 q.offer("M");
	 q.add("NM");
	 
	 System.out.println("Those who are in queue are gives as :" +q);
	 
	 q.remove();
	 q.poll();//retriving the element and then deletion from the front
	
	 System.out.println("Those who are in queue are gives as :" +q);
	 System.out.println("Those who are in queue are gives as :" +q);
	 
	 System.out.println(q.element());//retriving the element but not removing(so before polling we can retrive) but it 
	 System.out.println(q.size());
	 
	 System.out.println(q.isEmpty());
	 System.out.println(q.peek());//retriving the element but not removing(so before polling we can retrive)
	 
	 //System.out.println(q.element()+1);
	 
	 // so to retrive the element from the middle so wee have to convert into work with the index no's
	 List<String> list = new ArrayList<>(q);
	 System.out.println(list.get(1));
	 
	 
	 //we can get the elemnt using for loop
	 int i =0;
	 int indexno =1;
	 for(String items : q) {
		 if(i == indexno) {
			 System.out.println("Elemnt at index : " + i + "item is :" + items);
			 break;
		 }
		 i++;
	 }
	 */
		Queue<String> q = new ArrayDeque(); //In one common queue or a general queue we can enqueue(adding) or dequeue(deleting)
		 q.offer("Madhu");// insersition from back
		 q.offer("N");
		 q.offer("M");
		 q.add("NM");
		 
		 System.out.println("Those who are in queue are gives as :" +q);
		 
		 q.remove();
		 q.poll();//retriving the element and then deletion from the front
		
		 System.out.println("Those who are in queue are gives as :" +q);
		 System.out.println("Those who are in queue are gives as :" +q);
		 
		 System.out.println(q.element());//retriving the element but not removing(so before polling we can retrive) but it 
		 System.out.println(q.size());
		 
		 System.out.println(q.isEmpty());
		 System.out.println(q.peek());//retriving the element but not removing(so before polling we can retrive)
		 
		 //System.out.println(q.element()+1);
		 
		 // so to retrive the element from the middle so wee have to convert into work with the index no's
		 List<String> list = new ArrayList<>(q);
		 System.out.println(list.get(1));
		 
		 
		 //we can get the elemnt using for loop
		 int i =0;
		 int indexno =1;
		 for(String items : q) {
			 if(i == indexno) {
				 System.out.println("Elemnt at index : " + i + "item is :" + items);
				 break;
			 }
			 i++;
		 }
		 
		 System.out.println("____________Priority Queue Example____________");
		 
		// Queue<Integer> pq = new PriorityQueue <>();
		 //Queue<Integer> pq = new LinkedList <>(); // ordinary queue
		 Queue<Integer> pq = new PriorityQueue<>(); // priority Queue (sorted Queue)
		 
		 pq.offer(50);
		 pq.offer(70);
		 pq.offer(100);
		 
		 System.out.println("The elements" + pq);
		 
		 System.out.println(pq.poll());//It is lowest one // Scheduling
		 
		 //create a patient class (name,condition ,priority) and add in a priorityqueue using 1 , 2 or 3
	 
	 
	 
	 
	 
	 
	 
	 
	}
	
}
