# ChatSystem
![Java CI with Maven](https://github.com/Mallington/ChatSystem/workflows/Java%20CI%20with%20Maven/badge.svg)

Primary aim is to create a simple chat system, with the intention of later adding other features such as public key, private key cryptography via the Diffie-Hellman key exchange algorithm and maybe advancing to eliptical curve ciphers in the near future.

## Command Line Parameters
All parameters are case sensitive, they can be used to change IP, port, username, UID etc.
### ChatServer and ChatClient
	-GUI <Boolean> #True or False - Denotes whether GUI should run

### ChatClient
    -cca <String> #Address in the form of an IP address or another form
    -ccp <Integer>) #Port to be connected to
    -user <String> #Specified the uniquely assigned ID for an existing user
    -name <String> #Sets the display name of the user

### ChatServer
	-csp <Integer> #The port that the server will load on.
## Server OCI Deployment
### Kubernetes command
	kubectl apply -f https://raw.githubusercontent.com/Mallington/ChatSystem/master/deployment.yaml
## Screen Shots
### CLIENT
![Parameter Window](https://raw.githubusercontent.com/Mallington/ChatSystem/master/Screenshots/Client-Parameter.png)
![Client Window](https://raw.githubusercontent.com/Mallington/ChatSystem/master/Screenshots/Client.png)

### Server
![Parameter Window](https://raw.githubusercontent.com/Mallington/ChatSystem/master/Screenshots/Server-Parameter.png)
![Server Window](https://raw.githubusercontent.com/Mallington/ChatSystem/master/Screenshots/Server.png)
