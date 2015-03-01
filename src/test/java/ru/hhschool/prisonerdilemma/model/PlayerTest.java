package ru.hhschool.prisonerdilemma.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerTest {

    Player player;
    Player another;

    Strategy kind;
    Strategy evil;

    @Before
    public void setUp(){
        kind = Mockito.mock(Strategy.class);
        evil = Mockito.mock(Strategy.class);
        when(kind.play(any(Player.class))).thenReturn(Strategy.COOPERATE);
        when(evil.play(any(Player.class))).thenReturn(Strategy.DEFECT);
        when(kind.toString()).thenReturn("HELLO");
        when(kind.shortInfo()).thenReturn("HELLO");
        player = new Player(kind);
        another = new Player(evil);
    }

    @Test
    public void testScoreValue() throws Exception {

        assertEquals("Default score should be 0",0, player.getScore());
        player.setScore(10);
        assertEquals("New score should be 10",10, player.getScore());

    }

    @Test
    public void testGetStrategy() throws Exception {

        assertEquals("The strategy should be the kind one", kind, player.getStrategy());
    }



    @Test
    public void testIncScore() throws Exception {

        assertEquals("Score should be 0", 0, player.getScore());
        player.incScore(6);
        player.incScore(5);
        assertEquals("New score should be 11", 11, player.getScore());
    }

    @Test
    public void testPlay() throws Exception {
        player.play(another);
        verify(kind).play(another);
    }

    @Test
    public void testHandleResult() throws Exception {
        player.handleResult(another, Strategy.COOPERATE);
        verify(kind).handleResult(another, Strategy.COOPERATE);
    }

    @Test
    public void testToString() throws Exception {
        String expected = "Player Score: 0 Strategy: HELLO";
        String result = player.toString();
        assertEquals(expected, result);
    }

    @Test
    public void testShortInfo() throws Exception {
        String expected = "HELLO : 0";
        String result = player.shortInfo();
        assertEquals(expected, result);
    }
}