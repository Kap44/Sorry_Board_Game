package Model.Square;

import javax.swing.*;
import java.awt.*;

public class StartSquare extends Square{
    private Color color;

    /**
     * @param color
     * <b>Transformer</b>: Changes the color of a Start Square
     * <b>Postcondition</b>: Start Square color = param value
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * <b>Accessor</b>: Represent the color of the Start Square
     * @return the color of a Start Square
     */
    public Color getColor() {
        return color;
    }
}
