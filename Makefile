all: quarkus-container

quarkus-container: 
	./gradlew clean quarkusBuild -Dquarkus.container-image.build=true -Dquarkus.info.build.build-java-version=20.0.2

