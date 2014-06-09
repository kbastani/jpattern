package org.recognition.kernel.impl.util;

import com.sun.istack.internal.NotNull;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RecognizerTest extends TestCase {

    @Test
    public void testParse() throws Exception {
        @NotNull
        List<String> actual = Recognizer.Parse("101");
        List<String> expected = Arrays.asList("1", "10", "0", "101", "01");

        assertEquals(expected, actual);

    }

    @Test
    public void testSampleParse() throws Exception {
        @NotNull
        List<String> actual = Recognizer.Parse("0101");
        System.out.println(actual);
    }

    @Test
    public void testGetMap() throws Exception {
        List<String> input = Arrays.asList("1", "10", "0", "101", "01");

        HashMap<Integer, List<String>> expected = new HashMap<Integer, List<String>>();

        expected.put(1, Arrays.asList("1", "0"));
        expected.put(2, Arrays.asList("10", "01"));
        expected.put(3, Arrays.asList("101"));

        assertEquals(expected, Recognizer.getMap(input));
    }

    @Test
    public void testCountRedundancies() throws Exception {

        List<String> input = Arrays.asList("1", "10", "0", "101", "01");
        HashMap<Integer, HashMap<String, Integer>> expected = new HashMap<Integer, HashMap<String, Integer>>();

        expected.put(0, new HashMap<String, Integer>());
        expected.put(1, new HashMap<String, Integer>());
        expected.put(2, new HashMap<String, Integer>());
        expected.get(0).put("0", 2);
        expected.get(0).put("1", 3);
        expected.get(1).put("0", 2);
        expected.get(1).put("1", 1);
        expected.get(2).put("1", 1);

        assertEquals(expected, Recognizer.countRedundancies(input));
    }

    @Test
    public void testCountRedundancies1() throws Exception {
        HashMap<Integer, List<String>> input = new HashMap<Integer, List<String>>();

        input.put(1, Arrays.asList("1", "0"));
        input.put(2, Arrays.asList("10", "01"));
        input.put(3, Arrays.asList("101"));

        List<HashMap<Integer, HashMap<String, Integer>>> expected = new ArrayList<HashMap<Integer, HashMap<String, Integer>>>();

        for (int i = 1; i <= input.keySet().size(); i++) {
            expected.add(Recognizer.countRedundancies(input.get(i)));
        }

        assertEquals(expected, Recognizer.countRedundancies(input));
    }
}