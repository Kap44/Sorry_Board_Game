package Model.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StartSlideSquare extends SlideSquare{

    /**
     * <b>Constructor:</b> Initializes the color of the Slide Square
     * @param color
     */
    public StartSlideSquare(Color color) {
        super(color);
    }

    /**
     * <b>Accessor</b>: Represent the image of the a Start Slide Square
     * @return the image of the a Start Slide Square
     */
    @Override
    public ImageIcon getImage() {
        try {
            if(getColor() == Color.RED) {
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/redSlideStart.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else if(getColor() == Color.BLUE){
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/blueSlideStart.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else if(getColor() == Color.GREEN){
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/greenSlideStart.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
            else{
                return new ImageIcon(ImageIO.read(getClass().getResource("/images/slides/yellowSlideStart.png")).getScaledInstance(80, 60, Image.SCALE_SMOOTH)); // image
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
