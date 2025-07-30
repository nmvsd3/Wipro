package com.example;

//class cannot be declared as private or protected

final class ConstantVariables{
	
	public static final double gst = 0.18;
	public static final double pi =3.14;
	public static final int bonus = 1000;
	
	
	public static final  String companyName = "Wipro";
	
}
class Salary{  // default is the access specifier for a class when it is not declared
	
	//@Deprecated()
	public final double salary(double basicPay) {
		
		
		return basicPay*ConstantVariables.gst;
		
	}
}

//i want to intialize the Blank Final variable per object creation
 final class User{
	
	private final int id ; //blank final variables -- These non static blank final variables must be intilized in constructor
	private final String name;
	private static final int count;//blan static final variables must be intialized in static block
	
	
	public User(int id , String name) {
		this.id = id;
		this.name = name;
	}
static {
	count=1;
}
	
	
}

public class FinalKeywordExample  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Salary s =new Salary();
		//s.salary(40000);
		System.out.println(s.salary(40000));
		double sal = s.salary(40000);
		System.out.println("The Calcualted salary is : " + sal);

	}

}



//Use cases : final variables - In malls for discount , In banks for interest (EMI cal) , Maths computations , in company Id cards
//			  final  class -  grades in a final class , in google pay to chceck the payments


//so user class is immutable

/* public final class User
 {
 private final int id;
 private final string name;
 
 private User(int id , String name){
 
 this.id=id;
 this.name=name;
 }
 public int getId()
 {
 return id;
 }
 public int getName()
{
return id;
}
  
  }
 */

/*
 * public final class SecurityValidation
 * {
 * public void login(string user)
 * 
 *  System.out.println(user + "has logged in")
 *  {
 *  //below method cannot be overeidden or extended
 *  public void validationToken(string token)
 *  //logic regarding token validation
 *  
 *  }
 *  
 */

