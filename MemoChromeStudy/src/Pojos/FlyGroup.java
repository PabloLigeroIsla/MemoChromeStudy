/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.util.*;
import static Persistence.Support.*;

/**
 *
 * @author Pablo Ligero
 */
public class FlyGroup 
{
    private String flyGroupName;
    private ArrayList<State> states;
    private ArrayList<ChromatinGroup> chromatinGroups;
    private ArrayList<Gene> genes;
    
    public FlyGroup()
    {
        
        this.states = new ArrayList();
        this.chromatinGroups = new ArrayList();
        this.genes = new ArrayList();
    }
    public FlyGroup(String flyGroupName, ArrayList<State> states, ArrayList<ChromatinGroup> chromatinGroups) {
        this.flyGroupName = flyGroupName;
        this.states = states;
        this.chromatinGroups = chromatinGroups;
    }
    
    public int sizeOfStates()
    {
        return getStates().size();
    }
    
    public int sizeOfChromatinGroups()
    {
        return getChromatinGroups().size();
    }
    
    public int sizeOfGenes()
    {
        return getGenes().size();
    }
    
    public ArrayList<String> printInfo()
    {
        ArrayList<String> printInfo = new ArrayList();
       
        printInfo.add("\n    Type of the FlyGroup: "+getFlyGroupName());
        printInfo.add("\n    -----------------------------");
        printInfo.add("\n    Number of Genes: "+sizeOfGenes());
        printInfo.add("\n\n    Number of States: "+sizeOfStates());
        
        Iterator itS = getStates().iterator();
        while(itS.hasNext())
        {
            State st = (State) itS.next();
            jointArrayLists(printInfo,st.printInfo());
        }
        
        printInfo.add("\n\n    Number of chromatinGroups: "+sizeOfChromatinGroups());
        Iterator itC = getChromatinGroups().iterator();
        while(itC.hasNext())
        {
            ChromatinGroup ch = (ChromatinGroup) itC.next();
            jointArrayLists(printInfo,ch.printInfo());
        }
        
        printInfo.add("\n");
        
        return printInfo;
    }
    
    /*Getters and Setters*/
    public String getFlyGroupName() 
    {
        return flyGroupName;
    }

    public void setFlyGroupName(String nameExperiment) 
    {
        this.flyGroupName = nameExperiment;
    }

    public ArrayList<State> getStates() 
    {
        return states;
    }

    public void setStates(ArrayList<State> states) 
    {
        this.states = states;
    }

    public ArrayList<ChromatinGroup> getChromatinGroups() 
    {
        return chromatinGroups;
    }

    public void setChromatinGroups(ArrayList<ChromatinGroup> chromatinGroups) 
    {
        this.chromatinGroups = chromatinGroups;
    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Gene> genes) {
        this.genes = genes;
    }
}
