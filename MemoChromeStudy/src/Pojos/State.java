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
public class State 
{
    private String stateName;
    private int stateNumber;
    private ArrayList<String> genes;
    
    public State()
    {
        this.genes = new ArrayList();
    }
    
    public State(String name,ArrayList<String>genes)
    {
        this.stateName = name;
        String a = stateName.replace("state","");
        String b = a.replace(".","");
        String c = b.replace("genes.txt","");
        String d = c.replace(" ","");
        this.stateNumber = Integer.parseInt(d);
        this.genes =genes;
    }

    public int getStateNumber() {
        return stateNumber;
    }
    
    public int sizeOfState()
    {
        return genes.size();
    }
    
    public ArrayList<String> printInfo()
    {
        
        
        ArrayList<String> printInfo = new ArrayList();
        printInfo.add("\n");
        printInfo.add("    "+getStateName());
        printInfo.add(": "+sizeOfState()+" genes");
        
        return printInfo;
        
    }
    
    public void setStateName(String a)
    {
        this.stateName = a;
    }
    public void setGenes(ArrayList<String> a)
    {
        this.genes = a;
    }
    
    public String getStateName()
    {
        return this.stateName;
    }
    public ArrayList<String> getGenes()
    {
        return this.genes;
    }
}
