package ru.hhschool.prisonerdilemma.model.strategies;

import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public class KindStrategy implements Strategy {
    @Override
    public int play(Player player) {
        return Strategy.COOPERATE;
    }

    @Override
    public void handleResult(Player player, int result) {

    }

    @Override
    public String shortInfo() {
        return "Kind";
    }

    @Override
    public String toString(){
        return "Kind. Always cooperates.";
    }
}
