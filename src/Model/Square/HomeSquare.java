package Model.Square;

import javax.swing.*;
import java.awt.*;

public class HomeSquare extends Square{
    private Color color;

    /**
     * <b>Constructor</b>
     * <b>postcondition</b>: Initializes a Home Square and its color
     */
    HomeSquare(Color color){
        this.color = color;
    }

    /**
     * @param color
     * <b>Transformer</b>: Changes the color of the Home Square
     * <b>Postcondition</b>: Home Square color = param value
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * <b>Accessor</b>: Represent the color of the Home Square
     * @return the color of the Home Square
     */
    public Color getColor() {
        return color;
    }
}
