package api;

import api.GUI.myFrame;

public class Ex2{

    public static void main(String[] args) throws InterruptedException {
        MainManager m=new MainManager(1);
        m.addAgent(9);
        myFrame f=new myFrame(m);
        m.startGame();
        m.chooseNextEdge(0,8);
        m.move();

    }

}
