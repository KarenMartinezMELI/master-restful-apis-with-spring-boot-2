# master-restful-apis-with-spring-boot-2

---------------------------------------------------------------------------------------
Step-00: Introduction

---------------------------------------------------------------------------------------
Step-00: Create new git branch in local git repo and remote github repo
    - Verify we are in master branch    
        - git status
    - Create new branch
        - git checkout -b 05-Validations-GlobalExceptionHandler
    - Create new branch in remote github and setup upstream   
        - git push --set-upstream origin 05-Validations-GlobalExceptionHandler
    - Verify new branch in remote github & IDE GIT Perspective             
        - https://github.com/stacksimplify/springboot-buildingblocks     

---------------------------------------------------------------------------------------
Step-01: Implement Bean Validation
    - Entity Layer
        - Implement validations on User Entity
            - @NotEmpty(message = "Username is Mandatory field. Please provide username")
            - @Size(min=2, message="FirstName should have atleast 2 characters")            
    - Test using POSTMAN
         - Service: Create User Service
         - Request body
            - For firstname, give only 1 character  ("firstname": "K")
            - Send username as empty  ("username": "")
        - Verify Response on POSTMAN (JPA exception will be thrown)
    - Controller Layer
        - Enable Bean validation using @Valid
    - Test using POSTMAN
        - Service: Create User Service
        - Request body
            - For firstname, give only 1 character  ("firstname": "K")
            - Send username as empty  ("username": "")
        - Verify Response
            - HTTP Status 400 Bad request - with default response body 
            - MethodArgumentNotValidException resolved from logs      

--------------------------------------------------------------------------------------
Step-02: Implement Custom Global Exception Handler - MethodArgumentNotValidException
    - Exception Layer - CustomErrorInfo class
        - Create a new class CustomErrorInfo                  
            - Define Variables date, message, errordetails
        - Add Fields Constructor
        - Add Getters                
    - Exception Layer - CustomGlobalExceptionHandler
        - Create a new class CustomGlobalExceptionHandler
        - extends ResponseEntityExceptionHandler
        - @ControllerAdvice
            - Global code that can be applied to a wide range of controllers.
        - Implement & Override handleMethodArgumentNotValid from ResponseEntityExceptionHandler
    - Test using POSTMAN
        - Method: POST 
        - Service: Create User Service
        - URI: http://locahost:8080/users
        - Request body
            - For firstname, give only 1 character  ("firstname": "K")
            - Send username as empty  ("username": "")
        - Verify Response
            - Custom Error Response
            - HTTP 400 Bad Request        

---------------------------------------------------------------------------------------
Steo-03: Implement "HttpRequestMethodNotSupportedException" in Custom Global Exception Handler
    - Test using POSTMAN
        - Provide PATCH method for create user
        - Verify response code and body   
    - Exception Layer -  CustomGlobalExceptionHandler
        - Implement & Override handleHttpRequestMethodNotSupported from ResponseEntityExceptionHandler 
    - Test using POSTMAN
        - Provide PATCH method for create user
        - Verify response code and body  

---------------------------------------------------------------------------------------
Step-04: Implement ExceptionHandler for custom exception like "UserNameNotFoundException"
    - Exception Layer
        - Create a new class "UserNameNotFoundException" 
            - extends Exception
            - Generate constructor from Super class
    - Controller Layer
        - For getUserbyUsername Method, Throw UserNameNotFoundException if that user doesnt exists in Repository.
    - Test using POSTMAN
        - Method: GET
        - URI: http://localhost:8080/users/byusername/abcd
        - Verify default spring Exception (Response code HTTP 500)
    - Exception Layer -  CustomGlobalExceptionHandler
        - Create handleUserNameNotFoundException method
        - Annotate it with @ExceptionHandler
    - Test using POSTMAN
        - Method: GET
        - URI: http://localhost:8080/users/byusername/abcd
        - Verify the Response Body and HTTP Status Code 404  

---------------------------------------------------------------------------------------
Step-05: Path Variables Validation & Handling ConstraintViolationException using CustomGlobalExceptionHandler
    - Contoller Layer
        - getUserById method: Add @Min(1) for Path Variable
        - Add @Validated annotation to UserController class
    - Test using POSTMAN
        - Method: GET
        - URI: http://localhost:8080/users/0
        - Verify default spring Exception (Response code HTTP 500)
    - Exception Layer -  CustomGlobalExceptionHandler
        - Create handleConstraintViolationException class 
        - Annotate it with @ExceptionHandler
    - Test using POSTMAN
        - Method: GET
        - URI: http://localhost:8080/users/0
        - Verify the Response Body and HTTP Status Code - 400   

---------------------------------------------------------------------------------------
Step-06: Implement Global Exception Handling using RestControllerAdvice
    - Exception Layer - CustomGlobalExceptionHandler
        - Comment @ControllerAdvice
        - Test to ensure controller advice is not in action for getUserByUsername
    - Exception Layer 
        - Create new class GlobalRestControllerAdviceExceptionHandler
        - Annotate with @RestControllerAdvice
        - Handle UserNameNotFoundException        
            - Create a mehtod notFound 
            - Annotate it with @ExceptionHandler
            - Annotate it with @ResponseStatus - 404
    - Test using POSTMAN
        - Method: GET
        - URI: http://localhost:8080/users/byusername/abcd12
        - Verify the Response Body and HTTP Status Code  - 404          

---------------------------------------------------------------------------------------
Step-07: Note about switching between @ControllerAdvice and @RestControllerAdvice

---------------------------------------------------------------------------------------
Step-08: GIT Commit, Push, Merge to Master and Push

---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------