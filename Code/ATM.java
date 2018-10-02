/**
	Provides connective functionality between the interface and bank system, and processes the input passed by the interface. Also manages bill reserves.
*/
public class ATM
{
	private BankServer bank;
	private ATMInterface interf;
	private Operator oper;
	private int hundreds;
	private int fifties;
	private int twenties;
	private int fives;
	private final int checking;
	private final int savings;
	
	/**
		Constructs an ATM object.
		@param interf instance of the interface
	*/
	public ATM(ATMInterface aInterf)
	{
		bank = new BankServer();
		oper = new Operator();
		interf = aInterf;
		hundreds = fifties = twenties = fives = 100;
		checking = 1;
		savings = 2;
	}
	
	/**
		Checks passed user account against those stored in bankserver
		@param account the account # entered by user
		@return boolean if matches
	*/
	public boolean checkUser(String account)
	{
		return bank.findAccount(account);
	}
	
	/**
		Checks passed passcode against matching account in bankserver
		@param passcode the passcode entered by user
		@return boolean if matches
	*/
	public boolean checkP(String passcode)
	{
		return bank.checkPass(passcode);
	}
	
	/**
		Checks passed operator password against record
		@param password the operator password entered by user
		@return boolean if matches
	*/
	public boolean checkOper(String password)
	{
		return oper.checkPassword(password);
	}
	
	/**
		Processor that deals with customer interface
	*/
	public void custProc()
	{
		int input;
		boolean persist = true;
		//establish persistent customer after user/pass confirmed in interface
		Customer c = bank.getCustomer();
		interf.printOutput("Greetings " + c.getOwnerName() +
				  "\nCheckings Balance: $" + c.getCheckingBalance() +
				  "\nSavings Balance: $" + c.getSavingsBalance() +
				  "\nEnter 1 to Withdraw Money\nEnter 2 to Deposit Money" +
				  "\nEnter 3 to Transfer Money\nEnter 4 to Exit");
		input = interf.custInput();
		//loops until valid option selected
		while (persist)
		{
			persist = false;
			switch (input) {
				//withdraw
				case 1: 
					transact(c, 1);
					break;
				//deposit
				case 2: 
					transact(c, 2);
					break;
				//transfer
				case 3: 
					transfer(c);
					break;
				//exit
				case 4: 
					interf.printOutput("Thanks for using our ATM, goodbye");
					break;
				default: 
					interf.printOutput("Invalid option selected, please enter 1 through 4");
					input = interf.custInput();
					persist = true;
					break;
			}
		}
	}
	
	/**
		Processor for a transaction phase of ATM
		@param c customer account
		@param mode indicates dep or withd
	*/
	private void transact(Customer c, int mode)
	{
		int input;
		int accType = checking;
		boolean persist = true;
		interf.printOutput("Please select the account: \nEnter 1 for Checking\nEnter 2 for Savings");
		input = interf.custInput();
		//loops until valid account type entered
		while (persist)
		{
			persist = false;
			switch (input) {
				case 1:
					accType = checking;
					break;
				case 2:
					accType = savings;
					break;
				default:
					interf.printOutput("Invalid option, please reenter");
					input = interf.custInput();
					persist = true;
			}
		}
		//uses passed mode to determine if withdraw or deposit (1 or 2)
		if (mode == 1)
		{
			interf.printOutput("Enter the amount you would like to withdraw as a whole number");
			input = interf.custInput();
			if (accType == checking)
			{
				//verifies user is withdrawing less or equal to checking balance
				while (c.getCheckingBalance() < input)
				{
					interf.printOutput("You do not have enough funds for this withdrawal, please reenter");
					input = interf.custInput();
				}
				//updates bill reserves in ATM if transaction successful
				if (updateBills(input))
				{
					c.updateBalance(accType, 1, input);
					interf.printOutput("Your new account balance is: $" + c.getCheckingBalance()
						+ ", please remove money from slot.");
				}
				else
				{
					interf.printOutput("Insufficient reserves in ATM, please consult a branch");	
				}
			}
			else
			{
				//verifies user is withdrawing less or equal to savings balance
				while (c.getSavingsBalance() < input)
				{
					interf.printOutput("You do not have enough funds for this withdrawal, please reenter");
					input = interf.custInput();
				}
				if (updateBills(input))
				{
					c.updateBalance(accType, 1, input);
					interf.printOutput("Your new account balance is: $" + c.getSavingsBalance()
						+ ", please remove money from slot.");
				}
				else
				{
					interf.printOutput("Insufficient reserves in ATM, please consult a branch");
				}
			}
		}
		//deposit prompts user to enter bills as ATM is virtual
		else
		{
			int hundred, fifty, twenty, five, sum = 0;
			interf.printOutput("Please enter the amount of each bill as prompted:");
			interf.printOutput("Hundreds: ");
			hundred = interf.custInput();
			interf.printOutput("Fifty: ");
			fifty = interf.custInput();
			interf.printOutput("Twenty: ");
			twenty = interf.custInput();
			interf.printOutput("Five: ");
			five = interf.custInput();
			//updates ATM store of bills
			hundreds += hundred;
			fifties += fifty;
			twenties += twenty;
			fives += five;
			interf.printOutput("Please place tender inside the open slot");
			hundred = hundred * 100;
			fifty = fifty * 50;
			twenty = twenty * 20;
			five = five * 5;
			sum = hundred + fifty + twenty + five;
			c.updateBalance(accType, 2, sum);
			if (accType == 1)
				interf.printOutput("You have deposited $" + sum
					+ ", your account balance is now $" + c.getCheckingBalance());
			else
				interf.printOutput("You have deposited $" + sum
					+ ", your account balance is now $" + c.getSavingsBalance());
		}
		interf.printOutput("Thank you for using our ATM, goodbye.");
	}
	
