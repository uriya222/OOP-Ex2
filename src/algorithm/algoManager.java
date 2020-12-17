package algorithm;

import api.DWGraph_Algo;
import api.MainManager;
import api.dw_graph_algorithms;
import api.node_data;
import object.PokemonInterface;

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
        int g_info=main.getGameInfo().gameLevel();
        if(g_info!=11&&g_info!=20&&g_info!=23)
            diacstra_algo(main);
        else area_algo(main);
        while (main.isRunning());
        System.out.println("grade: " + main.getGameInfo().grade());
        System.out.println("moves: " + main.getGameInfo().moves());
    }


    /**
     * 1-choose the best start pos of any agent-by calculating a lot of variables.
     * 2-start the game(only at levels 11,20,23)
     * 3-run the game with Area_Algo
     * @param main
     */
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

    /**
     * 1-choose the best start pos of any agent-by calculating a lot of variables.
     * 2-start the game(the rest levels)
     * 3-run the game with diacstra_Algo
     * @param main
     */
    private void diacstra_algo(MainManager main){
        dw_graph_algorithms d=new DWGraph_Algo();
        d.init(main.getGraph());
        switch (main.getGameInfo().gameLevel()) {
            case 2:
            case 3:
                main.addAgent(4);
                break;
            case 4:
                main.addAgent(5);
                break;
            case 5:
            case 7:
                main.addAgent(7);
                break;
            case 8:
            case 9:
                main.addAgent(9);
                break;
            case 10:
                main.addAgent(13);
                break;
            default:
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
                break;
        }
        main.startGame();
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread tmp = new diacstraAlgo(main, i, d);
            tmp.start();
        }
    }

    /**
     * random algo -- not efficient so not in use(but its working)
     * only for control and staff
     * @param main
     */
    private void random_algo(MainManager main){
        int[] range = new int[main.getGraph().nodeSize()];
        int k =0;
        for (node_data n:main.getGraph().getV()
        ) {
            range[k++] = n.getKey();
        }
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            main.addAgent(random(range));
        }
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread n = new randomAlgo(main, i);

            n.start();
        }
        while (main.isRunning()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.move();
        }
    }
    private int random(int[] range){
        int num = (int)(Math.random()*(range.length));
        return range[num];
    }

}
