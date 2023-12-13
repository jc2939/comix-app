package com.comix.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import com.comix.controller.action.AddComicAction;
import com.comix.controller.action.AuthComicAction;
import com.comix.controller.action.EditComicAction;
import com.comix.controller.action.GradeComicAction;
import com.comix.controller.action.RemoveComicAction;
import com.comix.controller.action.SignComicAction;
import com.comix.controller.action.SlabComicAction;
import com.comix.controller.action.actionHistory.History;
import com.comix.persistence.PersonalCollectionDao;
import com.comix.persistence.PersonalCollectionFileDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.comix.model.collection.Collection;
import com.comix.model.comic.Comic;
import com.comix.model.comic.ConcreteComic;
import com.comix.model.comic.GradeDecorator;


@Testable
public class UndoRedoTest {
    private ObjectMapper mapper = new ObjectMapper();
    private PersonalCollectionDao collectionDao;
    
    @BeforeEach
    public void setup() throws IOException
    {

        collectionDao = new PersonalCollectionFileDao(mapper);
        collectionDao.createCollection("Jerry");
        Comic comic1 = new ConcreteComic(
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
        );
        Comic comic2 = new ConcreteComic(
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
        );
        Comic comic3 = new ConcreteComic(
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
        ); 
        Comic comic4 = new ConcreteComic(
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
        );
        Comic comic5 = new ConcreteComic(
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
        );
        ArrayList<Comic> comics = collectionDao.getCollection("Jerry").getComics();
        for (Comic comic : comics){
            collectionDao.removeComic("Jerry", comic);
        }

        collectionDao.addComic("Jerry", comic1);
        collectionDao.addComic("Jerry", comic2);
        collectionDao.addComic("Jerry", comic3);
        collectionDao.addComic("Jerry", comic4);
        collectionDao.addComic("Jerry", comic5);
    }

    @Test
    public void testAddComic()
    {
        Comic newComic = new ConcreteComic(
            "DC Comics",
            "Batman",
            "Gotham Knights",
            "Constants",
            "2000",
            "BlackRock",
            null,
            null,
            null,
            null);
        AddComicAction addAction = new AddComicAction("Jerry", newComic, collectionDao);
        addAction.createMemento();
        addAction.execute();
        ArrayList<Comic> updatedCollection = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, BlackRock, Red Hood, Red");
        History history = History.getHistory();
        history.undo();
        ArrayList<Comic> updatedCollection2 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection2.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood, Red");
        history.redo();
        ArrayList<Comic> updatedCollection3 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection3.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, BlackRock, Red Hood, Red");
    }

    @Test
    public void testRemoveComic()
    {
        Comic comicToDelete = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        RemoveComicAction removeAction = new RemoveComicAction("Jerry", comicToDelete, collectionDao);
        removeAction.createMemento();
        removeAction.execute();
        ArrayList<Comic> updatedCollection = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red");
        History history = History.getHistory();
        history.undo();
        ArrayList<Comic> updatedCollection2 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection2.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood, Red");
        history.redo();
        ArrayList<Comic> updatedCollection3 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection3.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red");
    }

    @Test
    public void testEditComic()
    {
        Comic comicToEdit = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        Comic comicCopy = comicToEdit.copy();
        comicCopy.setComicTitle("Changed Title");
        EditComicAction editAction = new EditComicAction("Jerry", comicToEdit, comicCopy, collectionDao);
        editAction.createMemento();
        editAction.execute();
        ArrayList<Comic> updatedCollection = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Changed Title, Red");
        History history = History.getHistory();
        history.undo();
        ArrayList<Comic> updatedCollection2 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection2.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood, Red");
        history.redo();
        ArrayList<Comic> updatedCollection3 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection3.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Changed Title, Red");
    }

    @Test
    public void testGradeComic()
    {
        Comic comicToGrade = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        GradeComicAction gradeAction = new GradeComicAction("Jerry", comicToGrade, 7, collectionDao);
        gradeAction.createMemento();
        gradeAction.execute();
        ArrayList<Comic> updatedCollection = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Graded: 7*, Red");
        History history = History.getHistory();
        history.undo();
        ArrayList<Comic> updatedCollection2 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection2.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood, Red");
        history.redo();
        ArrayList<Comic> updatedCollection3 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection3.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Graded: 7*, Red");
    }
    
    @Test
    public void testSlabComic()
    {
        Comic comicToGrade = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        GradeComicAction gradeAction = new GradeComicAction("Jerry", comicToGrade, 7, collectionDao);
        gradeAction.createMemento();
        gradeAction.execute();
        Comic comicToSlab = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        SlabComicAction slabAction = new SlabComicAction("Jerry", comicToSlab, true, collectionDao);
        slabAction.createMemento();
        slabAction.execute();
        ArrayList<Comic> updatedCollection = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Graded: 7* *Slabbed*, Red");
        History history = History.getHistory();
        history.undo();
        ArrayList<Comic> updatedCollection2 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection2.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Graded: 7*, Red");
        history.redo();
        ArrayList<Comic> updatedCollection3 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection3.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Graded: 7* *Slabbed*, Red");
    }

    @Test
    public void testSignComic()
    {
        Comic comicToSign1 = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        SignComicAction signAction1 = new SignComicAction("Jerry", comicToSign1, "Alec", collectionDao);
        signAction1.createMemento();
        signAction1.execute();
        Comic comicToSign2 = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        SignComicAction signAction2 = new SignComicAction("Jerry", comicToSign2, "Angela", collectionDao);
        signAction2.createMemento();
        signAction2.execute();
        ArrayList<Comic> updatedCollection = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=false, Angela=false}*, Red");
        History history = History.getHistory();
        history.undo();
        ArrayList<Comic> updatedCollection2 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection2.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=false}*, Red");
        history.redo();
        ArrayList<Comic> updatedCollection3 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection3.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=false, Angela=false}*, Red");
    }

    @Test
    public void testAuthComic()
    {
        Comic comicToSign1 = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        SignComicAction signAction1 = new SignComicAction("Jerry", comicToSign1, "Alec", collectionDao);
        signAction1.createMemento();
        signAction1.execute();
        Comic comicToSign2 = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        SignComicAction signAction2 = new SignComicAction("Jerry", comicToSign2, "Angela", collectionDao);
        signAction2.createMemento();
        signAction2.execute();

        Comic comicToAuth1 = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        AuthComicAction authAction1 = new AuthComicAction("Jerry", comicToAuth1, "Alec", collectionDao);
        authAction1.createMemento();
        authAction1.execute();
        Comic comicToAuth2 = collectionDao.getCollection("Jerry").getChild("DC Comics").getChild("Batman").getChild("Gotham Knights").getComics().get(2);
        AuthComicAction authAction2 = new AuthComicAction("Jerry", comicToAuth2, "Angela", collectionDao);
        authAction2.createMemento();
        authAction2.execute();
    
        ArrayList<Comic> updatedCollection = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=true, Angela=true}*, Red");
        History history = History.getHistory();
        history.undo();
        ArrayList<Comic> updatedCollection2 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection2.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=true, Angela=false}*, Red");
        history.undo();
        ArrayList<Comic> updatedCollection3 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection3.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=false, Angela=false}*, Red");
        history.redo();
        ArrayList<Comic> updatedCollection4 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection4.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=true, Angela=false}*, Red");
        history.redo();
        ArrayList<Comic> updatedCollection5 = collectionDao.getCollection("Jerry").getComics();
        assertEquals(updatedCollection5.toString().replaceAll("\\[|\\]", ""), "Darkness, Robins Death, Wings, Red Hood *Signed: {Alec=true, Angela=true}*, Red");
    }
}
