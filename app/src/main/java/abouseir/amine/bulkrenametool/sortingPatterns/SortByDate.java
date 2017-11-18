package abouseir.amine.bulkrenametool.sortingPatterns;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByDate implements SortingPattern {
    private boolean ascendant;

    public SortByDate(boolean ascendant) {
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
            File file = new File(listPath[i] + "/" + listName[i]);
            String[] list = {String.valueOf(file.lastModified()), listName[i], listPath[i]};
            arrayList.add(list);
        }
        Collections.sort(arrayList, new Comparator<String[]>() {
            @Override
            public int compare(String[] l1, String[] l2) {
                if (Long.valueOf(l1[0]) > Long.valueOf(l2[0]))
                    return 1;
                else if (Long.valueOf(l1[0]) < Long.valueOf(l2[0]))
                    return -1;
                else
                    return 0;
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
            File file = new File(listPath[i] + "/" + listName[i]);
            String[] list = {String.valueOf(file.lastModified()), listName[i], listPath[i]};
            arrayList.add(list);
        }
        Collections.sort(arrayList, new Comparator<String[]>() {
            @Override
            public int compare(String[] l1, String[] l2) {
                if (Long.valueOf(l2[0]) > Long.valueOf(l1[0]))
                    return 1;
                else if (Long.valueOf(l2[0]) < Long.valueOf(l1[0]))
                    return -1;
                else
                    return 0;
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