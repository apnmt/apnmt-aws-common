/*
 * SqsMessage.java
 *
 * (c) Copyright AUDI AG, 2022
 * All Rights reserved.
 *
 * AUDI AG
 * 85057 Ingolstadt
 * Germany
 */
package de.apnmt.aws.common.test;

public class SqsMessage {

    public String Type;
    public String MessageId;
    public String TopicArn;
    public String Subject;
    public String Message;
    public String Timestamp;
    public String SignatureVersion;
    public String Signature;
    public String SigningCertUrl;
    public String UnsubscribeUrl;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getTopicArn() {
        return TopicArn;
    }

    public void setTopicArn(String topicArn) {
        TopicArn = topicArn;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getSignatureVersion() {
        return SignatureVersion;
    }

    public void setSignatureVersion(String signatureVersion) {
        SignatureVersion = signatureVersion;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getSigningCertUrl() {
        return SigningCertUrl;
    }

    public void setSigningCertUrl(String signingCertUrl) {
        SigningCertUrl = signingCertUrl;
    }

    public String getUnsubscribeUrl() {
        return UnsubscribeUrl;
    }

    public void setUnsubscribeUrl(String unsubscribeUrl) {
        UnsubscribeUrl = unsubscribeUrl;
    }
}
