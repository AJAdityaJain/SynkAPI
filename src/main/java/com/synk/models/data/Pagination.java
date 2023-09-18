package com.synk.models.data;

import java.util.ArrayList;

public class Pagination<T> {
    public int pages;
    public int size;
    public int length;

    public ArrayList<ArrayList<T>> data;

    public Pagination(int size, T[] dataList) {
        this.size = size;
        length = dataList.length;
        pages = (int) Math.ceil((double) length / size);
        data = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (T item : dataList) {
            if (j == 0)
                data.add(new ArrayList<>());
            data.get(i).add(item);
            j++;
            if (j == size) {
                j = 0;
                i++;
            }
        }
    }
}


//        this.size = size;
//        length = dataList.length;
//        pages = (int) Math.ceil((double) length / size);
//        data = (T[][]) new Object[pages][size];
//        int i = 0;
//        int j = 0;
//        for (T item : dataList) {
//            data[i][j] = item;
//            j++;
//            if (j == size) {
//                j = 0;
//                i++;
//            }
//        }
//
