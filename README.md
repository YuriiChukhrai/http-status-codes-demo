**Table of Contents**

## Run Spring App.
`$> mvn spring-boot:run`

## Run tests
`$> mvn clean -T3 verify -Dtest.suite=before_artifact` - it's start to execute unit test suite, before the building of application artifact. And generate Allure report log's</br>
`$> mvn clean -T3 verify -Dtest.suite=after_artifact` - it's </br>


## Reports
`$> mvn allure:report`

## Endpoints

```shell
$> mvn allure:report
```

# Diagram

```mermaid
  graph LR;
      HTTP-request#1--GET-->Dispatcher-Servlet;
      HTTP-request#2--POST-->Dispatcher-Servlet;
      HTTP-request#3--DELETE-->Dispatcher-Servlet;
      Dispatcher-Servlet-->Rest-Controller;
      Rest-Controller-->Service;
      Service-->Repository;
      Repository-->DB[(DB)];      
```

```mermaid
  graph TD;
      HTTP-request#1 & HTTP-request#2 & HTTP-request#3--GET/POST/DELETE/OPTIONS-->Spring-Dispatcher-Servlet;
      Spring-Dispatcher-Servlet--'/api/v1/info'-->InfoController;
      Spring-Dispatcher-Servlet--'/api/v1/http/code'-->HttpCodeControllerImpl;
      
      Spring-Dispatcher-Servlet--'/api/v1/http/code/example'-->ExampleResponseController;
      InfoController & ExampleResponseController-->HttpCodeServiceImpl;
      HttpCodeController-->HttpCodeControllerImpl;
      
      HttpCodeControllerImpl-->HttpCodeServiceImpl;
      HttpCodeService-->HttpCodeServiceImpl;
      HttpCodeServiceImpl-->HttpCodeCustomRepositoryImpl;
      
      HttpCodeRepository-->HttpCodeCustomRepositoryImpl;
      HttpCodeCustomRepository & JpaRepository-->HttpCodeRepository;
      HttpCodeCustomRepositoryImpl--SQL-->DB[(H2)];
```



# References
* [Mermaid lets you create diagrams and visualizations using text and code.](https://mermaid-js.github.io/mermaid)