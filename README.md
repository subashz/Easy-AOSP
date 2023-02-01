# Easy AOSP

## Introduction
This project aims to quickly import AOSP framework(java part) and it's faster and more efficient than other solutions like aidegen or idegen.
Internally, it imports resources & java per aosp modules and bootstrap the process. I recommend using intellij-remote development for better result.


## Configuration introduction

### scripts/aosp.gradle
This is the main file where you define your aosp source code path and such. Also, aosp modules, such as framework, services, SystemUI, Settings, etc. can be configured here.

### settings.gradle
settings.gradle is the configuration file of the subproject (or module).
If you need to add new aosp module, you need to configure/add it here.

### root directory build.gradle
The build.gradle file in the root directory is configured with many extended gradle scripts, which can be added to the corresponding scripts or newly added according to the actual situation.

```bash
apply from: 'scripts/config.gradle'
apply from: 'scripts/aosp.gradle'
```

### config.gradle
In addition to some basic configurations, an allModules array is also maintained. If a module is added in settings.gradle, it should be added to the allModules array of config.gradle at the same time.
The function of this allModules array is to facilitate each module to quickly depend on each other, such as:

```bash
rootProject.ext.allModules.each { dependence -> compileOnly project(dependence.value) }
```
Even though, this will have circular dependency and the real app gradle project doesnot allow this. , and the real app gradle project cannot do this. This is done here because we are only reading or changing the code for the convenience and we are still using `make` for building the project

## compile
This function cannot compile framework.jar or services.jar, please use the compilation method recommended by aosp.
To compile the demo app for debugging, you can check the settings.gradle comments, annotate some modules according to the prompts; and set enable_boot_jar and build_app to true in config.gradle.

If necessary, copy framework.jar and services.jar to system_libs

```bash
cp out/target/common/obj/JAVA_LIBRARIES/framework-minus-apex_intermediates/classes.jar ../system_libs/framework-minus-apex.jar
cp out/target/common/obj/JAVA_LIBRARIES/services_intermediates/classes.jar ../system_libs/services.jar
```

## How to install & run
1. Install intellij ultimate edition (you can get a free version using student account.) (OPTIONAL)
2. Clone this repo on your local/remote server
3. Use the remote-development feature of intellij ultimate
4. Use ssh connection and browse this cloned project
5. Change the aosp directory in scripts/aosp.gradle.
5. Make sure you have 'Android' selected in project preview. 
5. Get code completion and drawable preview.
6. Enjoy browsing..


## Pros & Cons
1. Leverage server resources for indexing & other
2. It's blazing fast and feels running the ide locally instead of anydesk session and android studio
3. No codegeneration inside aosp modules
4. Quickly add gradle modules & syncing
5. Expandable feature with custom gradle task

## Cons
1. Only for java & resources for now
2. 
