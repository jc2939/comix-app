package com.comix.model.collection.visitor.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import com.comix.model.collection.Issue;
import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

public class GapsSearch implements SearchType{
    @Override
    public  boolean searchVolume(Volume volume) {
        ArrayList<Integer> issueNumber = new ArrayList<>();
        for (Issue issue: volume.getChildren().values())
        {
            Comic comic = issue.getComics().get(0);
            if (Pattern.matches("^[0-9]*$", comic.getIssue()))
            {
                Integer convert = Integer.parseInt(comic.getIssue());
                issueNumber.add(convert);
            }
        }
        Collections.sort(issueNumber);
        System.out.println(issueNumber);
        int consecutiveCount = 0;
        int missing = 0;
        for (int i = 0; i < issueNumber.size() - 11; i++)
        {
            for (int j = i; j < issueNumber.size() - 1; j++)
            {
                int difference = issueNumber.get(j + 1) - issueNumber.get(j);
                if (difference == 1)
                    consecutiveCount++;
                else if (difference == 2)
                {
                    consecutiveCount += 2;
                    missing += 1;
                }
                else if (difference == 3)
                {
                    consecutiveCount += 3;
                    missing += 2;
                }
                else
                {
                    consecutiveCount = 0;
                    missing = 0;
                    break;
                }
                if (missing > 2)
                {
                    consecutiveCount = 0;
                    missing = 0;
                    break;
                }
                if (consecutiveCount >= 12 && missing <= 2)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean searchBy(Comic comic) {
        return false;
    }   
}
