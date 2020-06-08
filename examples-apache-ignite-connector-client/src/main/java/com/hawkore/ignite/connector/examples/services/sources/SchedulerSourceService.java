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

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.api.config.scheduler.SchedulerConfiguration;
import com.hawkore.ignite.extensions.api.spring.beans.config.scheduler.CronScheduler;
import com.hawkore.ignite.extensions.internal.sources.scheduler.IgniteSchedulerSourceSvc;

/**
 * SchedulerSourceService.
 *
 * <p>
 *
 * Starts a scheduler source that will dispatch task's execution at scheduled
 * time
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class SchedulerSourceService extends AService {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.startSchedulerSource();
    }

    private void startSchedulerSource() {

        // unique scheduler ID within this app instance
        final String uniqueSchedulerId = "a_scheduler_1";

        final String description = "My cron scheduler 1";
        final boolean activeOnStart = true;
        final boolean concurrencyAllowed = false;
        final SchedulerConfiguration schedulerStrategy = new CronScheduler().withCronExpression("* * * * *");
        final String location = "a location for scheduler 1 within my app";

        // define scheduler source
        IgniteSchedulerSourceSvc scheduler = new IgniteSchedulerSourceSvc(uniqueSchedulerId, description, activeOnStart,
            concurrencyAllowed, schedulerStrategy, connection, location);

        // schedules task
        scheduler.doStart((payload, ctx) -> {

            try {

                // here goes your implementation for scheduled task
                System.out.println("****************************************");
                System.out.println("scheduler " + scheduler.getUuid() + " dispatch this task ");
                System.out.println("****************************************");

                return null;

            } catch (final Exception e) {
                return e;
            }
        });
    }

}
