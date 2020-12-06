package api.GUI;

import Server.Game_Server_Ex2;
import api.*;
import object.AgentsInterface;
import object.PokemonInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;

public class myPanel extends JPanel implements MouseListener{
    private directed_weighted_graph mainGraph = new DWGraph_DS();
    private GeoLocation geoLocation = new GeoLocation(0,0,0);
    private double[] min_max;
    private int screenSize = 500;
    MainManager main;

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

    protected boolean mainMenu(){ //cleanup

        return true;
    }

    /**
     * refresh the page
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        screenSize = this.getHeight()<this.getWidth()?this.getHeight()-50:this.getWidth()-60;
        int screenOffsetX = screenSize<this.getWidth()?(this.getWidth()-screenSize)/2:0;
        int screenOffsetY = screenSize<this.getHeight()?(this.getHeight()-screenSize)/2:0;

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
            char[] c = ("<"+n.getKey()).toCharArray();
            g.drawChars(c,0,c.length,x+11,y+11);
        }
        g.setColor(Color.red);
        for (PokemonInterface p: main.getPokemonList()
             ) {
            int x =(int)((p.getPos().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+screenOffsetX;
            int y =(int)((p.getPos().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+screenOffsetY;
            g.fillOval(x,y,10,10);

        }
        g.setColor(Color.blue);
        for (AgentsInterface a: main.getAgentList()
        ) {
            int x =(int)((a.getPos().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+screenOffsetX;
            int y =(int)((a.getPos().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+screenOffsetY;
            g.fillOval(x+2,y+2,6,6);
            char[] c = ("^"+a.getId()).toCharArray();
            //System.out.println(a.getId());
            g.drawChars(c,0,c.length,x,y+20);
        }
        //System.out.println("screen refresh");
    }

    @Override
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        geoLocation = new GeoLocation(x,y,0);
        repaint();
        System.out.println("click");
    }

    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e){

    }

    @Override
    public void mouseEntered(MouseEvent e){
        System.out.println("in");
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
}
