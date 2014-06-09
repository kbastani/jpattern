package org.recognition.kernel.impl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is a utility class that performs low-level pattern recognition in a sequence of characters
 */
public class Recognizer {

    /**
     * This method is a helper method to print all the patterns of a symbol sequence */
    public static void printRedundancyMatrix(String pattern)
    {
        List<HashMap<Integer, HashMap<String, Integer>>> redundancyMatrix = countRedundancies(getMap(Parse(pattern)));

        for (int i = 0; i < redundancyMatrix.size(); i++) {
            HashMap<Integer, HashMap<String, Integer>> redundancyList = redundancyMatrix.get(i);
            System.out.println();
            redundancyList.forEach((value, map) -> {
                    System.out.print(map);
            });
        }
    }

    // Find the features from a string and generate a redundancy matrix, scans maximum 8 symbol window
    public static List<HashMap<Integer, HashMap<String, Integer>>> findFeatures(String pattern)
    {
        return countRedundancies(getMap(Parse(pattern)));
    }

    public static List<String> Parse(String pattern)
    {
        List<String> stringList = new ArrayList<String>();

        // Scan through the string
        for (int i = 0; i < pattern.length() + 1; i++) {
            // Create a dependency tree by scan
            for (int j = Math.max(i - 8, 0); j < i; j++) {
                // Check stringList for pattern
                String val = pattern.substring(j, i);

                // stringList contains val?
                if(!stringList.contains(val))
                    stringList.add(val);
            }
        }

        return stringList;
    }

    public static HashMap<Integer, List<String>> getMap(List<String> stringList) {
        HashMap<Integer, List<String>> stringMap = new HashMap<Integer, List<String>>();

        // Iterate through list
        for (int i = 0; i < stringList.size(); i++) {

            int size = stringList.get(i).length();

            if (!stringMap.containsKey(size))
                stringMap.put(size, new ArrayList<String>());

            stringMap.get(size).add(stringList.get(i));
        }

        return  stringMap;
    }

    public static HashMap<Integer, HashMap<String, Integer>> countRedundancies(List<String> stringList)
    {
        HashMap<Integer, HashMap<String, Integer>> countMatrix = new HashMap<Integer, HashMap<String, Integer>>();

        // Count each of the redundancies
        for (int i = 0; i < stringList.size(); i++) {

            int size = stringList.get(i).length();

            for (int j = 0; j < size; j++) {
                Character symbol = stringList.get(i).toCharArray()[j];

                if (!countMatrix.containsKey(j))
                    countMatrix.put(j, new HashMap<String, Integer>());

                if (!countMatrix.get(j).containsKey(symbol.toString()))
                    countMatrix.get(j).put(symbol.toString(), 0);

                int val = countMatrix.get(j).get(symbol.toString());

                countMatrix.get(j).put(symbol.toString(), val + 1);
            }
        }

        return countMatrix;
    }

    public static List<HashMap<Integer, HashMap<String, Integer>>> countRedundancies(HashMap<Integer, List<String>> stringMap) {
        List<HashMap<Integer, HashMap<String, Integer>>> redundancyMatrix = new ArrayList<HashMap<Integer, HashMap<String, Integer>>>();

        for (int i = 1; i <= stringMap.keySet().size(); i++) {
            redundancyMatrix.add(countRedundancies(stringMap.get(i)));
        }
        return redundancyMatrix;
    }
}
