package Model.Card;

import Model.Card.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 * This class creates a new sorry card
 * @author ioannis kapetanakis
 */
public class SorryCard implements Card {
    private static int available_cards = 4;
    private String value;

    /**
     * <b>Constructor</b>
     * <b>postcondition</b>: Initializes tha value, the number of available cards and the image of the sorry card
     */
    public SorryCard(){
        this.value = "Sorry";
    }

    /**
     * @param num
     * <b>Transformer</b>: Changes the number of available sorry cards
     * <b>Postcondition</b>: available sorry cards = param value
     */
    public void setAvailableCards(int num){
        available_cards = num;
    }

    /**
     * <b>Accessor</b>: Returns the number of the available sorry cards
     * @return the number of the available seven cards
     */
    public int getAvailableCards(){
        return available_cards;
    }

    /**
     * <b>Accessor</b>: Returns the value of the sorry cards
     * @return the value of the sorry cards
     */
    @Override
    public String getValue() {
        return "Sorry";
    }

    /**
     * <b>Accessor</b>: Returns the image of the sorry card
     * @return the image of the sorry card
     */
    @Override
    public ImageIcon getImage() throws IOException {
        return new ImageIcon(ImageIO.read(getClass().getResource("/images/cards/cardSorry.png")));
    }
}
