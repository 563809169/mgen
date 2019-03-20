package com.npj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author pengjie.nan
 * @date 2019-03-20
 */
public class SearchTarget {

    public static void main(String[] args) {

        int[] num = {24, 80, 20, 84, 16};

        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> resultMap = new HashMap<>();

        for (int i = 0; i < num.length; i++) {
            map.put(num[i], i);
        }

        for (int i = 0; i < num.length; i++) {
            int target = 100 - num[i];
            if (map.containsKey(target) && map.get(target) > i) {
                resultMap.put(i, map.get(target));
            }
        }

        resultMap.forEach((k, v)->{
            System.out.println("(" + num[k] + "," + num[v] + ")");
        });



        HashSet<Integer> elSet = new HashSet<>(num.length);
        for (int el : num) {
            elSet.add(el);
        }

        for (int el : num) {
            int target = 100 - el;
            if (elSet.contains(target)) {
                elSet.remove(el);
                System.out.println("(" + target + "," + el + ")");
            }
        }


    }

}
