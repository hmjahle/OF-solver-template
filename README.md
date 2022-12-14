# Solver Template Guide 

If solver is not using or-tools, skip to section Environmental variables. Remember to have gradle installed.

## Copy into new project

Copy the whole project. If you are not going to use or-tools, do not copy or-tools-centos and or-tools-osx

###Naming rules for classes and packages

- The solver class for a given solver project should be named with upper case for the first letter of the SOLVER_ARTIFACT_ID + «Solver», e.g. SolvertemplateSolver
- Similarly for validator class: Solver_artifact_id + «Validator», e.g. SolvertemplateValidator
- SolvertemplateSolver should be in package com/visma/of/solvertemplate/solver/
- SolvertemplateValidator should be in package com/visma/of/solvertemplate/validator/

## Installation

SDKman is recommended to install specific version of Gradle. SDKman installation for OSX can be found [here](https://sdkman.io/install). 

When SDKman is installed, install the newest Gradle version by using e.g:

```bash
sdk install gradle 7.0
```

You also need to install swagger-cli, using this command:

```
npm install -g swagger-cli
```

Finish by running the following command:

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


## Troubleshooting
If IntelliJ fails to resolve classes in src and test, go to File -> Invalidate Cache / Restart.
This should hopefully resolve the issue.
Build the project again and make sure environement variables are set for JFROG_ACCESS_KEY and JFROG_USERNAME.
If there are any issues with the dependencies, run
```
gradle clean --refresh-dependencies
```
and refresh in the Gradle tool window.

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
NOTE: do not add any other folders than the _response_ and _request_ folder. Also, it is important that there is no new line in the file 
openapi-spec.yml between the end of the description and the paths-part.

### Adding documentation for an endpoint
1. Add **_endpointName_.yml** to the *paths* folder and write documentation for the endpoint in that file.
2. In **openapi-spec.yml** under *paths*, add the name of the endpoint, then the request type, and a reference to the **_endpointName_.yml** file. E.g. 
```
paths:
  /tasks:
    get:
      $ref: "./paths/listTasks.yml"
```

NOTE: do not add the *servers* components in the openapi-spec file, as this is added by Solver Service.

### Adding status codes for an endpoint
The start endpoint should not contain any status codes, this is handled inside solver service
For result endpoint it should only contain the success status codes. The other status codes should be handled by solver service and is general for all solvers

# Feature toggling guide
This is a description of how to use feature toggles inside a solver. The solver's responsibility is only to define the name of the feature flag and the default value. The retrieval of the flag from launch darkly is handled by Solver Wrapper.

In solver api there exists three different methods in relation to feature toggles:
1. getSolverFeatureFlagDefaultValues() - this method is used by Solver Wrapper to retrieve the default values for the solver. It is very important that these are hardcoded into the Solver class in the Solver project
2. setFeatureFlags() - this method is used by Solver Wrapper to set the feature flags after it has retrieved the values from Launch Darkly
3. getFeatureFlags/() - this method is used for the Solver to retrieve the feature flag. Keep in mind that the solver should never request the methods getSolverFeatureFlagDefaultValues() and setFeatureFlags(), these should only be used by Solver Wrapper 