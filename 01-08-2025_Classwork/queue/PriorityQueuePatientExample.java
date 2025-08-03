package com.example.queue;

import java.util.PriorityQueue;

public class PriorityQueuePatientExample {

    public static void main(String[] args) {

        // Creating a priority queue of Patient objects
        PriorityQueue<Patient> queue = new PriorityQueue<>();

        // Adding patients to the queue
        queue.offer(new Patient("Madhu", "Fracture", 2));
        queue.offer(new Patient("Varma", "Heart Attack", 1));
        queue.offer(new Patient("Narasimha", "Fever", 3));

        System.out.println("Patients will be treated in this order (based on priority):");
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
