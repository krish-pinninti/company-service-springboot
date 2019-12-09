FROM openjdk:8-jre-alpine

VOLUME /tmp

VOLUME /opt/technoaps/conf

COPY build/libs/*.jar app.jar

EXPOSE 8080

RUN apk add --no-cache curl unzip \
	&& mkdir -p /opt/appdynamics \
	&& chmod 755 /opt/appdynamics \
	&& cd /opt/appdynamics \
	&& curl -k -O https://artifact.technoaps.net/ext-release/com/technoaps/devops/appdynamics/4.5.4/AppServerAgent.zip \
	&& unzip -oq AppServerAgent.zip -d /opt/appdynamics \
	&& rm /opt/appdynamics/AppServerAgent.zip \
	&& mkdir -p /opt/contrast \
	&& chmod 755 /opt/contrast \
	&& cd /opt/contrast \
	&& curl -k -O http://technoaps.net/artifactory/ext-release-local/com/technoaps/devops/contrast.jar
	
HEALTHCHECK CMD curl - --silent --show-error "http://localhost:8080/company-service-1.0/actuator/health/"

ENTRYPOINT ["java", "-jar", "app.jar"]