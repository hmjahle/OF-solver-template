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

You also need to install swagger-cli, using this command:

```
npm install -g swagger-cli
```

Finish by running the following command which downloads the or-tools. This can be done once (creates build folder with or-tools).

```bash
gradle clean build
``` 

## Print version
To print the version of the project:
```bash
gradle -q printVersion
``` 

## Environmental variables

Some variables need to be defined:

* JFROG_USERNAME
* JFROG_ACCESS_KEY

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
gradle clean artifactoryPublish
``` 
# API Documentation Guide
This guide describes how to document endpoints and payloads for a given solver project. 
The outcome of the documentation process is the **openapi.yml** file, that will be hosted on AWS.

## Documentation process

The complete **openapi.yml** file is generated using the *swagger-cli bundle* command, which bundles the individual documentation files, located in the *resources/spec*-folder. 
The **openapi-spec.yml** file defines the outcome of the bundling, and should only be modified when adding documentation for an endpoint.
**Schemas.yml**, which is generated from a groovy function in **build.gradle**, is required for the bundling to work.


[How to write documentation](https://swagger.io/specification/)

### Adding documentation for a schema (DTO)
When adding a new schema, simply add **_fileName_.yml** to one of the folders in the *schemas* folder, and write the documentation in the file. 
NOTE: do not add any other folders than the _response_ and _request_ folder. 

### Adding documentation for an endpoint
1. Add **_endpointName_.yml** to the *paths* folder and write documentation for the endpoint in that file. Make sure to add this part: 

    ```
    x-amazon-apigateway-integration:
    uri:
        Fn::Sub: 'arn:aws:apigateway:${AWS::Region}:lambda:path/2015-03-31/functions/${AWSServerlessAPIName.Arn}/invocations'
    passthroughBehavior: "when_no_match"
    httpMethod: "POST"
    type: "aws_proxy"
    ```
    at the root-level. This part makes sure to invoke the correct aws service. **NOTE**: Change _httpMethod_ correctly and _AWSServerlessAPIName_ to the name specified for the API in the file **_of-solver-template-env.yaml_** in _SolverService_.

2. In **openapi-spec.yml** under *paths*, add the name of the endpoint, then the request type, and a reference to the **_endpointName_.yml** file. E.g. 
```
paths:
  /tasks:
    get:
      $ref: "./paths/listTasks.yml"
```

# Feature toggling guide
This is a description of how to use feature toggles inside a solver. The solver's responsibility is only to define the name of the feature flag and the default value. The retrieval of the flag from launch darkly is handled by Solver Wrapper.

In solver api there exists three different methods in relation to feature toggles:
1. getSolverFeatureFlagDefaultValues() - this method is used by Solver Wrapper to retrieve the default values for the solver. It is very important that these are hardcoded into the Solver class in the Solver project
2. setFeatureFlags() - this method is used by Solver Wrapper to set the feature flags after it has retrieved the values from Launch Darkly
3. getFeatureFlags/() - this method is used for the Solver to retrieve the feature flag. Keep in mind that the solver should never request the methods getSolverFeatureFlagDefaultValues() and setFeatureFlags(), these should only be used by Solver Wrapper 