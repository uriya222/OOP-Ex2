package algorithm;

import GUI.GUI;
import api.DWGraph_Algo;
import api.MainManager;
import api.dw_graph_algorithms;
import api.node_data;
import object.PokemonInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class algoManager extends Thread{
    MainManager main;
    public algoManager(MainManager main){
        this.main=main;
    }
    public static void main(String[] args) {
        //random_algo(11);
        MainManager main = new MainManager(12);
        //diacstra_algo(main);
        //improve_diacstra_algo(23);
        

    }
    
    @Override
    public void run(){
        super.run();
        diacstra_algo(main);
    }
    private void walkTest(){
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


private void speedTest(){
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

/*    System.out.println(main.chooseNextEdge(0, 9));
    System.out.println(main.getGraph().getEdge(8, 9).getWeight());
    while (true) {
        main.move();
        if (main.getAgentList().get(0).getDest() == -1) {
            System.out.println(System.currentTimeMillis());
            break;
        }
    }*/
}

    private static void improve_diacstra_algo(MainManager main){
        dw_graph_algorithms d=new DWGraph_Algo();
        d.init(main.getGraph());
        //new GUI(main);
        int [] start=new int[main.getGameInfo().getPokemon()];
        int k=0;
        boolean flag=false;
        for (PokemonInterface p:main.getPokemonList()) {
            start[k++]=p.getEdge().getSrc();
        }
        for (int j = 0; j < main.getGameInfo().agents(); j++) {
            if(j==start.length){
                flag=true;
                break;
            }
            main.addAgent(start[j]);
        }
        if (flag){
            for (int j = start.length; j < main.getGameInfo().agents(); j++){
                main.addAgent(0);
            }
        }
        System.out.println(main.getGame().getPokemons());
        main.startGame();
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread tmp = new improveDijAlgo(main, i,d);
            tmp.start();
        }
        while (main.isRunning()){
        }

    }
    private static void diacstra_algo(MainManager main){
        dw_graph_algorithms d=new DWGraph_Algo();
        //MainManager main = new MainManager(scenario);
        d.init(main.getGraph());
        //new GUI(main);
        int [] start=new int[main.getGameInfo().getPokemon()];
        int k=0;
        boolean flag=false;
        for (PokemonInterface p:main.getPokemonList()) {
            start[k++]=p.getEdge().getSrc();
        }
        for (int j = 0; j < main.getGameInfo().agents(); j++) {
            if(j==start.length){
                flag=true;
                break;
            }
            main.addAgent(start[j]);
        }
        if (flag){
            for (int j = start.length; j < main.getGameInfo().agents(); j++){
                main.addAgent(0);
            }
        }
        main.startGame();
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread tmp = new diacstraAlgo(main, i,d);
            tmp.start();
        }
        while (main.isRunning()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.move();
        }
    }
    private static void random_algo(int scenario){
        MainManager main = new MainManager(scenario);
        //new GUI(main);
        System.out.println(main.getGameInfo().agents());
        int[] range = new int[main.getGraph().nodeSize()];
        int k =0;
        for (node_data n:main.getGraph().getV()
        ) {
            range[k++] = n.getKey();
        }
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            main.addAgent(random(range));
        }
        //main.startGame(selectedLevel);
        System.out.println(main.timeToEnd());
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread n = new randomAlgo(main, i);

            n.start();
        }
        System.out.println("work");
        while (main.isRunning()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.move();
            System.out.println(main.timeToEnd());
        }
        System.out.println(main.getGameInfo().grade());
        System.out.println(main.getGameInfo().moves());
    }
    static private int random(int[] range){
        int num = (int)(Math.random()*(range.length));
        return range[num];
    }

}
