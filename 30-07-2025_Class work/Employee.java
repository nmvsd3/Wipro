package com.example.oops;

import java.util.Scanner;

public class Employee {
   
    private int id;
    private String name;
    private double salary;

   
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name.trim(); 
    }

    public void setSalary(double salary) {
        if (salary < 50000) {
            this.salary = salary;
        } else {
            System.out.println("Salary must be below 50000. ");
            this.salary = 0;
        }
    }

   
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    
    public void displayInfo() {
        System.out.println("Employee Information");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
    }

   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee emp = new Employee();

        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Employee Salary: ");
        double salary = scanner.nextDouble();

       
        emp.setId(id);
        emp.setName(name);
        emp.setSalary(salary);

      
        emp.displayInfo();

        scanner.close();
    }
}
