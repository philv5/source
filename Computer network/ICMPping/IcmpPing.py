# 부분코드 완성은 receiveOnePing() 부분
from socket import *
import os
import sys
import struct
import time
import select
import binascii  
import array

ICMP_ECHO_REQUEST = 8

# 교재 ICMPping.docx에 있는 checksum()이 wireshark에서 error가 나서
# 구글링으로 아래 checksum()으로 수정
if struct.pack("H",1) == "\x00\x01": #big endian
  def checksum(pkt):
    if len(pkt) % 2 == 1:
        pkt +="\0"
    s = sum(array.array("H",pkt))
    s = (s >> 16) + (s & 0xffff)
    s += s >> 16
    s = ~s
    return s & 0xffff
else:
  def checksum(pkt):
    if len(pkt) % 2 == 1:
        pkt +="\0"
    s = sum(array.array("H",pkt))
    s = (s >> 16) + (s & 0xffff)
    s += s >> 16
    s = ~s
    return (((s>>8)&0xff)|s<<8) & 0xffff

def receiveOnePing(mySocket, ID, timeout, destAddr):
  timeLeft = timeout
  
  while 1: 
    startedSelect = time.time()
    # UDPClient.py 에는 select함수 없음. UDP의 mux-demux기능 구현이 필요함
    whatReady = select.select([mySocket], [], [], timeLeft)
    howLongInSelect = (time.time() - startedSelect)
    if whatReady[0] == []: # Timeout
      return "Request timed out(select)"
    
    timeReceived = time.time() 
    # UDPClient.py : modifiedMessage, serverAddress = clientSocket.recvfrom(2048)
    recPacket, addr = mySocket.recvfrom(1024)
    # recPacket는 3layer+4layer
    # UDPClient modifedMessage는 5layer
####################### Fill in start
    # Fetch the ICMPHeader from the IP datagram
    icmpHeader = recPacket[20:28] # 반환되는 조각은 [start, end-1]
    
    # format : b(signed char) H(unsigned short) h(short) d(double:8B) s(char[])
    rawTTL = struct.unpack("s", bytes([recPacket[8]]))[0]  
    
    # binascii -- Convert between binary and ASCII  
    TTL = int(binascii.hexlify(rawTTL), 16) 
    
    icmpType, code, checksum, packetID, sequence = struct.unpack("bbHHh", icmpHeader)
    
    if packetID == ID:
      byte = struct.calcsize("d") 
      timeSent = struct.unpack("d", recPacket[28:28 + byte])[0]
      return "Reply from %s: bytes=%d time=%f5ms TTL=%d" % (destAddr, len(recPacket), (timeReceived - timeSent)*1000, TTL)
    
#################### Fill in end
    timeLeft = timeLeft - howLongInSelect
    # Timeout 이 후에 도착한 response
    if timeLeft <= 0:
      return "Request timed out(packetID)"
	
def sendOnePing(mySocket, destAddr, ID):
  # Header is 8Byte: type (8), code (8), checksum (16), id (16), sequence (16)
  # Data is 8Byte: time.time()
  
  myChecksum = 0
  # Make a dummy header with a 0 checksum
  # struct -- Interpret strings as packed binary data
  # format : b(signed char) H(unsigned short) h(short) d(double:8B)
  header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1)
  #header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, 0, 1) #for debug
  data = struct.pack("d", time.time())
  #data = struct.pack("d", 0) #for debug
  # Calculate the checksum on the data and the dummy header.
  
  myChecksum = checksum(header + data)

  # Get the right checksum, and put in the header
  if sys.platform == 'darwin': # Mac OS X
    # Convert 16-bit integers from host to network byte order
    myChecksum = htons(myChecksum) & 0xffff		
  else:
    myChecksum = htons(myChecksum)
  	
  header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1)
  #header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, 0, 1)
  #print('expected checksum 0xf6ff')
  packet = header + data
  
  # UDPClient.py : clientSocket.sendto(message,(serverName, serverPort))
  mySocket.sendto(packet, (destAddr, 1)) # AF_INET address must be tuple, not str
  # Both LISTS and TUPLES consist of a number of objects
  # which can be referenced by their position number within the object.
	
def doOnePing(destAddr, timeout): 
  icmp = getprotobyname("icmp")
  
  # SOCK_RAW is a socket type. http://sock-raw.org/papers/sock_raw
  # UDPClient.py : clientSocket = socket(AF_INET, SOCK_DGRAM)
  mySocket = socket(AF_INET, SOCK_RAW, icmp)
  
  myID = os.getpid() & 0xFFFF  # Return the current process i
  sendOnePing(mySocket, destAddr, myID)
  delay = receiveOnePing(mySocket, myID, timeout, destAddr)
  
  # UDPClient.py : clientSocket.close()
  mySocket.close()
  return delay

def ping(host, timeout=1):
  # timeout=1 means: If one second goes by without a reply from the server,
  # the client assumes that either the client's ping or the server's pong is lost
  dest = gethostbyname(host)
  print("Pinging " + dest + " using Python:")
  print("")
  # Send ping requests to a server separated by approximately one second
  while True :  
    delay = doOnePing(dest, timeout)
    print(delay)
    time.sleep(1)# one second
  return delay
	
#ping("google.com")
ping("192.168.56.101")

#ping("localhost")
#ping("192.168.56.101")
# ping()
#   doOneping()
#       sendOneping()
#       recievedOneping()
