package GUI;

import api.*;
import object.AgentsInterface;
import object.PokemonInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class myPanel extends JPanel implements MouseListener, ActionListener{
    private directed_weighted_graph mainGraph = new DWGraph_DS();
    private GeoLocation geoLocation = new GeoLocation(0,0,0);
    private double[] min_max;
    private int screenSize = 500;
    MainManager main;
    int menu = 1;
/*    enum menu {
        start,
        main,
        arina
    }*/
    private int IntPlay = 27 , direction = 1;
    private boolean first = true;
    private int screenOffsetX;
    private int screenOffsetY;
    boolean moreData = true;
    /**
     * constructor, takes a pointer to MainManager
     * @param main - pointer
     */
    public myPanel(MainManager main){
        this.main = main;
        this.setBackground(Color.white);
        this.addMouseListener(this);
        mainGraph = main.getGraph();
        minMax(mainGraph.getV());
    }
    /**
     * refresh the page
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (menu== 1) paintArina(g);
        else if (menu==2) mainMenu(g);

        //System.out.println("screen refresh");
    }

    /**
     * the main menu page
     * @param g
     * @return
     */
    protected boolean mainMenu(Graphics g){ //cleanup
        screenSize = this.getHeight()<this.getWidth()?this.getHeight()-50:this.getWidth()-60;
        if (direction>0){
            IntPlay++;
            if (IntPlay>30)direction=-1;
        }else {
            IntPlay--;
            if (IntPlay<12)direction=+1;
        }
        g.setColor(Color.gray);
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 30));
        char[] c = ("Pokemon ruby").toCharArray();
        int x =this.getWidth()/2;
        int y =this.getHeight()/4;
        g.drawChars(c,0,c.length,x-(int)(IntPlay*2)-40,y);
        if (first) {
            this.setLayout(null);
            JButton button1 = new JButton("start");
            Dimension size = button1.getPreferredSize();
            button1.setBounds(x,y*3, size.width, size.height);
            //button1.setBounds(12,12,100,100);
            button1.addActionListener(this);
            this.add(button1);
            first = false;
        }
        return true;
    }
    public boolean isOnMenu(){
        return menu!=1;
    }

    /**
     * this is the arina page
     * @param g
     */
    private void paintArina(Graphics g){
        screenSize = this.getHeight()<this.getWidth()?this.getHeight()-50:this.getWidth()-60;
        screenOffsetX = screenSize<this.getWidth()?(this.getWidth()-screenSize)/2:0;
        screenOffsetY = screenSize<this.getHeight()?(this.getHeight()-screenSize)/2:0;

        g.setColor(Color.gray);
        for (node_data n:mainGraph.getV()
        ) {
            for (edge_data e:mainGraph.getE(n.getKey())
            ) {
                int xSrs = (int)((mainGraph.getNode(e.getSrc()).getLocation().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+5+screenOffsetX;
                int ySrs = (int)((mainGraph.getNode(e.getSrc()).getLocation().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+5+screenOffsetY;
                int xDest = (int)((mainGraph.getNode(e.getDest()).getLocation().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+5+screenOffsetX;
                int yDest = (int)((mainGraph.getNode(e.getDest()).getLocation().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+5+screenOffsetY;
                g.drawLine(xSrs,ySrs,xDest,yDest);
            }
        }

        g.setColor(Color.black);
        for (node_data n:mainGraph.getV()
        ) {
            int x =(int)((n.getLocation().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+screenOffsetX;
            int y =(int)((n.getLocation().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+screenOffsetY;
            g.fillOval(x,y,10,10);
            //g.drawImage(getImage("home.png"),x-15,y-10,30,30,null);
            char[] c = ("<"+n.getKey()).toCharArray();
            g.drawChars(c,0,c.length,x+11,y+11);
        }

        g.setColor(Color.red);
        for (PokemonInterface p: main.getPokemonList()
        ) {
            int x =(int)((p.getPos().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+screenOffsetX;
            int y =(int)((p.getPos().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+screenOffsetY;
            //g.fillOval(x,y,10,10);
            g.drawImage(getImage("pok.png"),x-10,y-10,30,30,null);
        }

        g.setColor(Color.blue);
        for (AgentsInterface a: main.getAgentList().values()
        ) {
            int x =(int)((a.getPos().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+screenOffsetX;
            int y =(int)((a.getPos().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+screenOffsetY;
            g.fillOval(x+2,y+2,6,6);
            char[] c = ("^"+a.getId()).toCharArray();
            //System.out.println(a.getId());
            g.drawChars(c,0,c.length,x,y+20);
        }
        scoreBoard(g);
        if (moreData){
            moreData(g);
        }
        //System.out.println("screen refresh");
    }
    private void scoreBoard(Graphics g){
        int xSpace = 240;
        int ySpace = 80;
        int dis = 15;
        g.drawRect(this.getWidth()-xSpace,this.getHeight()-ySpace,xSpace-5,ySpace-5);
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 27));
        char[] c = ("Score Board").toCharArray();
        g.drawChars(c,0,c.length,this.getWidth()-xSpace,this.getHeight()-ySpace-5);

        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 13));

        c = ("time til end:  "+ (int)main.timeToEnd()/1000 +":"+(int)main.timeToEnd()%1000).toCharArray();
        g.drawChars(c,0,c.length,this.getWidth()-xSpace+5,this.getHeight()-ySpace+dis);

        c = ("grade:  "+ (int)main.getGameInfo().grade()).toCharArray();
        g.drawChars(c,0,c.length,this.getWidth()-xSpace+5,this.getHeight()-ySpace+dis*2);

        c = ("moves:  "+ (int)main.getGameInfo().moves()).toCharArray();
        g.drawChars(c,0,c.length,this.getWidth()-xSpace+5,this.getHeight()-ySpace+dis*3);

        c = ("logged in:  "+ main.getGameInfo().isLoggedIn()).toCharArray();
        g.drawChars(c,0,c.length,this.getWidth()-xSpace+5,this.getHeight()-ySpace+dis*4);

    }
    private void moreData(Graphics g){
        int xSpace = 100;
        int ySpace = 200;
        int dis = 15;
        g.drawRect(5,5,xSpace,ySpace);
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 12));
        int line = 0;
        for (int i = 0; i < main.getAgentList().size(); i++) {
            char[] c = ("agent "+i+" : ").toCharArray();
            g.drawChars(c,0,c.length,9,20+13*line++);
            c = ("speed: "+main.getAgentList().get(i).getSpeed()).toCharArray();
            g.drawChars(c,0,c.length,9,20+13*line++);
            c = ("score: "+main.getAgentList().get(i).getValue()).toCharArray();
            g.drawChars(c,0,c.length,9,20+13*line++);
            c = ("dest: "+main.getAgentList().get(i).getDest()).toCharArray();
            g.drawChars(c,0,c.length,9,20+13*line++);
            line++;
        }


    }

    private BufferedImage getImage(String file){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./gameAssets/"+file));
        } catch (IOException e) {
            System.out.println("reading pic failed");
        }
        return img;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        double x = min_max[0]+((min_max[1]-min_max[0])*(-screenOffsetX+e.getX()))/screenSize;
        double y = min_max[2]+((min_max[3]-min_max[2])*(-screenOffsetY+e.getY()))/screenSize;
        System.out.println(x+","+y);
        System.out.println(e.getX()+","+e.getY());
        System.out.println(mainGraph.getNode(8).getLocation());
        geoLocation = new GeoLocation(x,y,0);
        repaint();
        System.out.println("*****");
        moreData = !moreData;

    }

    @Override
    public void mousePressed(MouseEvent e){
    }

    @Override
    public void mouseReleased(MouseEvent e){

    }

    @Override
    public void mouseEntered(MouseEvent e){

    }

    @Override
    public void mouseExited(MouseEvent e){

    }

    /**
     * @param nodes
     * @return array of minMax values [0]-minX, [1]-maxX, [2]-minY, [3]-maxY
     */
    private void minMax(Collection<node_data> nodes){
        double minX=0,maxX=0;
        double minY=0,maxY=0;
        boolean first = true;
        for (node_data n:nodes
        ) {
            if(first) {
                minX=maxX=n.getLocation().x();
                minY=maxY=n.getLocation().y();
                first = false;
                continue;
            }
            if (n.getLocation().x() < minX) {
                minX=n.getLocation().x();
            }
            if (n.getLocation().x()>maxX){
                maxX=n.getLocation().x();
            }
            if (n.getLocation().y() < minY) {
                minY=n.getLocation().y();
            }
            if (n.getLocation().y()>maxY){
                maxY=n.getLocation().y();
            }
        }
        min_max = new double[]{minX, maxX, minY, maxY};
    }


    @Override
    public void actionPerformed(ActionEvent e){
        menu = 1;
        first = true;
        this.removeAll();
    }
}
