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
package com.hawkore.ignite.connector.examples.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hawkore.ignite.connector.examples.services.operations.QueueOperationsService;

/**
 * QueueOperations REST Controller
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Path("/queues")
public class QueueOperationsController {

    @Autowired
    private QueueOperationsService queueOperationsService;

    /**
     * Data ingestion into queue
     *
     * @param numberOfEntities
     *            number of entities to generate
     * @param initialId
     *            initial id
     * @return {@code IngestionResult}
     */
    @GET
    @Path("/ingestEntities")
    @Produces("application/json")
    public Response ingestEntities(@QueryParam("count") int numberOfEntities,
        @QueryParam("initialId") int initialId) {
        return Response
            .status(200)
            .entity(queueOperationsService.ingestEntities(numberOfEntities, initialId)).build();
    }

    /**
     * Sends a message to a queue
     * 
     * @param message
     *            the message to publish
     * 
     * @return a simple message
     */
    @POST
    @Path("/publish")
    @Produces("text/plain")
    public Response queuePublish(@QueryParam("message") String message) {

        queueOperationsService.publishQueueMessage(message);

        return Response
            .status(200).entity(String.format("Message '%s' published", message)).build();
    }

    /**
     * Sends a message to a queue listener that will process it and will return it back
     * once processed
     * 
     * @param message
     *            the message to process
     * 
     * @return result of processing by queue listener
     */
    @POST
    @Path("/publishConsume")
    @Produces("text/plain")
    public Response queuePublishConsume(@QueryParam("message") String message) {
        return Response
            .status(200).entity(queueOperationsService.publishConsumeQueueMessage(message)).build();
    }
}
