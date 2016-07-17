###JCompiler  
 Java source code compiler application using javax.tools package classes. Basically this application designed to compile the Java source code and outputs the compilation errors, not only java files it also builds the maven projects.  
 Components: Rest Service and UI.
##Approach.  
 1. To compile Java file, javax.tools package.  
 2. To compile maven project, using maven invoker plugin.  
 
###Pre-requisites.  
 Below tools/packages must be installed on targeted host prior to run this application.  
  1. JDK 7 or higher.  
  2. Gradle 2.11 or higher and set GRADLE_HOME/bin path.  
  3. Maven 3.3.9  and set the **MAVEN_HOME** in System Environment.  
  4. internet connectivity.  
  
### Technology Stack.  
  1. Spring framework.  
  2. Jersey as REST implementation.  
  3. Hibernate.  
  4. H2 embedded database.  
  5. Gradle and Maven build tools.  
  6. logback as logging framework.  
  7. Lombok utility to reduce biolarplate code.  
  
### Configuration.
  1. Rest API.
     default it runs on 8080 with binding IP as localhost, to change this configuration refer **java-compiler/src/main/resources/application.properties** file.  
	 Since it runs on Embedded database, you dont need to configure any db details.  
	 
  2. UI.  
     Default UI tries to make requests to ```http://localhost:8080/``` endpoint. If you make any changes to REST API regarding baseURL, same should be reflected in UI.
	 **java-compiler/web/scripts/app.js** is the file holds this configuration search for baseURL and update as per REST API configuration.  
	 
  3. NO external DB Configuration required.
  
### How to run.
  1. Bouncing up the Rest API.    
     Step into the directory **/java-compiler** and run the below commands to bootstrap the server.
      1. Windows  
	     >gradlew.bat bootRun
      2. Linux.
        $gradlew bootRun
  2. Open web UI.
     Step into the directory **/java-compiler/web** and Open JCompilerUI.html in any your favorite browser.
	 
  3. Logs can be found in application root **java-compiler/logs directory.
	 
###RequestURL and JSon input.
  

### Sample input.
  1. Use Java/Maven project files under **/sample-files** to upload and verify.