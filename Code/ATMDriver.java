import java.util.Scanner;
/**
	Driver class to set up ATM and reach first stage of user interface
*/
public class ATMDriver
{
	public static void main(String[] args)
	{
		//set up ATM system
		Scanner scan = new Scanner(System.in);
		ATMInterface i = new ATMInterface(scan);
		ATM a = new ATM(i);
		//begin ATM interface instance
		i.process(a);
		int state;
		//preserves accounts and balances until program is exited
		i.printOutput("Enter 1 to continue with ATM state or 2 to quit program");
		state = scan.nextInt();
		while (state != 2)
		{
			i.process(a);
			i.printOutput("Enter 1 to continue with ATM state or 2 to quit program");
			state = scan.nextInt();
		}
	}
}