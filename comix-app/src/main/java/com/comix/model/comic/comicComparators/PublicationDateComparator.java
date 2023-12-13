package com.comix.model.comic.comicComparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.comix.model.comic.Comic;

public class PublicationDateComparator implements Comparator<Comic> {
    
    Comparator<String> monthComparator = new Comparator<>(){

        Map<String, Integer> months = new HashMap<>(){{
            put("Jan", 1);
            put("Feb", 2);
            put("Mar", 3);
            put("Apr", 4);
            put("May", 5);
            put("Jun", 6);
            put("Jul", 7);
            put("Aug", 8);
            put("Sep", 9);
            put("Oct", 10);
            put("Nov", 11);
            put("Dec", 12);
        }};
        @Override
        public int compare(String o1, String o2) {
            if (months.get(o1) > months.get(o2)){
                return 1; // swap
            } else if (months.get(o1) == months.get(o2)) {
                return 0; // equal
            }
            return -1; // don't swap
        }

    };

    @Override
    public int compare(Comic o1, Comic o2) {
        // String[] o1DateParsed = (o1.getPublicationDate()).split(" ");
        // String[] o2DateParsed = (o2.getPublicationDate()).split(" ");

        // if (o1DateParsed[2].compareTo(o2DateParsed[2]) > 0){
        //     return 1; // swap
        // } else if (o1DateParsed[2].compareTo(o2DateParsed[2]) == 0){
        //     if (monthComparator.compare(o1DateParsed[1], o2DateParsed[1]) > 0){
        //         return 1; // swap
        //     }
        // } else if (o1DateParsed[2].compareTo(o2DateParsed[2]) == 0){
        //     if (monthComparator.compare(o1DateParsed[1], o2DateParsed[1]) == 0){
        //         if (o1DateParsed[0].compareTo(o2DateParsed[0]) > 0){
        //             return 1; // swap
        //         }
        //     }
        // }

        return o1.getPublicationDate().compareTo(o2.getPublicationDate()); // Don't swap
    }
    
}
