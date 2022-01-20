package de.apnmt.aws.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Application.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "cloud.aws", ignoreUnknownFields = true)
public class AwsCloudProperties {

    private final AwsCloudProperties.Credentials credentials = new AwsCloudProperties.Credentials();
    private final AwsCloudProperties.Logging logging = new AwsCloudProperties.Logging();

    public Credentials getCredentials() {
        return credentials;
    }

    public Logging getLogging() {
        return logging;
    }

    public static class Credentials {
        private String accessKey;
        private String secretKey;

        public Credentials() {
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }

    public static class Logging {
        private final AwsCloudProperties.Logging.CloudWatch cloudWatch = new AwsCloudProperties.Logging.CloudWatch();

        public Logging() {
        }

        public AwsCloudProperties.Logging.CloudWatch getCloudWatch() {
            return this.cloudWatch;
        }

        public static class CloudWatch {
            private boolean enabled = false;
            private String logStream = "";
            private int queueSize = 512;

            public CloudWatch() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getLogStream() {
                return logStream;
            }

            public void setLogStream(String logStream) {
                this.logStream = logStream;
            }

            public int getQueueSize() {
                return this.queueSize;
            }

            public void setQueueSize(int queueSize) {
                this.queueSize = queueSize;
            }
        }
    }

}
