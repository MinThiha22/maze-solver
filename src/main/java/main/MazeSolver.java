/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author minthihakoko
 */
public class MazeSolver implements ActionListener{
    
    JFrame frame;
    JButton button;
    JLabel label;
    Panel panel;
    
    public MazeSolver(){
        frame = new JFrame();
        frame.setTitle("Maze Solver Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        label = new JLabel("Welcome to Binary Maze App:");
        button = new JButton("Select Maze File");
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(150, 20));

        frame.add(label);
        frame.add(button);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new MazeSolver();


    }
    
      @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            JFileChooser imageFileChooser = new JFileChooser(new File("."));
            int stateImageFileChooser = imageFileChooser.showOpenDialog(null);
        
            if(stateImageFileChooser == JFileChooser.APPROVE_OPTION)
            {
                String fileName = imageFileChooser.getSelectedFile().getPath();
                initializeMazePanel(fileName);
            }
        }
    }
    
    private void initializeMazePanel(String fileName) {
        
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        panel = new Panel(fileName);
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(700, 600);
        frame.revalidate(); 
        frame.repaint();
        panel.startMaze();
        
    }
    
    
    
}
