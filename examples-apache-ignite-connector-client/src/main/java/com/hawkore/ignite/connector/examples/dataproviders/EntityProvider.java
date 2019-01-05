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
package com.hawkore.ignite.connector.examples.dataproviders;

import java.util.function.Supplier;

import com.hawkore.ignite.examples.entities.simple.Entity;

/**
 * EntityProvider
 * 
 * <p>
 * 
 * This supplier will creates entities for testing purpose
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */
public class EntityProvider implements Supplier<Entity> {

    
    private final int count;

    private int processed;

    private int initKey;
    
    private long init;
    
    private static final int LOG_EVERY = 10000;

    /**
     * 
     * @param count
     * @param initKey
     */
    public EntityProvider(int count, int initKey) {
        this.count = count;
        this.processed = 0;
        this.init = System.currentTimeMillis();
        this.initKey = initKey;
    }

    @Override
    public synchronized Entity get() {

        if (processed < count) {

            if (processed != 0 && processed % LOG_EVERY == 0) {
                System.out.format("Injected %d entities in %d ms\n", LOG_EVERY,
                    (System.currentTimeMillis() - init));
                init = System.currentTimeMillis();
            }

            Entity p = new Entity(initKey, "Generated entity "+ initKey);
            
            initKey++;
            processed++;
            
            return p;
        }
        return null;

    }
}
