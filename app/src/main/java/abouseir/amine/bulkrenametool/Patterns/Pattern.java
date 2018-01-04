package abouseir.amine.bulkrenametool.Patterns;

import java.util.ArrayList;

/**
 * Created by amineabouseir on 1/4/18.
 */

public abstract class Pattern {
    public abstract ArrayList<String[]> apply(String[] listNewName, String[] listPath, String[] listName);

    public abstract String getName();

    public abstract String getDescription();
}
