package com.synk.models;

import java.util.ArrayList;
import java.util.Objects;

public class MergeKey {

    public ArrayList<Integer> keys;

    public MergeKey() {
        keys = new ArrayList<Integer>();
    }

    public void fromString(String str) {
        this.keys.clear();
        String[] strings = str.split("_");
        for (String item : strings) {
            if (!Objects.equals(item, "")) {
                this.keys.add(Integer.parseInt(item));
            }
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int item : this.keys) {
            s.append("_").append(item);
        }
        return s.toString();
    }
}