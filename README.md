Reference:
* https://www.baeldung.com/spring-boot-console-app






# verkundenbot-device

The Verkundenbot Device is an AWS IoT enabled device.
  
The messages determine the state of the physical device.

## Hardware
For the purposes of this project I used a RaspberryPi 2 I had laying around.
Before we do anything else we should make sure we are running the latest
version of Raspian.

Begin by updating the repository package list:
```buildoutcfg
sudo apt update
```

When this is done, run the upgrade command:
```buildoutcfg
sudo apt dist-upgrade
```

Follow any prompts, and wait for the Pi to be upgraded. When you’re done, type:
```buildoutcfg
sudo apt clean
```

This will discard any unneeded files that have been downloaded as part of the upgrade. 
Finish by restarting:
```buildoutcfg
sudo reboot
```

When your Raspberry Pi has restarted, you’ll be using the latest version of Raspbian.


## IoT Device Software


mvn compile

mvn package 