#!/usr/bin/python
# file: rfcomm-server.py
# auth: Albert Huang <albert@csail.mit.edu>
# desc: simple demonstration of a server application that uses RFCOMM sockets
#
# $Id: rfcomm-server.py 518 2007-08-10 07:20:07Z albert $

from bluetooth import *
from indications import *

#left='/sys/class/leds/beaglebone:green:usr0/brightness'
#right='/sys/class/leds/beaglebone:green:usr1/brightness'
#front='/sys/class/leds/beaglebone:green:usr2/brightness'
#back='/sys/class/leds/beaglebone:green:usr3/brightness'

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
