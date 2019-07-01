# trivago
A booking engine for hotels

### Clone repository
- Clone the repository from https://github.com/mrclhl/trivago

### Build the .jar file
- In the terminal, go to the project folder (/booking)
- Execute 'mvn clean install' to build the .jar file

### Run the application
- Execute 'docker-compose' to run the postgresql database and the application
- The application will be available via http://localhost:8080/

### Documentation
##### Documentation of the endpoints is available via
* /availability.html
* /reservation.html
* /verification.html

### Kill the application
* Execute 'docker-compose down'
* Alternatively 'docker-compose down & docker-compose rm' to kill and destroy the container
