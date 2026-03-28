package Model.Card;

import Model.Card.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 * This class creates a new card
 * @author ioannis kapetanakis
 */
public class NumberCard implements Card {
    private String value;

    /**
     * <b>Constructor</b>
     * <b>postcondition</b>: Initializes tha value of the card
     */
    public NumberCard(String value){
        this.value = value;
    }

    /**
     * <b>Accessor</b>: Returns the value of the cards
     * @return the value of the cards
     */
    public String getValue() {
        return value;
    }

    /**
     * <b>Accessor</b>: Returns the image of the card
     * @return the image of the card
     */
    public ImageIcon getImage() throws IOException{
        return new ImageIcon(ImageIO.read(getClass().getResource("/images/cards/card"+value+".png")));
    }
}
