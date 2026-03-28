package Main;

import Controller.Controller;
import Model.Pawn.Pawn;
import View.GUI;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class jUnitTestsMain {
    @Test
    public void setLastCardValueTest() throws IOException {
        GUI g = new GUI();

        g.game.setLastCardValue("1");
        assertEquals(g.game.getLastCardValue(), "1");
    }

    @Test
    public void takeTurn() throws IOException {
        GUI g = new GUI();

        if(g.game.getTurn() == 2){
            g.game.changeTurn();
        }
        assertEquals(g.game.getTurn(), 1);
    }

    @Test
    public void getLastCardValueTest() throws IOException {
        GUI g = new GUI();

        String s = g.game.getRandomCard();
        assertEquals(g.game.getLastCardValue(), s);
    }

    @Test
    public void movePawnTest() throws IOException {
        GUI g = new GUI();

        Pawn p = g.game.getPawn11();
        g.game.MovePawn(p, 1, 1);

        int i = g.game.getPawn11().getXY()[0];
        int j = g.game.getPawn11().getXY()[1];

        assertEquals(i, 1);
        assertEquals(j, 1);
    }

    @Test
    public void startPawnCoordinatesTest() throws IOException {
        GUI g = new GUI();
        if(g.game.getTurn() == 2){
            g.game.changeTurn();
        }
        g.game.setLastCardValue("1");
        g.startRedPawnButton1.doClick();

        int i = g.game.getPawn11().getXY()[0];
        int j = g.game.getPawn11().getXY()[1];

        assertEquals(i, 0);
        assertEquals(j, 4);
    }

    @Test
    public void sorryCardTest() throws IOException {
        GUI g = new GUI();

        int i = g.game.getPawn11().getXY()[0];
        int j = g.game.getPawn11().getXY()[1];

        if(g.game.getTurn() == 2){
            g.game.changeTurn();
        }
        g.game.setLastCardValue("1");
        g.startRedPawnButton1.doClick();

        if(g.game.getTurn() == 1){
            g.game.changeTurn();
        }
        g.game.setLastCardValue("Sorry");

        g.startYellowPawnButton1.doClick();
        g.buttons[i][j].doClick();

        assertEquals(g.game.getPawn21().getXY()[0], i);
        assertEquals(g.game.getPawn21().getXY()[1], j);
    }
}
