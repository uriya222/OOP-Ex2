package GUI;

import api.MainManager;

public class GUI extends Thread{
    MainManager main;
    myFrame mainFrame;
    public int refreshRateInMillis = 10; //default

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

}


