# ATM-GUI
This is a GUI screen for an ATM that performs different actions:

1- Getting Stated Process

● Have a startup GUI: Displaying a menu like the one that appears on an actual ATM after inserting a card, along with the keypad

● Allow an ATM user to login using their unique username and 6-digit PIN

● Make sure the PIN digits are masked

● Authenticate user and print an error message in case the user entered a wrong
combination of username and PIN

● Keep track of unsuccessful login attempts per user

● Allow user only 3 login attempts

● Store user account information in a CSV file, i.e account number, username, user PIN, available balance, list of transactions (can’t save the file in a data structure inside your program)

2- ATM Functions

● Allow user to check the balance

● Allow user to withdraw money

● Allow user to change User PIN

● Allow user to deposit money

● Allow user to transfer money to another account (using username)

● Ask the user if they want to go green or print a transaction receipt

● Print a transaction receipt (no need for integration with printer)

● Allow user to logout (don’t terminate the program, just kickstart the authentication screen again)

● Store transaction logs in a text file, for each transaction

● Every transaction log must have sufficient information about the transaction, but it should not include the full account number of the user, nor their
