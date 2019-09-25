/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;
import static Persistence.Support.jointArrayLists;
import java.util.*;

/**
 *
 * @author Pablo
 */
public class Experiment 
{
    private String nameExperiment;
    private ArrayList<FlyGroup> flyGroups;
    
    /*Constructor*/
    public Experiment()
    {
        this.flyGroups = new ArrayList();
        
    }
    public Experiment(String nameExperiment, ArrayList<FlyGroup> flyGroups) {
        this.nameExperiment = nameExperiment;
        this.flyGroups = flyGroups;
    }

    public String getNameExperiment() {
        return nameExperiment;
    }

    public void setNameExperiment(String nameExperiment) {
        this.nameExperiment = nameExperiment;
    }

    public ArrayList<FlyGroup> getFlyGroups() {
        return flyGroups;
    }

    public void setFlyGroups(ArrayList<FlyGroup> flyGroups) {
        this.flyGroups = flyGroups;
    }
    
    public ArrayList<String> printInfo()
    {
        ArrayList<String>printInfo = new ArrayList();
        Iterator it = getFlyGroups().iterator();
        
        printInfo.add("------------------------------------\n");
        printInfo.add("Experiment: "+ getNameExperiment());
        printInfo.add("\n------------------------------------\n");
        
        while(it.hasNext())
        {
            FlyGroup fg = (FlyGroup) it.next();
            jointArrayLists(printInfo,fg.printInfo());
        }
        return printInfo;
    }

}