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

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.api.config.scheduler.SchedulerConfiguration;
import com.hawkore.ignite.extensions.api.scheduler.SchedulerTaskConfig;
import com.hawkore.ignite.extensions.api.scheduler.SchedulerTaskExecution;
import com.hawkore.ignite.extensions.api.spring.beans.config.scheduler.CronScheduler;
import com.hawkore.ignite.extensions.api.spring.beans.config.scheduler.FixedFrequencyScheduler;
import com.hawkore.ignite.extensions.internal.operations.SchedulerIgniteOperationsSvc;

/**
 * SchedulerOperationsService
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 */
public class SchedulerOperationsService extends AService {

    /**
     * Get Scheduler tasks configuration
     * 
     * @return list of scheduler configurations
     */
    public List<SchedulerTaskConfig> schedulerTasks() {
        return SchedulerIgniteOperationsSvc.schedulerTasks(connection);
    }

    /**
     * Get Scheduler running tasks
     * 
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * 
     * @return list of running task
     */
    public List<SchedulerTaskExecution> schedulerRunList(String taskId) {
        return SchedulerIgniteOperationsSvc.schedulerRunList(taskId, connection);
    }

    /**
     * 
     * Reschedule task on scheduler using fixed frequency configuration
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @param activeOnStart
     *            if scheduler is active on start
     * @param concurrencyAllowed
     *            if concurrency is allowed
     * @param frequency
     *            the frequency of the scheduler in timeUnit
     * @param startDelay
     *            the time in timeUnit to wait before executing the first task
     * @param timeUnit
     *            the {@link java.util.concurrent.TimeUnit} of the scheduler
     */
    public void schedulerRescheduleAtFixedFrequency(String taskId, boolean activeOnStart, boolean concurrencyAllowed,
        long frequency, long startDelay, TimeUnit timeUnit) {
        FixedFrequencyScheduler schedulerStrategy = new FixedFrequencyScheduler();
        schedulerStrategy.setFrequency(frequency);
        schedulerStrategy.setStartDelay(startDelay);
        schedulerStrategy.setTimeUnit(timeUnit);
        this.schedulerReschedule(taskId, activeOnStart, concurrencyAllowed, schedulerStrategy);
    }

    /**
     * 
     * Reschedule task on scheduler using fixed frequency configuration
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @param activeOnStart
     *            if scheduler is active on start
     * @param concurrencyAllowed
     *            if concurrency is allowed
     * @param cronExpression
     *            the cron expression to set
     */
    public void schedulerRescheduleWithCronExpression(String taskId, boolean activeOnStart, boolean concurrencyAllowed,
        String cronExpression) {
        CronScheduler schedulerStrategy = new CronScheduler();
        schedulerStrategy.setCronExpression(cronExpression);
        this.schedulerReschedule(taskId, activeOnStart, concurrencyAllowed, schedulerStrategy);
    }

    /**
     * Run scheduler task
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     */
    public void schedulerRunNow(String taskId) {
        SchedulerIgniteOperationsSvc.schedulerRunNow(taskId, connection);
    }

    /**
     * Start scheduler
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     */
    public void schedulerStart(String taskId) {
        SchedulerIgniteOperationsSvc.schedulerStart(taskId, connection);
    }

    /**
     * Stop scheduler
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     */
    public void schedulerStop(String taskId) {
        SchedulerIgniteOperationsSvc.schedulerStop(taskId, connection);
    }

    /**
     * 
     * Reschedule task on scheduler
     *
     * @param taskId
     *            task identifier (a.k.a. uuid from scheduler id)
     * @param activeOnStart
     *            if scheduler is active on start
     * @param concurrencyAllowed
     *            if concurrency is allowed
     * @param schedulerStrategy
     *            the scheduler strategy
     */
     private void schedulerReschedule(String taskId, boolean activeOnStart, boolean concurrencyAllowed,
        SchedulerConfiguration schedulerStrategy) {

        SchedulerIgniteOperationsSvc.schedulerReschedule(taskId, activeOnStart, concurrencyAllowed, schedulerStrategy,
            connection);
    }

}
