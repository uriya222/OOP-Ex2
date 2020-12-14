package GUI;

import api.MainManager;

public class GUI extends Thread{
    MainManager main;
    myFrame mainFrame;
    public int refreshRateInMillis = 10; //default

    /**
     *  constructor that start the GUI main
     *  screen without starting the game
     */
    public GUI(){
        MainManager main = new MainManager();
        Thread thread = new GUI(main,new myFrame(main),this.refreshRateInMillis);
        thread.start();
    }

    /**
     * constructor that start the GUI of the game(the graph and the agents)
     * without the GUI main menu
     * @param main
     */
    public GUI(MainManager main){
        Thread thread = new GUI(main,new myFrame(main),this.refreshRateInMillis);
        thread.start();
    }

    /**
     * constructor that start the GUI of the game(the graph and the agents)
     * without the GUI main menu and with a given refresh rate
     * @param main
     * @param refreshRateInMillis
     */
    public GUI(MainManager main, int refreshRateInMillis ){
        Thread thread = new GUI(main,new myFrame(main),refreshRateInMillis);
        thread.start();
    }

    /**
     * internal constructor for running in another thread
     * @param main
     * @param mainFrame
     * @param refreshRateInMillis
     */
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


