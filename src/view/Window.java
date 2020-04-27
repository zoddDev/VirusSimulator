package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    private Dimension size;

    private BufferedImage img;
    private JLabel imgLabel;

    public Window(Dimension size) {
        super("Virus Simulator");
        this.size = size;
        setLayout(new BorderLayout());

        configureImage();

        // Window Configuration
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configureImage() {
        JPanel imgPanel = new JPanel(new BorderLayout());
        imgLabel = new JLabel();

        img = new BufferedImage((int) size.getWidth(), (int) size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        imgLabel.setIcon(new ImageIcon(img));
        imgPanel.add(imgLabel, BorderLayout.CENTER);

        add(imgPanel, BorderLayout.CENTER);
    }

    public BufferedImage getImg() {
        return img;
    }

    public JLabel getImgLabel() {
        return imgLabel;
    }

    public Graphics2D getImgGraphics() {
        return (Graphics2D) img.getGraphics();
    }

    public void refresh() {
        imgLabel.repaint();
    }
}
