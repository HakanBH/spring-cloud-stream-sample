# Getting Started
This is a sample Spring Cloud Stream project, which integrates with Kafka, RabbitMQ, Google PubSub & Azure Event Hubs.

## TODO:
1. Add samples with dynamic binder resolution for both sink & source flow
2. README.md imrovements:
- document more binder/binding configuration options;  
- document how to configure partitioning, consumer groups & DLQ
- Add reference documentation for setting up cloud services (Google PubSub & Azure Event Hubs)

## Running with Kafka binder
1. Set the `spring.cloud.stream.defaultBinder` property in application yaml to `kafka-1` to set Kafka as the default binder. 

2. Configure Kafka connection
- To run with local a Kafka, you can use the docker-compose file in this repository to quickly start a Kafka instance:  
`docker-compose -f deployment/docker-compose-kafka.yaml up -d`  
The application is configured to connect to a local Kafka by default, so no need of any configuration changes.
- To run with a remote Kafka cluster, you need to provide the appropriate connection details in `application.yaml` under `spring.cloud.stream.binders.kafka-1`.
```yaml
        kafka-1:
          type: kafka
          environment:
            spring.cloud.stream.kafka.binder:
              replicationFactor: 1
              brokers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
              jaas.options.username: ${KAFKA_USERNAME}
              jaas.options.password: ${KAFKA_PASSWORD}
              jaas.loginModule: org.apache.kafka.common.security.plain.PlainLoginModule
              configuration:
                key.serializer: org.apache.kafka.common.serialization.StringSerializer
                security.protocol: ${KAFKA_SECURITY_PROTOCOL:PLAINTEXT}
                sasl.mechanism: PLAIN
```

## Running with RabbitMQ binder
1. Set the `spring.cloud.stream.defaultBinder` property in application yaml to `rabbitmq-1` to set RabbitMQ as the default binder. 

2. Configure RabbitMQ
- To run with local RabbitMQ, you can use the docker-compose file in this repository to quickly start a RabbitMQ instance:  
`docker-compose -f deployment/docker-compose-rabbitmq.yaml up -d`  
The application is configured to connect to a local RabbitMQ by default, so no need of any configuration changes.
- To run with a remote RabbitMQ server, you need to provide the appropriate connection details in `application.yaml` under `spring.cloud.stream.binders.rabbitmq-1`.
```yaml
        rabbitmq-1:
          type: rabbit
          environment:
            spring.rabbitmq:
              host: localhost
              port: 5672
              username: guest
              password: guest
              virtual-host: /
```
## Running with Google Cloud PubSub
1. Set the `spring.cloud.stream.defaultBinder` property in application yaml to `pubsub-1` to set PubSub as the default binder. 
2. Configure PubSub connection details. 
- Uncomment the configurations under `spring.cloud.gcp` node in `application.yaml`
```yaml
#    gcp:
#      project-id: ${GCP_PROJECT_ID}
#      credentials.location: ${GCP_CREDENTIALS_FILE_LOCATION}
```
- Provide values for the GCP_PROJECT_ID, GCP_CREDENTIALS_FILE_LOCATION environment variables.


## Running with Azure Event Hubs
1. Set the `spring.cloud.stream.defaultBinder` property in application yaml to `eventhub-1` to set Event Hubs as the default binder. 

2. Configure Event Hubs connection details. To do that, you can either:
 - Provide values for the EVENTHUB_CONNECTION_STRING, EVENTHUB_STORAGE_ACCOUNT, EVENTHUB_STORAGE_ACCOUNT environment variables.
 - Hardcode the configurations in `application.yaml` under `spring.cloud.stream.binders.eventhub-1` node.
```yaml
        eventhub-1:
          type: eventhub
          environment:
            spring.cloud.azure.eventhub:
              connection-string: ${EVENTHUB_CONNECTION_STRING}
              checkpoint-storage-account: ${EVENTHUB_STORAGE_ACCOUNT}
              checkpoint-access-key: ${EVENTHUB_STORAGE_ACCOUNT}
```
