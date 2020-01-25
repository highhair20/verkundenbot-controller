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


## Interfaces

#### Who will be communicating with Verkundenbot?
* __Datadog__ - we use Datadog as our monitoring system. Any critical alerts that are triggered 
should be sent to the device.
* __Deployments (Jenkings or AWS CodeBuild)__ - When we deploy code we want to notify the device so that we get an audible
alerting on what system was update and who made the change.
* __Verkundenbot Web App__ - There is a React web app where you can interect with the device.
You can control plugs A, B and C and you can send custom messages which will be converted
to voice and send through the speakers.
* __PagerDuty (future)__ - in the future we may move alerts from Datadog to PagerDuty. At which 
point we will want to move the webhook from Datadog to PagerDuty as well.  

#### How will the sources interect?
* __Datadog__ - 
* __Deployments__ -
* __Verkundenbot Site__ -
* __PagerDuty (future)__ - TDB

## IoT Device Software


mvn compile

mvn package 