from socket import *
serverName = '192.168.56.101' # remote server or 'localhost'
serverPort = 6789
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName,serverPort)) # three-way setup
rqMsg = "GET /HelloWorld.html HTTP/1.1\r\n"
rqMsg += "\r\n"

clientSocket.send(rqMsg.encode())
rpMsg = clientSocket.recv(1024)
print("From Server: ",rpMsg.decode())

#rqMsg = "GET /HelloWorld.html HTTP/1.1 200\r\n"
#clientSocket.send(rqMsg.encode())
data1 = clientSocket.recv(1024)
print("From Server: ",data1.decode())


clientSocket.close() # four-way termination
input("press enter key to exit")
