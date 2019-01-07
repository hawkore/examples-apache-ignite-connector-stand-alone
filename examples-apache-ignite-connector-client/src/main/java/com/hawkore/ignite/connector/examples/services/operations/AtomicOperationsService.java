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

import com.hawkore.ignite.connector.examples.AService;
import com.hawkore.ignite.extensions.internal.operations.AtomicIgniteOperationsSvc;

/**
 * 
 * AtomicOperationsService
 *
 * @TODO: Implement REST controller for testing
 * 
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 */
public class AtomicOperationsService extends AService {

    /**
     * Add and get AtomicLong value
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @param valueToAdd
     *            value to add
     * @return value after add
     */
    public long atomicLongAddAndGet(String atomic, long valueToAdd) {
        return AtomicIgniteOperationsSvc.atomicLongAddAndGet(atomic, valueToAdd, connection);
    }

    /**
     * Compare and set AtomicLong
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @param expected
     *            expected current atomic long value
     * @param newValue
     *            new value to set if current atomic long value is equals to
     *            expected
     * @return whether current AtomicLong value is equals to expected, implies
     *         atomicLong value was updated to newValue
     */
    public boolean atomicLongCompareAndSet(String atomic, long expected, long newValue) {
        return AtomicIgniteOperationsSvc.atomicLongCompareAndSet(atomic, expected, newValue, connection);
    }

    /**
     * Decrement and get AtomicLong
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @return value after decrement
     */
    public long atomicLongDecrementAndGet(String atomic) {
        return AtomicIgniteOperationsSvc.atomicLongDecrementAndGet(atomic, connection);
    }

    /**
     * Get current AtomicLong value
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @return current long value
     */
    public long atomicLongGet(String atomic) {
        return AtomicIgniteOperationsSvc.atomicLongGet(atomic, connection);
    }

    /**
     * Get and add AtomicLong value
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @param valueToAdd
     *            value to add
     * @return value before add
     */
    public long atomicLongGetAndAdd(String atomic, long valueToAdd) {
        return AtomicIgniteOperationsSvc.atomicLongGetAndAdd(atomic, valueToAdd, connection);
    }

    /**
     * Get and decrement AtomicLong
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @return value before decrement
     */
    public long atomicLongGetAndDecrement(String atomic) {
        return AtomicIgniteOperationsSvc.atomicLongGetAndDecrement(atomic, connection);
    }

    /**
     * Get and increment AtomicLong
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @return value before increment
     */
    public long atomicLongGetAndIncrement(String atomic) {
        return AtomicIgniteOperationsSvc.atomicLongGetAndIncrement(atomic, connection);
    }

    /**
     * Set AtomicLong value
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @param valueToSet
     *            value to set
     * @return value before set
     */
    public long atomicLongGetAndSet(String atomic, long valueToSet) {
        return AtomicIgniteOperationsSvc.atomicLongGetAndSet(atomic, valueToSet, connection);
    }

    /**
     * Increment and get AtomicLong
     *
     * @param atomic
     *            Ignite AtomicLong name
     * @return value after increment
     */
    public long atomicLongIncrementAndGet(String atomic) {
        return AtomicIgniteOperationsSvc.atomicLongIncrementAndGet(atomic, connection);
    }

    /**
     * Add and Get AtomicSequence value
     *
     * @param atomic
     *            Ignite AtomicSequence name
     * @param value
     *            value to add
     * @return value after add
     */
    public long atomicSequenceAddAndGet(String atomic, long value) {
        return AtomicIgniteOperationsSvc.atomicSequenceAddAndGet(atomic, value, connection);
    }

    /**
     * Get AtomicSequence value
     *
     * @param atomic
     *            Ignite AtomicSequence name
     * @return current value
     */
    public long atomicSequenceGet(String atomic) {
        return AtomicIgniteOperationsSvc.atomicSequenceGet(atomic, connection);
    }

    /**
     * Get and add AtomicSequence value
     *
     * @param atomic
     *            Ignite AtomicSequence name
     * @param value
     *            value to add
     * @return value before add
     */
    public long atomicSequenceGetAndAdd(String atomic, long value) {
        return AtomicIgniteOperationsSvc.atomicSequenceGetAndAdd(atomic, value, connection);
    }

