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

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.connector.examples.dataproviders.EntityProvider;
import com.hawkore.ignite.examples.entities.simple.Entity;
import com.hawkore.ignite.extensions.api.ingestion.IQueueDataProvider;
import com.hawkore.ignite.extensions.api.ingestion.IngestionProgressNotifier;
import com.hawkore.ignite.extensions.api.ingestion.IngestionResult;
import com.hawkore.ignite.extensions.api.queue.message.QueueMessage;
import com.hawkore.ignite.extensions.internal.operations.QueueIgniteOperationsSvc;

/**
 * QueueOperationsService
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class QueueOperationsService extends AService {

    /**
     * Sends a message to a queue listener
     * 
     * NOTE: a queue listener for "QUEUE1" is defined in
     * {@link com.hawkore.ignite.connector.examples.services.sources.QueueListenerService}
     * and will receive/process sent messages
     * 
     * @param message
     *            the message to send
     */
    public void publishQueueMessage(Object message) {

        String publishToQueue = QUEUE1;

        QueueIgniteOperationsSvc.queuePublish(message, publishToQueue, 60, TimeUnit.SECONDS,
            UUID.randomUUID().toString(),
            connection);
    }

    /**
     * Sends a message to a queue listener that will process it and will return
     * it back once processed
     * 
     * <p>
     * 
     * NOTE: a queue listener for "QUEUE1" is defined in
     * {@link com.hawkore.ignite.connector.examples.services.sources.QueueListenerService}
     * and will receive/process sent messages
     * 
     * <p>
     * 
     * Typical horizontal scaling (clustered application) where message is consume by ONE processor
     * located within an application instance on some node (the first that polls message from queue) and
     * returns processed message to invoker
     * 
     * 
     * @param message
     * 
     * @return processed message
     */
    public Object publishConsumeQueueMessage(Object message) {

        String publishToQueue = QUEUE1;

        return QueueIgniteOperationsSvc.queuePublishConsume(message, publishToQueue, 60, TimeUnit.SECONDS,

            // here message will be received once it was processed on
            // queue listener
            (queueMessage, theQueueName) -> {
                Object value = queueMessage;

                if (value instanceof QueueMessage) {
                    final QueueMessage command = (QueueMessage) value;
                    value = command.getValue();
                }

                // here you process received message
                System.out.println("****************************************");
                System.out.format("Received message from %s, millis = %s, message = %s %n", publishToQueue,
                    System.currentTimeMillis(), value);
                System.out.println("****************************************");

                return value;

            }, UUID.randomUUID().toString(), connection);
    }

    /**
     * 
     * Ingest data and publish to QUEUE2
     * 
     * <p>
     * 
     * NOTE: a queue listener for "QUEUE2" is defined in
     * {@link com.hawkore.ignite.connector.examples.services.sources.QueueListenerService}
     * and will receive/process sent messages
     * 
     * 
     * @param numberOfEntities
     *            - number of entities to generate
     * @param initialId
     *            - initial id
     * @return the ingestion result
     */
    public IngestionResult ingestEntities(int numberOfEntities, int initialId) {

        final String publishToQueue = QUEUE2;
        final int batchSize = 1000;
        final int numberOfIngesters = 4;
        final boolean async = false;

        return QueueIgniteOperationsSvc.queueIngestData(publishToQueue, new IQueueDataProvider<Entity, Entity>() {

            // thread-safe data supplier
            EntityProvider dataProvider = new EntityProvider(numberOfEntities, initialId);

            // thread-safe progress notifier
            IngestionProgressNotifier progressNotifier = new IngestionProgressNotifier() {

                private IngestionResult result = new IngestionResult();

                private int receivedFinishNotifications = 0;

                @Override
                public synchronized void notify(IngestionResult result) {

                    if (result.isFinished()) {
                        this.result.updateGlobal(this.result.getProcessed() + result.getProcessed(),
                            this.result.getSent() + result.getSent(),
                            Math.max(this.result.getElapsedTime(), result.getElapsedTime()));
                        receivedFinishNotifications++;
                        if (receivedFinishNotifications == numberOfIngesters) {
                            this.result.finish();
                        }
                    }
                }

                @Override
                public synchronized IngestionResult getIngestionResult() {
                    return this.result;
                }
            };

            @Override
            public Supplier<Entity> getDataSupplier() {
                return dataProvider;
            }

            @Override
            public Predicate<Entity> getFilter() {
                return null;
            }

            @Override
            public IngestionProgressNotifier getProgressNotifier() {
                return progressNotifier;
            }

            @Override
            public Function<Entity, Entity> getTransformer() {
                return p -> {
                    p.setData(p.getData() + " trasformed ");
                    return p;
                };
            }
        }, batchSize, null, numberOfIngesters, async, connection);
    }
}
