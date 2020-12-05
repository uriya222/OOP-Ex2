package api.GUI;

import api.GeoLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class myPanel extends JPanel implements MouseListener{

    private GeoLocation geoLocation = new GeoLocation(0,0,0);
    public myPanel(){
        this.setBackground(Color.red);
        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.fillOval((int)geoLocation.x(),(int)geoLocation.y(),20,20);


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
}
