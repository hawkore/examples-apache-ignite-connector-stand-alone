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

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hawkore.ignite.connector.examples.services.operations.TopicOperationsService;

/**
 * TopicOperations REST Controller 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Path("/topics")
public class TopicOperationsController {

    @Autowired
    private TopicOperationsService topicOperationsService;
    
    /**
     * Publish a message to a topic
     * 
     * @param message the message to send
     * 
     * @return http code
     */
    @POST
    @Path("/publish")
    @Produces("text/plain")
    public Response searchPois(@QueryParam("message") String message) {

        topicOperationsService.sendTopicMessage(message);

        return Response
            .status(200).entity(String.format("Message '%s' published", message)).build();
    }
}
