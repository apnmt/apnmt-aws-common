package de.apnmt.aws.common.test;

import java.io.IOException;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@DirtiesContext
@Testcontainers
public abstract class AbstractEventSenderIT {

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.13.0")).withServices(Service.SNS, Service.SQS);
    @Autowired
    protected AmazonSQSAsync sqsAsync;
    @Autowired
    protected ObjectMapper objectMapper;

    @DynamicPropertySource
    static void overrideConfiguration(DynamicPropertyRegistry registry) {
        registry.add("cloud.aws.sqs.endpoint", () -> localStack.getEndpointOverride(Service.SQS));
        registry.add("cloud.aws.sns.endpoint", () -> localStack.getEndpointOverride(Service.SNS));
        registry.add("cloud.aws.credentials.access-key", localStack::getAccessKey);
        registry.add("cloud.aws.credentials.secret-key", localStack::getSecretKey);
    }

    @BeforeEach
    void beforeAll() throws IOException, InterruptedException {
        localStack.execInContainer("awslocal", "sns", "create-topic", "--name", getTopic());
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", getQueue());
        localStack.execInContainer("awslocal", "sns", "subscribe", "--topic-arn", "arn:aws:sns:us-east-1:000000000000:" + getTopic(), "--protocol", "sqs", "--notification" +
                "-endpoint", "arn:aws:sqs:us-east-1:000000000000:" + getQueue());
    }

    protected abstract String getTopic();

    protected abstract String getQueue();

}
