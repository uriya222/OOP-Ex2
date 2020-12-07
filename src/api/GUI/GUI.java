package api.GUI;

import api.MainManager;
import object.AgentsInterface;

public class GUI extends Thread{
    MainManager main;
    myFrame mainFrame;
    public int refreshRateInMillis = 200; //default
    public GUI(MainManager main ){
        Thread thread = new GUI(main,new myFrame(main),this.refreshRateInMillis);
        thread.start();
    }

    public GUI(MainManager main, int refreshRateInMillis ){
        Thread thread = new GUI(main,new myFrame(main),refreshRateInMillis);
        thread.start();
    }
    private GUI(MainManager main, myFrame mainFrame, int refreshRateInMillis ){
        this.refreshRateInMillis = refreshRateInMillis;
        this.main = main;
        this.mainFrame = mainFrame;
    }


    @Override
    public void run(){
        super.run();
        mainFrame.refresh(refreshRateInMillis);
    }


    public static void main(String[] args){
        MainManager main = new MainManager(11);
        myFrame mainFrame = new myFrame(main);
        main.getAgentList();
        AgentsInterface agent;
        for (AgentsInterface a:main.getAgentList().values()
        ) {
            if (a.getId()==7){
                agent = a;
                break;
            }
        }



    }






}
