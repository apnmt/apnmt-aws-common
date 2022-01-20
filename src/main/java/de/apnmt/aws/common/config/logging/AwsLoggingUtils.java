/*
 * LoggingUtils.java
 *
 * (c) Copyright AUDI AG, 2022
 * All Rights reserved.
 *
 * AUDI AG
 * 85057 Ingolstadt
 * Germany
 */
package de.apnmt.aws.common.config.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.contrib.jackson.JacksonJsonFormatter;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.cloudwatchlogbackappender.CloudWatchAppender;
import de.apnmt.aws.common.config.AwsCloudProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AwsLoggingUtils {

    private static final Logger log = LoggerFactory.getLogger(AwsLoggingUtils.class);

    public static void addCloudWatchAppender(LoggerContext context, ObjectMapper objectMapper, String appName, String region, AwsCloudProperties awsCloudProperties) {
        log.info("Initializing CloudWatch loggingProperties");
        CloudWatchAppender cloudWatchAppender = new CloudWatchAppender();
        cloudWatchAppender.setContext(context);
        PatternLayout patternLayout = new PatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern("%d{dd.MM.yyyy HH:mm:ss.SSS} [%thread] %-5level %class - %msg%n");
        patternLayout.start();
        JsonLayout jsonLayout = new JsonLayout();
        JacksonJsonFormatter jacksonJsonFormatter = new JacksonJsonFormatter();
        jacksonJsonFormatter.setObjectMapper(objectMapper);
        jsonLayout.setJsonFormatter(jacksonJsonFormatter);
        cloudWatchAppender.setLayout(jsonLayout);
        cloudWatchAppender.setName("ASYNC_CLOUDWATCH");
        cloudWatchAppender.setAccessKeyId(awsCloudProperties.getCredentials().getAccessKey());
        cloudWatchAppender.setSecretKey(awsCloudProperties.getCredentials().getSecretKey());
        cloudWatchAppender.setRegion(region);
        cloudWatchAppender.setLogGroup(appName);
        cloudWatchAppender.setLogStream(awsCloudProperties.getLogging().getCloudWatch().getLogStream());
        cloudWatchAppender.setInternalQueueSize(awsCloudProperties.getLogging().getCloudWatch().getQueueSize());
        cloudWatchAppender.setPrintRejectedEvents(true);
        cloudWatchAppender.start();
        context.getLogger("ROOT").addAppender(cloudWatchAppender);
    }

}
