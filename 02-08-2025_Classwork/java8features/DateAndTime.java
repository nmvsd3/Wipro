package com.example.java8features;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAndTime {
	
	public static void main(String[] args) {
		
		LocalDate today = LocalDate.now();
		LocalDate nextWeek =  today.plusWeeks(1);
		
		System.out.println(today + " " +  nextWeek);
		
		// Formatting
		
		DateTimeFormatter ft = DateTimeFormatter.ofPattern("dd MM yyyy");
		System.out.println("Today Date : " + today.format(ft));
		System.out.println("Next Week : " + nextWeek.format(ft));
		
		LocalDate parsing  = LocalDate.parse("02 08 2025" ,ft);
		
		System.out.println("After Parsing : " + parsing);
	}

}
