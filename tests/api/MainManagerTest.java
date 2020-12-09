package api;

import Server.Game_Server_Ex2;
import GUI.GUI;

class MainManagerTest{
    public static void main(String[] args){
        //MainManager main = new MainManager(1);
       // myFrame n = new myFrame(main);
        game_service game = Game_Server_Ex2.getServer(1);
        MainManager main = new MainManager(game);
        new GUI(main,5);
        //System.out.println("test");
        main.addAgent(9);
        //main.startGame();
        System.out.println(game.getAgents());
        game.chooseNextEdge(0,8);
        System.out.println(game.getAgents());

        main.getAgentList();
        //System.out.println(game);
        String s = game.getPokemons();
        //System.out.println(game.getPokemons());
        main.chooseNextEdge(0,8);
        try {
            Thread.sleep(2000);//543
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(game.getAgents());

        int i = 0;
 //while (true){
     main.move();
        i++;
     if (!s.equals(game.getPokemons()) ){
         //System.out.println(game.getPokemons());
         //break;
     }
 //}
        System.out.println(game.getAgents());
        System.out.println(game.chooseNextEdge(0,6));
        System.out.println(game.getAgents());

       // System.out.println(i);


//{"Pokemons":[{"Pokemon":{"value":5.0,"type":-1,"pos":"35.197656770719604,32.10191878639921,0.0"}},{"Pokemon":{"value":8.0,"type":-1,"pos":"35.199963710098416,32.105723673136964,0.0"}}]}



    }

}