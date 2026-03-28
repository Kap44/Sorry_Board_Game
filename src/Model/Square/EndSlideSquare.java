package Model.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EndSlideSquare extends SlideSquare{

    /**
     * <b>Constructor:</b> Initializes the color of the End Slide Square
     * @param color
     */
    public EndSlideSquare(Color color) {
        super(color);
    }

    /**
     * <b>Accessor</b>: Represent the image of the End Slide Square
     * @return the image of the End Slide Square
     */
    @Override
    public ImageIcon getImage() {
        try {
            if(getColor() == Color.RED) {
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/redSlideEnd.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else if(getColor() == Color.BLUE){
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/blueSlideEnd.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else if(getColor() == Color.GREEN){
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/greenSlideEnd.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else{
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/yellowSlideEnd.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
