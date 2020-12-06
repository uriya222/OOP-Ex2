package api;

import api.GUI.myFrame;
import object.Agent;
import object.AgentsInterface;

import static org.junit.jupiter.api.Assertions.*;

class MainManagerTest{
    public static void main(String[] args){
        MainManager main = new MainManager(11);
        myFrame n = new myFrame(main);
        main.move();
        for (AgentsInterface a:main.getAgentList()
             ) {
            //System.out.println(a.getPos());
        }
    }

}