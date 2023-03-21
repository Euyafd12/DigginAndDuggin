import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI extends JPanel {

    private Graphics2D g2d;

    public GUI() {
        g2d = null;
    }

    public void display(String s) {

        JFrame frame = new JFrame(s);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(224*4, 288*4);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void prepGUI() {
    }

    public void paintComponent(Graphics window) {

        g2d = (Graphics2D) window;
        prepGUI();
    }

    public static void main(String[] args) {

        new GUI().display("DigDug");
    }
}