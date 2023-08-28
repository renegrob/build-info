all: quarkus-container

quarkus-container: 
	./gradlew clean quarkusBuild -Dquarkus.container-image.build=true

