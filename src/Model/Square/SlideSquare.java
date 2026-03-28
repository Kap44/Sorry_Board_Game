package Model.Square;

import javax.swing.*;
import java.awt.*;

abstract class SlideSquare extends Square{
    private Color color;

    /**
     * <b>Constructor:</b> Initializes the color of the slide square
     * @param color
     */
    SlideSquare(Color color){
        this.color = color;
    }

    /**
     * <b>Accessor</b>: Represent the image of a Slide Square
     * @return the image of a Slide Square
     */
    abstract public ImageIcon getImage();

    /**
     * @param color
     * <b>Transformer</b>: Changes the color of the Slide Square
     * <b>Postcondition</b>: Slide Square color = param value
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * <b>Accessor</b>: Represents the color of the Slide Square
     * @return the color of the Slide Square
     */
    public Color getColor(){
        return this.color;
    }
}
