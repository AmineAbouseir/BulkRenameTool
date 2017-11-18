package abouseir.amine.bulkrenametool.sortingPatterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByType implements SortingPattern {
    private boolean ascendant;

    public SortByType(boolean ascendant) {
        this.ascendant = ascendant;
    }

    public ArrayList<String[]> apply(String[] listName, String[] listPath) {
        if (this.ascendant)
            return sortASC(listName, listPath, 1);
        else {
            return sortDEASC(listName, listPath, -1);
        }
    }

    private ArrayList<String[]> sortASC(String[] listName, String[] listPath, int sortType) {
        int n = listName.length;
        ArrayList<String[]> arrayList = new ArrayList<>();
        String[] sortedListName = new String[n];
        String[] sortedListPath = new String[n];
        for (int i = 0; i < n; i++) {
            int index = listName[i].lastIndexOf(".");
            String[] list = new String[3];
            list[1] = listName[i];
            list[2] = listPath[i];
            if (index != -1 && index < listName[i].length() - 1) {
                list[0] = listName[i].substring(index + 1);
            } else {
                list[0] = "§";
            }
            arrayList.add(list);
        }
        Collections.sort(arrayList, new Comparator<String[]>() {
            @Override
            public int compare(String[] l1, String[] l2) {
                return l1[0].compareToIgnoreCase(l2[0]);
            }
        });
        for (int i = 0; i < n; i++) {
            sortedListName[i] = arrayList.get(i)[1];
            sortedListPath[i] = arrayList.get(i)[2];
        }
        arrayList = new ArrayList<>();
        arrayList.add(sortedListName);
        arrayList.add(sortedListPath);
        return arrayList;
    }

    private ArrayList<String[]> sortDEASC(String[] listName, String[] listPath, int sortType) {
        int n = listName.length;
        ArrayList<String[]> arrayList = new ArrayList<>();
        String[] sortedListName = new String[n];
        String[] sortedListPath = new String[n];
        for (int i = 0; i < n; i++) {
            int index = listName[i].lastIndexOf(".");
            String[] list = new String[3];
            list[1] = listName[i];
            list[2] = listPath[i];
            if (index != -1 && index < listName[i].length() - 1) {
                list[0] = listName[i].substring(index + 1);
            } else {
                list[0] = "§";
            }
            arrayList.add(list);
        }
        Collections.sort(arrayList, new Comparator<String[]>() {
            @Override
            public int compare(String[] l1, String[] l2) {
                return l2[0].compareToIgnoreCase(l1[0]);
            }
        });
        for (int i = 0; i < n; i++) {
            sortedListName[i] = arrayList.get(i)[1];
            sortedListPath[i] = arrayList.get(i)[2];
        }
        arrayList = new ArrayList<>();
        arrayList.add(sortedListName);
        arrayList.add(sortedListPath);
        return arrayList;
    }
}