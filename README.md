# RandomSignalAnalysisSystem
Java MOM programming practice

---

## Choose a message broker
### ActiveMQ
Apache ActiveMQ is an open source message broker written in Java together with a full Java Message Service (JMS) client. It provides "Enterprise Features" which in this case means fostering the communication from more than one client or server. Supported clients include Java via JMS 1.1 as well as several other "cross language" clients. The communication is managed with features such as computer clustering and ability to use any database as a JMS persistence provider besides virtual memory, cache, and journal persistency.  
[Wikipedia: Apache ActiveMQ][1]  
[维基百科: Apache ActiveMQ][2]  
Official download address: [http://activemq.apache.org/download.html][3]

---

## Configure message-broker environment
- Download activeMQ
- Add ```activemq\bin\win64``` or ```activemq\bin\win32``` to system path according to your system version, so that you can open activemq by just typing activemq into shell no matter which path it is now.
- Open activemq
  - If you have did previous step, just open cmd and add type ```activemq```
  - If not, change directory to ```activemq\bin\win64``` or ```activemq\bin\win32``` and then type ```activemq```
- Test
  - In the default configuration, open ```http://localhost:8161``` in browser and you will see a welwcome to Apache ActiveMQ page which means your activemq works properly.
  - Open ```http://localhost:8161/admin``` with user ```admin``` and password ```admin```, you will get a visualized background management system for activemq.

---

## MessageQueueBasic
The basic message queue practice.

### How to run?
#### Server side
- Place Publisher.java in server
- Place ```activemq\active-all-XXXX.jar``` in server
- Add active-all-XXXX.jar to project dependence or to classpath
- Change brokerURL to activemq server' ip and port, line 14 in publisher.java
  ``` java
  // Message broker address tcp://ip:port
  private final String brokerURL = "tcp://localhost:61616";
  ```
- Compile it
  ``` java
  javac Publisher.java
  ```
- Run it
  ``` java
  java Publisher
  ```

#### Client side
##### Synchronous consumer
- Place SyncConsumer.java in client
- Place ```activemq\active-all-XXXX.jar``` in client
- Add active-all-XXXX.jar to project dependence or to classpath
- Change brokerURL to activemq server' ip and port, line 15 in SyncConsumer.java
  ``` java
  // Message broker address tcp://ip:port
  String brokerURL = "tcp://localhost:61616";
  ```
- Compile it
  ``` java
  javac SyncConsumer.java
  ```
- Run it
  ``` java
  java SyncConsumer
  ```
##### Asynchronous consumer
- Place AsyncConsumer in client
- Place ```activemq\active-all-XXXX.jar``` in client
- Add active-all-XXXX.jar to project dependence or to classpath
- Change brokerURL to activemq server' ip and port, line 17 in AsyncConsumer\AsyncConsumer.java
  ``` java
  // Message broker address tcp://ip:port
  String brokerURL = "tcp://localhost:61616";
  ```
- Compile them
  ``` java
  javac AsyncConsumer\*.java
  ```
- Run it
  ``` java
  java AsyncConsumer.AsyncConsumer
  ```

---



[1]: https://en.wikipedia.org/wiki/Apache_ActiveMQ
[2]: https://zh.wikipedia.org/wiki/Apache_ActiveMQ
[3]: http://activemq.apache.org/download.html