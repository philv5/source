"""
ProxyServer.docx의 skeleton code와 3가지가 다름
1. command line argument를 받지 않음 (예: pythone ProxyServer.py server_ip)
   IDLE환경에서는 command line argument를 받지 못함
2.socket.makefile() method는 win7에서는 사용못함
   socket.send() 로 변경
3. tmpFile=open("./"+filename,"wb")은 error가 남
   tmpFile=open("./"+filename,"w")로 변경하면
   socket data를 file로 저장하면
   \r\n가  \r\r\n로 변경되고
   file에서 읽으면 
   data가 \r\r\n가 \n\n로 변경됨
   file에서 data를 읽어서 socket으로 보낼때는 아래와 같이 하면 됨
   rdata = f.read()
   outputdata = rdata.replace('\n\n','\r\n')
   tcpCliSock.send(outputdata.encode()) 
"""
from socket import *
import sys
tcpSerSock = socket(AF_INET, SOCK_STREAM)
tcpSerSock.bind(('', 8888))
tcpSerSock.listen(1)

while 1:
  # Start receiving data from the client
  print('Ready to serve...')
  tcpCliSock, addr = tcpSerSock.accept()
  print('Received a connection from:', addr)
  message = tcpCliSock.recv(1024).decode()
  print('message: \n'+message)
  # Extract the filename from the given message
  # GET /192.168.56.1 HTTP/1.1
  print('message.split '+message.split()[1])
  filename = message.split()[1].partition("/")[2]
  print('filename '+filename)
  if filename == 'favicon.ico':
    tcpCliSock.send("HTTP/1.1 404 Not Found\r\n\r\n".encode())
    tcpCliSock.close() 
    continue
  fileExist = "false"
  filetouse = "/" + filename
  print('filetouse '+filetouse)
  try:
    # Check wether the file exist in the cache
    f = open(filetouse[1:], "r")                      
    # ProxyServer finds a cache hit and generates a response message
     
    # 본인이 작성하라 # cache hit의 경우
    rdata = f.read()
    outputdata = rdata.replace('\n\n','\r\n')
    #koko1
    #tcpCliSock.send('HTTP/1.1 200 OK\r\n\r\n'.encode())
    
    tcpCliSock.send(outputdata.encode())
    tcpCliSock.close()

    # 본인이 작성하라 # cache hit의 경우

    fileExist = "true"
    print('Read from cache')   
    f.close()
  # Error handling for file not found in cache
  except IOError:
    if fileExist == "false": 
      # Create a socket on the proxyserver
      c = socket(AF_INET, SOCK_STREAM)
      hostn = filename.replace("www.","",1)         
      print('hostn '+hostn)                                   
      try:
        # Connect to the socket to port 80
        c.connect((hostn, 80))      
        # ask port 80 for the file requested by the client
        rqMsg = "GET / HTTP/1.1\r\n"
        rqMsg += "\r\n"
        c.send(rqMsg.encode())
        # Read the response into buffer
        rpMsg = c.recv(1024)

        print("file content : "+rpMsg.decode())
        # Create a new file in the cache for the requested file. 
        tmpFile = open("./" + filename,"w")
        
        # 본인이 작성하라 # cache miss의 경우
        tmpFile.write(rpMsg.decode())


        tcpCliSock.send(rpMsg)

        #c.close()

        # 본인이 작성하라 # cache miss의 경우

        

        tmpFile.close()
      except:
      	print("Illegal request")
    else:
      # HTTP response message for file not found
      tcpCliSock.send("HTTP/1.0 404 sendErrorErrorError\r\n".encode())
      tcpCliSock.send("Content-Type:text/html\r\n".encode())
      tcpCliSock.send("\r\n".encode())
  # Close the client and the server sockets    
  tcpCliSock.close() 
tcpSerSock.close()
