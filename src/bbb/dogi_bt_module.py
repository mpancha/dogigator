#!/usr/bin/python
from bluetooth import *
import time

#open Bluetooth socket and listen for connections
server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(2)

port = server_sock.getsockname()[1]

uuid = "34B1CF4D-1069-4AD6-89B6-E161D79BE4D8"

#advertise our Bluetooth service so other devices can find it
advertise_service( server_sock, "BeagleBoneService", service_id = uuid, service_classes = [ uuid, SERIAL_PORT_CLASS ], profiles = [ SERIAL_PORT_PROFILE ])
print("Waiting for connection on RFCOMM channel %d" % port)

#wait for an incoming connection
client_sock, client_info = server_sock.accept()
print("Accepted connection from ", client_info)
data = client_sock.recv(1024)
print data
client_sock.close()
server_sock.close()
print "done"
