package com.airtribe.meditrack.util;

import java.util.*;

/**
 * Generic DataStore class demonstrating generics and collections.
 * Provides basic CRUD operations for any type of entity.
 * @param <T> The type of entity stored
 */
public class DataStore<T> {
    private Map<String, T> store;
    private List<T> list;
    
    public DataStore() {
        this.store = new HashMap<>();
        this.list = new ArrayList<>();
    }
    
    /**
     * Add an entity to the store.
     *
     * @param key the key
     * @param entity the entity
     */
    public void add(String key, T entity) {
        store.put(key, entity);
        list.add(entity);
    }
    
    /**
     * Get entity by key.
     *
     * @param key the key
     * @return the t
     */
    public T get(String key) {
        return store.get(key);
    }
    
    /**
     * Get all entities.
     *
     * @return the all
     */
    public List<T> getAll() {
        return new ArrayList<>(list);
    }
    
    /**
     * Get entity by index.
     *
     * @param index the index
     * @return the by index
     */
    public T getByIndex(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }
    
    /**
     * Update entity.
     *
     * @param key the key
     * @param entity the entity
     */
    public void update(String key, T entity) {
        if (store.containsKey(key)) {
            int index = list.indexOf(store.get(key));
            store.put(key, entity);
            if (index >= 0) {
                list.set(index, entity);
            }
        }
    }
    
    /**
     * Delete entity by key.
     *
     * @param key the key
     * @return true, if successful
     */
    public boolean delete(String key) {
        T entity = store.remove(key);
        if (entity != null) {
            list.remove(entity);
            return true;
        }
        return false;
    }
    
    /**
     * Check if key exists.
     *
     * @param key the key
     * @return true, if successful
     */
    public boolean exists(String key) {
        return store.containsKey(key);
    }
    
    /**
     * Get total count of entities.
     *
     * @return the int
     */
    public int size() {
        return store.size();
    }
    
    /**
     * Clear the store.
     */
    public void clear() {
        store.clear();
        list.clear();
    }
    
    /**
     * Get iterator for entities.
     *
     * @return the iterator
     */
    public Iterator<T> iterator() {
        return list.iterator();
    }
    
    /**
     * Search by predicate (using Java 8 streams if needed).
     *
     * @param predicate the predicate
     * @return the list
     */
    public List<T> search(SearchPredicate<T> predicate) {
        List<T> results = new ArrayList<>();
        for (T entity : list) {
            if (predicate.matches(entity)) {
                results.add(entity);
            }
        }
        return results;
    }
    
    /**
     * Functional interface for search predicates
     */
    @FunctionalInterface
    public interface SearchPredicate<T> {
        boolean matches(T entity);
    }
}
