/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 */
public class Gene 
{
    private String name;
    private String chromatinGroup;
    private String state;
    
    public Gene(String name, String chromatinGroup, String state)
    {
        this.name = name;
        this.chromatinGroup = chromatinGroup;
        this.state = state;
    }
    
    public ArrayList<String> printInfo()
    {
        ArrayList<String> a = new ArrayList();
        
        a.add("Name: "+getName());
        a.add("State: "+getState());
        a.add("Chromatin Group: "+getChromatinGroup());
        
        return a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChromatinGroup() {
        return chromatinGroup;
    }

    public void setChromatinGroup(String chromatinGroup) {
        this.chromatinGroup = chromatinGroup;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
}
