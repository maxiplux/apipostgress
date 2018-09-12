package com.domain.apipostgress.model;

/**
 * User: franc
 * Date: 10/09/2018
 * Time: 12:04
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Pair<K, V> {

    public final K key;
    public final V value;

    @JsonCreator
    public Pair(@JsonProperty("country") K key, @JsonProperty("value") V value) {
        this.key = key;
        this.value = value;
    }


    public boolean equals(Object o) {
        return o instanceof Pair && Objects.equals(key, ((Pair<?, ?>) o).key) && Objects.equals(value, ((Pair<?, ?>) o).value);
    }

    public int hashCode() {
        return 31 * Objects.hashCode(key) + Objects.hashCode(value);
    }

    public String toString() {
        return key + "=" + value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}