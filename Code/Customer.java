/**
	Customer class stores customer attributes and manages account balances
*/
public class Customer
{
	private String ownerName, acctNum, passcode;
	private int checkingBalance, savingBalance;
	private final int checking = 1;
	private final int savings = 2;
	private final int withdraw = 1;
	private final int deposit = 2;
	
	/**
		Construct a Customer object
		@param aOwnerName owner's name
		@param aAcctNum account number
		@param aPasscode new passcode
		@param aCheckingBalance initial checking balances
		@param aSavingsBalance initial savings balance
	*/
	public Customer(String aOwnerName, String aAcctNum, String aPasscode, int aCheckingBalance, int aSavingsBalance)
	{
		ownerName = aOwnerName;
		acctNum = aAcctNum;
		passcode = aPasscode;
		checkingBalance = aCheckingBalance;
		savingBalance = aSavingsBalance;
	}
	
	/**
		Get the current checking balance
		@return the current checking balance
	*/
	public int getCheckingBalance()
	{
		return checkingBalance;
	}
	
	/**
		Get the current saving balance
		@return the current savings balance
	*/
	public int getSavingsBalance()
	{
		return savingBalance;
	}
	
	/**
		Updates balance according to passed option (checking/saving), passed mode (withdraw/deposit), and amount
		@param option the desired account (1 for checking, 2 for saving)
		@param mode the desired function (1 for withdraw, 2 for deposit)
		@param amount the desired amount
	*/
	public void updateBalance(int option, int mode, int amount)
	{
		if (option == checking)
			if (mode == withdraw)
				checkingBalance = checkingBalance - amount;
			else
				checkingBalance = checkingBalance + amount;
		else
			if (mode == withdraw)
				savingBalance = savingBalance - amount;
			else
				savingBalance = savingBalance + amount;
	}
	
	/**
		Get the current account number
		@return the current account number
	*/
	public String getAcctNum()
	{
		return acctNum;
	}
	
	/**
		Get the owner's name
		@return owner name from current account
	*/
	public String getOwnerName()
	{
		return ownerName;
	}
	
	/**
		Checks passcode against current passcode
		@param pass the passcode to test
		@return boolean whether they match
	*/
	public boolean passCheck(String pass)
	{
		for (int i = 0; i < 5; i++)
		{
			if (passcode.equals(pass))
				return true;
		}
		return false;
	}
}
