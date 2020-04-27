package model.viruses;

import model.utils.Colors;

import java.awt.*;
import java.util.Objects;

public class Virus {
    private String name;
    private Point position;
    private int strength;
    private Color color;

    private long frequency;
    private long cooldown;

    private boolean done;

    public Virus(String name, Point position, int strength, long frequency, Color color) {
        this.name = name;
        this.position = position;
        this.strength = strength;
        this.frequency = frequency;
        this.color = color;
        this.cooldown = 0;
        this.done = false;
    }

    public Virus(Point position, Color color) {
        this("?", position, 10, 5, color);
    }

    public void update() {
        if(canSpread()) {
            cooldown = 0;
//            color = Colors.fixedRandomColor();
        } else {
            cooldown++;
        }
    }

    // ----------------------------------------

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public long getFrequency() {
        return frequency;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDone() { return done; }

    public void setDone() { done = true; }

    public boolean canSpread() {
        return cooldown >= frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Virus virus = (Virus) o;
        return Objects.equals(position, virus.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
