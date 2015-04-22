#!/usr/bin/python
import time
from bluetooth import *
from indications import *
import sys

left='/sys/class/leds/beaglebone:green:usr0/brightness'
back='/sys/class/leds/beaglebone:green:usr1/brightness'
front='/sys/class/leds/beaglebone:green:usr2/brightness'
right='/sys/class/leds/beaglebone:green:usr3/brightness'
#time.sleep(10)
def turn(direction):
	f=open(direction,'w')
	f.write("255")
	f.close()
	time.sleep(2)
	f=open(direction,'w')
	f.write("0")
	f.close()

server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)

port = server_sock.getsockname()[1]

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

advertise_service( server_sock, "SampleServer",
                   service_id = uuid,
                   service_classes = [ uuid, SERIAL_PORT_CLASS ],
                   profiles = [ SERIAL_PORT_PROFILE ],
#                   protocols = [ OBEX_UUID ]
                    )
print("Waiting for connection on RFCOMM channel %d" % port)

print "Test LEDs"
turn(left)
turn(right)
turn(front)
client_sock, client_info = server_sock.accept()
print("Accepted connection from ", client_info)

try:
    while True:
        data = client_sock.recv(1024)
        if len(data) == 0: break
        print("received [%s]" % data)
	if "left" in data:
	   print "left"
           turn(left)
   	elif "right" in data:
	   print "right"
	   turn(right)
	else:
	   print "else"
 	   turn(front)
except IOError:
    pass

print("disconnected")

client_sock.close()
server_sock.close()
print("all done")
