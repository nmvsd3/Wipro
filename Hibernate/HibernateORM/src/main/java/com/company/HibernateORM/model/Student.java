package com.company.HibernateORM.model;


import jakarta.persistence.*;

@Entity
@Table(name="StudentTable")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  
    private String name;
    private String email;
    private String course;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String setName(String name) { return this.name = name; }

    public String getEmail() { return email; }
    public String setEmail(String email) { return this.email = email; }

    public String getCourse() { return course; }
    public String setCourse(String course) { return this.course = course; }
	public Student(int id, String name, String email, String course) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.course = course;
	}
	
	public Student(String name, String email, String course) {
		super();
		
		this.name = name;
		this.email = email;
		this.course = course;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", email=" + email + ", course=" + course + "]";
	}
    
    
    
}


