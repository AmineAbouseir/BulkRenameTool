package abouseir.amine.bulkrenametool.renamePatterns;

public class SuffixPattern implements RenamePattern {

    private String suffix;

    public SuffixPattern(String suffix) {
        this.suffix = suffix;
    }

    public String[] apply(String[] listName) {
        String[] listNewName = new String[listName.length];
        for (int i = 0; i < listName.length; i++) {
            listNewName[i] = apply(listName[i]);
        }
        return listNewName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    private String apply(String name) {
        int index = name.lastIndexOf(".");
        String newName;
        if (index != -1)
            newName = name.substring(0, index) + suffix + name.substring(index);
        else {
            newName = name + suffix;
        }
        return newName;
    }
}
