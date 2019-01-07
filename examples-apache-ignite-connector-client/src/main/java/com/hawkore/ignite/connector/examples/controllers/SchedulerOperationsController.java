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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hawkore.ignite.connector.examples.services.operations.SchedulerOperationsService;

/**
 * SchedulerOperations REST Controller
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Path("/scheduler")
public class SchedulerOperationsController {

    @Autowired
    private SchedulerOperationsService schedulerOperationsService;

    /**
     * Get Scheduler tasks info
     * 
     * @return list of SchedulerTaskConfig
     */
    @GET
    @Path("/tasks")
    @Produces("application/json")
    public Response schedulerTasks() {
        return Response
            .status(200).entity(schedulerOperationsService.schedulerTasks()).build();
    }

    /**
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @return list of SchedulerTaskExecution
     */
    @GET
    @Path("/executions")
    @Produces("application/json")
    public Response schedulerRunList(@QueryParam("id") String taskId) {
        return Response
            .status(200).entity(schedulerOperationsService.schedulerRunList(taskId)).build();
    }

    /**
     * 
     * Reschedule task on scheduler.
     * 
     * <p>
     * 
     * On this sample, if cronExpression is provided, a CRON scheduler will be
     * used
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @param activeOnStart
     *            if scheduler is active on start
     * @param concurrencyAllowed
     *            if concurrency is allowed
     * @param frequency
     *            the frequency of the scheduler in timeUnit.
     * @param startDelay
     *            the time in timeUnit to wait before executing the first task.
     * @param timeUnit
     *            the {@link java.util.concurrent.TimeUnit} of the scheduler.
     * @param cronExpression
     *            the cron expression to set
     * @return a simple response
     */
    @POST
    @Path("/reschedule")
    @Produces("text/plain")
    public Response schedulerReschedule(@QueryParam("id") String taskId,
        @QueryParam("activeOnStart") @DefaultValue("true") boolean activeOnStart,
        @QueryParam("concurrencyAllowed") @DefaultValue("false") boolean concurrencyAllowed,
        @QueryParam("frequency") @DefaultValue("1") long frequency,
        @QueryParam("startDelay") @DefaultValue("1") long startDelay,
        @QueryParam("timeUnit") @DefaultValue("SECONDS") TimeUnit timeUnit,
        @QueryParam("cron") String cronExpression) {

        if (cronExpression != null && cronExpression.length() > 0) {
            schedulerOperationsService.schedulerRescheduleWithCronExpression(taskId, activeOnStart, concurrencyAllowed,
                cronExpression);
        } else {
            schedulerOperationsService.schedulerRescheduleAtFixedFrequency(taskId, activeOnStart, concurrencyAllowed,
                frequency, startDelay, timeUnit);
        }
        return Response
            .status(200).entity(String.format("Operation success for scheduler %s", taskId)).build();
    }

    /**
     * Run scheduler task
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @return a simple response
     */
    @POST
    @Path("/run")
    @Produces("text/plain")
    public Response schedulerRunNow(@QueryParam("id") String taskId) {

        schedulerOperationsService.schedulerRunNow(taskId);

        return Response
            .status(200).entity(String.format("Operation success for scheduler %s", taskId)).build();
    }

    /**
     * Start scheduler
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @return a simple response
     */
    @POST
    @Path("/start")
    @Produces("text/plain")
    public Response schedulerStart(@QueryParam("id") String taskId) {

        schedulerOperationsService.schedulerStart(taskId);

        return Response
            .status(200).entity(String.format("Operation success for scheduler %s", taskId)).build();
    }

    /**
     * Stop scheduler
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @return a simple response
     */
    @POST
    @Path("/stop")
    @Produces("text/plain")
    public Response schedulerStop(@QueryParam("id") String taskId) {

        schedulerOperationsService.schedulerStop(taskId);

        return Response
            .status(200).entity(String.format("Operation success for scheduler %s", taskId)).build();
    }

}
