package com.labs.taqwa.util;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<String> listImage;

    public static void setListImage(List<String> arrayList){
        listImage = new ArrayList<>();
        listImage = arrayList;
    }

    public static List<String> getListImage(){
        return listImage;
    }
}
