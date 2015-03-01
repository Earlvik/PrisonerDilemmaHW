package ru.hhschool.prisonerdilemma.model.strategies;

import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

import java.util.*;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public class TitForTatStrategy implements Strategy {

    Set<Player> evilDoers = new HashSet<Player>();
    final double forgivenessProbability;
    Random rnd = new Random();

    public double getForgivenessProbability(){
        return forgivenessProbability;
    }

    public TitForTatStrategy(double forgivenessProbability){
        this.forgivenessProbability = forgivenessProbability;
    }

    @Override
    public int play(Player player) {
        if(evilDoers.contains(player)){
            evilDoers.remove(player);
            boolean forgive = (rnd.nextDouble() < forgivenessProbability);
            return forgive?COOPERATE:DEFECT;
        }
        return COOPERATE;
    }


    @Override
    public void handleResult(Player player, int result) {
        if(result == Strategy.DEFECT){
            evilDoers.add(player);
        }
    }

    @Override
    public String shortInfo() {
        return "TfT - "+(int)(100 * forgivenessProbability)+"%";
    }

    @Override
    public String toString(){
        return "Tit fo Tat. Cooperates, but if the partner defects, revenges with probability "+(1-forgivenessProbability);
    }
}
