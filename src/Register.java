

import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class Register 
{
	private String _Testusername;
	private char _testAccount;
	private boolean usernameLoop = true;
	private boolean accountTypeLoop = true;
	private DatabaseConnection connect = new DatabaseConnection();
	
	public Register(){}
	
	
	public void registerUser()
	{
		boolean flag = true;
		String _username = "";
		String _password = "";
		char _choice = 'y';
		boolean accountType = true;
		Scanner userInput = new Scanner(System.in);
		while(flag)
		{
			boolean flag_2 = true;
			System.out.printf("\n%-1s %s\n", "", "Register");
			System.out.printf("%s\n","---------------------------");
			//Checks if username already exsists
			while(usernameLoop)
			{
				System.out.print("Enter username: ");
				_username = userInput.nextLine();
				checkTakenUsername(_username);
			}
			System.out.print("Enter password: ");
			_password = userInput.nextLine();
			System.out.print("are you a business owner [y/n]: ");
			_choice = userInput.next().charAt(0);
			//set the account type will loop until customer enters the correct 
			while(accountTypeLoop)
			{
				System.out.print("are you a business owner [y/n]: ");
				_choice = userInput.next().charAt(0);;
				accountType = setAccountType(_choice);
			}
			System.out.printf("\n%-1s %s\n", "", "Confirm");
			String _acc;
			//create a string that can be printed to display account type
			if(accountType = false){ _acc = "Business"; }else{ _acc = "Customer"; }
			System.out.println("Username: "+_username+" Password: "+_password+" Account Type: "+_acc);
			System.out.print("[y/n]");
			_choice = userInput.next().charAt(0);
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
	
	@Before
	public void setupCheckTakenUsernameTrue()
	{
		_Testusername = "apple10";
	}
	@Before
	public void setupCheckTakenUsernameFalse()
	{
		_Testusername = "Simba01";
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
	@Test
	public void testCheckTakenUsernameTrue()
	{
		assertTrue("Username was uniqe", checkTakenUsername(_Testusername));
	}
	@Test
	public void testCheckTakenUsernameFalse()
	{
		assertFalse("Username was not uniqe", checkTakenUsername(_Testusername));
	}
	
	@Before
	public void setupSetAccountTypeBusi()
	{
		_testAccount = 'y';
	}
	@Before
	public void setupSetAccountTypeCust()
	{
		_testAccount = 'n';
	}
	//This method grabs the inital user input and selects either customer or business owner as the account type
	public Boolean setAccountType(char account)
	{
		if(account == 'y' || account == 'Y')
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	@Test
	public void testsetAccountTypeBusiness()
	{
		assertFalse("Account type business", setAccountType(_testAccount));
	}
	@Test
	public void testsetAccountTypeCustomer()
	{
		assertTrue("Account type customer", setAccountType(_testAccount));
	}
	
}
