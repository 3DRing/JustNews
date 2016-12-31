package com.ringov.justnews;

/**
 * Created by Сергей on 31.12.2016.
 */

public class Utils {
    public static long hash(String str){
        final long b = 378551;
        long a = 63689;
        long hash = 0;

        for(int i = 0; i < str.length(); i++)
        {
            hash = hash * a + str.charAt(i);
            a *= b;
        }

        return hash;
    }
}
