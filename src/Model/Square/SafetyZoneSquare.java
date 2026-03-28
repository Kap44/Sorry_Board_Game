package Model.Square;

import javax.swing.*;
import java.awt.*;

public class SafetyZoneSquare extends Square{
    private Color color;

    /**
     * <b>Constructor</b>
     * <b>postcondition</b>: Initializes a Safety Zone Square and its color
     */
    public SafetyZoneSquare(Color color){
        this.color = color;
    }

    /**
     * @param color
     * <b>Transformer</b>: Changes the color of the Safety Zone Square
     * <b>Postcondition</b>: Safety Zone Square color = param value
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * <b>Accessor</b>: Represent the color of the Safety Zone Square
     * @return the color of the Safety Zone Square
     */
    public Color getColor() {
        return color;
    }
}
