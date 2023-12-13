// import com.comix.model.collection.visitor.PersonalCollectionVisitor;
// import com.comix.model.collection.visitor.strategy.SearchType;
// import com.comix.model.collection.visitor.strategy.SeriesTitleSearch;
// import com.comix.model.comic.ConcreteComic;

// import java.util.TreeMap;

// public class TestCollectionSearch {
// public static void main(String[] args) {
// TreeMap<String, ConcreteComic> comics = new TreeMap<>();

// comics.put("Batman - Death in the Family - Part 1", new ConcreteComic(
// "DC Comics",
// "Batman",
// "Death in the Family",
// "The Diplomat's Son",
// "1988",
// null,
// null,
// null,
// null,
// null
// ));

// comics.put("Batman - Death in the Family - Part 2", new ConcreteComic(
// "DC Comics",
// "Batman",
// "Death in the Family",
// "Dead Letter",
// "1988",
// null,
// null,
// null,
// null,
// null
// ));

// comics.put("Batman - Death in the Family - Part 3", new ConcreteComic(
// "DC Comics",
// "Batman",
// "Death in the Family",
// "A Death in the Family",
// "1989",
// null,
// null,
// null,
// null,
// null
// ));

// comics.put("Batman - Death in the Family - Part 4", new ConcreteComic(
// "DC Comics",
// "Batman",
// "Death in the Family",
// "Ashes to Ashes",
// "1989",
// null,
// null,
// null,
// null,
// null
// ));

// SearchType searchTitle = new SeriesTitleSearch(true, "Death in the Family");
// PersonalCollectionVisitor visitor = new PersonalCollectionVisitor();
// visitor.setStrategy(searchTitle);

// for (ConcreteComic comic : comics.values()) {
// comic.accept(visitor);
// }
// }
// }
