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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hawkore.ignite.connector.examples.services.operations.QueryOperationsService;

/**
 * QueryOperations REST Controller 
 * 
 * <p>
 * 
 * This sample uses Hawkore's advanced indexing/searching features. Take a look
 * to
 * <a href="https://docs.hawkore.com/private/apache-ignite-advanced-indexing">
 * Apache Ignite Advanced Indexing Documentation site</a> provided by
 * <a href="https://www.hawkore.com">Hawkore, S.L.</a>
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 *
 */
@Path("/queries")
public class QueryOperationsController {

    @Autowired
    private QueryOperationsService queryOperationsService;

    /**
     * Get Poi by id using SQL
     * 
     * @param id
     *            the Poi's id
     * @param lang
     *            transalation (es, en ...)
     * @return the Poi
     */
    @GET
    @Path("/pois/{id}")
    @Produces("application/json")
    public Response getPoiById(@PathParam("id") int id, @QueryParam("lang") String lang) {

        Object entity = queryOperationsService.getPoiById(id, lang);

        return Response
            .status(entity == null ? 404 : 200)
            .entity(entity).build();
    }

    /**
     * Delete Poi by Id using SQL
     * 
     * @param id
     *            the Poi's id
     * @return SQL delete operation result
     */
    @DELETE
    @Path("/pois/{id}")
    @Produces("application/json")
    public Response deletePoiById(@PathParam("id") int id) {
        return Response
            .status(200)
            .entity(queryOperationsService.deletePoiById(id)).build();
    }

    /**
     * Search Pois by text using SQL
     * 
     * @param query
     *            a text string
     * @param lang
     *            translation (es, en ...)
     * @param limit
     *            page size
     * @param pageNumber
     *            page number
     * @return list of Pois
     * 
     * @throws Exception
     */
    @GET
    @Path("/pois/sqlSearch")
    @Produces("application/json")
    public Response searchPoisbyText(@QueryParam("query") @DefaultValue("{ refresh : true }") String query,
        @QueryParam("lang") String lang, @QueryParam("limit") @DefaultValue("20") int limit,
        @QueryParam("page") Integer pageNumber) throws Exception {
        return Response
            .status(200)
            .entity(queryOperationsService.searchPoisbyText(query, lang, limit, pageNumber)).build();
    }

    /**
     * Search Pois by text using {@link org.apache.ignite.cache.query.TextQuery}
     * 
     * @param query
     *            a text string
     * @param lang
     *            translation (es, en ...)
     * @param limit
     *            page size
     * @param pageNumber
     *            page number
     * @return list of Pois
     * 
     * @throws Exception
     */
    @GET
    @Path("/pois/textSearch")
    @Produces("application/json")
    public Response searchPois(@QueryParam("query") @DefaultValue("{ refresh : true }") String query,
        @QueryParam("limit") @DefaultValue("20") int limit) throws Exception {
        return Response
            .status(200)
            .entity(queryOperationsService.searchPoisbyTextQuery(query, limit)).build();
    }

    /**
     * Search pois within a provided radius from point (latitude = 40.416775,
     * longitude= -3.703790) sorted by distance to this point using SQL query
     * and advanced lucene search
     * 
     * @param radius
     *            radius in string format (10km, 20m ...)
     * @param lang
     *            translation (es, en ...)
     * @param limit
     *            page size
     * @param pageNumber
     *            page number
     * @return list of Pois
     * @throws Exception
     */
    @GET
    @Path("/pois/sqlGeoSearch")
    @Produces("application/json")
    public Response searchPoisbySQL(@QueryParam("radius") @DefaultValue("10km") String radius,
        @QueryParam("lang") String lang, @QueryParam("limit") @DefaultValue("20") int limit,
        @QueryParam("page") Integer pageNumber) throws Exception {
        return Response
            .status(200)
            .entity(queryOperationsService.searchPoisbyGeoSearch(radius, lang, limit, pageNumber)).build();
    }
}
