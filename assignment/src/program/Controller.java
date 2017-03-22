package program;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class Controller
{
	public Controller(){}
	
	Scanner kb = new Scanner(System.in);
	
	/*  
	 * 
	 * LUKE MASON
	 * 
	 */
	public boolean addNewEmployee()
	{
		Scanner kb = new Scanner(System.in);
		DatabaseConnection connect = new DatabaseConnection();
		boolean loopAgain;
		String employeeName;
		String employeePayRate;
		double employeePayRate2;
		//Enter in the employees Full name correctly
		do
		{
			loopAgain = false;
			System.out.print("Enter in the new employee's full name [/exit to quit] >> ");
			employeeName = kb.nextLine().toLowerCase();
			//Attempting to see if the input is valid
			//Checking to see if the input contains any non-alphabetical characters e.g ?>!#%$#12345
			if(employeeName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			if(checkInputToContainInvalidChar(employeeName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);
		do
		{
			loopAgain = false;
			System.out.print("Enter in the pay rate of " + employeeName + " [/exit to quit] >> ");
			employeePayRate = kb.nextLine();
			//Attempting to change string into an integer
			//Checking to see if the amount contains any non-digit characters
			if(employeePayRate.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			employeePayRate2 = changeInputIntoValidDouble(employeePayRate);
			if(employeePayRate2<0)
			{
				System.out.println("The amount you have entered contains invalid characters, is less than 0 or greater that 10000 ");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);
		do
		{
			loopAgain = false;
			System.out.println("What would you like to do now?");
			System.out.println("1. Save and Add " + employeeName +"'s working times for the next month");
			System.out.println("2. Save and Exit");
			System.out.println("3. Exit without Saving");
			System.out.print("Select an option >> ");
			int answer = kb.nextInt();
			kb.nextLine();
			switch(answer)
			{
				case 1:
					connect.addEmployee(employeeName, employeePayRate2);
					/*if(!(addWorkingTimeForNextMonth(numberOfEmployees)))
					{
						System.out.println("Exiting to main menu");
						return false;
					}*/
					System.out.println("ADD WORKING TIME FOR THIS EMPLOYEE NOT IMPLEMEMENTED, New Employee added");
					
					return true;
				case 2: connect.addEmployee(employeeName, employeePayRate2); 
							
					return true;
				case 3: 
					do
					{
						loopAgain = false;
						System.out.print("Are you sure you don't want to save the employee to the database?(Y/N)");
						String exit = kb.nextLine();
						if(exit.equalsIgnoreCase("y"))
						{
							
							return false;
						}
						else if(exit.equalsIgnoreCase("n"))
						{
							continue;
						}
						else
						{
							System.out.println("Please enter in \"y\" or \"n\" only");
							loopAgain = true;
						}
					}
					while(loopAgain);
				default: System.out.println("Invalid option, Try again"); 
						 loopAgain = true; 
					 	 break;
			}		
		}
		while(loopAgain);
		System.out.println("Unexpected Error: Please consult the developers");
		
		return false;
	}

	//Checks each letter in the string and see's if the letter is not in the alphabet(lower case) and is not in the alphabet(Upper case)
	//if it is not in either alphabet, then it returns true
	public boolean checkInputToContainInvalidChar(String string)
	{
		if(string.length()==0 || string.length() > 40)
		{
			return true;
		}
		for(int i = 0; i<string.length(); i++)
		{
			if((int)string.charAt(i)< 97 || (int)string.charAt(i)> 122)//checks if the char is not a lower case letter
			{
				if((int)string.charAt(i)< 65 || (int)string.charAt(i)> 90)//checks if the char is not an upper case letter
				{
					if((int)string.charAt(i)!= 32)//checks if the char is not a 'space'
					{
						return true;
					}
				}
			}
		}	
		return false;
	}
	
	
	//Changes the string number into an Double
	//return -1 if the input is a negative number OR if the input contains non-numeric characters except decimal
	//Checks if the input contains more than 1 decimal
	public double changeInputIntoValidDouble(String string) 
	{
		try {
		      Double input = Double.parseDouble(string);
		      //Checking to see if the input is a negative, negatives are not used as inputs in this project
		      if(input < 0 || input > 10000)
		      {
		    	  return -1;
		      }
		      return input;
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public boolean addWorkingTimesForEmployee()
	{
		Scanner kb = new Scanner(System.in);
		DatabaseConnection connect = new DatabaseConnection();
		String employeeName;
		boolean loopAgain;
		do
		{
			loopAgain = false;
			System.out.print("Search - Enter in Employee's name [/exit to quit] >> ");
			employeeName = kb.nextLine().toLowerCase();
			if(employeeName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				return false;
			}
			//Attempting to see if the input is valid
			//Checking to see if the input contains any non-alphabetical characters e.g ?>!#%$#12345
			if(checkInputToContainInvalidChar(employeeName))
			{
				System.out.println("The name you have entered contains non-alphabetical characters");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);
		connect.getEmployees(employeeName);
		/*do
		{
			loopAgain = false;
			System.out.print("Enter in the pay rate of " + employeeName + " [/exit to quit] >> ");
			employeePayRate = kb.nextLine();
			//Attempting to change string into an integer
			//Checking to see if the amount contains any non-digit characters
			if(employeePayRate.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				
				return false;
			}
			employeePayRate2 = changeInputIntoValidDouble(employeePayRate);
			if(employeePayRate2<0)
			{
				System.out.println("The amount you have entered contains invalid characters, is less than 0 or greater that 10000 ");
				System.out.println("Please try again");
				loopAgain = true;
			}
		}
		while(loopAgain);*/
		return false;
	}
}
