package ru.hhschool.prisonerdilemma.model;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameTest {

    Game game;
    Player player;
    Player[] morePlayers;

    @Before
    public void setUp(){
        game = new Game();
        player = Mockito.mock(Player.class);
        morePlayers = new Player[10];
        for(int i=0; i<10; i++){
            morePlayers[i] = Mockito.mock(Player.class);
            when(morePlayers[i].play(any(Player.class))).thenReturn(Strategy.COOPERATE);
        }
    }




    @Test
    public void testGameScoreValue() throws Exception {
        int score = game.getGameScore();
        assertEquals("Game score should be 0 before playing, but was "+score, 0, score);
        game.setGameScore(100);
        score = game.getGameScore();
        assertEquals("Game score was set to 100, but was "+score, 100, score);
    }


    @Test
    public void testAddPlayer() throws Exception {
        Player[] players = game.getPlayers();
        assertEquals("There should be no players before adding", 0, players.length);
        game.addPlayer(player);
        players = game.getPlayers();
        assertEquals("There should be one added player", 1, players.length);
        assertEquals("The returned player should be the same", player, players[0]);
    }

    @Test
    public void testRemovePlayer() throws Exception {
        game.addPlayer(player);
        for(Player p:morePlayers){
            game.addPlayer(p);
        }

        Assert.assertEquals("Added 11 players and should see this number", 11, game.getPlayers().length);
        game.removePlayer(player);
        Assert.assertEquals("Removed player and should see ten", 10, game.getPlayers().length);

    }

    @Test
    public void testPlayBothCoop() throws Exception {
        Player kind1 = new Player(new Strategy() {
            @Override
            public int play(Player player) {
                return COOPERATE;
            }

            @Override
            public void handleResult(Player player, int result) {

            }

            @Override
            public String shortInfo() {
                return null;
            }
        });

        Player kind2 = new Player(new Strategy() {
            @Override
            public int play(Player player) {
                return COOPERATE;
            }

            @Override
            public void handleResult(Player player, int result) {

            }

            @Override
            public String shortInfo() {
                return null;
            }
        });

        game.play(kind1, kind2);
        assertEquals("Game score after game should be "+game.getBothCoop(),game.getBothCoop(), game.gameScore);
        assertTrue("Players score should be the same and equal to " + game.getBothCoopPersonal(),
                (game.getBothCoopPersonal() == kind1.getScore() && game.getBothCoopPersonal() == kind2.getScore()));
    }

    @Test
    public void testPlayBothDefect(){
        Player evil1 = new Player(new Strategy() {
            @Override
            public int play(Player player) {
                return DEFECT;
            }

            @Override
            public void handleResult(Player player, int result) {

            }

            @Override
            public String shortInfo() {
                return null;
            }
        });

        Player evil2 = new Player(new Strategy() {
            @Override
            public int play(Player player) {
                return DEFECT;
            }

            @Override
            public void handleResult(Player player, int result) {

            }

            @Override
            public String shortInfo() {
                return null;
            }
        });

        game.play(evil1, evil2);
        assertEquals("Game score after game should be "+game.getBothDefect(),game.getBothDefect(), game.gameScore);
        assertTrue("Players score should be the same and equal to " + game.getBothDefect(),
                (game.getBothDefectPersonal() == evil1.getScore() && game.getBothDefectPersonal() == evil2.getScore()));
    }


    @Test
    public void testPlayOneDefect(){
        Player kind = new Player(new Strategy() {
            @Override
            public int play(Player player) {
                return COOPERATE;
            }

            @Override
            public void handleResult(Player player, int result) {

            }

            @Override
            public String shortInfo() {
                return null;
            }
        });

        Player evil = new Player(new Strategy() {
            @Override
            public int play(Player player) {
                return DEFECT;
            }

            @Override
            public void handleResult(Player player, int result) {

            }

            @Override
            public String shortInfo() {
                return null;
            }
        });
        game.play(kind, evil);
        assertEquals("Game score after game should be " + game.getOneDefect(), game.getOneDefect(), game.gameScore);
        assertEquals("Kind player score should be and equal to " + game.getTheyDefect(),
                game.getTheyDefect(), kind.getScore());

        assertEquals("Evil player score should be and equal to " + game.getYouDefect(),
                game.getYouDefect(), evil.getScore());
    }

    @Test
    public void testPlayEveryone() throws Exception {
        for(Player p:morePlayers){
            game.addPlayer(p);
        }

        int[] results = game.playEveryone();
        assertEquals("Result should be an array of size equal to players number plus total", 1+morePlayers.length, results.length);
    }

    @Test
    public void testGameRulesValue() throws Exception {
        String rulesBefore = game.getGameRules();
        game.setGameRules(0,1,2,3,4,5,6);
        //Check new values
        assertEquals(0, game.getBothCoop());
        assertEquals(1,game.getBothDefect());
        assertEquals(2,game.getOneDefect());
        assertEquals(3,game.getBothCoopPersonal());
        assertEquals(4,game.getBothDefectPersonal());
        assertEquals(5,game.getYouDefect());
        assertEquals(6,game.getTheyDefect());
        assertTrue("Game rules have not changed!", !rulesBefore.equals(game.getGameRules()));

    }

    @Test
    public void testReset() throws Exception {
        game.setGameScore(100);
        game.addPlayer(player);
        game.reset();
        assertEquals("Game score should be reset to 0", 0, game.gameScore);
        verify(player).setScore(0);
    }

    @Test
    public void testGetPlayers() throws Exception {
        for(Player p:morePlayers)
            game.addPlayer(p);
        assertEquals("Added players should be the same as they were", morePlayers.length, game.getPlayers().length);
    }

    @Test
    public void testToString() throws Exception {
        for(Player p: morePlayers)
            game.addPlayer(p);
        String str =game.toString();
        assertTrue(!str.equals(""));

    }

    @Test
    public void testResults() throws Exception {
        for(Player p: morePlayers)
            game.addPlayer(p);
        game.playEveryone();
        String str =game.results();
        assertTrue(!str.equals(""));
    }
}