package algorithm;

import api.DWGraph_Algo;
import api.MainManager;
import api.dw_graph_algorithms;
import api.node_data;
import object.PokemonInterface;

//TODO finish javadoc
public class algoManager extends Thread{
    MainManager main;
    private int[] A11A={0,10,11,12,13,14,15,16,17,18,19,20,30};
    private int[] A11B={0,1,7,8,9,10,21,22,23,24,25,26};
    private int[] A11C={1,2,3,4,5,6,7,27,28,29};
    private int[] A20A={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
    private int[] A23A={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,29,37,38};
    private int[] A20B={16,17,18,19,20,21,22,23,24,25,26,27,29,30,31,32,33,34,35,36,37,38};
    private int[] A20C={14,15,15,26,27,28,29,30,38,39,40,41,42,43,44,45,46,47};
    public algoManager(MainManager main){
        this.main=main;
    }
    @Override
    public void run(){
        super.run();
        if(main.getGameInfo().gameLevel()!=11&&main.getGameInfo().gameLevel()!=20&&main.getGameInfo().gameLevel()!=23)
            diacstra_algo(main);
        else area_algo(main);
    }
    //TODO maybe remove this method
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

    //TODO maybe remove this method
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

    private void area_algo(MainManager main){
        dw_graph_algorithms d=new DWGraph_Algo();
        d.init(main.getGraph());
        if (main.getGameInfo().gameLevel()==11) {
            main.addAgent(13);
            main.addAgent(22);
            main.addAgent(7);
            main.startGame();
            for (int i = 0; i < main.getGameInfo().agents(); i++) {
                Thread tmp = new AreaAlgo(main, i, d, A11A, A11B, A11C);
                tmp.start();
            }
        }
        if (main.getGameInfo().gameLevel()==20||main.getGameInfo().gameLevel()==23) {
            main.addAgent(13);
            main.addAgent(21);
            main.addAgent(40);
            main.startGame();
            for (int i = 0; i < main.getGameInfo().agents(); i++) {
                Thread tmp;
                if(main.getGameInfo().gameLevel()==20)
                    tmp = new AreaAlgo(main, i, d, A20A, A20B, A20C);
                else
                    tmp = new AreaAlgo(main, i, d, A23A, A20B, A20C);
                tmp.start();
            }
        }

    }
    private void diacstra_algo(MainManager main){
        dw_graph_algorithms d=new DWGraph_Algo();
        d.init(main.getGraph());
        int[] start = new int[main.getGameInfo().getPokemon()];
        int k = 0;
        boolean flag = false;
        for (PokemonInterface p : main.getPokemonList()) {
            start[k++] = p.getEdge().getSrc();
        }
        for (int j = 0; j < main.getGameInfo().agents(); j++) {
            if (j == start.length) {
                flag = true;
                break;
            }
            main.addAgent(start[j]);
        }
        if (flag) {
            for (int j = start.length; j < main.getGameInfo().agents(); j++) {
                main.addAgent(0);
            }
        }
        main.startGame();
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread tmp = new diacstraAlgo(main, i, d);
            tmp.start();
        }
    }
    //TODO maybe remove this method
    private void random_algo(int scenario){
        MainManager main = new MainManager(scenario);
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
    private int random(int[] range){
        int num = (int)(Math.random()*(range.length));
        return range[num];
    }

}
