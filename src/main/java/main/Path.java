/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author minthihakoko
 */
public class Path {
    

    private ArrayList<Node> pathNodes;
    
    public Path()
    {
        
        this.pathNodes = new ArrayList<Node>();
        
    }
    
    public void addPathNodes(Node node){
        this.pathNodes.add(node);
    }
    
    
    public ArrayList<Node> getPathNodes() {
        return pathNodes;
    }
      
}
