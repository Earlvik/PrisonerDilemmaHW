package ru.hhschool.prisonerdilemma.model.strategies;

import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

import java.util.Random;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public class RandomStrategy implements Strategy {

    Random rnd = new Random();

    @Override
    public int play(Player player) {
        return rnd.nextInt(Strategy.COOPERATE + 1);
    }

    @Override
    public void handleResult(Player player, int result) {

    }

    @Override
    public String shortInfo() {
        return "Random";
    }

    @Override
    public String toString(){
        return "Random. Non-predictable behaviour.";
    }
}