	/**
		Processor for a transfer
		@param c current customer account
	*/
	public void transfer(Customer c)
	{
		int input;
		int accType = 0;
		boolean persist = true;
		interf.printOutput("Please enter the account owner you wish to transfer to:");
		input = interf.custInput();
		//determines if entered account exists
		if(bank.findAccount(""+input))
		{
			interf.printOutput("Please select the account: \nEnter 1 for Checking\nEnter 2 for Savings");
			input = interf.custInput();
			//check loop for account type
			while (persist)
			{
				persist = false;
				switch (input) {
					case 1:
						accType = checking;
						break;
					case 2:
						accType = savings;
						break;
					default:
						interf.printOutput("Invalid option, please reenter");
						input = interf.custInput();
						persist = true;
				}
			}
			interf.printOutput("Enter the amount you would like to transfer as a whole number");
			input = interf.custInput();
			if (accType == checking)
			{
				while (c.getCheckingBalance() < input)
				{
					interf.printOutput("You do not have enough funds for this transfer, please reenter");
					input = interf.custInput();
				}
				interf.printOutput(bank.transfer(c, input, accType)); 
			}
			else
			{
				while (c.getSavingsBalance() < input)
				{
					interf.printOutput("You do not have enough funds for this transfer, please reenter");
					input = interf.custInput();
				}
				interf.printOutput(bank.transfer(c, input, accType)); 
			}
		}
	}
	
	/**
		Updates bill count in the ATM according to passed amount
		@param amount the withdrawal amount
		@return boolean flag to determine success
	*/
	public boolean updateBills(int amount)
	{
		//gets current atm balance in dollars
		int balance = (hundreds * 100) + (fifties * 50) + (twenties * 20) + (fives * 5);
		//checks that ATM contains more than requested amount
		if (balance < amount)
			return false;
		//determines the amount of each bill to remove to reach amount
		int runningAmt = 0;
		int billAmt = 0;
		billAmt = amount / 100;
		while (hundreds > 0 && billAmt != 0)
		{
			hundreds--;
			billAmt--;
		}
		runningAmt = (amount % 100) + (billAmt * 100);
		billAmt = runningAmt / 50;
		while (fifties > 0 && billAmt != 0)
		{
			fifties--;
			billAmt--;
		}
		runningAmt = (amount % 50) + (billAmt * 50);
		billAmt = runningAmt / 20;
		while (twenties > 0 && billAmt != 0)
		{
			twenties--;
			billAmt--;
		}
		runningAmt = (amount % 20) + (billAmt * 20);
		billAmt = runningAmt / 5;
		while (fives > 0 && billAmt != 0)
		{
			fives--;
			billAmt--;
		}
		return true;
	}
	
	/**
		Processor for operator phase of ATM
	*/
	public void operMode()
	{
		int input = 0;
		boolean persist = true;
		interf.printOutput("Welcome to operator mode, please select from the following options:"
						+"\nEnter 1 to fill ATM\nEnter 2 to remove from ATM\nEnter 3 to exit Operator mode.");
		input = interf.operProc();
		//check loop for operator menu selection input
		while (persist)
		{
			persist = false;
			switch (input) {
				case 1:
					int hundred, fifty, twenty, five, sum = 0;
					interf.printOutput("Please enter the amount of each bill as prompted:");
					interf.printOutput("Hundreds: ");
					hundreds += interf.operProc();
					interf.printOutput("Fifty: ");
					fifties += interf.operProc();
					interf.printOutput("Twenty: ");
					twenties += interf.operProc();
					interf.printOutput("Five: ");
					fives += interf.operProc();
					break;
				case 2:
					interf.printOutput("Enter amount to remove in dollars");
					input = interf.operProc();
					if (updateBills(input))
						interf.printOutput("Remove stack from ATM");
					else
					{
						int balance = (hundreds * 100) + (fifties * 50) + (twenties * 20) + (fives * 5);
						boolean remPlaceholder = updateBills(balance);
						interf.printOutput("Amount exceeded ATM reserves, $" + balance + " was removed instead.");
					}
					break;
				case 3:
					interf.printOutput("Exiting Operator mode");
					break;
				default:
					interf.printOutput("Invalid option, enter 1 through 3");
					persist = true;
					break;
			}
		}
	}
}