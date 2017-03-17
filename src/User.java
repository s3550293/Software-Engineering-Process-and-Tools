
public class User
{
	/*
	 * Protected values used by child classes
	 */
	protected int _id;
	protected String _username, _password;
	protected boolean _accountType;
	public User(int id, String username, String password, boolean accountType)
	{
		_id = id;
		_username = username;
		_password = password;
		_accountType = accountType;
	}
	public User(){}
	
	public String toString()
	{
		return _username + " " + _password + " " + _accountType;
	}
}
