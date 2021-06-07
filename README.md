# Akka Sample Application using HTTP Module
> Akka is a toolkit for building highly concurrent, distributed, and resilient message-driven applications for Java and Scala.
> This project guide you about how to create a simple web server using Akka HTTP Module.

### To run the application in terminal 
#### Run commands as follows: 
> mvn clean package  
> java -jar target/<<artifact-id>-<version>-SNAPSHOP.jar>  

### To run in a docker container
> docker build -t <imagename>:<tag> .  
> docker run -d -it -p 8080:8080 <imagename>:<tag>
