/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author minthihakoko
 */
public class Maze extends Thread{
    public Node[] nodes;
    public int[][] adjacencyMatrix;
    public int nodeCount;
    public int edgeCount;
    public int numberOfColumns;
    public int numberOfRows;
    
    public HashMap<String, Integer> nodeIndex;
    private Panel panel;
    private FileManager fileManager;
    
    public Maze(Panel panel)
    {   
        this.panel = panel;
        nodeCount = 0;
        edgeCount = 0;
        numberOfColumns = 0;
        numberOfRows = 0;
        nodeIndex = new HashMap<>();
    }
    
    public void addMazeData(String fileName){
        fileManager = new FileManager(fileName);
        fileManager.readFile(fileName);
        String[] lineData = fileManager.lineData;
        
        addHeaderData(lineData[0]);
        
        nodeCount = fileManager.numberOfLines-1;
        nodes = new Node[nodeCount];
        adjacencyMatrix = new int[nodeCount][nodeCount];
        
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
        
        addNodeData(lineData);
        addEdges(lineData, adjacencyMatrix);
          
    }
    
    private void addHeaderData(String firstLine){
        String[] headerParts = firstLine.split(",");
        edgeCount = Integer.parseInt(headerParts[0]);
        numberOfColumns = Integer.parseInt(headerParts[1]);
        numberOfRows = Integer.parseInt(headerParts[2]);
    }
    
    private void addNodeData(String[] lineData){
        for(int i=1;i<=nodeCount;i++){
            String[] parts = lineData[i].split(",");
            String name = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            
            nodes[i-1] = new Node(name,x,y);
            nodeIndex.put(name, i-1);
            
        }
        
    }
    
    private void addEdges(String[] lineData, int [][] adjacencyMatrix){
        for(int i=1;i<=nodeCount;i++){
            String[] parts = lineData[i].split(",");
            int currentNodeIndex = nodeIndex.get(parts[0]);
            
            for(int j=3;j<parts.length;j++){
                String linkNode = parts[j];
                
                if(!linkNode.equals("W") && !linkNode.equals("A")){
                    int linkNodeIndex = nodeIndex.get(linkNode);
                    adjacencyMatrix[currentNodeIndex][linkNodeIndex] = 1;
                    
                }
                else if(linkNode.equals("W")){
                    adjacencyMatrix[currentNodeIndex][nodeCount-1] = 1;
                }
            }
        }
    }
    
    public Path findPath(){
        Node start = this.nodes[0];
        Node end = this.nodes[nodeCount-1];
        Path mazePath = new Path();
        Queue<Node> queue = new LinkedList<>();
        ArrayList<Node> visited = new ArrayList<>();
        int[] previousNode = new int[nodeCount];
        for(int i = 0 ;i<previousNode.length;i++){
            previousNode[i] = -1;
        }
        
        queue.add(start);
        visited.add(start);
        
        while(!queue.isEmpty()){
            Node current = queue.poll();
            int currentIndex = nodeIndex.get(current.name);
            
            if (current == end) {
                break;
            }
                      
            for(int i=0;i<adjacencyMatrix[currentIndex].length;i++){
                if(adjacencyMatrix[currentIndex][i]==1 && !visited.contains(nodes[i])){
                    queue.add(nodes[i]);
                    visited.add(nodes[i]);
                    if(previousNode[i]==-1){
                        previousNode[i] = currentIndex;
                    }
                }
            }
            
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, e);

            }
            
            panel.updateCurrentPath(visited);
            
        }
        
        String indexPath = "";
        
        for (int i = nodeIndex.get(end.name);previousNode[i]>=0;i=previousNode[i]) {
            indexPath = i+","+indexPath;
        }
        indexPath = nodeIndex.get(start.name)+","+indexPath;
        String[] indexStrings = indexPath.split(",");
        for (String indexString : indexStrings) {
            int nodeIndex = Integer.parseInt(indexString);
            mazePath.addPathNodes(nodes[nodeIndex]);
        }
        
        panel.updateFinalPath(mazePath);
        return mazePath;
    }

    @Override
    public void run() {
        findPath();
        panel.forceRepaint();
    }
        
}
