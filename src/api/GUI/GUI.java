package api.GUI;

import api.MainManager;
import object.Agent;
import object.AgentsInterface;

public class GUI{
    public static void main(String[] args){
        MainManager main = new MainManager(11);
        myFrame mainFrame = new myFrame(main);
        main.getAgentList();
        AgentsInterface agent;
        for (AgentsInterface a:main.getAgentList()
             ) {
            if (a.getId()==7){
                agent = a;
                break;
            }
        }



    }






}
