package com.lucasdev.desafioanotaai.services.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {
    private final AmazonSNS snsClient;
    private final Topic catalogTopic;

    public AwsSnsService(AmazonSNS snsClient, @Qualifier("catalogTopic") Topic catalogTopic) {
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO message) {
        System.out.println(message.message());
        PublishResult publishResult = this.snsClient.publish(catalogTopic.getTopicArn(), message.message());
        System.out.println(publishResult);
    }
}