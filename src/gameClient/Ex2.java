package gameClient;


import GUI.GUI;
import Server.Game_Server_Ex2;
import algorithm.algoManager;
import api.MainManager;
import api.game_service;


/**
 * from this class we starting the game.
 */

public class Ex2{
    public static void main(String[] args) {

        //letting the GUI do all the work
        if (args.length==0)
            new GUI();
        // for playing with the commands for starting the game
        else {
            game_service game = Game_Server_Ex2.getServer(Integer.parseInt(args[1]));
            MainManager main = new MainManager(game);
            main.login(Long.parseLong(args[0]));
            GUI graphic = new GUI(main);
            Thread algoManager = new algoManager(main);
            algoManager.start();
        }
    }
}
