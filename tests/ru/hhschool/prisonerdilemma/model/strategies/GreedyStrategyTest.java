package ru.hhschool.prisonerdilemma.model.strategies;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Виктор on 01.03.2015.
 */
public class GreedyStrategyTest {

    Player kindPlayer;
    Player testPlayer;
    GreedyStrategy greedyStrategy;

    @Before
    public void setUp(){
        kindPlayer = Mockito.mock(Player.class);
        when(kindPlayer.play(any(Player.class))).thenReturn(Strategy.COOPERATE);
        greedyStrategy = new GreedyStrategy();
        testPlayer = new Player(greedyStrategy);
    }

    @Test
    public void testPlayWithLowerScore(){
        kindPlayer.setScore(100);
        testPlayer.setScore(200);
        assertEquals(Strategy.COOPERATE, testPlayer.play(kindPlayer));
    }

    @Test
    public void testPlayWithHigherScore(){
        kindPlayer.setScore(300);
        testPlayer.setScore(200);
        assertEquals(Strategy.DEFECT, testPlayer.play(kindPlayer));
    }

    @Test
    public void testInfo() throws Exception {
        assertEquals("Greedy. Defects if other player has bigger score", greedyStrategy.toString());
        assertEquals("Greedy", greedyStrategy.shortInfo());
    }
}
