/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author minthihakoko
 */
public class Node {
    public String name;
    public int x_position;
    public int y_position;

    
    public Node(){
        name = "";
        x_position = 0;
        y_position = 0;
    }
    
    public Node(String name, int x_position, int y_position){
        this.name = name;
        this.x_position = x_position;
        this.y_position = y_position;
    }
    
    
    
    
}
