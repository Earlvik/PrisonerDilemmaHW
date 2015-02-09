package ru.hhschool.prisonerdilemma.ui;

import ru.hhschool.prisonerdilemma.model.Game;
import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.strategies.EvilStrategy;
import ru.hhschool.prisonerdilemma.model.strategies.KindStrategy;
import ru.hhschool.prisonerdilemma.model.strategies.RandomStrategy;
import ru.hhschool.prisonerdilemma.model.strategies.TitForTatStrategy;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public class ConsolePlay {
    public static void main(String[] args) {
        Game game = new Game();

        Player kindPlayer = new Player(new KindStrategy());
        Player evilPlayer = new Player(new EvilStrategy());
        Player randomPlayer = new Player(new RandomStrategy());
        Player tit50Player = new Player(new TitForTatStrategy(0.5));
        Player tit30Player = new Player(new TitForTatStrategy(0.3));
        Player tit80Player = new Player(new TitForTatStrategy(0.8));

        game.addPlayer(kindPlayer);
        game.addPlayer(evilPlayer);
        game.addPlayer(randomPlayer);
        game.addPlayer(tit30Player);
        game.addPlayer(tit50Player);
        game.addPlayer(tit80Player);

        game.setLogging(true);

        for(int i = 0; i< 100; i++){
            game.playEveryone();
        }

        System.out.println(game);
    }


}
