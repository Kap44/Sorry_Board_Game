package Model.Card;

import javax.swing.*;
import java.io.IOException;

/**
 * Τhis Interface includes the method signatures of the cards
 * @author ioannis kapetanakis
 */
public interface Card {
    /**
     * <b>Accessor</b>: Returns the value of the card
     * @return the value of the card
     */
    public String getValue();

    /**
     * <b>Accessor</b>: Returns the image of the card
     * @return the image of the card
     */
    public ImageIcon getImage() throws IOException;
}
