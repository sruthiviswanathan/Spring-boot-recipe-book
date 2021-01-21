FROM centos

RUN yum install -y java-11-openjdk
RUN java -version

volume /tmp
ADD recipebook-0.0.1-SNAPSHOT.jar my-app.jar
RUN sh -c 'touch /my-app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/my-app.jar"]