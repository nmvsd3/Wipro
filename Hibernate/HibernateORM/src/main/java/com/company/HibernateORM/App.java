package com.company.HibernateORM;



import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.company.HibernateORM.Util.HibernateUtil;
import com.company.HibernateORM.model.Student;



/**
 * Hello world!
 *
 */
public class App 
{
	
	
	@SuppressWarnings("removal")
	public static void main( String[] args )
    {
		// to get the session from sessionfactory (It can be multiple session based on multiple services or work)
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		// To create a  transaction ( Transient + Persistent) -- Transient when you do not to reflect and save in backend  but persistent to save into a backend
	   Transaction tx = session.beginTransaction();
	   Student student = null;
	   Scanner sc = new Scanner(System.in);
       System.out.println("Enter Student Name :");
       String name = student.setName(sc.next());
       System.out.println("Enter Student Email Id :");
       String emailid =  student.setEmail(sc.next());
       System.out.println("Enter the Course Name :");
       String coursename = student.setCourse(sc.next());
      student = new Student(name,emailid,coursename);
	   
		session.persist(student); // we are persisting the data from transient (by default it is transient)
		
		Student data = session.get(Student.class,student.getId()); // retreiving the data from session
		System.out.println("Data id : " + data); // displaying on a console
	
		tx.commit(); // Here it will added finally to the database after committed
		
    }
}
