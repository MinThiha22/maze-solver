/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author minthihakoko
 */
public class Panel extends JPanel {

    Maze maze;
    private final int scalePosition;
    private ArrayList<Node> currentPath;
    private Path finalPath;
    private String pathName;

    public Panel(String fileName) {
        pathName = "";
        scalePosition = 100;
        maze = new Maze(this);
        maze.addMazeData(fileName);
        currentPath = null;
        finalPath = null;
    }

    public void startMaze() {
        maze.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Total Nodes: " + maze.nodeCount, 20, 20);
        g.drawString("Total Edges: " + maze.edgeCount, 20, 40);
        g.drawString("Path: " + pathName, 300, 20);

        for (Node node : maze.nodes) {
            drawNode(g, node, Color.BLUE);
            if (node.name.equals("EXIT")) {
                drawNode(g, node, Color.RED);
            }
        }

        for (int i = 0; i < maze.nodeCount; i++) {
            for (int j = 0; j < maze.nodeCount; j++) {
                if (maze.adjacencyMatrix[i][j] == 1) {
                    drawEdge(g, maze.nodes[i], maze.nodes[j], Color.BLACK);
                }
            }
        }

        if (currentPath != null) {
            for (Node node : currentPath) {
                highlightNode(g, node, Color.YELLOW);
            }
        }

        if (finalPath != null) {
            ArrayList<Node> pathNodes = finalPath.getPathNodes();
            for (Node node : pathNodes) {
                highlightNode(g, node, Color.GREEN);
            }
            for (Node node : finalPath.getPathNodes()) {
                pathName += node.name + " ";
            }
            for (int i = 0; i < pathNodes.size() - 1; i++) {
                Node currentNode = pathNodes.get(i);
                Node nextNode = pathNodes.get(i + 1);
                drawEdge(g, currentNode, nextNode, Color.GREEN);
            }
        }
    }

    private void drawNode(Graphics g, Node node, Color color) {
        int nodeSize = 30;
        int x = node.x_position * scalePosition + 20;
        int y = (maze.numberOfRows - node.y_position) * scalePosition - 80;

        g.setColor(color);
        g.fillOval(x, y, nodeSize, nodeSize);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(node.name, x + nodeSize - 40, y + nodeSize - 35);
    }

    private void drawEdge(Graphics g, Node node1, Node node2, Color color) {
        int x1 = node1.x_position * scalePosition + 35;
        int y1 = (maze.numberOfRows - node1.y_position) * scalePosition - 65;
        int x2 = node2.x_position * scalePosition + 35;
        int y2 = (maze.numberOfRows - node2.y_position) * scalePosition - 65;

        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

    private void highlightNode(Graphics g, Node node, Color color) {
        int nodeSize = 30;
        int x = node.x_position * scalePosition + 20;
        int y = (maze.numberOfRows - node.y_position) * scalePosition - 80;

        g.setColor(color);
        g.fillOval(x, y, nodeSize, nodeSize);
    }
    
    public void updateCurrentPath(ArrayList<Node> path) {
        this.currentPath = path;
        repaint();
    }

    public void updateFinalPath(Path path) {
        this.finalPath = path;
        
        repaint();
    }
    
    public void forceRepaint() {
        revalidate();
        repaint();
    }
}