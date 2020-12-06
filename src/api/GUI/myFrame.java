package api.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myFrame extends JFrame implements ActionListener{

    MenuItem menuItem =new MenuItem("inside");
    private myPanel panel = new myPanel();

    public static void main(String[] args){
        myFrame n = new myFrame();
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
    }
    public myFrame(){
        this.setTitle("Pokémon ruby");
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        panel.mainMenu();
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
