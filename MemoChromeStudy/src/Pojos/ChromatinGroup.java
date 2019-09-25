/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;
import java.util.*;
/**
 *
 * @author Pablo
 */
public class ChromatinGroup 
{
    private String groupName;
    private ArrayList<String> genes;
    private ArrayList<Integer> states;
    
    public ChromatinGroup()
    {
        this.genes = new ArrayList();
    }
    
    public ChromatinGroup(String name, ArrayList<String> array, ArrayList<Integer> states)
    {
        this.groupName = name;
        this.genes = array;
        this.states = states;
    }
    public ChromatinGroup(String name, ArrayList<String> array)
    {
        this.groupName = name;
        this.genes = array;
        this.states = new ArrayList();
    }

    public int sizeOfChromatinGroup()
    {
        return getGenes().size();
    }
    
    public ArrayList<String> printInfo()
    {
        
        ArrayList<String> printInfo = new ArrayList();
        printInfo.add("\n");
        printInfo.add("    "+getGroupName());
        printInfo.add(": "+sizeOfChromatinGroup()+" genes");
        return printInfo;
    }
    
    public String getGroupName() {
        return groupName;
    }

    public ArrayList<String> getGenes() {
        return genes;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGenes(ArrayList<String> genes) {
        this.genes = genes;
    }
    
    
}
