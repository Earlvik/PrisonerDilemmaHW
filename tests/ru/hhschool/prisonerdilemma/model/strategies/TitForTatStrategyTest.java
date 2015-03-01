package ru.hhschool.prisonerdilemma.model.strategies;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TitForTatStrategyTest {

    Player evilPlayer;
    Player kindPlayer;
    TitForTatStrategy tftStrategy;

    @Before
    public void setUp(){
        evilPlayer = Mockito.mock(Player.class);
        kindPlayer = Mockito.mock(Player.class);

        when(evilPlayer.play(any(Player.class))).thenReturn(Strategy.DEFECT);
        when(kindPlayer.play(any(Player.class))).thenReturn(Strategy.COOPERATE);
        tftStrategy = new TitForTatStrategy(0);
    }


    //TfT player cooperates first, then defects if betrayed and then cooperates again
    @Test
    public void testPlay() throws Exception {
        assertEquals(Strategy.COOPERATE, tftStrategy.play(evilPlayer));
        tftStrategy.handleResult(evilPlayer , Strategy.DEFECT);
        assertEquals(Strategy.DEFECT, tftStrategy.play(evilPlayer));
        assertEquals(Strategy.COOPERATE, tftStrategy.play(evilPlayer));

        assertEquals(Strategy.COOPERATE, tftStrategy.play(kindPlayer));
        assertEquals(Strategy.COOPERATE, tftStrategy.play(kindPlayer));
    }


    @Test
    public void testInfo() throws Exception {
        assertEquals("Tit fo Tat. Cooperates, but if the partner defects, revenges with probability 1.0", tftStrategy.toString());
        assertEquals("TfT - 0%", tftStrategy.shortInfo());
    }
}