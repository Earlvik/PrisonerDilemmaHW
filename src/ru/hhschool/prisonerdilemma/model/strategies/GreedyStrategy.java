package ru.hhschool.prisonerdilemma.model.strategies;

import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

/**
 * Created by Виктор on 01.03.2015.
 */
public class GreedyStrategy implements Strategy{
    @Override
    public int play(Player player) {
        return 0;
    }

    @Override
    public void handleResult(Player player, int result) {

    }

    @Override
    public String shortInfo() {
        return null;
    }
}
