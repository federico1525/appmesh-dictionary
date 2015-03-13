package com.appmesh.dictionary;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TrieNode {
    
    //everything here must be final or volatile to meet the concurrency expectations
    private final char value;
    private final Map<Character, TrieNode> children;
    
    //make sure they have an isComplete flag so that words that are complete words, but also not
    //leaf nodes are also returned
    private volatile boolean isComplete;
    
    public TrieNode(char value) {
        this.value = value;
        this.children = new LinkedHashMap<Character, TrieNode>();
        
        this.isComplete = false;
    }
    
    public boolean isComplete() {
        return this.isComplete;
    }
    
    /**
     * Add a word.
     * @param word
     */
    public void addWord(String word) {
        addWord(word, 0);
    }
    
    /**
     * Recursively add a word.
     * @param word
     * @param index
     */
    private void addWord(String word, int index) {
	    //using index instead of substring since substring will create a new copy of the string
        if (index < word.length()) {
            //get or create the next node
            char curChar = word.charAt(index);
            TrieNode curNode = this.children.get(curChar);
            if(curNode == null) {
                curNode = new TrieNode(curChar);
                this.children.put(curChar, curNode);
            }
            
            if(index == word.length() - 1) {
                //if we are at the last character, set the isComplete flag
                curNode.isComplete = true;
            } else {
                //add the next character
                curNode.addWord(word, index + 1);
            }
        }
    }
    
    /**
     * Get the node represented by the last character in the prefix.
     * @param prefix
     * @return
     */
    public TrieNode getNode(String prefix) {
        return getNode(prefix, 0);
    }
    
    /**
     * Recursively get the node.
     * @param prefix
     * @param index
     * @return
     */
    private TrieNode getNode(String prefix, int index) {
        if(index < prefix.length()) {
            char curChar = prefix.charAt(index);
            TrieNode curNode = this.children.get(curChar);
            if (curNode == null) {
                //prefix does not exist
                return null;
            } else {
                if(index == prefix.length() - 1) {
                    //found the node
                    return curNode;
                } else {
                    return curNode.getNode(prefix, index + 1);
                }
            }
        } else {
            //something went wrong
            return null;
        }
    }
    
    /**
     * Get all words that are children of this node, append prefix to those words.
     * @param prefix
     * @return
     */
    public List<String> getWords(String prefix) {
        List<String> words = new LinkedList<String>();
        
        TrieNode node = getNode(prefix);
        if (node != null) {
            node.getWords(prefix, words, false);
        }
        
        return words;
    }
    
    /**
     * Recursively get all of the words from this node.
     * @param prefix
     * @param words
     * @param appendValue
     */
    private void getWords(String prefix, List<String> words, boolean appendValue) {
        String newPrefix = appendValue ? prefix + this.value : prefix;
        if(this.isComplete) {
            words.add(newPrefix);
        }
        
        for(TrieNode curNode : this.children.values()) {
            curNode.getWords(newPrefix, words, true);
        }
    }

}
