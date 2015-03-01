package ru.hhschool.prisonerdilemma.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public class Game {

    Logger logger = LoggerFactory.getLogger(Game.class);

    private int roundsNum = 0;



    int gamesNum = 0;
    private int bothCoop = 10;
    private int oneDefect = 5;
    private int bothDefect = 0;
    private int bothCoopPersonal = 6;
    private int youDefect = 4;
    private int theyDefect = 0;
    private int bothDefectPersonal = 1;

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

        logger.info("Play: "+player1+" and \n"+player2);

        int result1 = player1.play(player2);
        int result2 = player2.play(player1);

        if(result1 == result2){
            if( result1 == Strategy.COOPERATE){
                gameScore += getBothCoop();
                player1.incScore(getBothCoopPersonal());
                player2.incScore(getBothCoopPersonal());
            }else{
                gameScore += getBothDefect();
                player1.incScore(getBothDefectPersonal());
                player2.incScore(getBothDefectPersonal());
            }
        }else {
            gameScore += getOneDefect();
            if( result1 == Strategy.COOPERATE){
                player1.incScore(getTheyDefect());
                player2.incScore(getYouDefect());
            }else{
                player1.incScore(getYouDefect());
                player2.incScore(getTheyDefect());
            }
        }

        player1.handleResult(player2, result2);
        player2.handleResult(player1, result1);

        logger.info("Player 1 "+((result1 == Strategy.COOPERATE)?"COOPERATED":"DEFECTED")+
            "\nPlayer 2 "+((result2 == Strategy.COOPERATE)?"COOPERATED":"DEFECTED")+
            "\nPlayer 1 score became "+player1.getScore()+
            "\nPlayer 2 score became "+player2.getScore()+
            "\nGame score became "+gameScore);

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

        return "If both players cooperate, company gets "+bothCoop+
        " and each player gets "+bothCoopPersonal+
        "\nIf both players defect, company gets "+getBothDefect()+
        " and each player gets "+bothDefectPersonal+
        "\nIf one cooperates and one defects, company gets "+oneDefect+
        " and cooperator gets "+theyDefect+" and defector gets "+youDefect;

    }

    public void setGameRules(int bothCoop, int bothDefect, int oneDefect, int bothCoopPersonal, int bothDefectPersonal,
    int youDefect, int theyDefect){
        setBothCoop(bothCoop);
        setBothDefect(bothDefect);
        setBothCoopPersonal(bothCoopPersonal);
        setBothDefectPersonal(bothDefectPersonal);
        setOneDefect(oneDefect);
        setYouDefect(youDefect);
        setTheyDefect(theyDefect);
    }

    public void reset(){
        gameScore = 0;
        for(Player player:players){
            player.setScore(0);
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

    /**
     * Общий счёт если оба сотрудничают
     */
    public int getBothCoop() {
        return bothCoop;
    }

    public void setBothCoop(int bothCoop) {
        this.bothCoop = bothCoop;
    }

    /**
     * Общий счёт если один предаёт
     */
    public int getOneDefect() {
        return oneDefect;
    }

    public void setOneDefect(int oneDefect) {
        this.oneDefect = oneDefect;
    }

    /**
     * Общий счёт если оба предают
     */
    public int getBothDefect() {
        return bothDefect;
    }

    public void setBothDefect(int bothDefect) {
        this.bothDefect = bothDefect;
    }

    /**
     * Личный счёт если оба сотрудничают
     */
    public int getBothCoopPersonal() {
        return bothCoopPersonal;
    }

    public void setBothCoopPersonal(int bothCoopPersonal) {
        this.bothCoopPersonal = bothCoopPersonal;
    }

    /**
     * Личный счёт если игрок предал
     */
    public int getYouDefect() {
        return youDefect;
    }

    public void setYouDefect(int youDefect) {
        this.youDefect = youDefect;
    }

    /**
     * Личный счёт если игрока предали
     */
    public int getTheyDefect() {
        return theyDefect;
    }

    public void setTheyDefect(int theyDefect) {
        this.theyDefect = theyDefect;
    }

    /**
     * Личный счёт если оба предали
     */
    public int getBothDefectPersonal() {
        return bothDefectPersonal;
    }

    public void setBothDefectPersonal(int bothDefectPersonal) {
        this.bothDefectPersonal = bothDefectPersonal;
    }
}
