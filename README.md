# Java Example for Thingface Platform
Simple example how to connect device to thingface server in Java SE.
With the example you are able to send sensor data.
You can also receive commands, which user(s) can send to your device.
Take an inspiration and feel free to clone this repository and modify it as you wish.

# Prerequisite
- Java JDK version 1.8.x [download](https://java.com/en/download/)
- Maven Project Management [download](https://maven.apache.org/)
- IDE for Java Programming [NetBeans](https://netbeans.org/), [Eclipse](https://eclipse.org/ide/) or some text editor

# How to run
- Download or clone this repository to your PC
- Setup constants `THINGFACE_HOST`, `THINGFACE_DEVICE_ID` and `THINGFACE_DEVICE_SECRET` in [App.java](src/main/java/io/thingface/examples/App.java)
- Build `mvn package` and run `mvn exec:java`

# More information
- [GitHub Repositories](https://github.com/thingface "GitHub Repositories")
- [Thingface Platform Website](http://thingface.io/ "Thingface Platform Website")
