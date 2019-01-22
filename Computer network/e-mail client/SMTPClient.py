# smtp:25, imap:143, pop3:110
from socket import *

# Message to send
msg = '\r\nI love computer networks!'
endmsg = '\r\n.\r\n'

# Choose a mail server (e.g. Google mail server) and call it mailserver
mailserver = 'smtp.gmail.com' # gmail require STARTTLS command
mailserver = 'mail.hanyang.ac.kr' 

# Create socket called clientSocket and establish a TCP connection with mailserver
clientSocket = socket(AF_INET, SOCK_STREAM)

# Port number may change according to the mail server
clientSocket.connect((mailserver, 25))
recv = clientSocket.recv(1024).decode()
print('recv '+recv)
if recv[:3] != '220':
	print('220 reply not received from server.')

# Send HELO command and print server response.
heloCommand = 'HELO hanyang.ac.kr\r\n'
clientSocket.send(heloCommand.encode())
recv1 = clientSocket.recv(1024).decode()
print('recv1 '+recv1)
if recv1[:3] != '250':
	print('250 reply not received from server.')
	
# Send MAIL FROM command and print server response.
mailfrom = 'MAIL FROM: <khshimto@hanyang.ac.kr>\r\n'
clientSocket.send(mailfrom.encode())
recv2 = clientSocket.recv(1024).decode()
print('recv2 '+recv2)
if recv2[:3] != '250':
	print('250 reply not received from server.')

# Send RCPT TO command and print server response. 
#rcptto = 'RCPT TO: <khshimto@naver.com>\r\n' # Relaying denied
rcptto = 'RCPT TO: <khshimto@hanyang.ac.kr>\r\n'
clientSocket.send(rcptto.encode())
recv3 = clientSocket.recv(1024).decode()
print('recv3 '+recv3)
if recv3[:3] != '250':
	print('250 reply not received from server.')

# Send DATA command and print server response. 
data = 'DATA\r\n'
clientSocket.send(data.encode())
recv4 = clientSocket.recv(1024).decode()
print('recv4 '+recv4)
if recv4[:3] != '354':
	print('354 reply not received from server.')

# Send message data.
clientSocket.send('From: khshimto@hanyang.ac.kr\r\n'.encode())
clientSocket.send('To: khshimto@hanyang.ac.kr\r\n'.encode())
clientSocket.send('Subject: Greeting To you!\r\n'.encode())
clientSocket.send(msg.encode())
# Message ends with a single period.
clientSocket.send(endmsg.encode())
recv5 = clientSocket.recv(1024).decode()
print('recv5 '+recv5)
if recv5[:3] != '250':
	print('250 reply not received from server.')

# Send QUIT command and get server response.
quitcommand = 'QUIT\r\n'
clientSocket.send(quitcommand.encode())
recv6 = clientSocket.recv(1024).decode()
print('recv6 '+recv6)
if recv6[:3] != '221':
	print('221 reply not received from server.')

