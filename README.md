# Devo Robot.

![library branch workflow](https://github.com/icraus/devo/actions/workflows/CI.yaml/badge.svg??branch=master)


## Quick start
The application consists of **Reusable Java Library**, **Frontend**:
- **Reusable Java Library** is written in **Java**.
- **UI Frontend application** is written in **Java fx**.

## Dependencies
- **Java 11 or higher**
- **JavaFX**

## Project structure
```
└───src
    ├───main
    │   ├───java
    │   │   └───com
    │   │       └───icraus
    │   │           └───devo //contains java files
    │   └───resources
    └───test
        ├───java
        └───resources



```
![image](https://user-images.githubusercontent.com/19273690/164977300-fb31510a-abb9-48b5-8c7f-e3d91d348b8c.png)

## Getting started with Java Library:
- clone the project using.
  ```
    git clone https://github.com/Icraus/devo.git -b master
  ```
- run
  ```
  gradlew :release
  ```
  this should generate release with jar file.

## Getting started with UI Client:
- clone the project using.
  ```
    git clone https://github.com/Icraus/devo.git -b FE
  ```
- run
  ```
  gradlew :run
  ```
## Notes about the design:
- First I have to say that I would have chosen other approaches to deal with
  these tasks as this is not a production code.
  For example:
  * I would have used better animations.
  * Better UI.
 
