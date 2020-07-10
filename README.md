# PSE 2020 VisAQ
VisAQ is planned and developed during the PSE-project at the [Karlsruhe Institute of Technology](https://www.kit.edu) in cooperation with [TECO](https://www.teco.edu/) .
VisAQ visualizes air quality data from the project [SmartAQNet](https://www.smartaq.net/de).

## About
This repository contains the backend-part of the project. The backend is written as a Spring Application in Java. It can be build with maven and then executed on a server connected to the internet. After that the REST-API of the backend will be reachable at 
> {addressOfServer}:8080

## Building and running
To build the project navigate to the submodule VisAQBackend then run
```bash
mvn package
```
now the project will be build and a runnable jar will be created at 
```
../target/
```