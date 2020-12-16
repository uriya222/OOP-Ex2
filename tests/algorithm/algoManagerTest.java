package algorithm;

import GUI.GUI;
import api.MainManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class algoManagerTest{
    public static void main(String[] args){
        walkTest();
        //speedTest();
    }
   @Test
   static private void walkTest(){
       // test walking between 8 and 9
       MainManager main = new MainManager(1);
       new GUI(main);
        main.addAgent(8);
        main.startGame();
        double w;
        long a;
        long b = 1;
        int trys = 0;
        int times = 0;
        while (true) {
            a = main.chooseNextEdge(0, 9);
            //if (main.getAgentList().get(0).getDest()!=-1)
            w = main.getGraph().getEdge(main.getAgentList().get(0).getSrc(),main.getAgentList().get(0).getDest()).getWeight();
            System.out.println("8->9");
            System.out.println(w);
            System.out.println("w *1000: "+(w*1000));
            try {
                Thread.sleep((long)(w*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (main.getAgentList().get(0).getDest()!=-1) {
                main.move();
                b=System.currentTimeMillis();
                trys++;
            }
            System.out.println("trys: "+trys);
            if (trys>1) System.err.println("++++_+*)(&$)(*YEGCAKGLCGcajcla");
            System.out.println("real time: "+(b-a));
            System.out.println(times++);
            trys = 0;
            a = main.chooseNextEdge(0, 8);
            w = main.getGraph().getEdge(main.getAgentList().get(0).getSrc(),main.getAgentList().get(0).getDest()).getWeight();
            System.out.println("******");
            System.out.println("9->8");
            System.out.println(w);
            System.out.println("w *1000: "+(w*1000));
            try {
                Thread.sleep((long)(w*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (main.getAgentList().get(0).getDest()!=-1) {
                main.move();
                b=System.currentTimeMillis();
                trys++;
            }
            System.out.println("trys: "+trys);
            if (trys>1) System.err.println("++++_+*)(&$)(*YEGCAKGLCGcajcla");
            System.out.println("real time: "+(b-a));
            trys = 0;
            System.out.println("******");
            System.out.println(times++);

        }


    }

    static private void speedTest(){
        //check the speed
       MainManager main = new  MainManager(1);
        new GUI(main);
        main.addAgent(8);
        main.startGame();
        double w;
        long a;
        long b = 1;
        while (true) {
            a = main.chooseNextEdge(0, 9);
            //if (main.getAgentList().get(0).getDest()!=-1)
            w = main.getGraph().getEdge(main.getAgentList().get(0).getSrc(),main.getAgentList().get(0).getDest()).getWeight();
            System.out.println("8->9");
            System.out.println(w);
            System.out.println(w*1000);
            while (main.getAgentList().get(0).getDest()!=-1) {
                main.move();
                b=System.currentTimeMillis();
            }
            System.out.println(b-a);
            a = main.chooseNextEdge(0, 8);
            w = main.getGraph().getEdge(main.getAgentList().get(0).getSrc(),main.getAgentList().get(0).getDest()).getWeight();
            System.out.println("******");
            System.out.println("9->8");
            System.out.println(w);
            System.out.println(w*1000);
            while (main.getAgentList().get(0).getDest()!=-1) {
                main.move();
                b=System.currentTimeMillis();
            }
            System.out.println(b-a);
            System.out.println("******");

        }
    }

}