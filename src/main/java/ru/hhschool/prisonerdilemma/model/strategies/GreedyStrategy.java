package ru.hhschool.prisonerdilemma.model.strategies;

import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

/**
 * Created by Виктор on 01.03.2015.
 */
public class GreedyStrategy implements Strategy{

    Player player;

    @Override
    public int play(Player player) {
        return (this.player.getScore() >= player.getScore())?COOPERATE:DEFECT;
    }

    @Override
    public void handleResult(Player player, int result) {
    }

    @Override
    public String shortInfo() {
        return "Greedy";
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public String toString(){
        return "Greedy. Defects if other player has bigger score";
    }


}
