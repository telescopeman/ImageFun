import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author  Caleb Copeland, Nicolas Filotto [getNewImage() method only]
 * @since 5/26/21
 */
public class ImageFun extends JFrame {

    static BufferedImage image = null;

    public static void main(String[] args) {

    }

    private static BufferedImage getNewImage() throws IOException {
        final JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new ImageFilter());
        // Open the dialog using null as parent component if you are outside a
        // Java Swing application otherwise provide the parent component instead
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Retrieve the selected file
            File file = fc.getSelectedFile();
            return ImageIO.read(file);
        }
        else
        {
            return null;
        }
    }
}
