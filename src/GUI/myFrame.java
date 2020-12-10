package GUI;


import api.MainManager;

import javax.swing.*;

public class myFrame extends JFrame{
    private MainManager main;
    private int refreshRateInMillis = 100;
    private myPanel panel;

    /**
     * constructor for the GUI
     * adds pointer to MainManager
     * @param main
     */
    public myFrame(MainManager main){
        this.main = main;
        this.setTitle("Pokémon Game");
        this.setSize(1200,900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new myPanel(main);
        this.add(panel);
        panel.repaint();
        this.setVisible(true);

    }
    public void refresh(int refreshRateInMillis){
        this.refreshRateInMillis = refreshRateInMillis;
        while (true) {
            try {
                Thread.sleep(this.refreshRateInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint();
            //if (main.isSet&&main.timeToEnd()==-1)break;
        }
    }

}
