package com.appmesh;


import com.appmesh.dictionary.Dictionary;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DictionaryTest {


    private Dictionary dictionary = Dictionary.getInstance();

    @Before
    public void initializeDictionary() {

        // Initialize dictionary with few test words

        List<String> testWords = Arrays.asList("foo", "fooz", "bar", "baz");

        for(String word : testWords)
        {
            Dictionary.getInstance().addWord(word);
        }
    }

    @Test
    public void testComplete() throws IOException {

        //  If a prefix is a complete word, that word should also be returned.

        List<String> actual = dictionary.getWords("baz");

        List<String> expected = Arrays.asList("baz");
        assertThat(actual, is(expected));
    }

    @Test
    public void testNotFound() throws IOException {

        //  Returns an empty list if the prefix is not found

        List<String> actual = dictionary.getWords("banana");

        List<String> expected = Collections.emptyList();
        assertThat(actual, is(expected));
    }

    @Test
    public void testPrefix() throws IOException {

        // Given a prefix such as "aberran", the dictionary should return "aberrance", "aberrancy", and "aberrant".

        List<String> actual = dictionary.getWords("foo");

        List<String> expected = Arrays.asList("foo", "fooz");
        assertThat(actual, is(expected));
    }

    @Test
    public void testCache()
    {
        dictionary.getWords("foo");

        List<String> actual = dictionary.getLastResults();
        List<String> expected = Arrays.asList("foo", "fooz");

        // Test the cached result
        assertThat(actual, is(expected));

        // Invalidate cache
        dictionary.getWords("invalidate");
        actual = dictionary.getLastResults();

        // Test that the cache is no longer valid
        assertThat(actual, not(is(expected)));

    }


}
