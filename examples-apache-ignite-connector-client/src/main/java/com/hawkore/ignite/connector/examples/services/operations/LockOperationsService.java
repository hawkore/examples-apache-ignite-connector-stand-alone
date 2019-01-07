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
import java.util.concurrent.TimeUnit;

import com.hawkore.ignite.cache.utils.IgniteUtil;
import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.internal.operations.LockIgniteOperationsSvc;

/**
 * 
 * LockOperationsService
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
public class LockOperationsService extends AService {

    /**
     * Try execute a task within a distributed lock
     * 
     * @param name
     *            the lock to use
     * @param timeout
     *            timeout
     * @param timeoutUnit
     *            the timeout unit
     * @return the lock response (Exception if lock was not acquired or process
     *         execution within lock scope otherwise)
     */
    public Serializable lockScope(String name, long timeout, TimeUnit timeoutUnit) {

        Serializable response = null;

        try {
            response = (Serializable) LockIgniteOperationsSvc.lockScope(name, timeout, timeoutUnit, connection, () -> {
                // here goes your implementation within lock scope
                
                // a long process simulation
                IgniteUtil.sleepSafe(10000);
                
                return "{\"msg\":\"process executed with lock acquired!!\"}";
            });

            if (response instanceof Exception) {
                throw (Exception) response;
            }

        } catch (Exception e) {
            return String.format("{\"msg\":\"%s\"}", e.getMessage());
        }

        return response;
    }

}
