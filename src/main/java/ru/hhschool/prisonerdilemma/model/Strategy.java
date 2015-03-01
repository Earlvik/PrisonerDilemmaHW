package ru.hhschool.prisonerdilemma.model;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public interface Strategy {

    public static final int COOPERATE = 1;
    public static final int DEFECT = 0;

    public int play(Player player);
    public void handleResult(Player player, int result);

    public String shortInfo();
}
