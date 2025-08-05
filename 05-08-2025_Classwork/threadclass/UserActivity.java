package com.example.threadclass;


//WAP to  create two users user-a  and user-b to perform some task (FileUpload)( they both are thread thread-A and thread-B  )
//Main thread will wait for both users before showing the task is completed of user-a and user b

class UploadFile extends Thread{
	String username;
	
	public UploadFile(String username) {
		this.username = username;
	}
	
	public void run() {
		System.out.println(username + " upload file");
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(username + " uploaded file");
	}
}

public class UserActivity {
	public static void main(String[] args) throws InterruptedException {
		UploadFile threadA = new UploadFile("Veena");
		UploadFile threadB = new UploadFile("Sourav");
		
		threadA.start();
		threadA.join();
		threadB.start();
		threadB.join();
		
//		try {
//			threadA.join();
//			threadB.join();
//		}
//		catch(InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("Main Task completed");
	}
}