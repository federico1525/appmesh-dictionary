package com.appmesh.dictionary;

import java.lang.ref.SoftReference;
import java.util.List;

public class LastResult {

    private final String lastSearch;
    
    //this is a soft reference so it can be cleared by the GC is needed before an OOM
    private final SoftReference<List<String>> results;
    
    public LastResult(String lastSearch, List<String> results) {
        this.lastSearch = lastSearch;
        this.results = new SoftReference<List<String>>(results);
    }
    
    public String getLastSearch() {
        return this.lastSearch;
    }
    
    public List<String> getResults() {
        return this.results.get();
    }

}
