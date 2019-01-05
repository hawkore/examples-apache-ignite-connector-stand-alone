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

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hawkore.ignite.connector.examples.services.operations.CacheOperationsService;

/**
 * CacheOperations REST Controller 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Path("/caches")
public class CacheOperationsController {
    
    @Autowired
    private CacheOperationsService cacheOperationsService;
    
    /**
     * Cache data ingestion
     * 
     * @param countryCode country code (ES, FR or PT)
     * @param numberOfPois number of pois to generate
     * @param initialId initial Poi id 
     * @return ingestion result
     */
    @GET
    @Path("/ingestPois")
    @Produces("application/json")
    public Response ingestPois(@QueryParam("cc") String countryCode, @QueryParam("count") int numberOfPois,
        @QueryParam("initialId") int initialId) {
        return Response
            .status(200)
            .entity(cacheOperationsService.ingestPois(countryCode, numberOfPois, initialId)).build();
    }
    
    /**
     * Cache size
     * 
     * @param cacheName
     * @return cache size
     */
    @GET
    @Path("/size")
    @Produces("text/plain")
    public Response cacheSize(@QueryParam("cache") String cacheName) {
        return Response
            .status(200).entity(cacheOperationsService.cacheSize(cacheName)).build();
    }
    
    /**
     * Clear cache 
     * 
     * @param cacheName
     * @return cache size
     */
    @GET
    @Path("/clear")
    @Produces("text/plain")
    public Response cacheClear(@QueryParam("cache") String cacheName) {
        cacheOperationsService.cacheClear(cacheName);
        return cacheSize(cacheName);
    }
    
    /**
     * Put an entry into cache
     * 
     * @param key
     * @param value
     * @param expireSeconds
     * @return http code
     */
    @PUT
    @Path("/put")
    @Produces("text/plain")
    public Response cachePut(@QueryParam("key") int key, @QueryParam("value") String value,
        @QueryParam("expires") @DefaultValue("0") int expireSeconds) {
        cacheOperationsService.cachePut(key, value, expireSeconds);
        return Response
            .status(200).entity(String.format("Entry stored: key = '%s', val= '%s'", key, value)).build();
    }
    
    /**
     * Get an entry from cache
     * 
     * @param key
     * @return cache value
     */
    @GET
    @Path("/get")
    @Produces("text/plain")
    public Response cacheGet(@QueryParam("key") int key) {
        return Response
            .status(200).entity(cacheOperationsService.cacheGet(key)).build();
    }
    
    /**
     * Delete an entry from cache
     * 
     * @param key
     * @return whether entry was deleted or not
     */
    @DELETE
    @Path("/del")
    @Produces("text/plain")
    public Response cacheDelete(@QueryParam("key") int key) {
        return Response
            .status(200).entity(cacheOperationsService.cacheDelete(key)).build();
    }
}
