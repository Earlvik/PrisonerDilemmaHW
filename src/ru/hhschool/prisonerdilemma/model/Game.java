package ru.hhschool.prisonerdilemma.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public class Game {

    boolean logging;
    private int roundsNum = 0;

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }


    int gamesNum = 0;
    /**
     * Общий счёт если оба сотрудничают
     */
    public int BOTH_COOP = 10;
    /**
     * Общий счёт если один предаёт
     */
    public int ONE_DEFECT = 5;
    /**
     * Общий счёт если оба предают
     */
    public int BOTH_DEFECT = 0;
    /**
     * Личный счёт если оба сотрудничают
     */
    public int BOTH_COOP_PERSONAL = 6;
    /**
     * Личный счёт если игрок предал
     */
    public int YOU_DEFECT = 4;
    /**
     * Личный счёт если игрока предали
     */
    public int THEY_DEFECT = 0;
    /**
     * Личный счёт если оба предали
     */
    public int BOTH_DEFECT_PERSONAL = 1;

    int gameScore = 0;

    List<Player> players = new ArrayList<Player>();

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public void play(Player player1, Player player2){
        gamesNum++;
        if(logging){
            System.out.println("Play: "+player1+" and \n"+player2);
        }
        int result1 = player1.play(player2);
        int result2 = player2.play(player1);

        if(result1 == result2){
            if( result1 == Strategy.COOPERATE){
                gameScore += BOTH_COOP;
                player1.incScore(BOTH_COOP_PERSONAL);
                player2.incScore(BOTH_COOP_PERSONAL);
            }else{
                gameScore += BOTH_DEFECT;
                player1.incScore(BOTH_DEFECT_PERSONAL);
                player2.incScore(BOTH_DEFECT_PERSONAL);
            }
        }else {
            gameScore += ONE_DEFECT;
            if( result1 == Strategy.COOPERATE){
                player1.incScore(THEY_DEFECT);
                player2.incScore(YOU_DEFECT);
            }else{
                player1.incScore(YOU_DEFECT);
                player2.incScore(THEY_DEFECT);
            }
        }

        player1.handleResult(player2, result2);
        player2.handleResult(player1, result1);
        if(logging){
            System.out.println("Player 1 "+((result1 == Strategy.COOPERATE)?"COOPERATED":"DEFECTED"));
            System.out.println("Player 2 "+((result2 == Strategy.COOPERATE)?"COOPERATED":"DEFECTED"));
            System.out.println("Player 1 score became "+player1.getScore());
            System.out.println("Player 2 score became "+player2.getScore());
            System.out.println("Game score became "+gameScore);
        }
    }

    public int[] playEveryone(){
        roundsNum++;
        for(int i = 0; i < players.size() - 1; i++ ){
            for( int j = i+1; j < players.size(); j++){
                play(players.get(i), players.get(j));
            }
        }
        int[] results = new int[players.size() + 1];
        results[0] = gameScore;
        for(int i = 1; i < results.length; i++){
            results[i] = players.get(i-1).getScore();
        }
        return results;
    }

    public String getGameRules(){
        StringBuilder sb = new StringBuilder("");
        sb.append("If both players cooperate, company gets ").append(BOTH_COOP);
        sb.append(" and each player gets ").append(BOTH_COOP_PERSONAL);
        sb.append("\nIf both players defect, company gets ").append(BOTH_DEFECT);
        sb.append(" and each player gets ").append(BOTH_DEFECT_PERSONAL);
        sb.append("\nIf one cooperates and one defects, company gets ").append(ONE_DEFECT);
        sb.append(" and cooperator gets ").append(THEY_DEFECT).append(" and defector gets ").append(YOU_DEFECT);
        return sb.toString();
    }

    public void setGameRules(int bothCoop, int bothDefect, int oneDefect, int bothCoopPersonal, int bothDefectPersonal,
    int youDefect, int theyDefect){
        BOTH_COOP = bothCoop;
        BOTH_DEFECT = bothDefect;
        BOTH_COOP_PERSONAL = bothCoopPersonal;
        BOTH_DEFECT_PERSONAL = bothDefectPersonal;
        ONE_DEFECT = oneDefect;
        YOU_DEFECT = youDefect;
        THEY_DEFECT = theyDefect;
    }

    public void reset(){
        for(Player player:players){
            player.setScore(0);
            gameScore = 0;
            gamesNum = 0;
            roundsNum = 0;
        }
    }

    public Player[] getPlayers(){
        return players.toArray(new Player[players.size()]);
    }



    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        for(Player player: players){
            sb.append(player.toString())
                    .append("\n");
        }
        sb.append(results());
        return sb.toString();
    }

    public String results(){
        return "Total score: " + gameScore + "\nAverage game score: "
                + ((double) gameScore / (double) gamesNum)
                + "\nAverage round score: " + ((double) gameScore / (double) roundsNum);
    }

}
