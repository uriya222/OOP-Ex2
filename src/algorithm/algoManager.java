package algorithm;

import GUI.GUI;
import api.MainManager;
import api.node_data;

public class algoManager{


    public static void main(String[] args) {
        random_algo(11);


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
        main.startGame();
        System.out.println(main.timeToEnd());
        for (int i = 0; i < main.getGameInfo().agents(); i++) {
            Thread n = new randomAlgo(main, i);

            n.start();
        }
        System.out.println("work");
        while (main.isRunning()){
            try {
                Thread.sleep(100);
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
