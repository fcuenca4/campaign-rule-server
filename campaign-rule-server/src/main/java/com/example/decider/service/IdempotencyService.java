package com.example.decider.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IdempotencyService<K,V > {
    private Map<K,V> kvs = new ConcurrentHashMap<>();
    public V getValue(K k){
        return kvs.get(k);
    }

    public void setValue(K k, V v) {
        kvs.put(k,v);
    }
}
