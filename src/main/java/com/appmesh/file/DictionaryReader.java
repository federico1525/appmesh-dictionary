package com.appmesh.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.appmesh.dictionary.Dictionary;

/**
 * 
 * A file reader that reads a list of newline delimited words into a Dictionary.
 *
 */
public class DictionaryReader {
    
    private final String fileLoc;
    
    /**
     * Create a new dictionary reader for the file at fileLoc.
     * @param fileLoc
     */
    public DictionaryReader(String fileLoc) {
        this.fileLoc = fileLoc;
    }
    
    /**
     * Fill the Dictionary with all words in the file.
     * @param dictionary
     * @throws IOException 
     */
    public void fillDictionary(Dictionary dictionary) throws IOException {
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(this.fileLoc));
            
            String curWord;
            while((curWord = reader.readLine()) != null) {
                dictionary.addWord(curWord);
            }
        } finally {
            //make sure resources are closed
            if(reader != null) reader.close();
        }
    }
    
}
