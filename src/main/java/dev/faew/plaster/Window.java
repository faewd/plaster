package dev.faew.plaster;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public void init(Viewport viewport) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final var pane = this.getContentPane();
        pane.add(viewport, BorderLayout.CENTER);

        this.setSize(viewport.getWidth(), viewport.getHeight());
        this.setVisible(true);
    }
}
