package com.ksu.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ksu on 12.03.2016.
 */
public class Collections {

    public static void main(String[] args){
        String[] langs = {"Java", "C++", "Python", "Perl"};

        List<String> langsList = new ArrayList<String>();
        langsList.add("Java");
        langsList.add("PHP");

        List<String> langsList2 = Arrays.asList("Java", "C++", "Python", "Perl");

        for(String l: langsList2){
            System.out.println("I want to learn " + l);
        }

        for(int i = 0; i < langsList.size(); i++){
            System.out.println("I want to learn " + langsList.get(i));
        }

        List nonTypeList =  Arrays.asList("Java", 2, "Python", 4.5);
        for(Object l: nonTypeList){
            System.out.println("I want to learn " + l);
        }
    }
}
