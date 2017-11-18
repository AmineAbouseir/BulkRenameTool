package abouseir.amine.bulkrenametool.renamePatterns;

public class ReplacePattern implements RenamePattern {

    private String oldString;
    private String newString;

    public ReplacePattern(String oldString, String newString) {
        this.oldString = oldString;
        this.newString = newString;
    }

    public String[] apply(String[] listName) {
        String[] listNewName = new String[listName.length];
        for (int i = 0; i < listName.length; i++) {
            listNewName[i] = listName[i].replaceAll(oldString, newString);
        }
        return listNewName;
    }

    public String getOldString() {
        return oldString;
    }

    public void setOldString(String oldString) {
        this.oldString = oldString;
    }

    public String getNewString() {
        return newString;
    }

    public void setNewString(String newString) {
        this.newString = newString;
    }
}
