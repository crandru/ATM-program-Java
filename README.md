Java ATM Course project - WIP

ATM program consists of 5 classes:
- ATM
- ATMInterface
- BankServer
- Customer
- Operator

ATM will act as the connecting class between ATMInterface and BankServer, using ATMInterface to take input from the user and send responses back, and using BankServer to verify customer/operator information before performing transactions on their accounts.

9/19
- making changes to ATM, ATMInterface, and BankServer prior to upload.
- new class added (ATMDriver) which functions to run the program and reach initial screen
- diagrams may be revised as development progresses

9/20
- Customer class added new getOwnerName method
- Operator class code cleaned up

9/22
- UML Diagrams updated to reflect current status of design
- Old diagrams removed

9/23
- Updated the CRC cards to final design
- Added finalized use case document to show functions of ATM

9/28
- Added the ATMDriver and ATMInterface classes (will reexamine when all files are uploaded)

10/1
- Added the BankServer class which manages objects of the Customer class and creates 5 initial Customers (due to nature of course project)
