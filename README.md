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
    │   │           └───devo // contains main source packages
    │   └───resources
    └───test
        ├───java
        │   └───com
        │       └───icraus
        │           └───devo
        │               └───robot //tests are here 
        └───resources


```
![image](https://user-images.githubusercontent.com/19273690/164977363-bb379a19-fdb3-4830-95f7-1ffe6bb90214.png)

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
