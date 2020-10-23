package controller;

import model.VirusSimulator;
import model.utils.Colors;
import model.viruses.Virus;
import view.Window;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
    private long WAIT_TIME = 5L;
    private int PIXEL_SIZE = 1;

    private Dimension size;
    private Window window;

    private VirusSimulator vs;
    private Virus selectedVirus;

    private Thread t;

    public Controller(Dimension size) {
        this.size = size;
        vs = new VirusSimulator();

        configureWindow();

        setThread(); // This thread will update the program each 10 ms
    }

    public void configureWindow() {
        window = new Window(size);

        fillBackground();

        window.getImgLabel().addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                switch (mouseWheelEvent.getWheelRotation()) {
                    case 1: zoomOut(); break; // DOWN
                    case -1: zoomIn(); break; // UP
                    default: break;
                }
            }
        });

        window.getImgLabel().addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Point position = mouseEvent.getPoint();
                addVirus(position);
            }

            @Override
            public void mouseClicked(MouseEvent mouseEvent) { }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) { }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) { }

            @Override
            public void mouseExited(MouseEvent mouseEvent) { }

        });
    }

    private void addVirus(Point position) {
        int x = (int) position.getX();
        int y = (int) position.getY();

        vs.add(new Virus(new Point(x, y), Colors.fixedRandomColor()));
    }

    // -------------------- <Graphical things> --------------------

    private void fillBackground() {
        Graphics2D g = window.getImgGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
    }

    private void render() {
        Graphics2D g = window.getImgGraphics();
        fillBackground();

        for(Virus v : vs.getViruses()) {
            Point fixed = getFixedPosition(v.getPosition());
            g.setColor(v.getColor());
            g.fillRect((int) fixed.getX(), (int) fixed.getY(), PIXEL_SIZE, PIXEL_SIZE);
        }

        window.refresh();
    }

    private Point getFixedPosition(Point position) {
        int x = (int) position.getX();
        int y = (int) position.getY();
        int fixedX = x - (x % PIXEL_SIZE);
        int fixedY = y - (y % PIXEL_SIZE);

        return new Point(fixedX, fixedY);
    }

    private void zoomOut() {
        PIXEL_SIZE = PIXEL_SIZE == 1 ? 1 : PIXEL_SIZE - 1;
    }

    private void zoomIn() {
        PIXEL_SIZE += 1;
    }

    // -------------------- </> --------------------

    public void setThread() {
        Runnable r1 = () -> {
            try {
                while (true) {
                    update();
                    render();
                    Thread.sleep(WAIT_TIME);
                }
            } catch (InterruptedException iex) {}
        };
        t = new Thread(r1);
        t.start();
    }

    private void update() {
        LinkedList<Virus> additions = new LinkedList<>();
        for(Virus v : vs.getViruses()) {
            int x = v.getPosition().x;
            int y = v.getPosition().y;

            // Virus movement
            {
                Random rand = new Random();
                int fx = rand.nextInt(3) - 1;
                int fy = rand.nextInt(3) - 1;
                x += fx;
                y += fy;
                v.setPosition(new Point(x, y));
            }

            if(!v.isDone()) {
                if(v.canSpread()) {
                    // Virus movement
                    {
                        Random rand = new Random();
                        int fx = rand.nextInt(3) - 1;
                        int fy = rand.nextInt(3) - 1;
                        x += fx;
                        y += fy;
                        v.setPosition(new Point(x, y));
                    }

                    // New child
                    {
                        Random rand = new Random();
                        int fx = rand.nextInt(3) - 1;
                        int fy = rand.nextInt(3) - 1;
                        x += fx;
                        y += fy;

                        Color newColor = v.getColor();
                        int r = newColor.getRed() - (fx * 5);
                        int g = newColor.getGreen() - (fy * 5);
                        int b = newColor.getBlue() - (fx * 5);
                        if(r > 255) r = 255;
                        else if(r < 0) r = 0;
                        if(g > 255) g = 255;
                        else if(g < 0) g = 0;
                        if(b > 255) b = 255;
                        else if(b < 0) b = 0;
                        newColor = new Color(r, g, b);

                        Virus addition = new Virus(new Point(x, y), newColor);
//                    if(!vs.getViruses().contains(addition))
                        additions.add(addition);
                    }

                    v.setDone();
                }
                v.update();
            }
        }

        for(Virus v : additions) {
            vs.add(v);
        }
    }
}
