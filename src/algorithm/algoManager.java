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
         //diacstra_algo(2);

    }

    @Override
    public void run(){
        super.run();
        diacstra_algo(main);
    }

    public void diacstra_algo(MainManager main){
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
        main.startGame();
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread tmp = new diacstraAlgo(main, i,d);
            tmp.start();
        }
        while (main.isRunning()){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.move();
            //System.out.println(main.timeToEnd());
        }
        try {
            Files.write(Paths.get("machineLearning.txt"), (main.getGameInfo().grade()+","+main.getGameInfo().moves()+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
        }
        System.out.println(main.getGameInfo().grade());
        System.out.println(main.getGameInfo().moves());

    }
    private static void random_algo(int scenario){
        MainManager main = new MainManager(scenario);
        new GUI(main);
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
                Thread.sleep(0);
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
