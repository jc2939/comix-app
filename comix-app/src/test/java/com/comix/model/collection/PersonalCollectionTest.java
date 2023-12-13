package com.comix.model.collection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import com.comix.model.comic.Comic;
import com.comix.model.comic.ConcreteComic;
import com.comix.model.comic.GradeDecorator;

@Testable
public class PersonalCollectionTest {
    private PersonalCollection comix;
    
    @BeforeEach
    public void setup(){
        comix=new PersonalCollection("Biki");
        comix.addComic(new ConcreteComic(
            "DC Comics",
            "Batman",
            "Gotham Knights",
            "Constants",
            "2000",
            "Wings",
            null,
            null,
            null,
            null
    ));



    comix.addComic(new ConcreteComic(
            "DC Comics",
            "Batman",
            "Gotham Knights",
            "Down with the Ship",
            "1988",
            "Red Hood",
            null,
            null,
            null,
            null
    ));
    
        comix.addComic(new ConcreteComic(
            "DC Comics",
            "Batman",
            "Gotham Knights",
            "Bad Karma",
            "1988",
            "Robins Death",
            null,
            null,
            null,
            null
    ));   

        comix.addComic(new ConcreteComic(
            "DC Comics",
            "Flash",
            "The Road to FlashPoint",
            "Case Two: The road to Flashpoint part one",
            "1988",
            "Red",
            null,
            null,
            null,
            null
    ));   


    comix.addComic(new ConcreteComic(
        "DC Comics",
        "Batman",
        "Death in the Family",
        "The Diplomat's Son",
        "1988",
        "Darkness",
        null,
        null,
        null,
        null
    ));
       
}
    
    @Test
    public void testGetChildren(){
        assertEquals(1,comix.getChildren().size());
    }
    
    @Test
    public void testGetComics(){
        //gets all the comics under a specific volume
        String results=comix.getComics().toString().replaceAll("\\[|\\]", "");
        assertEquals(results,"Darkness, Robins Death, Wings, Red Hood, Red");
    }

    @Test
    public void testRemoveComic(){
        Comic comic = comix.getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        comix.removeComic(comic);
        assertEquals(comix.getComics().toString().replaceAll("\\[|\\]", ""),"Darkness, Robins Death, Wings, Red");
    }
    @Test
    public void testAddComic(){
        
        comix.addComic(new ConcreteComic(
            "DC Comics",
            "Batman",
            "Gotham Knights",
            "Constants",
            "2000",
            "BlackRock",
            null,
            null,
            null,
            null
    ));
    assertEquals(comix.getComics().toString().replaceAll("\\[|\\]", ""),"Darkness, Robins Death, Wings, BlackRock, Red Hood, Red");
    }

    public String getComicsAtVolume(String publisherName, String Series, String volume){
        return comix.getChild(publisherName).getChild(Series).getChild(volume).getComics().toString().replaceAll("\\[|\\]", "");
    }
    public Comic getComicsByIndex(String publisherName, String Series, String volume, String Issue, int index){
        return comix.getChild(publisherName).getChild(Series).getChild(volume).getChild(Issue).getComics().get(index);
    }

    // @Test
    // public void testEditComic(){
    //     comix.addComic(new ConcreteComic(
    //         "DC Comics",
    //         "Batman",
    //         "Gotham Knights",
    //         "Constants",
    //         "2000",
    //         "ChoclateRain",
    //         null,
    //         null,
    //         null,
    //         null
    // ));
    //     String Comics=getComicsAtVolume("DC Comics", "Batman", "Gotham Knights");
        
    //     assertEquals(Comics, "Robins Death, Wings, ChoclateRain, Red Hood");
    //     Comic comic=getComicsByIndex("DC Comics", "Batman", "Gotham Knights","Constants", 1);
    //     Comic comicCopy=comic.copy();
    //     Comic comic2=getComicsByIndex("DC Comics", "Batman", "Gotham Knights","Constants", 0);
        
    //     assertEquals(comic2.toString(),"Wings");
    //     /*
    //      * Comics ids are the same when creating a deepcopy
    //      */
    //     assertEquals(comic.getComicID(),comicCopy.getComicID());
        
    //     //assertEquals(comic.getComicID(),comic2.getComicID());
        
    //     comix.getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").removeComic(comic2);
    //     assertEquals(getComicsAtVolume("DC Comics", "Batman", "Gotham Knights"),Comics);

        // assertEquals(comic.toString(),"ChoclateRain");

        // comix.removeComic(comic);
        
        // assertEquals(,null);

        // comic.setComicTitle("ChoclateStain");
        // comix.addComic(comic);


        //assertEquals(comic.getComicID(),comix.getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getChild("Constants").getComics().get(0).getComicID());

        //assertEquals(comix.getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().toString().replaceAll("\\[|\\]", ""),null);

    // }



    

}
