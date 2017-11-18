package abouseir.amine.bulkrenametool.renamePatterns;

public class PrefixPattern implements RenamePattern {

    private String prefix;

    public PrefixPattern(String prefix) {
        this.prefix = prefix;
    }

    public String[] apply(String[] listName) {
        String[] listNewName = new String[listName.length];
        for (int i = 0; i < listName.length; i++) {
            listNewName[i] = this.prefix + listName[i];
        }
        return listNewName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
