
/**
	BankServer manages and initializes 5 customer accounts 
*/
public class BankServer
{
	//Initializing 5 hard coded accounts
	private Customer c1 = new Customer("Bob", "1001", "1111", 100000, 200);
	private Customer c2 = new Customer("Jill", "1002", "2222", 500, 100);
	private Customer c3 = new Customer("Steve", "1003", "3333", 1000, 0);
	private Customer c4 = new Customer("Marshal", "1004", "4444", 300, 300);
	private Customer c5 = new Customer("Kagura", "1005", "5555", 450, 450);
	private Customer current;
	
	//Creating array to hold customers
	Customer[] c = {c1, c2, c3, c4, c5};
	
	/**
		Checks a passed account number to see if it matches a current account
		@param account the account number entered
		@return boolean det. whether the account was found
	*/
	public boolean findAccount(String account)
	{
		for (int i = 0; i < 5; i++)
		{
			if (account.equals(c[i].getAcctNum()))
			{
				current = c[i];
				return true;
			}
		}
		return false;
	}
	
	/**
		Checks a passed passcode securely through a Customer method to see if it matches
		@param passcode the passcode to check
		@return boolean det. whether the passcode matched
	*/
	public boolean checkPass(String passcode)
	{
		for (int i = 0; i < 5; i++)
		{
			if (current.passCheck(passcode))
				return true;
		}
		return false;
	}
	
	/**
		Passes a customer to the ATM once credentials have been verified
		@return Customer the current customer
	*/
	public Customer getCustomer()
	{
		return current;
	}
	
	/**
		Performs a transfer to the designated user account from the current account.
		@param c current customer
		@param amount the amount to transfer
		@param type the account type
		@return String transfer confirmation
	*/
	public String transfer(Customer c, int amount, int type)
	{
		c.updateBalance(type, 1, amount);
		current.updateBalance(type, 2, amount);
		if (type == 1)
			return ("You have transferred $" + amount + ", your account balance is now $" + c.getCheckingBalance());
		else
			return ("You have transferred $" + amount + ", your account balance is now $" + c.getSavingsBalance());
	}
}