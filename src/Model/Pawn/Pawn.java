package Model.Pawn;

import Model.Player.Player;
import javafx.geometry.Pos;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Contains the methods signatures needed for
 * creating a piece
 * @author ioannis kapetanakis
 */

public class Pawn {
    private Color color;
    private String name;
    private Player holder;
    private Position position;
    private int x,y;
    private int id; // id = 1 or id = 2
    private boolean has_played;

    /**
     * @param player <b>Constructor</b>
     *               <b>postcondition</b>: Initializes the holder, the color and the position of the pawn
     */
    public Pawn(Player player, int id, String name) {
        holder = player;
        color = holder.getColor();
        this.id = id;
        this.name = name;
        position = Position.Start;
        has_played = false;
    }

    /**
     * <b>Transformer</b>: Changes the position of the pawn
     * @param pos
     * <b>Postcondition</b>: current position = param value
     */
    public void setPosition(Position pos) {
        this.position = pos;
    }

    /**
     * <b>Accessor</b>: Returns the position of a pawn
     *
     * @return the position of a pawn
     */
    public Position getPosition() {
        return position;
    }

    /**
     * <b>Accessor</b>: Returns the color of the pawn (depends on the color of the player who owns it)
     *
     * @return the color of the pawn
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * <b>Accessor</b>: Returns the image of the pawn
     *
     * @return the image of the pawn
     */
    public ImageIcon getImage() {
        try {
            if (getColor() == Color.RED) {
                if (id == 1) {
                    return new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)); // image
                } else {
                    return new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/redPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)); // image
                }
            } else {
                if (id == 1) {
                    return new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn1.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)); // image
                } else {
                    return new ImageIcon(ImageIO.read(getClass().getResource("/images/pawns/yellowPawn2.png")).getScaledInstance(50, 40, Image.SCALE_SMOOTH)); // image
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <b>Accessor</b>: Returns the name of the pawn
     *
     * @return Returns the name of the pawn
     */
    public String getName() {
        return name;
    }

    /**
     * <b>Accessor</b>: Returns the coordinates of the pawn on the board
     *
     * @return the coordinates of the pawn on the board
     */
    public int[] getXY(){
        return new int[]{x, y};
    }

    /**
     * <b>Transformer</b>: Changes the x coordinate of the pawn on the board
     * @param x
     * <b>Postorder</b>: Sets x position of the pawn to param value
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * <b>Transformer</b>: Changes the y coordinate of the pawn on the board
     * @param y
     * <b>Postorder</b>: Sets y position of the pawn to param value
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * <b>Transformer</b>: Determines whether a pawn has moved
     * @param bool
     * <b>Postorder</b>: Sets true if a pawn has moved, else false
     */
    public void setPlayed(boolean bool){
        has_played = bool;
    }

    /**
     * <b>Observer</b>: checks if a pawn has moved
     * @return true if a pawn has moved, else false
     */
    public boolean hasPlayed() {
        return has_played;
    }
}
