This is a Shared Library for Jenkins. It contains tooling common for the different build scripts we have for our Unity projects.

# Using the library

Do the following at the top of your Jenkinsfile:

```
@Library("JenkinsBuildScripts@<version>") _
```

# Developing the library

Logic is in src/main/...
Variables are exposed to the Jenkinsfile via vars/...

Run tests by doing `gradlew test`.
