
# A Spring Boot 3 and RestTemplate 

## Purpose
1.Make an external API call: Use RestTemplate to send a request and receive a response from the external API.<br>
2.Test the API response: Validate the response data and ensure it meets the expected criteria (e.g., status code, response time, and payload structure).<br>
3.Generate Prometheus metrics: Create and expose gauge and counter metrics based on the API response, making them available for Prometheus to scrape.<br>

## How to Build Image
```
$ podman build -t default-route-openshift-image-registry.apps-crc.testing/alr-aslani/spring-boot-3-and-rest-template:latest .
Sending build context to Docker daemon  22.04MB
Step 1/5 : FROM registry.access.redhat.com/ubi8/openjdk-17-runtime:latest
 ---> 0d4d53977a4a
Step 2/5 : WORKDIR /work/
 ---> Using cache
 ---> 54c99c5c9e68
Step 3/5 : COPY target/*.jar /work/application.jar
 ---> 7bef5e7894f1
Removing intermediate container 53e57b1487e4
Step 4/5 : EXPOSE 8080
 ---> Running in a237a9a2ce7b
 ---> 66126c75cfb7
Removing intermediate container a237a9a2ce7b
Step 5/5 : CMD java -jar application.jar
 ---> Running in cc844389dd16
 ---> eb8042f0bc94
Removing intermediate container cc844389dd16
Successfully built eb8042f0bc94
Successfully tagged default-route-openshift-image-registry.apps-crc.testing/alr-aslani/spring-boot-3-and-rest-template:latest
```

## How to Run Image
```
$ podman run -p 8080:8080 default-route-openshift-image-registry.apps-crc.testing/alr-aslani/spring-boot-3-and-rest-template:latest
```