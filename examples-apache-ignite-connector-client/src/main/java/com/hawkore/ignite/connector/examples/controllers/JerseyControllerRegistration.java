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

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * 
 * JerseyControllerRegistration 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Component
public class JerseyControllerRegistration extends ResourceConfig
{
    /**
     * 
     */
    public JerseyControllerRegistration()
    {
        register(CacheOperationsController.class);
        register(QueueOperationsController.class);
        register(QueryOperationsController.class);
        register(TopicOperationsController.class);
        register(SchedulerOperationsController.class);
        register(LockOperationsController.class);
        register(FilesystemOperationsController.class);
    }
}