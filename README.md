# Sanctioned People Service

[![N|Solid](https://www.pngfind.com/pngs/m/53-535670_spring-framework-logo-spring-boot-hd-png-download.png)]()

## General Info

- App is working on Port: 10130
- Using Java 17
- Using Gradle
- Using H2 database
- Swagger URL:
  ```sh 
  http://localhost:10130/swagger-ui.html
  ```
- H2 database console URL:
  ```sh 
  http://localhost:10130/h2-console
  ```

## Database

- **Driver Class**: org.h2.Driver
- **JDBC URL**: jdbc:h2:mem:sanctionsDB
- **User Name**: admin
- **Password**: changeit

```sh 
| ID | PERSON_NAME          |
|----|----------------------|
| 1  | OSAMA BIN LADEN      |
| 2  | VLADIMIR PUTIN       |
| 3  | MARIA VORONTSOVA     |
| 4  | DMITRY MEDVEDEV      |
| 5  | ALEXANDER LUKASHENKO |
| 6  | SERGEY LAVROV        |
| 7  | VLADIMIR KOZHIN      |
| 8  | SERGEI IVANOV        |
| 9  | YEVGENY MIKHAILOV    |
| 10 | VALERY KULIKOV       |
  ```
