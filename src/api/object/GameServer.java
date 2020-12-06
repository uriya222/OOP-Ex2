package api.object;

import api.game_service;

public interface GameServer{



    public int getPokemon();
    public boolean isLoggedIn();
    public int moves();
    public int grade();
    public int gameLevel();
    public int maxUserLevel();
    public int id();
    public String graph(); //i dont know what is it
    public int agents();

    /*
    {"GameServer":{"pokemons":6,"is_logged_in":false,"moves":0,"grade":0,"game_level":11,"max_user_level":-1,"id":0,"graph":"data/A2","agents":3}}
    */


}
