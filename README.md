# trivago
A booking engine for hotels

## Entity Map
An entity map can be found in the top level of the project folder, the file is called 'EntityMap.png'.

## Database script
The database script can be found under ./sql/schema.sql with creation of the database tables and the master data.

## Setup from github
If you want to download the repository from github, follow beneath steps:
#### Clone repository
- Clone the repository from https://github.com/mrclhl/trivago

#### Build the .jar file
- In the terminal, go to the project folder (/booking)
- Execute 'mvn clean install' to build the .jar file


- Go to the step 'Run the application'.

## Setup with downloaded .zip file
If you retrieved a .zip file containing the project folder, you do not need to build the .jar file. You can unzip the 
directory anddirectly go to the next step 'Run the application'.

## Run the application
- Execute 'docker-compose' to run the postgresql database and the application
- The application will be available via http://localhost:8080/

#### Documentation
##### Documentation of the endpoints is available via
* /docs/availability.html
* /docs/reservation.html
* /docs/verification.html

#### Kill the container
* Execute 'docker-compose down'
* Alternatively 'docker-compose down & docker-compose rm' to kill and destroy the container

## Side notes
There are definitely some architectural decisions that can be discussed, e.g. 
* input validation could also be done in the service layer
* I've decided to NOT use hibernate entities for the sake of keeping the architecture clean. Using hibernate entities will
couple the application closely to the database. Instead, I am using POJO's to remain flexible and be apple to work with
any data source more easily.

* A full test coverage could obviously not be done given the time constraint and tasks. However, there is a good coverage
for controller tests including documentation and the services.

* I tried to follow kotlin best practices, however there might be places where it could be improved.

* I am happy to discuss your architecture- and code related questions.

