import controller.Controller;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Dimension size = new Dimension(500, 500); // default size

        if(args.length >= 2 && args[0] != null && args[1] != null) {
            size = new Dimension(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1])
            );
        }

        new Controller(size);
    }
}

