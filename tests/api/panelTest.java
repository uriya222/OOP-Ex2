package api;

import GUI.myPanel;

public class panelTest{
    public static void main(String[] args){
        MainManager main = new MainManager(1);
        myPanel p = new myPanel(main);
        geo_location a = new GeoLocation(0,0,0);
        geo_location b = new GeoLocation(1,-1,0);
        System.out.println(p.vectorDirection(a,b));
        System.out.println(p.vectorDirection(b,a));
    }
}
