package com.successfactors.library.client.helper;

import java.util.Comparator;

public class MyComparator implements Comparator<String>{

    public int compare(String o1,String o2) {
       if(o1.compareTo(o2) < 0)
           return -1;
       else
           return 1;
       }

}
