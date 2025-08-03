package com.example.queue;

public class Patient implements Comparable<Patient> {
    private String name;
    private String condition;
    private int priority; 

    public Patient(String name, String condition, int priority) {
        this.name = name;
        this.condition = condition;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public int getPriority() {
        return priority;
    }

    // For sorting patients: lower priority number means higher priority
    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "Patient{name='" + name + "', condition='" + condition + "', priority=" + priority + "}";
    }
}
