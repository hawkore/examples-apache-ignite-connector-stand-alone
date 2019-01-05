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
package com.hawkore.ignite.connector.examples.services.operations;

import java.io.Serializable;
import java.util.UUID;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.api.config.common.enums.IgniteMessagingSubscribersGroup;
import com.hawkore.ignite.extensions.internal.operations.TopicMessagingIgniteOperationsSvc;

/**
 * TopicOperationsService
 * 
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 */
public class TopicOperationsService extends AService {

    /**
     * Send a message to topic subscribers
     * 
     * <p>
     * 
     * NOTE: a topic listener for "TOPIC1" is defined in
     * {@link com.hawkore.ignite.connector.examples.services.sources.TopicListenerService}
     * and will receive/process sent messages
     * 
     * @param message
     *            to send
     */
    public void sendTopicMessage(Serializable message) {

        final String topic = TOPIC1;
        final IgniteMessagingSubscribersGroup subscribersGroup = IgniteMessagingSubscribersGroup.ANY;
        final String correlationId = UUID.randomUUID().toString();

        TopicMessagingIgniteOperationsSvc.topicPublish(topic, message, subscribersGroup, correlationId, connection);
    }

}
