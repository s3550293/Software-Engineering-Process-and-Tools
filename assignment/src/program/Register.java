package program;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class Register 
{
	private static Logger log = Logger.getLogger(Register.class);
	private boolean usernameLoop = true;
	private boolean accountPasswordLoop = true;
	private DatabaseConnection connect = new DatabaseConnection("company.db");
	
	public Register(){}
	
	
	public void registerUser()
	{
		boolean flag = true;
		String _username = "";
		String _password = "";
		char _choice = 'y';
		int accountType = 0;
		@SuppressWarnings("resource")
		Scanner userInput = new Scanner(System.in);
		while(flag)
		{
			boolean flag_2 = true;
			System.out.printf("\n%-1s %s\n", "", "Register");
			System.out.printf("%s\n","---------------------------");
			//Checks if username already exsists
			do
			{
				System.out.print("Enter username: ");
				_username = userInput.nextLine();
				checkTakenUsername(_username);
			}
			while(usernameLoop);
			do
			{
				System.out.print("Enter password: ");
				_password = userInput.nextLine();
				if(checkPassword(_password) == true)
				{
					accountPasswordLoop = false;
				}
			}
			while(accountPasswordLoop);
			System.out.println("Confirm");
			String _acc;
			//create a string that can be printed to display account type
			if(accountType == 1){ _acc = "Business"; }else{ _acc = "Customer"; }
			System.out.println("Username: "+_username+" Password: "+_password+" Account Type: "+_acc);
			System.out.print("[y/n]");
			_choice = userInput.next().charAt(0);
			userInput.nextLine();
			//comfirming
			while(flag_2)
			{
				switch(_choice)
				{
					case 'y':
					case 'Y':
						flag = false;
						connect.addUser(_username, _password, accountType);
						System.out.println("Account created!");
						flag_2 = false;
						flag = false;
						break;
					case 'n':
					case 'N':
						//do nothing, let loop;
						break;
					default:
						System.out.print("Error");
						break;
						
				}
			}
		}
	}
	
	//This method checks if the username is equal to one already in the database
	public boolean checkTakenUsername(String username)
	{
		boolean output = true;
		if(!username.equalsIgnoreCase(connect.getUser(username).getUsername()))
		{
			usernameLoop = false;
			output = false;
		}
		else
		{
			System.out.println("Username Taken please enter another username");
		}
		return output;
	}
	//Checks the length of the password
	public boolean checkPassword(String _password)
	{
		boolean upper=false;
		boolean lower=false;
		boolean number=false;
		for (char c : _password.toCharArray()) {
		      if (Character.isUpperCase(c)) {
		        upper = true;
		      } else if (Character.isLowerCase(c)) {
		        lower = true;
		      } else if (Character.isDigit(c)) {
		        number = true;
		      }
		    }
		int length = _password.length();
		if(length >= 6 && upper && lower && number)
		{
			log.debug("Upper = "+upper+" Lower = "+lower+" Number = "+number+" Password = "+_password);
			return true;
		}
		else if(length < 6)
		{
			System.out.println("Password is too short please enter a shorter password");
			log.debug("Upper = "+upper+" Lower = "+lower+" Number = "+number+" Password = "+_password);
			return false;
		}
		else if(!upper)
		{
			System.out.println("Password must contain Uppercase characters");
			log.debug("Upper = "+upper+" Lower = "+lower+" Number = "+number+" Password = "+_password);
			return false;
		}
		else if(!lower)
		{
			System.out.println("Password must contain lowercase characters");
			log.debug("Upper = "+upper+" Lower = "+lower+" Number = "+number+" Password = "+_password);
			return false;
		}
		else if(!number)
		{
			System.out.println("Password must contain a number");
			log.debug("Upper = "+upper+" Lower = "+lower+" Number = "+number+" Password = "+_password);
			return false;
		}
		else
		{ 
			System.out.println("Password must contain Uppercase, lowercase and a number");
			log.debug("Upper = "+upper+" Lower = "+lower+" Number = "+number+" Password = "+_password);
			return false; 
		}
	}
}
