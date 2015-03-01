package ru.hhschool.prisonerdilemma.model.strategies;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class RandomStrategyTest {

    Player evilPlayer;
    Player kindPlayer;
    RandomStrategy randomStrategy;

    @Before
    public void setUp(){
        evilPlayer = Mockito.mock(Player.class);
        kindPlayer = Mockito.mock(Player.class);

        when(evilPlayer.play(any(Player.class))).thenReturn(Strategy.DEFECT);
        when(kindPlayer.play(any(Player.class))).thenReturn(Strategy.COOPERATE);

        randomStrategy = new RandomStrategy();
    }

    @Test
    public void testPlay() throws Exception {
        boolean hasCoop = false,hasDefect = false;
        for(int i = 0; i< 100; i++){
            if(randomStrategy.play(kindPlayer) == Strategy.COOPERATE){
                hasCoop = true;
            }else{
                hasDefect = true;
            }
        }
        assertTrue("Both results should be present", hasDefect && hasCoop);

    }


    @Test
    public void testInfo() throws Exception {
        assertEquals("Random. Non-predictable behaviour!", randomStrategy.toString());
        assertEquals("Random", randomStrategy.shortInfo());
    }
}