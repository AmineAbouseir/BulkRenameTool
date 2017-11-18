package abouseir.amine.bulkrenametool.sortingPatterns;

import java.util.ArrayList;

public interface SortingPattern {
    ArrayList<String[]> apply(String[] listName, String[] listPath);
}