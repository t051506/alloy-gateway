FROM java:8
MAINTAINER tankechao
ADD ./target/alloy-gateway.jar alloy-gateway.jar
EXPOSE 9999
ENTRYPOINT ["java","-jar","alloy-gateway.jar"]
