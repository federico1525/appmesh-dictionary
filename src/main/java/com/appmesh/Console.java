package com.appmesh;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.appmesh.dictionary.Dictionary;
import com.appmesh.file.DictionaryReader;

public class Console {

    public static void main(String [] args) throws IOException {
        //fill the dictionary from words.txt
        DictionaryReader reader = new DictionaryReader("words.txt");
        reader.fillDictionary(Dictionary.getInstance());
        
        //wait for input
        Scanner in = new Scanner(System.in);
        while(true){
            String prefix = in.nextLine();
            
            //if input is "exit", quit, else return all words that start with or matches the prefix
            if (prefix.equals("EXIT")) {
                break;
            } else {
                List<String> words = prefix.equals("LAST RESULTS") ? Dictionary.getInstance().getLastResults() : Dictionary.getInstance().getWords(prefix);
                if(words != null) {
                    for(String word : words) {
                        System.out.println(" - " + word);
                    }
                } else {
                    System.out.println(" - NO RESULTS");
                }
            }
        }
    }
    
}
