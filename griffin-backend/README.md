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
./gradlew clean bootrun
```

To remove build files from the project:

```Bash
./gradlew clean
```

To build a jar file:

```Bash
./gradlew clean build
```

## Components

### Collector

General algorithm:

```Bash
Read a configuration file with a list of IP addresses

for each address:
	send GET request to identify Gitlab, Bitbucket, or other
	if bitbucket:
		use bitbucketProject Class
	else if gitlab:
		use gitlabProject Class
	else:
		error
	get all project names and create project object for each
	for each project:
		get all repositories and create an object for each
		for each repository:
			get clone urls
			if repo already cloned:
				pull changes
			else:
				clone repository locally
				store reference to repo path (so we can pull changes later)
			store time-stamp of clone or pull time
			run crawler to get build files
			store build files locally and in memory as an attribute of repo class
	Send list of project objects to transformer service
```

Note:

- Repositories are currently stored on file system, not in a db. This simplifies development.
- If Jgit has the right support (i.e. if you can read a repo into a Git object straight from bytes), it might actually be easier to store the repo files in a db.

### Transformer

```Bash
for each Project Object:
	for each Repository Object:
		extract dependency info from build file(s)
		extract insights from repo files
		add repository mapping and exctracted information to insights database
```
	
### Insights Database

The main purpose of the insights database is to store snapshots of a repository and it's dependencies over time.

For detailed documentation see [database docs](https://confluence.cis.unimelb.edu.au:8443/display/SWEN900132022TZ/Dependency+Resolver)

### InsightsAPI

This is the endpoint the front-end will use to get repository and dependency information from the backed.
