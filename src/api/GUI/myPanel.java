package api.GUI;

import Server.Game_Server_Ex2;
import api.*;

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
    public myPanel(){
        this.setBackground(Color.white);
        this.addMouseListener(this);
    }
    protected boolean mainMenu(){

        return true;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        screenSize = this.getHeight()<this.getWidth()?this.getHeight():this.getWidth();
        int H = 500;
        int W = 500;
        getGraph();
        for (node_data n:mainGraph.getV()
        ) {
            int x =(int)((n.getLocation().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]));
                    int y =(int)((n.getLocation().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]));
            g.fillOval(x,y,10,10);
            //System.out.println((int)((n.getLocation().x()-min_max[0])*500/min_max[1])+" - "+ n.getLocation().y()); //debug
            //System.out.println((n.getLocation().x()-min_max[0])*1400);
            for (edge_data e:mainGraph.getE(n.getKey())
            ) {
                int xSrs = (int)((mainGraph.getNode(e.getSrc()).getLocation().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+5;
                int ySrs = (int)((mainGraph.getNode(e.getSrc()).getLocation().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+5;
                int xDest = (int)((mainGraph.getNode(e.getDest()).getLocation().x()-min_max[0])*screenSize/(min_max[1]-min_max[0]))+5;
                int yDest = (int)((mainGraph.getNode(e.getDest()).getLocation().y()-min_max[2])*screenSize/(min_max[3]-min_max[2]))+5;
                g.drawLine(xSrs,ySrs,xDest,yDest);
            }
        }
    }

    private void getGraph(){ /// temporary
        game_service g = Game_Server_Ex2.getServer(11);
        String s = g.getGraph();
        System.out.println(s);
        try {
            PrintWriter t = new PrintWriter(new File("GServer.json"));
            t.write(s);
            t.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dw_graph_algorithms al = new DWGraph_Algo();
        al.load("GServer.json");
        mainGraph = al.getGraph();
        min_max=minMax(mainGraph.getV());
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
    private double[] minMax(Collection<node_data> nodes){
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
        return new double[]{minX, maxX, minY, maxY};
    }
}