    /**
     * Get and increment AtomicSequence value
     *
     * @param atomic
     *            Ignite AtomicSequence name
     * @return value before increment
     */
    public long atomicSequenceGetAndIncrement(String atomic) {
        return AtomicIgniteOperationsSvc.atomicSequenceGetAndIncrement(atomic, connection);
    }

    /**
     * Get and increment AtomicSequence value
     *
     * @param atomic
     *            Ignite AtomicSequence name
     * @return value before increment
     */
    public long atomicSequenceIncrementAndGet(String atomic) {
        return AtomicIgniteOperationsSvc.atomicSequenceIncrementAndGet(atomic, connection);
    }

    /**
     * Compare and Set AtomicReference's object
     *
     * @param atomic
     *            Ignite AtomicReference name
     * @param expected
     *            expected AtomicReference's object
     * @param newValue
     *            value to set if current AtomicReference's object is equals to
     *            expected AtomicReference's object
     * @return whether current AtomicReference's object is equals to expected
     *         AtomicReference's object, implies AtomicReference's object was
     *         set to newValue
     */
    public boolean atomicReferenceCompareAndSet(String atomic, Serializable expected, Serializable newValue) {
        return AtomicIgniteOperationsSvc.atomicReferenceCompareAndSet(atomic, expected, newValue, false, connection);
    
    }

    /**
     * Get AtomicReference's object
     *
     * @param atomic
     *            Ignite AtomicReference name
     * @return current reference object
     */
    public Serializable atomicReferenceGet(String atomic) {
        return AtomicIgniteOperationsSvc.atomicReferenceGet(atomic, false, connection);
    }

    /**
     * Set AtomicReference's object
     *
     * @param atomic
     *            Ignite AtomicReference name
     * @param value
     *            value to set
     * @param connection
     *            the acting connection
     */
    public void atomicReferenceSet(String atomic, Serializable value) {
        AtomicIgniteOperationsSvc.atomicReferenceSet(atomic, value, false, connection);
    }

    /**
     * Compare and set AtomicStamped's value and stamp
     *
     * @param atomic
     *            Ignite AtomicStamped name
     * @param expectedValue
     *            expected AtomicStamped's value
     * @param newValue
     *            new AtomicStamped's value to set if expectedValue equals to
     *            current value
     * @param expectedStamp
     *            expected AtomicStamped's stamp
     * @param newStamp
     *            new AtomicStamped's stamp to set if expectedStamp equals to
     *            current stamp
     * @return whether expectedValue equals to current value and expectedStamp
     *         is equals to current stamp, implies AtomicStamped's value and
     *         stamp were set to newValue and newStamp respectively
     */
    public Boolean atomicStampedCompareAndSet(String atomic, Serializable expectedValue, Serializable newValue,
        Serializable expectedStamp, Serializable newStamp) {
        return AtomicIgniteOperationsSvc.atomicStampedCompareAndSet(atomic, expectedValue, newValue, expectedStamp, newStamp, false, connection);
    }

    /**
     * Get AtomicStamped's stamp
     *
     * @param atomic
     *            Ignite AtomicStamped name
     * @return current AtomicStamped's stamp
     */
    public Serializable atomicStampedGetStamp(String atomic) {
        return AtomicIgniteOperationsSvc.atomicStampedGetStamp(atomic, false, connection);
    }

    /**
     * Get AtomicStamped's value
     *
     * @param atomic
     *            Ignite AtomicStamped name
     * @return current AtomicStamped's value
     */
    public Serializable atomicStampedGetValue(String atomic) {
        return AtomicIgniteOperationsSvc.atomicStampedGetValue(atomic, false, connection);
    }

    /**
     * Set AtomicStamped's value and stamp
     *
     * @param atomic
     *            Ignite AtomicStamped name
     * @param value
     *            value to set
     * @param stamp
     *            stamp to set
     * @param mustSerialize
     *            whether value and stamp must be serialized before store them
     */
    public void atomicStampedSet(String atomic, Serializable value, Serializable stamp) {
        AtomicIgniteOperationsSvc.atomicStampedSet(atomic, value, stamp, false, connection);
    }

}
