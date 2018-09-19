/**
	Operator class stores the operator password and performs password verification
*/
public class Operator
{
	// Holds password to enter operator mode. abc123 - joke password that should never be used
	private String password;
	
	/**
		Constructs the operator class
	*/
	public Operator()
	{
		password = "abc123";
	}
	
	/**
		Checks if password is correct
		@param enteredPass password to checkPassword
		@return true if passwords match, else false
	*/
	public boolean checkPassword(String enteredPass)
	{
		if (password.equals(enteredPass))
			return true;
		else
			return false;
	}
}
	