from socket import *
serverName = '192.168.56.101' # remote server or 'localhost'
serverPort = 12000
clientSocket = socket(AF_INET, SOCK_DGRAM)
# 189쪽의 raw_input()은 python에 없는 함수이므로 input()으로 변경
#message = input('Input lowercase sentence: ')
message = 'TUGIHA KOITUDAA'
clientSocket.sendto(message.encode(),(serverName, serverPort))
modifiedMessage, serverAddress = clientSocket.recvfrom(2048)
print(modifiedMessage.decode())
print('enter key to exit')
clientSocket.close()
