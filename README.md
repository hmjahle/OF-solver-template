# Solver Template Guide 

If solver is not using or-tools, skip to section Environmental variables. Remember to have gradle installed.

## Copy into new project

Copy the whole project. If you are not going to use or-tools, do not copy or-tools-centos and or-tools-osx

## Installation and OR-Tools

Gradle 5.6 needs to be installed to run or-tools locally. SDK is recommended to install specific version of Gradle. SDK installation for OSX can be found [here](https://sdkman.io/install). 

When SDK is installed, install Gradle version 5.6:

```bash
sdk install gradle 5.6
```

If you would like to run or-tools in a main class locally, vm options need to be added. This can be done by editing configurations and adding the following code into vm options: 

```
-Djava.library.path=build/my-libs/or-tools-osx/
```

Download the files or-tools-centos and or-tools-osx and put them in top folder (under ka-solver)

Finish by running the following command which downloads the or-tools. This can be done once (creates build folder with or-tools).

```bash
gradle clean build
``` 

## Environmental variables

Some variables need to be defined:

* JFROG_USERNAME
* JFROG_ACCESS_KEY
* SOLVER_ARTIFACT_VERSION

Artifact version is the version number uploaded to jfrog artifact.

To be able to run locally in IntelliJ, remember to set these environmental variables in .bash_profile.

## Usage

To see the available usages:

```bash
gradle tasks
```

In order to build the project, 

```bash
gradle clean build
``` 

In order to publish to artifactory (this might require the ENV variables to be defined in .bash_profile)

```bash
gradle artifactoryPublish
``` 
