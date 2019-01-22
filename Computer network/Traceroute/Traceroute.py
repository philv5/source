from socket import *
import os
import sys
import struct
import time
import select
import binascii  
import array

ICMP_ECHO_REQUEST = 8
ICMP_ECHO_REPLY = 0
ICMP_TTL_EXPIRED = 11 
ICMP_DEST_UNREACHABLE = 3 
MAX_HOPS = 30
TIMEOUT  = 2.0 # 2.0 second

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

def build_packet():
  # Header is type (8), code (8), checksum (16), id(16), sequence (16)
  # Data is 8Byte: time.time()

  myChecksum = 0
  # Make a dummy header with a 0 checksum
  # struct -- Interpret strings as packed binary data
  # format : b(signed char) H(unsigned short) h(short) d(double:8B)
  header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, (os.getpid() & 0xFFFF), 1)
  data = struct.pack("d", time.time())
  # Calculate the checksum on the data and the dummy header.
  myChecksum = checksum(header + data)
  
  # Get the right checksum, and put in the header
  if sys.platform == 'darwin': # Mac OS X
    # Convert 16-bit integers from host to network  byte order
    myChecksum = htons(myChecksum) & 0xffff		
  else:
    myChecksum = htons(myChecksum)
  	
  header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, (os.getpid() & 0xFFFF), 1)
  packet = header + data
  return packet

def get_route(hostname):
  timeLeft = TIMEOUT
  for ttl in range(1,MAX_HOPS): #range(1,MAX_HOPS) 1,..,MAX_HOPS-1
 
    destAddr = gethostbyname(hostname)
####################### Fill in start
    # SOCK_RAW is a socket type. http://sock-raw.org/papers/sock_raw
    mySocket = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP)
####################### Fill in end
    # setsockopt method is used to set the time-to-live field. 
    # format : I(unsigned int)
    mySocket.setsockopt(IPPROTO_IP, IP_TTL, struct.pack('I', ttl)) 
    mySocket.settimeout(TIMEOUT)
    try:
      d = build_packet()
      # UDPClient.py : clientSocket.sendto(message,(serverName, serverPort)
      mySocket.sendto(d, (hostname, 0))
      t= time.time()
      startedSelect = time.time()
      whatReady = select.select([mySocket], [], [], timeLeft)
      howLongInSelect = (time.time() - startedSelect)
      if whatReady[0] == []: # Timeout
        print("  *        *        *    Request timed out(select)")
  # UDPClient.py : modifiedMessage, serverAddress = clientSocket.recvfrom(2048)
      recvPacket, addr = mySocket.recvfrom(1024)
      timeReceived = time.time()
      timeLeft = timeLeft - howLongInSelect
      # Timeout 이후에 도착한 response
      if timeLeft <= 0:
        print("  *        *        *    Request timed out(packet ID)")
    except timeout:
      continue			
    
    else:
####################### Fill in start
      # Fetch the ICMP type and code from the received packet
      types, code = recvPacket[20:22]
####################### Fill in end
      
      if types == 11: # ICMP_TTL_EXPIRED
        bytes = struct.calcsize("d")
        timeSent = struct.unpack("d", recvPacket[28:28 + bytes])[0]
        print("TTL_EXPIRED  %d    rtt=%.0f ms    %s" %(ttl, (timeReceived -t)*1000, addr[0]))
      
      elif types == 3: # ICMP_DEST_UNREACHABLE
        bytes = struct.calcsize("d") 
        timeSent = struct.unpack("d", recvPacket[28:28 + bytes])[0]
        print("DEST_UN  %d    rtt=%.0f ms    %s" %(ttl, (timeReceived-t)*1000, addr[0]))
      
      elif types == 0: # ICMP_ECHO_REPLY
        bytes = struct.calcsize("d") 
        timeSent = struct.unpack("d", recvPacket[28:28 + bytes])[0]
        print("REPLY  %d    rtt=%.0f ms    %s" %(ttl, (timeReceived - timeSent)*1000, addr[0]))
        return
      
      else:
        print("error")			
    finally:				
      mySocket.close()		

get_route("google.com")


#예외처리: try, execpt, else, finally.  try노 나카데 예외데나캇타라 else니 잇테 finally니 이쿠.
#                                       try노 나카데 예외데타라 execept니 잇테 finally니 이크.
