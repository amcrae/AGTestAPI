Auto & General Technical Task
================================

Implementation of 
  https://join.autogeneral.com.au/swagger-ui/?url=/swagger.json
  

A Utopian Design
-----------------
Not enough time for me to complete this design as I have not used RDS or Dynamo or API Gateway before and have not used Postgres in Docker yet.
However with more time ideally I'd like to set it up like this:

Four main components would be:

>  A`[ AWS API Gateway router to B and C.    ]`  
>  B`[ AWS Lambda( /tasks/validateBrackets ) ]`  
>  C`[ SpringBoot( /todo/** )                ]`  
>  D`[ AWS RDS PostGres( ToDoItems )         ]`     

These would be built like:

* validateBracket function can be tested in dev environment with nodejs or Rhino.
* AWS API Gateway routing all the /v1/tasks/validateBrackets into the Lambda function.  
	  
	  
* One Eclipse project containing the /todo implementation as JAX-RS & AWS JDBC with Spring Boot.
* One generated Docker Image of Java 8 + Todo JAR running Todo API.
* AWS Container running this docker image.
* AWS API Gateway routing all the /v1/todo/** requests into an instance of this container.    
	  
	  
* For such a simple information model as ToDo list items, a NoSQL store should be used. But I don't have the time to learn how to use these non-JDBC interfaces. Instead I could use an RDS Postgres database running in AWS, which has both classic SQL and JSON datatype support so can be used both ways for the ToDo microservices.
* The DB would be initialised by the first instance of app C that connects to it, where C can detect that the right schema does not exist yet, and creates it.  
	  
	  
* Some kind of AWS internal networking setup which only allows container 'C' to access the database 'D'.
  
  
The value of designing it this way would be:
* By using the API gateway the different container services would be effectively mapped into the same URL address space from the client's perspective.
* Therefore the different functions in the API (the microservices) can be scaled independently to deal with load. 
* The different services can be implemented with different technologies appropriate to their functionality or performance requirements. e.g. The brackets validation has output which is determined entirely by the input message, it is stateless, therefore it is a function and can be implemented efficiently by a javascript function in Lambda.
* The extra /v1/ in the location allows a new package release of the API later on with a place reserved in address space for updated clients to use. 
* The database is separate and various caching ideas are possible here to reduce reads of the database from the todo GET requests.  


Initial Implementation
--------------------------
Due to time and experience constraints, the utopian design could not have been achieved. Instead, here is a minimalist design which lacks scalability and data persistence.

- One project containing both the /tasks and /todo implementation as JAX-RS & Spring Boot.
- One generated Docker Image of Java 8 + API JAR for running both services plus an in-memory non-persistent HSQLDB database.
- One container running this docker image.
- Single-node cluster running that container.


How To Build
---------------

	mvn package
	
	docker build -t agtestapi .

How to Run
------------

Run docker image locally:

	docker run -it --rm --network=bridge -p 127.0.0.1:8080:8080 --name agtestapi agtestapi:latest

Deploy docker image on AWS:

	docker image tag  agtestapi  nnnnnnnnnnnn.dkr.ecr.ap-southeast-2.amazonaws.com/research
	
	aws ecr get-login --no-include-email
	
	docker login -u AWS  https://nnnnnnnnnnnn.dkr.ecr.ap-southeast-2.amazonaws.com
	
	docker push nnnnnnnnnnnn.dkr.ecr.ap-southeast-2.amazonaws.com/research
	  

The EC2 launch option creates a VPC and SG management puzzle, so it was easier to create the container with FARGATE compatibility and create a Task with the Fargate launch type.
A free tier t2.tiny size instance of 512MB and 256/1024 cpu slices was plenty to run this task.
