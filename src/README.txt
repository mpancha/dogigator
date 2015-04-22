# dogigator
Wireless Project source code (ECE575)
=====================================
The project contains source code for two devices
1. Modified OsmAnd application for Android device
2. Beagle bone black Angstrom service "dogi" daemon (python module)

1. Modified OsmAnd application
------------------------------
HOW TO BUILD
------------
1. Copy the changed files from osmand_change to osmandapp directory
2. Build apk using following command
    ```
    cd android/OsmAnd
    ../gradlew --refresh-dependencies assembleFullLegacyarmv7Debug
    ```

2. Beagle Bone service
------------------------
HOW TO ENABLE Dogi Service (One time operation)
1. Copy dogi.service file to /lib/systemd/system/
   and dogi_server.py file to /home/root/ on beaglebone black
2. To enable the service
	systemctl enable dogi
3. To start the service
	systemctl start dogi
4. Change all LEDs trigger to [none]
	echo none > /sys/class/leds/beaglebone\:green\:usr0/trigger
	echo none > /sys/class/leds/beaglebone\:green\:usr1/trigger
	echo none > /sys/class/leds/beaglebone\:green\:usr2/trigger
	echo none > /sys/class/leds/beaglebone\:green\:usr3/trigger
	echo 0 > /sys/class/leds/beaglebone\:green\:usr0/brightness
	echo 0 > /sys/class/leds/beaglebone\:green\:usr1/brightness
	echo 0 > /sys/class/leds/beaglebone\:green\:usr2/brightness
	echo 0 > /sys/class/leds/beaglebone\:green\:usr3/brightness

  (Step 2 to 4 can be performed by calling init_dogi.sh script)

HOW TO RUN
==========

1. After enabling Dogi service as described above, Reboot the beaglebone and wait till four LEDs on beaglebone turns OFF
2. Start modified OsmAnd application on Android device and start navigation.

NOTE: Bluetooth pairing needs to be done manually.

