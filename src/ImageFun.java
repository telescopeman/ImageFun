import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author  Caleb Copeland, Nicolas Filotto [getNewImage() method only], Jan Bodnar
 * @since 5/26/21
 */
public class ImageFun extends JFrame implements ActionListener, Runnable {
    private static int h_padding;
    private static final int w_padding = 0;
    private static final String OPEN_IMAGE = "Open Image";
    private final JLabel imageBox = new JLabel();
    private final JButton open_new_image;
    private final static boolean debug_mode = true;

    public volatile boolean running;

    public static void main(String[] args) {


        EventQueue.invokeLater(() -> {
            ImageFun ex = null;
            try {
                ex = new ImageFun();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert ex != null;
            ex.setVisible(true);
            ex.run();
        });
    }

    public ImageFun() throws IOException {
        running = true;

        setLayout(new BorderLayout());
        setTitle("Image");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        BufferedImage img;
        if (debug_mode)
        {
            img = RandomImage.get();
        }
        else
            img = loadNewImage();
        if (img == null)
        {
            dispose();
            System.exit(0);
        }
        PixelPool.setBaseImage(img);

        //menuBar = new JMenuBar();
        setSize(new Dimension(500,500));
        //JMenu fileMenu =new JMenu("File");
        open_new_image = new JButton(OPEN_IMAGE);
        open_new_image.addActionListener(this);

        JPanel p = new JPanel();
        p.add(open_new_image);
        add(p,BorderLayout.NORTH);
        //menuBar.add(fileMenu);
        open_new_image.setVisible(true);
        //setJMenuBar(menuBar);
        //this.add(menuBar, BorderLayout.NORTH);

        add(imageBox);

        h_padding = 0;
        renderImage(img);
        running = true;

    }

    private void renderImage(BufferedImage img) {
        imageBox.setIcon(new ImageIcon(img));

        imageBox.paintAll(imageBox.getGraphics());
        //menuBar.paintAll(menuBar.getGraphics());
        //menuBar.updateUI();
        open_new_image.paint(open_new_image.getGraphics());
        open_new_image.setEnabled(true);
        repaint();
        revalidate();
        imageBox.getInputMap().put(KeyStroke.getKeyStroke("N"),
                "pressed");
        imageBox.getActionMap().put("pressed",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            loadNewImage();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });


        //setSize(new Dimension(img.getWidth(),img.getHeight()+h_padding*2));
        //pack();

    }

    /**
     * Fits an image within the bounds of the screen.
     */
    private static BufferedImage fit(BufferedImage img)
    {
        Dimension screen_size
                = Toolkit.getDefaultToolkit().getScreenSize();

        float img_width = img.getWidth(),
                img_height = img.getHeight();



        float max_width = screen_size.width - w_padding,
                max_height = screen_size.height - h_padding;

        final float w_proportion = img_width / max_width,
                h_proportion = img_height / max_height;

        final double new_height, new_width;

        System.out.println(img_width + " by " + img_height + " ---> " + w_proportion*100 + "% / " + h_proportion*100 + "%");

        if (w_proportion <= 1 && h_proportion <= 1) // just right
        {
            return img;
        }
        else if (w_proportion <= 1) // too much height
        {
            new_height =  (img_height / h_proportion);
            new_width = (img_width / h_proportion);
        }
        else //too much width
        {
            new_height =  (img_height / w_proportion);
            new_width = (img_width / w_proportion);
        }

        BufferedImage outputImage = new BufferedImage((int) new_width,
                (int) new_height, img.getType());

        System.out.println(new_width + " / " + new_height);
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(img, w_padding/2, h_padding, (int) new_width, (int) new_height, null);
        g2d.dispose();

        if (w_proportion > 1 || h_proportion > 1)
        {
            return fit(outputImage);
        }
        return outputImage;
    }



    private static BufferedImage getNewImage() throws IOException {
        final JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);
        // Open the dialog using null as parent component if you are outside a
        // Java Swing application otherwise provide the parent component instead.
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(OPEN_IMAGE))
        {

            try {
                loadNewImage();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        }
    }

    private BufferedImage loadNewImage() throws IOException {
        BufferedImage tempImage = getNewImage();
        if (tempImage == null)
        {
            return null;
        }
        BufferedImage baseImage = fit(tempImage);
        renderImage(baseImage);
        return baseImage;
    }

    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }

            if(running) {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    public void tick()
    {
        PixelPool.tick();
    }

    public void render()
    {
        BufferedImage img = PixelPool.getCurrentImage();
        //System.out.println(img);
        //repaint();
        revalidate();
        renderImage(img);
    }
}
