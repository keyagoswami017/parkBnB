package edu.neu.csye6200.parkingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Rohan
 * 
 */

@SpringBootApplication
public class Driver {
	public static void main(String[] args) {
		System.out.println("============Main Execution Start===================\n\n");

		SpringApplication.run(Driver.class, args);
		 
		System.out.println("\n\n============Main Execution End===================");
	}

}
