The AutoSub program uses a series of modules to accomplish its task.

These can be broken up into the following:
Customer GUI
Manager GUI
Staff GUI
Server

Each of the GUI components is contained within a single project and can be
selected after running the main program from the selector window.

The server program must be running for the client program to work. This is
a separate program that runs independently from the client process. The server also requires the rmi registry process to be run.

Instructions of use:

For the server on linux, run "rmi registry 8080 &" in the terminal
Then run the server program. Only one instance needs to be run, but it must be run for the client tools to work.

Next, run the AutoSub application and select which GUI to use, manager, staff, or customer. Each of these performs a different function.

To shut down the system, quit the client processes, then shut down the server application, and finally kill the rmi registry process.

Server:
As of this release, there is no way to change the location of the server and it
must be run on the local host. This will open a server window that provides no function other than to shut down the server process. No feedback is given from the server.

Customer GUI:
To start this portion of the code.
1) Run CustomerGUI
2) Click anywhere on start screen to start order process
3) Click either 6 or 12 inch button.
4) Click button for type of bread desired.
5) Select whether you would like toasted or not option.
6) Select what type of filling you would like for your sub.
7) Select whatever vegetables you would like.  When finished, press done button to go to next screen.
8) Select whatever sauces you would like.  When done, press either next sub or done.  
9) For now, the system just exits because the server is not connected to this component yet.

Staff GUI:
Used for orders to be displayed to staff for processing. Read from server.
To use the Staff GUI:
Start "Program.java"
Select "Staff Window"
Select an order to mark it completed
Select that same order to clear it off the screen


Manager GUI:
Used to check item availability, change item availability, view transaction history, add or  remove managers, send refunds to disgruntled customers.
To view the transaction history window follow the following steps:
Start "Program.java"
Select "Manager Window"
Click "Transaction History" button


This release is in alpha and functions may not work correctly.
