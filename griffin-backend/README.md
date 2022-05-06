# Overview

The backend is currently contained within a single Spring Boot application, with the following versions:

- Java version: `17`
- Spring version: `2.6.7`
- Production JDK: `OpenJDK Temurin-17.0.3+7` can be downloaded [here](https://adoptium.net/temurin/releases/)

Other JDK's should work for development, however the above JDK will be used in production.


## Development

### IDE

Intellij is **Strongly** recommended as an IDE. As students, we have access to the enterprise version for free.

To work on the backend, open this directory as a project in Intellij.

### Commands

Gradle binaries are contained within the repository (`gradlew` and `gradlew.bat`), meaning you don't have to install gradle on your local machine. Development commands on Windows and Unix environments are extremely similar. The following commands work for a Unix environment.

To run the application:

```Bash
./gradlew bootrun
```

To remove build files from the project:

```Bash
./gradlew clean
```

To build a jar file:

```Bash
./gradlew clean build
```
