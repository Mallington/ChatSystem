FROM java:8
WORKDIR /
ADD target/MessagingSystem-1.0-SNAPSHOT.jar server.jar
ENV SERVER_PORT=default_value
CMD java -jar server.jar -GUI false -nodetype SERVER -csp $SERVER_PORT
