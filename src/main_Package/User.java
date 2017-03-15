package main_Package;

public class User
{
	/*
	 * Protected values used by child classes
	 */
	protected String _username, _password;
	protected boolean _accountType;
	public User(String username, String password, boolean accountType)
	{
		_username = username;
		_password = password;
		_accountType = accountType;
	}
	public User(){}
}
