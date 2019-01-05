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
package com.hawkore.ignite.connector.examples;

import org.springframework.beans.factory.InitializingBean;

import com.hawkore.ignite.extensions.api.spring.beans.IgniteConnectionManager;

/**
 * AService. Sample abstract service.
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 * 
 */
public abstract class AService implements InitializingBean {
   
    /** */
    public static final String CACHE1 = "CACHE1";
    
    /** */
    public static final String CACHE2 = "CACHE2";
    
    /** */
    public static final String QUEUE1 = "QUEUE1";
    
    /** */
    public static final String QUEUE2 = "QUEUE2";
    
    /** */
    public static final String TOPIC1 = "TOPIC1";
    
    
    protected IgniteConnectionManager connection;

    /**
     * @param connection
     *            the connection to set
     */
    public void setConnection(IgniteConnectionManager connection) {
        this.connection = connection;
    }

    /**
     * @return the connection
     */
    public IgniteConnectionManager getConnection() {
        return connection;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
