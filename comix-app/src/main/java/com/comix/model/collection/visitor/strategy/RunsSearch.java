package com.comix.model.collection.visitor.strategy;

import com.comix.model.collection.Issue;
import com.comix.model.collection.Volume;
import com.comix.model.comic.Comic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.*;

public class RunsSearch implements SearchType{

    @Override
    public boolean searchVolume(Volume volume) {
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
        for (int i = 0; i < issueNumber.size() - 11; i++) 
        {
            if (issueNumber.get(i + 1) - issueNumber.get(i) == 1) 
            {
                int consecutiveCount = 2; // Start with two consecutive numbers
                for (int j = i + 1; j < issueNumber.size() - 1; j++) 
                {
                    if (issueNumber.get(j + 1) - issueNumber.get(j) == 1) 
                    {
                        consecutiveCount++;
                        if (consecutiveCount >= 12) 
                        {
                            return true; // Found 12 or more consecutive numbers
                        }
                    } else {
                        break; // Break the inner loop if consecutive sequence is broken
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean searchBy(Comic comic) {
        return false;
    }

    // public static void main(String args[])
    // {
    //     PersonalCollection comix = new PersonalCollection("Bob");
    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "1",
    //             "Sep 24, 2014",
    //             "Title1",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "1",
    //             "Sep 24, 2014",
    //             "Title2",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "3",
    //             "Sep 24, 2014",
    //             "Title3",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "4",
    //             "Sep 24, 2014",
    //             "Title4",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "6",
    //             "Sep 24, 2014",
    //             "Title5",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //         "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "6",
    //             "Sep 24, 2014",
    //             "Title6",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "7",
    //             "Sep 24, 2014",
    //             "Title7",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "8",
    //             "Sep 24, 2014",
    //             "Title8",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "9",
    //             "Sep 24, 2014",
    //             "Title9",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));
        
    //         comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "10",
    //             "Sep 24, 2014",
    //             "Title10",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));   

    //         comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "11",
    //             "Sep 24, 2014",
    //             "World War Wendigo!, Part Four",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));   


    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics", 
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "12", 
    //             "Oct 22, 2014", 
    //             "World War Wendigo!, Part 5", 
    //             null, 
    //             null, 
    //             "1st Printing", 
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "13A",
    //             "Sep 24, 2014",
    //             "Title1",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));

    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "13",
    //             "Sep 24, 2014",
    //             "Title13",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));
    //     comix.addComic(new ConcreteComic(
    //             "Marvel Comics",
    //             "Amazing X-Men, Vol. 2",
    //             "2",
    //             "14",
    //             "Sep 24, 2014",
    //             "Title14",
    //             null,
    //             null,
    //             null,
    //             null
    //     ));
    //     Volume volume = (Volume)comix.getChild("Marvel Comics").getChild("Amazing X-Men, Vol. 2").getChild("2");
    //     System.out.println(searchVolume(volume));
    // }
    
}
