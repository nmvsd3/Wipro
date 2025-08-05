package com.example.creationaldesignpattern;


// Where you need one object we can use the same for centralizing the things we are logging all the messages

class Logger
{
	private static Logger instance;
	
//	Logger l = new Logger(); // Eager Initialization
	
	private Logger() {} // which is private

	public static Logger getInstance()
	{
		if(instance == null)
		{
			
		instance = 	new Logger(); // Lazy initialization  when we want then the object  will be created
		}
		
		return instance;
	}
	
	public void log(String message)
	{
		System.out.println(" Log message is :" + message);
	}
}











public class SingletonExample {
	
	public static void main(String[] args) {
		Logger log1 = Logger.getInstance(); //We asked for an instance using getInstance()
		
		Logger log2 = Logger.getInstance();
		Logger log3 = Logger.getInstance();
		
		log1.log("User has logged in on this so an so date ....");
		log2.log("Order has been placed on this date :");
		
			System.out.println(" is the instance same :"  + ((log1 == log2) && (log2 == log3)));
	}

}
