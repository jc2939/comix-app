package com.comix.model.comic.comicComparators;

import java.util.Comparator;

import com.comix.model.comic.Comic;

public class DefaultComparator implements Comparator<Comic> {
    @Override
    public int compare(Comic o1, Comic o2) {
        int result = o1.getSeries().compareTo(o2.getSeries());
        if (result == 0)
        {
            result = o1.getVolume().compareTo(o2.getVolume());
            if (result == 0)
            {
                return o1.getIssue().compareTo(o2.getIssue());
            }
            return result;
        }
        return result;
    }
}
