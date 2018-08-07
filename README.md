SpringBoot-ApacheCamel is the POC project I have tried to configure Apache Camel with Spring Boot technology. Apache Camel is a integration tool to track no of application information. Spring Boot provides auto configuration for Apache Camel. We need to define the below dependency in POM.XML file for apache camel.

```xml
<dependency>
    <groupId>org.apache.camel</groupId>
    <artifactId>camel-spring-boot</artifactId>
    <version>${camel.version}</version>
</dependency>
```

I have even declared the below dependecies to avoid error/exception because I am referring few classes from each of these dependencies.
```xml
<dependency>
  <groupId>org.apache.camel</groupId>
  <artifactId>camel-metrics-starter</artifactId>
  <version>2.22.0</version>
</dependency>
<dependency>
  <groupId>org.jolokia</groupId>
  <artifactId>jolokia-core</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
	<groupId>io.dropwizard.metrics</groupId>
	<artifactId>metrics-core</artifactId>
</dependency>
<dependency>
	<groupId>io.dropwizard.metrics</groupId>
	<artifactId>metrics-graphite</artifactId>
</dependency>
<dependency>
	<groupId>org.apache.camel</groupId>
	<artifactId>camel-stream</artifactId>
	<version>2.22.0</version>
</dependency>
```

I have also reset the Server Port along with the management port.
```
server.port=8181
management.server.port: 9001
management.server.address: 127.0.0.1
```

Below are the other properties placed in application.properties file.
```
#To Keep the main thread blocked
camel.springboot.main-run-controller=true

# how often to trigger the timer
timer.period = 5000

# expose actuator endpoint via HTTP
management.endpoints.web.exposure.include=info,health,camelroutes

# show verbose health details (/actuator/info) so you can see Camel information also
management.endpoint.health.show-details=always

# to turn off Camel info in (/actuator/info)
management.info.camel.enabled=false

# allow to obtain basic information about Camel routes (read only mode)
management.endpoint.camelroutes.enabled = true
management.endpoint.camelroutes.read-only = true

management.endpoint.shutdown.enabled=true

greet = "Congratulation..! Your Apache Camel Application running Successfully."
```

For testing purpose I have included one TestController. You can ignore that for this Camel application. We can access the application on http://localhost:8181/api/test, also we can access management details by http://localhost:9001/actuator/health

For more informatoin, please go through the Source code of each package.
