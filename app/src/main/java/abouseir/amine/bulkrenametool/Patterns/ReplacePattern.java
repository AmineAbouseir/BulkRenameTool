package abouseir.amine.bulkrenametool.Patterns;

import java.util.ArrayList;

public class ReplacePattern extends Pattern {

    private String oldString;
    private String newString;
    private String name = "Replace Sequence";

    public ReplacePattern(String oldString, String newString) {
        this.oldString = oldString;
        this.newString = newString;
    }

    public ArrayList<String[]> apply(String[] listNewName, String[] listPath, String[] listName) {
        for (int i = 0; i < listName.length; i++) {
            listNewName[i] = listNewName[i].replaceAll(oldString, newString);
        }
        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(listNewName);
        arrayList.add(listPath);
        arrayList.add(listName);
        return arrayList;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Replace '" + oldString + "' by '" + newString + "'";
    }

}
