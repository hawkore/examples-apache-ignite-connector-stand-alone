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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.cache.expiry.ExpiryPolicy;

import org.apache.ignite.IgniteCache;

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.connector.examples.dataproviders.PoisProvider;
import com.hawkore.ignite.examples.entities.pois.Poi;
import com.hawkore.ignite.examples.entities.pois.PoiKey;
import com.hawkore.ignite.extensions.api.config.common.enums.ExpiryPolicyType;
import com.hawkore.ignite.extensions.api.ingestion.ICacheDataProvider;
import com.hawkore.ignite.extensions.api.ingestion.IngestionProgressNotifier;
import com.hawkore.ignite.extensions.api.ingestion.IngestionResult;
import com.hawkore.ignite.extensions.api.spring.beans.config.cache.scope.CacheScopeStrategy;
import com.hawkore.ignite.extensions.internal.operations.CacheIgniteOperationsSvc;

/**
 * CacheOperationsService 
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 */
public class CacheOperationsService extends AService{

    
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
     * Put an entry on cache
     * 
     * @param key cache key
     * @param value cache value
     * @param expireSeconds expiration in seconds once created
     */
    public void cachePut(Serializable key, Serializable value, int expireSeconds){
        
        final String cacheName = CACHE2;
        final boolean mustSerialize = false;
        final boolean async = false;
        
        // entry expiration
        final int entryTTL = expireSeconds; // 0 disables expiration
        final TimeUnit entryTTLTimeUnit = TimeUnit.SECONDS;
        final ExpiryPolicyType expiryPolicy = ExpiryPolicyType.CREATED_EXPIRY_POLICY;
        
        CacheIgniteOperationsSvc.cachePut(cacheName, key, value, expiryPolicy, entryTTL, entryTTLTimeUnit, mustSerialize, async, connection);
    }
    
    /**
     * Get an entry from cache
     * 
     * @param key cache key
     * 
     * @return the cache entry
     */
    public Serializable cacheGet(Serializable key){
        
        final String cacheName = CACHE2;
        final boolean mustDeserialize = false;
        
        return CacheIgniteOperationsSvc.cacheGet(cacheName, key, mustDeserialize, connection);
    }
    
    /**
     * 
     * @param cacheName
     * @return  cache size
     */
    @SuppressWarnings("rawtypes")
    public long cacheSize(String cacheName){       
        return ((IgniteCache)CacheIgniteOperationsSvc.cacheInstance(cacheName, connection)).sizeLong();
    }
    
    
    /**
     * 
     * @param cacheName
     */
    public void cacheClear(String cacheName){       
         CacheIgniteOperationsSvc.cacheClear(cacheName, connection);
    }
    
    /**
     * Delete an entry from cache and return deleted entry
     * @param key
     * @return if entry was deleted
     */
    public boolean cacheDelete(Serializable key){
        
        final String cacheName = CACHE2;
        final boolean mustDeserialize = false;
        
        return CacheIgniteOperationsSvc.cacheRemove(cacheName, key, mustDeserialize, connection);
    }
    
    
    /**
     * 
     * Generate random pois using a cache data provider
     * 
     * @param countryCode
     *            - ES, FR or PT
     * @param numberOfPois
     *            - number of pois to generate
     * @param initialId
     *            - initial id
     * @return the ingestion result
     */
    public IngestionResult ingestPois(String countryCode, int numberOfPois, int initialId) {

        final String cacheName = "pois";
        final int batchSize = 1000;
        final boolean allowOverwrite = false;
        final long autoFlushFreq = 0;
        final boolean serializeBeforePut = false;
        final int numberOfIngesters = 4;
        final boolean async = false;

        return CacheIgniteOperationsSvc.cacheIngestData(cacheName, new ICacheDataProvider<PoiKey, Poi, Poi>() {

            // thread-safe pois supplier
            PoisProvider poisSupplier = new PoisProvider(numberOfPois, countryCode, initialId);
            
            // thread-safe progress notifier
            IngestionProgressNotifier progressNotifier = new IngestionProgressNotifier() {

                private IngestionResult result = new IngestionResult();

                private int receivedFinishNotifications = 0;

                @Override
                public synchronized void notify(IngestionResult result) {
                    
                    if (result.isFinished()) {
                        this.result.updateGlobal(this.result.getProcessed() + result.getProcessed(),
                            this.result.getSent() + result.getSent(),
                            Math.max(this.result.getElapsedTime(), result.getElapsedTime()));
                        receivedFinishNotifications++;
                        if (receivedFinishNotifications == numberOfIngesters) {
                            this.result.finish();
                        }
                    }
                }

                @Override
                public synchronized IngestionResult getIngestionResult() {
                    return this.result;
                }
            };

            @Override
            public Supplier<Poi> getDataSupplier() {
                return poisSupplier;
            }

            @Override
            public Function<Poi, ExpiryPolicy> getExpiryPolicier() {
                return null;
            }

            @Override
            public Predicate<Poi> getFilter() {
                // just do not filter input
                return null;
            }

            @Override
            public Function<Poi, PoiKey> getKeyResolver() {
                // extract key from poi to store into "pois" cache
                return Poi::getKey;
            }

            @Override
            public IngestionProgressNotifier getProgressNotifier() {
                return progressNotifier;
            }

            @Override
            public Function<Poi, Poi> getTransformer() {
                // just return poi without transformation
                return p -> p;
            }

        }, batchSize, allowOverwrite, autoFlushFreq, serializeBeforePut, numberOfIngesters, async, connection);
    }
    
}
