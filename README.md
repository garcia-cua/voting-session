# Voting Sessions

# Requirements

- Java 11
- SpringBoot
- H2 Database
- Maven
- AWS account for using SQS service

# Inserting AWS credentials configuration

* Go to application.yml e replace the amazon.accessKey and amazon.secretKey with your account credentials

# Running the project

Two ways to run the project
```bash
$ mvn spring-boot:run
```
```bash
$ mvn clean package && java -jar target/voting-sessions-1.0.0-SNAPSHOT.jar
```

# Payloads for test

* POST request in http://localhost:8080/api/v1/associates

```json lines
{
  "name": "João da Silva"
}
```

* POST request in http://localhost:8080/api/v1/topics (example)

```json lines
{
  "description": "Descrição da pauta"
}
```

* POST request in http://localhost:8080/api/v1/sessions (example)

```json lines
{
  "topic": {
    "id": 13
  }
}
```

* POST request in http://localhost:8080/api/v1/votes (example)

```json lines
{
  "associate": {
    "id": 1
  },
  "session": {
    "id": 14
  },
  "answer": "N"
}
```

The vote will be recorded and a JMS message sent to the "session-result" queue. When consuming the message, will be show the voting result.

* Example log result

```shell
$ INFO  [com.fernando.votingsessions.service.SessionService] resultClosedSession: Resultado: (Sim=3, Não=1)
```