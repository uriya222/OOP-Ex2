package api;

import GUI.GUI;
import GUI.myPanel;
import algorithm.algoManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class panelTest{
    public static void main(String[] args){
//      MainManager main = new MainManager(1);
//        myPanel p = new myPanel(main);
//        geo_location a = new GeoLocation(0,0,0);
//        geo_location b = new GeoLocation(1,-1,0);
//        System.out.println(p.vectorDirection(a,b));
//        System.out.println(p.vectorDirection(b,a));


            MainManager main = new MainManager(1);
            Thread algo = new algoManager(main);
            algo.start();
            GUI p = new GUI(main);


    }
}
