package Model.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class InternalSlideSquare extends SlideSquare{

    /**
     * <b>Constructor:</b> Initializes the color of the Internal Slide Square
     * @param color
     */
    public InternalSlideSquare(Color color) {
        super(color);
    }

    /**
     * <b>Accessor</b>: Represent the image of the Internal Slide Square
     * @return the image of the Internal Slide Square
     */
    @Override
    public ImageIcon getImage() {
        try {
            if(getColor() == Color.RED) {
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/redSlideMedium.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else if(getColor() == Color.BLUE){
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/blueSlideMedium.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else if(getColor() == Color.GREEN){
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/greenSlideMedium.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else{
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/yellowSlideMedium.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
