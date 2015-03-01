/**
 * Created by Earlviktor on 04.02.2015.
 */
package ru.hhschool.prisonerdilemma.model;

public class Player {
    int score = 0;
    final Strategy strategy;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void incScore(int inc){
        score+=inc;
    }

    public int play(Player other){
        return strategy.play(other);
    }

    public void handleResult(Player other, int result){
        strategy.handleResult(other , result);
    }

    @Override
    public String toString(){
        return "Player Score: "+score+" Strategy: "+strategy.toString();
    }

    public String shortInfo() { return strategy.shortInfo() + " : "+score;}

    public Player(Strategy strategy) {
        this.strategy = strategy;
    }
}
