import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI extends JPanel {

    private Graphics2D g2d;
    public  int[][] matrix;
    public GUI() {
        g2d = null;

    }

    public void display(String s) {

        JFrame frame = new JFrame(s);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(224*4, 288*4);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public void prepGUI() {

    }

    public void paintComponent(Graphics window) {

        g2d = (Graphics2D) window;
        prepGUI();
    }

    public void createGround()
    {
        for(int r=0; r<matrix.length; r++)
        {
            for(int c=0; c<matrix[r].length; c++)
            {
                if(matrix[r][c])
            }
        }
    }

    public static void main(String[] args) {

        new GUI().display("DigDug");
    }
}