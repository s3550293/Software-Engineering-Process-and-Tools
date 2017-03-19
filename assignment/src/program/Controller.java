package program;

import static org.junit.Assert.assertEquals;
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
		boolean loopAgain;
		String employeeName;
		String employeePayRate;
		int employeePayRate2;
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
				System.out.println("The name you have entered contains non-alphatical characters");
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
			if(employeeName.equalsIgnoreCase("/exit"))
			{
				System.out.println("Exitting to main menu...");
				return false;
			}
			employeePayRate2 = changeInputIntoValidInteger(employeePayRate);
			if(employeePayRate2<0)
			{
				System.out.println("The amount you have entered contains invalid characters, or it is less than 0");
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
					addEmployeeToDatabase(returnNextEmployeeId(),employeeName, employeePayRate2);
					if(!(addWorkingTimeForNextMonth(returnNextEmployeeId())))
					{
						System.out.println("Exitting to main menu");
						return false;
					}
					return true;
				case 2: addEmployeeToDatabase(returnNextEmployeeId(),employeeName, employeePayRate2); 
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
	
	
	//Changes the string number into an integer
	//return -1 if the input is a negative number OR if the input contains non-numeric characters
	public int changeInputIntoValidInteger(String string) 
	{
		try {
		      Integer input = Integer.parseInt(string);
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

	
	//Counts how many employees there currently are in the database and generates 
	//the next ID according to that number e.g 15 current employees, next id = "00016"
	public String returnNextEmployeeId()
	{
		/* SELECT COUNT(employeeID) FROM employee; 
		 */
		// int amountOfEmployees;
		String id = "";
		return id;
	}
	
	public void addEmployeeToDatabase(String id, String name, int payRate)
	{
		String insertSql = "INSERT INTO employee VALUES ('" + id + "','" + name + "'," + payRate + ");";
	}
	
	public boolean addWorkingTimeForNextMonth(String Id)
	{
		return false;
	}
	
	
	

	
	
	@Before
	public void setUp()
	{

	}
	@Test
	public void testCheckInputToContainNonAlphabetChar() 
	{
		assertFalse(checkInputToContainInvalidChar("Luke Mason"));
		assertFalse(checkInputToContainInvalidChar("LukeyyyMason"));
		assertTrue(checkInputToContainInvalidChar(""));
		assertTrue(checkInputToContainInvalidChar("1010101LUKE"));
		assertTrue(checkInputToContainInvalidChar("LUKEEEEEEEEEEEEEEEEEEEEEEEEEEE                                        "));
		assertTrue(checkInputToContainInvalidChar("luke%@#$"));
	}
	/*
	@Test
	public void testEmpID() throws SQLException{
		
		assertFalse(employeeIDCheck(123));
		assertTrue(employeeIDCheck(223));
	}
	
	@Test
	public void testChangeInputIntoValidInteger() 
	{
		assertEquals(-1,changeInputIntoValidInteger(""));
		assertEquals(-1,changeInputIntoValidInteger("lel"));
		assertEquals(-1,changeInputIntoValidInteger("$"));
		assertEquals(-1,changeInputIntoValidInteger("100$"));
		assertEquals(-1,changeInputIntoValidInteger("-1"));
		assertEquals(-1,changeInputIntoValidInteger("10001"));
		assertEquals(10000,changeInputIntoValidInteger("10000"));
		assertEquals(10,changeInputIntoValidInteger("10"));
		assertEquals(0,changeInputIntoValidInteger("0"));
	}
	*/

}
