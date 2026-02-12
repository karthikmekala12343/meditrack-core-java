package com.airtribe.meditrack.interface_;

/**
 * Interface for searchable entities.
 * Demonstrates interface-based polymorphism and default methods.
 */
public interface Searchable {
    
    /**
     * Search by unique identifier.
     *
     * @param id the id
     * @return the object
     */
    Object searchById(String id);
    
    /**
     * Search by name.
     *
     * @param name the name
     * @return the object
     */
    Object searchByName(String name);
    
    /**
     * Validate search results.
     *
     * @param result the result
     * @return true, if is valid search result
     */
    default boolean isValidSearchResult(Object result) {
        return result != null;
    }
}
