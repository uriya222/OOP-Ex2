package GUI;


import api.MainManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myFrame extends JFrame implements ActionListener{
    private MainManager main;
    private int refreshRateInMillis = 100;
    MenuItem menuItem =new MenuItem("inside");
    private myPanel panel;
/*    public static void main(String[] args){ //TODO: cleanup / not in use
        myFrame n = new myFrame(new MainManager(11));
       // n.setSize(500,500);
       // n.setVisible(true);
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("main");
        menuBar.add(menu);
        n.setMenuBar(menuBar);
        menu.add(n.menuItem);
        n.menuItem.addActionListener(n);
        //myPanel panel =new myPanel();
        //n.add(panel);
        n.getHeight();
        n.getWidth();
    }*/

    /**
     * constructor for the GUI
     * adds pointer to MainManager
     * @param main
     */
    public myFrame(MainManager main){
        this.main = main;
        this.setTitle("Pokémon ruby");
        this.setSize(700,550);
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
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==menuItem) System.out.println("test");
    }


/*
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Polygon p = new Polygon();
        for (int i = 0; i < 5; i++)
                p.addPoint((int) (100 + 50 * Math.cos(i * 2 * Math.PI / 5)),(int) (100 + 50 * Math.sin(i * 2 * Math.PI / 5)));
        g.drawPolygon(p);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.white);
        frame.setTitle("Pokémon ruby");

        frame.addWindowListener(new WindowAdapter() { //check if the frame is closing and end the thread
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = frame.getContentPane();
        myFrame m = new myFrame();
        m.setBackground(Color.blue);
        contentPane.add(m);
        frame.show();
    }*/
}
