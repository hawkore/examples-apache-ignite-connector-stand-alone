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
import java.util.concurrent.TimeUnit;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.examples.entities.simple.Entity;
import com.hawkore.ignite.extensions.api.queue.message.QueueMessage;
import com.hawkore.ignite.extensions.internal.operations.CacheIgniteOperationsSvc;
import com.hawkore.ignite.extensions.internal.sources.queue.IgniteQueueListenerSvc;

/**
 * QueueListenerService.
 * 
 * <p>
 * 
 * Starts a queue listener that will process received messages
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class QueueListenerService extends AService {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.startQueueListener1();
        this.startQueueListener2();
    }

    private void startQueueListener1() {

        final String queueName = QUEUE1;
        
        final long timeout = 60;
        final TimeUnit timeoutUnit = TimeUnit.SECONDS;
        final int numberOfConsumers = 4;
        final int maxConcurrency = 8;
        final String location = "a location for this queue listener 1 within my app";

        // define a queue listener source
        IgniteQueueListenerSvc queueListener = new IgniteQueueListenerSvc(queueName, timeout, timeoutUnit,
            numberOfConsumers, maxConcurrency, connection, location);

        // start listening
        queueListener.doStart((payload, ctx) -> {

            try {

                Serializable value = payload;

                if (value instanceof QueueMessage) {
                    final QueueMessage command = (QueueMessage) value;
                    value = command.getValue();
                }

                // here goes your implementation to process received
                // messages
                System.out.println("****************************************");
                System.out.println("***** " + queueListener.getQueueName() + " queue source on "
                    + queueListener.getLocation() + " received a message: " + value);
                System.out.println("****************************************");

                return "my queue messsage process finishes, millis=" + System.currentTimeMillis() + ", message = "
                    + value;

            } catch (final Exception e) {
                return e;
            }
        });

    }

    // we are going to use this queue listener to store received messages into
    // CACHE2
    private void startQueueListener2() {

        final String queueName = QUEUE2;
        
        final long timeout = 60;
        final TimeUnit timeoutUnit = TimeUnit.SECONDS;
        final int numberOfConsumers = 4;
        final int maxConcurrency = 8;
        final String location = "a location for this queue listener 2 within my app";

        // queue listener source
        IgniteQueueListenerSvc queueListener = new IgniteQueueListenerSvc(queueName, timeout, timeoutUnit,
            numberOfConsumers, maxConcurrency, connection, location);

        // start listening
        queueListener.doStart((payload, ctx) -> {

            try {

                Serializable value = payload;

                if (value instanceof QueueMessage) {
                    final QueueMessage command = (QueueMessage) value;
                    value = command.getValue();
                }

                // store received data into cache
                if (value instanceof Entity) {
                    Entity ent = (Entity) value;
                    CacheIgniteOperationsSvc.cachePut(CACHE2, ent.getKey(), ent.getData(), null, 0, null, false, true,
                        connection);
                }

                return null;

            } catch (final Exception e) {
                return e;
            }
        });

    }
}
