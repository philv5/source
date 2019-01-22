from socket import *
import sys

serverSocket = socket(AF_INET, SOCK_STREAM)
#Prepare a sever socket
serverPort = 6789
serverSocket.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
serverSocket.bind(("", serverPort)) #set up socket connection
serverSocket.listen(1)


serverSocket.listen(1) #tells the server to try a maximum of one connect request before ending connection



while True:
    #Establish the connection
    print ('Ready to serve...')


    connectionSocket, addr = serverSocket.accept()
    print ('connected to port',serverPort)



    try:

         message = connectionSocket.recv(1024).decode() #Makes it so that you can recieve message from client
         #print(message)
         filename = message.split()[1]
         print('filename '+filename)
         f = open(filename[1:])

         outputdata = f.read()

            #Send one HTTP header line into socket2
            #Fill in start
         connectionSocket.send('HTTP/1.1 200 OK\r\n\r\n'.encode())

            
         connectionSocket.send(outputdata.encode())
         connectionSocket.close()

            #Send the content of the requested file to the client
           # for i in range(0, len(outputdata)):
              ## connectionSocket.send(outputdata[i])
              # connectionSocket.close()

               
    except IOError:
    #Send response message for file not found

        print ('404 Error : File Not Found.')

    #Close client socket

        connectionSocket.close()

serverSocket.close()
