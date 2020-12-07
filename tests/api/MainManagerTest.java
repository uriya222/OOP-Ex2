package api;

import Server.Game_Server_Ex2;
import api.GUI.GUI;
import api.GUI.myFrame;
import object.AgentsInterface;

class MainManagerTest{
    public static void main(String[] args){
        //MainManager main = new MainManager(1);
       // myFrame n = new myFrame(main);
        game_service game = Game_Server_Ex2.getServer(1);
        MainManager main = new MainManager(game);
        GUI n = new GUI(main,1000);
        System.out.println("test");
        main.addAgent(9);
        main.startGame();
        main.getAgentList();
        System.out.println(game);
        String s = game.getPokemons();
        System.out.println(game.getPokemons());
        main.chooseNextEdge(0,8);
        try {
            Thread.sleep(150);//543
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 0;
 while (true){
     main.move();
        i++;
     if (!s.equals(game.getPokemons()) ){
         System.out.println(game.getPokemons());
         break;
     }
 }
        System.out.println(i);


//{"Pokemons":[{"Pokemon":{"value":5.0,"type":-1,"pos":"35.197656770719604,32.10191878639921,0.0"}},{"Pokemon":{"value":8.0,"type":-1,"pos":"35.199963710098416,32.105723673136964,0.0"}}]}



    }

}