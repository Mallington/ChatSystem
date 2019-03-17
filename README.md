# ChatSystem
Primary aim is to create a simple chat system, with the intention of adding other features such as public key, private key cryptography via the Diffie-Hellman key exchange algorithm and maybe advancing to eliptical curve ciphers in the near future.

---Command Line Parameters---
Note: All parameters are case sensitive
	---ChatServer and ChatClient---
	-GUI (Optional): Accepts true or false : Denotes whether GUI should run

	---ChatClient---
    -cca (Optional): Address in the form of an IP address or another form
    -ccp (Optional): Port to be connected to
    -user (Optional): Specified the uniquely assigned ID for an existing user
    -name (Optional): Sets the display name of the user

    ---ChatServer---
	-csp (Optional): The port that the server will load on.

---Console Interface---
	On both interfaces, if the parameter -GUI has not been specified, you will be
	prompted (Yes/No) whether you would like to run the GUI. Note the program has
	not started, you will have to choose an option before being able to exit the
	interface.

	---ServerInterface---
		The console interface can be used to see an overview of the packets being 
		routed and their contents. As well as new user logons and user logoffs.

	---ServerInterface---
		The console interface can be used to see an overview of the packets being 
		routed and their contents. As per the specification, EXIT can be called
		to exit the interface.

	As per the specification, EXIT can be called to exit both interfaces.

---Graphical Interface---
	---Running the GUI (different options):---
		*The parameter '-GUI true' is entered into either the ChatClient or 
		ChatServer main methods as commandline arguments

		*The alternative classes ChatClientGUI or ChatServerGUI can be run

		*Or 'Yes' can be entered when prompted to when running the normal
		ChatServer and ChatClient classes

	---Parameter Window ---
	Both the server and client first launches a graphical menu with all the
	possible parameters that can be modified. See the parameters section for
	all the definitions. To modify a parameter, select a value field and start 
	typing, to leave the value as the commandline argument already entered or
	the default value, keep the syntax "USE_DEFAULT_VALUE" to keep it un-modified

	---Chat Client GUI ---
	*Room List: Contains a list of all the chat rooms the user is currently in,
	in future developement multiple chat rooms will be enabled, but for the scope
	of this task I have limited it to just one ChatRoom. Each ChatRoom tile shows
	the number of users currently online in the chat room and the name.

	*ClientConsole: Displays general updates on connection status

	*Main Chat View: Each bubble represents a message, bubbles on the right are
	the other client messages, the top line denoted the display name of that
	particular user. Note your display name can be changed through the '-name'
	parameter

	*Messaging: To send a message, simply type in the text box and click enter

	*Contacts Sidebar: Contains the UIDs and display names of every single user
	the client has ever come into contact with while being online, note: retains
	user cache even after user has logged off.

	*Exiting: Simply press the close window button and you will be automatically
	logged off the server

	---Chat Server GUI ---
	Shares similar function to the ServerConsole interface, however in window form
	*Main Console: Routed traffic will be displayed here, scroll to see future and
	previous events
	*Also displays other information such as the amount of users online and the
	port it is on
	*To exit press the close window button.

---Network Overview---
*The server acts acts like a REST server, the client makes a request to the
server and the server sends a response. For example: when the client wants to
create a new user to connect with, it sends a Packet with an UPDATE_USER header
. To see packet handling for all the different types of requests see the
ServerNetwork class.

*The Client periodically sends requests for updates on information such as
Channel objects which contain users and message IDS which the client can fetch 
through a further request to the server.

*Adding to this, when a message is wished to be sent, a packet with the header:
SEND_MESSAGE is sent with a Message object as its payload. This message is only
written to the interface when another general update request is made and recieved
from the server.

*The network classes do not directly interact with either the GUI or Console, they
do however update through the use of generic interfaces, that both the GUI and the
console implement, making it easier to add more features more quickly.


*Please Note: Event of the client not being able to connect or has been disconnected,
the client will repeatedly attempt to reconnect to the server, unless prompted to exit via EXIT or closing the window.
