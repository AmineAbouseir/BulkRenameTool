package abouseir.amine.bulkrenametool.Patterns;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;

public class NumberPattern extends Pattern {

    private int start;
    private int step;
    private String delimiter;
    private boolean sameLength;
    private String name = "Add Numbers";

    public NumberPattern(int start, int step, String delimiter, boolean sameLength) {
        this.start = start;
        this.step = step;
        this.delimiter = delimiter;
        this.sameLength = sameLength;
    }

    public ArrayList<String[]> apply(String[] listNewName, String[] listPath, String[] listName) {
        int length = 0;
        if (this.sameLength) {
            int end = start + step * listName.length;
            length = String.valueOf(end).length();
        }
        for (int i = 0; i < listName.length; i++) {
            listNewName[i] = apply(listNewName[i], i, length);
        }
        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(listNewName);
        arrayList.add(listPath);
        arrayList.add(listName);
        return arrayList;
    }

    private String apply(String name, int listIndex, int length) {
        int index = name.lastIndexOf(".");
        int number = start + step * listIndex;
        int numZeros = length - String.valueOf(number).length();
        String str = TextUtils.join("", Collections.nCopies(numZeros, "0")) + String.valueOf(number);
        String newName;
        if (index != -1)
            newName = name.substring(0, index) + delimiter + str + name.substring(index);
        else {
            newName = name + delimiter + str;
        }
        return newName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Start: " + String.valueOf(start) + ", Step: " + String.valueOf(step) + ", Same length: " + String.valueOf(sameLength);
    }
}
