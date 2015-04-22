# dogigator
Wireless Project code (ECE575)

Procedure to Run:
1. Place the files in respective folders as described below.
2. Perform Bluetooth pairing between bbb and android device (Required only once)
3. Turn on beaglebone black and wait for all LEDs to turn off(except power LED).
4. Start android application.

Files:
|===OsmAnd-full-legacy-armv7-debug.apk
|        Application apk. To be installed on android device
|
|===init_dogi.sh
|        Init script to be run on beaglebone black for one time init.
|
|===dogi.service 
|        Serivce file to be placed in /lib/systemd/system/ directory on bbb
|
|===dogi_server.py  
|	 server module to be placed in /home/root which is called 
|        on service start
|
|===indications.py  
|        LED driving code imported by dogi_server.py. To be placed in /home/root
|        on bbb
