package com.decider.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
* This service is responsible to simulates idempotency
* */
@Service
public class IdempotencyService<K,V > {

    // I REALLY REALLY(!!!) DON'T LIKE THIS LINE
    private Map<K,V> kvs = new ConcurrentHashMap<>();

    public V getValue(K k){
        return kvs.get(k);
    }

    public void setValue(K k, V v) {
        kvs.put(k,v);
    }
}
