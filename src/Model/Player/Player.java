package Model.Player;

import Model.Pawn.Pawn;
import Model.Pawn.Position;

import java.awt.*;

/**
 * Contains the methods signatures needed for
 * creating a player
 * @author ioannis kapetanakis
 */
public class Player {
    private String name;
    private int id;
    private Color color;
    private Pawn pawn1;
    private Pawn pawn2;
    private boolean foldPressed, has_played;

    /**
     * @param name
     * @para id
     * @param color
     * <b>Constructor</b>
     * <b>postcondition</b>: Initializes the name, the id and the color of the player
     */
    public Player(String name, int id, Color color){
        this.name = name;
        this.id = id;
        this.color = color;
        foldPressed = false;
        has_played = false;
    }

    /**
     * @param name
     * <b>Transformer</b>: Changes the name of the player
     * <b>Postcondition</b>: player name = param value
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param color
     * <b>Transformer</b>: Changes the color of the player
     * <b>Postcondition</b>: player color = param value
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * @param id
     * <b>Transformer</b>: Changes the id of the player
     * <b>Postcondition</b>: player id = param value
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @param pawn1
     * <b>Transformer</b>: Initializes the first pawn of the player
     * <b>Postcondition</b>: Pawn1 field = first pawn
     */
    public void setPawn1(Pawn pawn1) {
        this.pawn1 = pawn1;
    }

    /**
     * @param pawn2
     * <b>Transformer</b>: Initializes the second pawn of the player
     * <b>Postcondition</b>: Pawn2 field = second pawn
     */
    public void setPawn2(Pawn pawn2) {
        this.pawn2 = pawn2;
    }

    /**
     * <b>Accessor</b>: Returns the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * <b>Accessor</b>: Returns the id of the player
     * @return the id of the player
     */
    public int getId() {
        return id;
    }

    /**
     * <b>Accessor</b>: Returns the color of the player
     * @return the color of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * <b>Accessor</b>: Returns the first pawn of the player
     * @return the first pawn of the player
     */
    public Pawn getPawn1() {
        return pawn1;
    }

    /**
     * <b>Accessor</b>: Returns the second pawn of the player
     * @return the second pawn of the player
     */
    public Pawn getPawn2() {
        return pawn2;
    }

    /**
     * <b>Observer</b>: Checks if the player moved both of his pawns at home
     * @return true if the player moved both of his pawns at home, else false
     */
    public boolean has_finished(){
        if(pawn1.getPosition() == Position.Home && pawn2.getPosition() == Position.Home){
            return true;
        }
        return false;
    }


    /**
     * <b>Accessor</b>: Checks if fold button is pressed
     * @return true if pressed, else false
     */
    public boolean getFoldPressed(){
        return foldPressed;
    }

    /**
     * @param bool
     * <b>Transformer</b>: Represent the press and reinitializing of the fold button
     * <b>Postcondition</b>: Sets foldPressed = true if pressed, else false
     */
    public void setFoldPressed(Boolean bool){
        foldPressed = bool;
    }

    /**
     * <b>Observer</b>: Checks if player completed its moves
     * @return true if player completed its moves, else false
     */
    public boolean has_played(){
        return has_played;
    }
}
