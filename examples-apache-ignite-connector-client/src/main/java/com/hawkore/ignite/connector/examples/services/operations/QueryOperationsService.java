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

import static org.hawkore.ignite.lucene.builder.Builder.geoDistance;
import static org.hawkore.ignite.lucene.builder.Builder.search;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.hawkore.ignite.cache.serialization.serializers.SerializationManager;
import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.api.query.pagination.Filter;
import com.hawkore.ignite.extensions.api.query.pagination.Page;
import com.hawkore.ignite.extensions.api.query.pagination.Sort;
import com.hawkore.ignite.extensions.api.spring.beans.config.cache.scope.CacheScopeStrategy;
import com.hawkore.ignite.extensions.internal.operations.CacheIgniteOperationsSvc;
import com.hawkore.ignite.extensions.internal.operations.QueryIgniteOperationsSvc;

/**
 * QueryOperationsService.
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
public class QueryOperationsService extends AService {

    private static String sqlTextQuery = "SELECT id,"
        + "countryCode,"
        + "public.hkmv_text(name, ?, public.HKMV_DEFAULT_ISO(name)) as name,"
        + "public.hkmv_text(description, ?, public.HKMV_DEFAULT_ISO(description)) as description,"
        + "public.hkmv_text(name, 'iata', null) as iata"
        + " FROM \"pois\".poi where lucene = ?";

    private static String sqlGeoQuery = "SELECT id,"
        + "countryCode,"
        + "public.hkmv_text(name, ?, public.HKMV_DEFAULT_ISO(name)) as name,"
        + "public.hkmv_text(description, ?, public.HKMV_DEFAULT_ISO(description)) as description,"
        + "public.hkmv_text(name, 'iata', null) as iata,"
        + "PUBLIC.ST_DISTANCE_SPHERE(place, 'POINT (-3.703790 40.416775)')/1000 as distance_km"
        + " FROM \"pois\".poi where lucene = ?";

    
    private CacheScopeStrategy cacheScopeStrategy;
    
    /**
     * @param cacheScopeStrategy
     *            the cacheScopeStrategy to set
     */
    public void setCacheScopeStrategy(CacheScopeStrategy cacheScopeStrategy) {
        this.cacheScopeStrategy = cacheScopeStrategy;
    }

    /**
     * @return the cacheScopeStrategy
     */
    public CacheScopeStrategy getCacheScopeStrategy() {
        return cacheScopeStrategy;
    }
    
    /**
     * Get poi by id with translation if available using SQL
     * 
     * @param id
     *            the poi id
     * @param lang
     *            translation is available
     * @return the poi as HashMap
     */
    public Object getPoiById(Integer id, String lang) {
        String sqlQuery = "SELECT id,"
            + "countryCode,"
            + "public.hkmv_text(name, ?, public.HKMV_DEFAULT_ISO(name)) as name,"
            + "public.hkmv_text(name, ?, public.HKMV_DEFAULT_ISO(description)) as description,"
            + "public.hkmv_text(name, 'iata', null) as iata"
            + " FROM \"pois\".poi where id = ?";
        List<Object> queryParams = Arrays.asList(lang, lang, id);
        List<Filter> filters = null;
        List<Sort> sortings = null;
        Page page = null;
        boolean distributedJoins = false;
        boolean replicatedOnly = false;
        boolean collocated = false;
        boolean lazy = false;
        boolean local = false;
        boolean enforceJoinOrder = false;
        int timeout = 0;
        TimeUnit timeoutUnit = TimeUnit.SECONDS;

        Collection<Object> pois = QueryIgniteOperationsSvc.querySql(sqlQuery, queryParams, filters, sortings, page,
            distributedJoins, replicatedOnly, collocated, lazy, local, enforceJoinOrder, timeout, timeoutUnit,
            connection);

        if (pois.isEmpty()) {
            return null;
        }

        return pois.toArray()[0];
    }

    /**
     * Delete Poi by id using SQL
     * 
     * @param id
     * 
     * @return SQL delete response
     */
    public Object deletePoiById(Integer id) {
        String sqlQuery = "DELETE \"pois\".poi where id = ?";
        List<Object> queryParams = Arrays.asList(id);
        List<Filter> filters = null;
        List<Sort> sortings = null;
        Page page = null;
        boolean distributedJoins = false;
        boolean replicatedOnly = false;
        boolean collocated = false;
        boolean lazy = false;
        boolean local = false;
        boolean enforceJoinOrder = false;
        int timeout = 0;
        TimeUnit timeoutUnit = TimeUnit.SECONDS;

        Collection<Object> pois = QueryIgniteOperationsSvc.querySql(sqlQuery, queryParams, filters, sortings, page,
            distributedJoins, replicatedOnly, collocated, lazy, local, enforceJoinOrder, timeout, timeoutUnit,
            connection);

        if (pois.isEmpty()) {
            return null;
        }

        return pois.toArray()[0];
    }

    /**
     * Search pois by text using SQL query
     * 
     * @param text
     *            a lucene query, text to find
     * @param lang
     *            translation if available
     * @param limit
     *            page size
     * @param pageNumber
     *            page number
     * @return json array
     * 
     * @throws Exception
     */

    public String searchPoisbyText(String text, String lang, int limit, Integer pageNumber) throws Exception {
        return searchPoisbySQL(sqlTextQuery, text, lang, limit, pageNumber);
    }

    /**
     * Search pois within a provided radius from point (latitude = 40.416775,
     * longitude= -3.703790, within Spain - countryCode = 'ES') sorted by
     * distance to this point using SQL query and advanced lucene search
     * 
     * @param radius
     * @param lang
     * @param limit
     * @param pageNumber
     * @return json array as string
     * 
     * @throws Exception
     */
    public String searchPoisbyGeoSearch(String radius, String lang, int limit, Integer pageNumber) throws Exception {

        String advancedLuceneFilter = search().refresh(true)
            .filter(geoDistance("place", 40.416775, -3.703790, radius))
            .sort(geoDistance("place", 40.416775, -3.703790)).build();

        return searchPoisbySQL(sqlGeoQuery, advancedLuceneFilter, lang, limit, pageNumber);
    }

    /**
     * Search by TextQuery
     * 
     * @param text
     *            lucene query
     * @param limit
     *            max number of results
     * @return json array as string
     * 
     * @throws Exception
     */
    public String searchPoisbyTextQuery(String text, int limit) throws Exception {

        // cache response applying a cache scope strategy (defined as a bean on
        // client-app-config.xml)
        
        String cachedKey = "text search " + text;
        
        return (String) CacheIgniteOperationsSvc.cacheScope(null, cachedKey, cacheScopeStrategy, () -> {

            System.out.println("****************************************");
            System.out.format("Caching query results for TextQuery  %s %n", text);
            System.out.println("****************************************");

            boolean local = false;

            // make the query and serialize as JSON
            return SerializationManager
                .toJson(QueryIgniteOperationsSvc.queryLucene("\"pois\".poi", limit, local, text, connection));
        });
    }

    private String searchPoisbySQL(String sqlQuery, String luceneFilter, String lang, int limit, Integer pageNumber)
        throws Exception {

        // cache response applying a cache scope strategy (defined as a bean on
        // client-app-config.xml)
        
        String cachedKey = "sql search " + luceneFilter + lang;
        
        return (String) CacheIgniteOperationsSvc.cacheScope(null, cachedKey,
            cacheScopeStrategy, () -> {

                System.out.println("****************************************");
                System.out.format("Caching query results for SQL Query %s, %s, %s %n", sqlQuery, luceneFilter, lang);
                System.out.println("****************************************");

                List<Object> queryParams = Arrays.asList(lang, lang, luceneFilter, limit);
                List<Filter> filters = null;
                List<Sort> sortings = null;
                Page page = new Page(limit, pageNumber);
                boolean distributedJoins = false;
                boolean replicatedOnly = false;
                boolean collocated = false;
                boolean lazy = false;
                boolean local = false;
                boolean enforceJoinOrder = false;
                int timeout = 0;
                TimeUnit timeoutUnit = TimeUnit.SECONDS;

                // make the query and serialize as JSON
                return SerializationManager
                    .toJson(QueryIgniteOperationsSvc.querySql(sqlQuery, queryParams, filters, sortings, page,
                        distributedJoins, replicatedOnly, collocated, lazy, local, enforceJoinOrder, timeout,
                        timeoutUnit,
                        connection));
            });
    }
}
