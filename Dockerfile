FROM java:8
WORKDIR /
ADD target/MessagingSystem-1.0-SNAPSHOT.jar server.jar
EXPOSE 14006
CMD java -jar server.jar -GUI false -nodetype SERVER -csp 14006
