package ru.hhschool.prisonerdilemma.model.strategies;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class EvilStrategyTest {

    Player evilPlayer;
    Player kindPlayer;
    EvilStrategy evilStrategy;

    @Before
    public void setUp(){
        evilPlayer = Mockito.mock(Player.class);
        kindPlayer = Mockito.mock(Player.class);

        when(evilPlayer.play(any(Player.class))).thenReturn(Strategy.DEFECT);
        when(kindPlayer.play(any(Player.class))).thenReturn(Strategy.COOPERATE);

        evilStrategy = new EvilStrategy();
    }

    @Test
    public void testPlay() throws Exception {
        assertEquals(Strategy.DEFECT, evilStrategy.play(evilPlayer));
        assertEquals(Strategy.DEFECT, evilStrategy.play(kindPlayer));
    }


    @Test
    public void testInfo() throws Exception {
        assertEquals("Evil. Always Defects.", evilStrategy.toString());
        assertEquals("Evil", evilStrategy.shortInfo());
    }
}