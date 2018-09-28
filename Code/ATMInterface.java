import java.util.Scanner;

/**
	An ATM interface which reads input from the user and displays appropriate output
*/
public class ATMInterface
{
	private Scanner scan;
	/**
		Construct an interface object.
		@param scan scanner object to read input
	*/
	public ATMInterface(Scanner aScan)
	{
		scan = aScan;
	}
	
	/**
		Displays information to the user
		@param output the desired information to output
	*/
	public void printOutput(String output)
	{
		System.out.println(output);
	}
	
	/**
		Initial processor that verifies account before passing control to ATM
		@param ATM the ATM instance for the interface to pass to
		@return ATM passes state of ATM for next phase
	*/
	public void process(ATM a)
	{
		String input = "";
		//Initial ATM prompt on every boot
		printOutput("Hello, please enter the Account # followed by (*)");
		boolean persist = true;
		while (persist)
		{
			input += scan.nextLine();
			if (input.indexOf('*') >= 0 || input.indexOf('#') >= 0)
			{	
				if (input.indexOf('*') >= 0)
				{
					input = input.replaceAll("\\*","");
					if (a.checkUser(input))
					{
						input = "";
						printOutput("Please enter the passcode on one line followed by (*)");
						input = scan.nextLine();
						input = input.replaceAll("\\*","");
						if (a.checkP(input))
						{
							persist = false;
							a.custProc();
						}
						else
						{
							printOutput("Invalid passcode, please follow prompts to reenter account and passcode");
							input = "";
							printOutput("Please enter the Account # followed by (*)");
						}
					}
					else
					{
						input = "";
						printOutput("Invalid account, please reenter #");
					}
				}
				else
				{	
					input = input.replaceAll("#","");
					if (a.checkOper(input))
					{
						persist = false;
						a.operMode();
					}
					else
					{
						input = "";
						printOutput("Invalid account, please reenter #");
					}
				}
			}
		}
	}
	
	/**
		Processor for calls from ATM customer phase
		@return int containing user input
	*/
	public int custInput()
	{
		int input = scan.nextInt();
		return input;
	}
	
	/**
		Processor for calls from the ATM operator phase
	*/
	public int operProc()
	{
		int input = scan.nextInt();
		return input;
	}
}