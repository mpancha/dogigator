#!/usr/bin/python
import time
left='/sys/class/leds/beaglebone:green:usr0/brightness'
back='/sys/class/leds/beaglebone:green:usr1/brightness'
front='/sys/class/leds/beaglebone:green:usr2/brightness'
right='/sys/class/leds/beaglebone:green:usr3/brightness'

def turn(direction):
	f=open(direction,'w')
	f.write("255")
	f.close()
	time.sleep(2)
	f=open(direction,'w')
	f.write("0")
	f.close()

#turn(left)
#turn(right)
#turn(front)
#turn(back)

