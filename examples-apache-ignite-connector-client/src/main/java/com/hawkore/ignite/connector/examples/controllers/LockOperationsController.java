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

import java.util.concurrent.TimeUnit;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.connector.examples.services.operations.LockOperationsService;

/**
 * TopicOperations REST Controller
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Path("/locks")
public class LockOperationsController {

    @Autowired
    private LockOperationsService lockOperationsService;

    /**
     * Try to execute a task within a distributed lock
     * 
     * @param timeout
     *            timeout
     * @param timeoutUnit
     *            the timeout unit
     * 
     * @return execution response
     */
    @GET
    @Path("/runTask")
    @Produces("application/json")
    public Response tryLockExecution(@QueryParam("timeout") @DefaultValue("1") long timeout,
        @QueryParam("timeoutUnit") @DefaultValue("SECONDS") TimeUnit timeoutUnit) {
        return Response
            .status(200).entity(lockOperationsService.lockScope(AService.LOCK_1, timeout, timeoutUnit)).build();
    }
}
