package abouseir.amine.bulkrenametool.Patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByType extends Pattern {

    private boolean ascending;
    private String name = "Sort by Type";

    public SortByType(boolean ascending) {
        this.ascending = ascending;
    }

    public ArrayList<String[]> apply(String[] listNewName, String[] listPath, String[] listName) {
        if (this.ascending)
            return sortASC(listNewName, listPath, listName);
        else {
            return sortDEASC(listNewName, listPath, listName);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        if (ascending)
            return "Ascending Sort";
        else
            return "Descending Sort";
    }

    private ArrayList<String[]> sortASC(String[] listNewName, String[] listPath, String[] listName) {
        int n = listName.length;
        ArrayList<String[]> arrayList = new ArrayList<>();
        String[] sortedListNewName = new String[n];
        String[] sortedListPath = new String[n];
        String[] sortedListName = new String[n];
        String type = null;
        for (int i = 0; i < n; i++) {
            int index = listName[i].lastIndexOf(".");
            if (index != -1 && index < listName[i].length() - 1) {
                type = listName[i].substring(index + 1);
            } else {
                type = "§";
            }
            String[] list = {type, listNewName[i], listPath[i], listName[i]};
            arrayList.add(list);
        }
        Collections.sort(arrayList, new Comparator<String[]>() {
            @Override
            public int compare(String[] l1, String[] l2) {
                return l1[0].compareToIgnoreCase(l2[0]);
            }
        });
        for (int i = 0; i < n; i++) {
            sortedListNewName[i] = arrayList.get(i)[0];
            sortedListPath[i] = arrayList.get(i)[1];
            sortedListName[i] = arrayList.get(i)[2];
        }
        arrayList = new ArrayList<>();
        arrayList.add(sortedListNewName);
        arrayList.add(sortedListPath);
        arrayList.add(sortedListName);
        return arrayList;
    }

    private ArrayList<String[]> sortDEASC(String[] listNewName, String[] listPath, String[] listName) {
        int n = listName.length;
        ArrayList<String[]> arrayList = new ArrayList<>();
        String[] sortedListNewName = new String[n];
        String[] sortedListPath = new String[n];
        String[] sortedListName = new String[n];
        String type = null;
        for (int i = 0; i < n; i++) {
            int index = listName[i].lastIndexOf(".");
            if (index != -1 && index < listName[i].length() - 1) {
                type = listName[i].substring(index + 1);
            } else {
                type = "§";
            }
            String[] list = {type, listNewName[i], listPath[i], listName[i]};
            arrayList.add(list);
        }
        Collections.sort(arrayList, new Comparator<String[]>() {
            @Override
            public int compare(String[] l1, String[] l2) {
                return l2[0].compareToIgnoreCase(l1[0]);
            }
        });
        for (int i = 0; i < n; i++) {
            sortedListNewName[i] = arrayList.get(i)[0];
            sortedListPath[i] = arrayList.get(i)[1];
            sortedListName[i] = arrayList.get(i)[2];
        }
        arrayList = new ArrayList<>();
        arrayList.add(sortedListNewName);
        arrayList.add(sortedListPath);
        arrayList.add(sortedListName);
        return arrayList;
    }
}