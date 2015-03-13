package com.appmesh.dictionary;

import java.util.List;

/**
 * 
 * A thread-safe concurrent Dictionary singleton that allows for adding words and
 * searching words by prefix. For more information on assumptions on concurrency
 * see the README.
 *
 */
public class Dictionary {
    
    //this has to be final or volatile to be thread safe, but should be final since it does not change
    private final TrieNode root;
    
    //since we don't need compare and set, volatile will be the same as an AtomicReference, but either
    //can be used.
    private volatile LastResult lastResults;
    
    private Dictionary() {
        this.root = new TrieNode('-');
        this.lastResults = null;
    }
    
    /**
     * Return the shared Dictionary singleton.
     * @return
     */
    public static Dictionary getInstance() {
        return Singleton.INSTANCE;
    }
    
    /**
     * Add a word to the current dictionary.
     * @param word
     */
    public void addWord(String word) {
        this.root.addWord(word);
    }
    
    /**
     * Get all words that start with or matches prefix.
     * @param prefix
     * @return
     */
    public List<String> getWords(String prefix) {
        List<String> lastResults = this.root.getWords(prefix);
        
        //cache the results
        this.lastResults = new LastResult(prefix, lastResults);
        
        return lastResults;
    }
    
    /**
     * Get the results of the last search from a memory sensitive cache. The cache can
     * be cleared if the memory is needed and the search should just be performed again.
     * @return
     */
    public List<String> getLastResults() {
        //store a reference to the last results since this.lastResults reference might
        //change if another thread does a search
        LastResult lastResults = this.lastResults;
        
        if(lastResults == null) {
            //either no last search or last search returned no results
            return null;
        } else {
            List<String> lastResultValues = lastResults.getResults();
            //if null the cache was cleared so return a new search
            if(lastResultValues == null) {
                return this.root.getWords(lastResults.getLastSearch());
            } else {
                return lastResultValues;
            }
        }
    }
    
    private static class Singleton {
        
        //this is a lazy loaded singleton, but could also be eagerly loaded, but make sure
        //there is no double checked locking going on
        private static final Dictionary INSTANCE = new Dictionary();
        
    }

}
