/*
 * Copyright (C) 2018 HAWKORE S.L. (http://hawkore.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hawkore.ignite.connector.examples.services.sources;

import java.io.Serializable;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.api.queue.message.TopicMessage;
import com.hawkore.ignite.extensions.internal.sources.topic.IgniteTopicMessagingListenerSvc;

/**
 * TopicListenerService.
 * 
 * <p>
 * 
 * Starts a topic listener that will process received messages
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class TopicListenerService extends AService {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.startTopicListener();
    }

    private void startTopicListener() {

        final String subscribeToTopic = TOPIC1;
        final String location = "a location for this topic listener within my app";

        // define a topic source listener
        IgniteTopicMessagingListenerSvc topicListener = new IgniteTopicMessagingListenerSvc(subscribeToTopic, location,
            connection);

        // start listening (subscribe to TOPIC1)
        topicListener.doStart((payload, ctx) -> {
            try {

                Serializable value = payload;

                if (value instanceof TopicMessage) {
                    final TopicMessage command = (TopicMessage) value;
                    value = command.getValue();
                }
                // here goes your implementation to process received
                // messages
                System.out.println("****************************************");
                System.out.println("***** " + topicListener.getTopic() + " source on "
                    + topicListener.getLocation() + " received a message: " + value);
                System.out.println("****************************************");

                return "my topic messsage process finish: " + value;

            } catch (final Exception e) {
                return e;
            }
        });
    }

}
