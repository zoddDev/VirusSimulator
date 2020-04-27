package model.viruses;

import java.awt.*;

public abstract class Premades {

    public static Virus EBOLA(Point pos) {
        return new Virus("Ebola", new Point((int) pos.getX(), (int) pos.getY()), 20, 100, Color.YELLOW);
    }
}
