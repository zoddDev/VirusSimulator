package model;

import model.utils.Colors;
import model.viruses.Virus;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class VirusSimulator {

    private ConcurrentLinkedQueue<Virus> viruses;

    public VirusSimulator() {
        viruses = new ConcurrentLinkedQueue<>();
    }

    public void add(Virus v) {
        viruses.add(v);
    }

    public ConcurrentLinkedQueue<Virus> getViruses() {
        return viruses;
    }
}
