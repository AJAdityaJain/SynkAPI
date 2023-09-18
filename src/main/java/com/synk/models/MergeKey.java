package com.synk.models;

import java.util.ArrayList;
import java.util.Objects;

public class MergeKey {

    public ArrayList<Integer> keys;

    public MergeKey() {
        keys = new ArrayList<Integer>();
    }

    public static MergeKey fromString(String str) {
        MergeKey m = new MergeKey();
        m.keys.clear();
        String[] strings = str.split("_");
        for (String item : strings) {
            if (!Objects.equals(item, "")) {
                m.keys.add(Integer.parseInt(item));
            }
        }
        return m;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int item : this.keys) {
            s.append("_").append(item);
        }
        return s.toString();
    }
}