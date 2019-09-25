/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

import static Persistence.Support.*;
import Persistence.Archive;
import Pojos.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Pablo
 */
public class Program 
{
    
    static Archive arch1 = new Archive("Experiment1");
    static Archive arch2 = new Archive("Experiment2");
    
    public static void main (String args[]) throws IOException
    {
        Experiment exp1;
        Experiment exp2;

        exp1 = arch1.loadExperiment();
        exp2 = arch2.loadExperiment();
        
        loop: while(true)
        {
            
            
            ArrayList<String> printInfo;
            ArrayList<ArrayList<String>> matrices;
            printMenu();
            System.out.println("Introduce an Option: ");
            
            int option = writeInteger(1,10);

            switch(option)
            {
                case 1:
                    //Loads into the System the new Experiments
                    arch1.setExperimentsInput();
                    wait4Enter(true);
                    
                    exp1 = arch1.loadExperiment();
                    exp2 = arch2.loadExperiment();
                    
                    break;
                case 2:
                    //Print Basic Info of a experiemnt
                    System.out.println("\n Which Experiment you want to see? \n");
                    int op = writeInteger(1,2);
                    if(op == 1)
                    {
                        printInfo = exp1.printInfo();
                    }else
                    {
                        printInfo = exp2.printInfo();
                    }
                    
                    printArrayList(printInfo);
                    wait4Enter(true);
                    break;
                case 3:
                    //Print the comparation between the Experiments
                    if(checkExperimentInfo(exp1,exp2))
                    {
                        printInfo = compareExperimentsChromatinGroups(exp1,exp2);
                        printArrayList(printInfo);
                    }
                    wait4Enter(true);
                    break;
                case 4:
                    //print the matrix to be copied in an excel and performs the necessary changes 
                    matrices = comparisonMatrixStatesPrinting(exp1,exp2);
                    printArrayListOfArrayList(matrices);
                    wait4Enter(true);
                    break;
                case 5:
                    ArrayList<ArrayList<String>> matrixInfo = matricesStudy(exp1.getFlyGroups(),exp2.getFlyGroups());
                    printArrayListOfArrayList(matrixInfo);
                    wait4Enter(true);
                    break;
                case 6:
                    System.out.println("You want the information of both experiments?");
                    if(!writeBoolean())
                    {
                        System.out.println("1st Experiment?");
                        if(writeBoolean())
                        {
                            printInfo = exp1.printInfo();
                            storeInformation(printInfo);
                        }else
                        {
                            printInfo = exp2.printInfo();
                            storeInformation(printInfo);
                        }
                    }else
                    {
                        printInfo = exp1.printInfo();
                        jointArrayLists(printInfo,exp2.printInfo());
                        storeInformation(printInfo);
                    }
                    
                    wait4Enter(true);
                    break;
                case 7:
                    printInfo = compareExperimentsChromatinGroups(exp1,exp2);
                    storeInformation(printInfo);
                    wait4Enter(true);
                    break;
                case 8:
                    matrices = comparisonMatrixStatesPrinting(exp1,exp2);
                    printInfo = jointArrayListOfArrayList(matrices);

                    storeInformation(printInfo);
                    wait4Enter(true);
                    break;
                case 9:
                    
                    matrices = matricesStudy(exp1.getFlyGroups(),exp2.getFlyGroups());
                    printInfo = jointArrayListOfArrayList(matrices);

                    storeInformation(printInfo);
                    wait4Enter(true);
                    break;
                case 10:
                    System.out.println("\t\tExit\t\t");
                    wait4Enter(true);
                    break loop;
                default:
                    System.out.println("Option not provided by the aplication");
                    wait4Enter(false);
            }
        }
        
    }
    
    public static void printMenu()
    {
        //This method prints the basic menu for the User Interface
        System.out.println(
                "Options:"
                + "\n Option 1: Load a new Set of Experiments set in the InputFile"
                + "\n Option 2: Print basic information of a Experiment"
                + "\n Option 3: Compare Experiment Chromatin Groups"
                + "\n Option 4: Print Comparison Matrices"
                + "\n Option 5: Study Matrices"
                + "\n Option 6: Option 2 + file"
                + "\n Option 7: Option 3 + file"
                + "\n Option 8: Option 4 + file"
                + "\n Option 9: Option 5 + file"
                + "\nOption 10: Exit"
                );
    }
    
    public static ArrayList<String> compareExperimentsChromatinGroups(Experiment exp1,Experiment exp2) throws IOException
    {
        
        ArrayList<String> printInfo = new ArrayList();
        
        ArrayList<FlyGroup> flyG1 = exp1.getFlyGroups();
        ArrayList<FlyGroup> flyG2 = exp2.getFlyGroups();
        
        Iterator itFG1 = flyG1.iterator();
        System.out.println("Set as the correct experiment the first or the second one\n 1 or 2");
        int opt1 = writeInteger(1,2);
        while(itFG1.hasNext())
        {
            FlyGroup fg1 = (FlyGroup) itFG1.next();

            printInfo.add("\n-----------------------------------");
            printInfo.add("\nFlyGroup: "+fg1.getFlyGroupName());
            printInfo.add("\n-----------------------------------\n\n");
            
            FlyGroup fg2 = searchFlyGroup(flyG2,fg1.getFlyGroupName());
            
            ArrayList<ChromatinGroup>exp1CHG = fg1.getChromatinGroups();
            ArrayList<ChromatinGroup>exp2CHG = fg2.getChromatinGroups();
            ChromatinGroup ch1;
            ChromatinGroup ch2;
            ArrayList<State>exp1ST = fg1.getStates();
            ArrayList<State>exp2ST = fg2.getStates();
            ArrayList<String> similarity;
            ArrayList<String> difference;


            //We compare each Chromatin Group
            if(opt1 == 1)
            {
                printInfo.add("We take the Experiemnt 1 as the Correct Experiment\n");
                Iterator exp1CHGIt = exp1CHG.iterator();
                while(exp1CHGIt.hasNext())
                {
                    ch1 = (ChromatinGroup) exp1CHGIt.next();
                    ch2 = searchChromatinGroup(exp2CHG,ch1.getGroupName());

                    printInfo.add("\nNumber of Genes in "+ch1.getGroupName()+" Group 1: "+ch1.sizeOfChromatinGroup());
                    printInfo.add("\nNumber of Genes in "+ch2.getGroupName()+" Group 2: "+ch2.sizeOfChromatinGroup());
                    similarity = compareArrayListsSimilarity(ch1.getGenes(),ch2.getGenes());
                    difference= compareArrayListDifferences(ch1.getGenes(),ch2.getGenes());
                    printInfo.add("\nSimilar Genes: "+similarity.size());
                    if(difference.size()>0)
                    {


                        printInfo.add("\nDifferent Genes Found in: \n");
                        ArrayList<ChromatinGroup> foundCHG = searchGenesChromatinGroups(difference,exp2CHG);
                        ArrayList<State> foundST = searchGenesStates(difference,exp2ST);

                        Iterator itFG = foundCHG.iterator();
                        while(itFG.hasNext())
                        {
                            ChromatinGroup ch =(ChromatinGroup) itFG.next();
                            if(ch.getGenes().size()>0)
                            {
                                printInfo.add("\t"+ch.getGroupName()+": "+ch.getGenes().size()+";");
                            }
                        }
                        printInfo.add("\n\n");
                        Iterator itST = foundST.iterator();
                        while(itST.hasNext())
                        {
                            State st = (State)itST.next();
                            if(st.getGenes().size()>0)
                            {
                                printInfo.add("\t"+st.getStateName()+": "+st.getGenes().size()+";");
                            }
                        }
                        printInfo.add("\n\n");
                    }else
                    {
                        printInfo.add("\nThey Have the same Genes\n");
                    }

                }
            }else
            {
                printInfo.add("\nWe take the Experiemnt 2 as the Correct Experiment\n");
                Iterator exp2CHGIt = exp2CHG.iterator();
                while(exp2CHGIt.hasNext())
                {
                    ch2 = (ChromatinGroup) exp2CHGIt.next();
                    ch1 = searchChromatinGroup(exp1CHG,ch2.getGroupName());

                    printInfo.add("\nNumber of Genes in "+ch2.getGroupName()+" Group 2: "+ch2.sizeOfChromatinGroup());
                    printInfo.add("\nNumber of Genes in "+ch1.getGroupName()+" Group 1: "+ch1.sizeOfChromatinGroup());
                    similarity = compareArrayListsSimilarity(ch2.getGenes(),ch1.getGenes());
                    difference = compareArrayListDifferences(ch2.getGenes(),ch1.getGenes());
                    printInfo.add("\nSimilar Genes: "+similarity.size());
                    if(difference.size()>0)
                    {
                        printInfo.add("\nDifferent Genes: "+difference.size());
                        printInfo.add("\nDifferent Genes Found in: \n");
                        ArrayList<ChromatinGroup> foundCHG =searchGenesChromatinGroups(difference,exp1CHG);
                        ArrayList<State> foundST = searchGenesStates(difference,exp1ST);


                        Iterator itFG = foundCHG.iterator();
                        while(itFG.hasNext())
                        {
                            ChromatinGroup ch =(ChromatinGroup) itFG.next();
                            if(ch.getGenes().size()>0)
                            {
                                printInfo.add("\t"+ch.getGroupName()+": "+ch.getGenes().size());
                            }

                        }
                        printInfo.add("\n\n");
                        Iterator itST = foundST.iterator();
                        while(itST.hasNext())
                        {
                            State st = (State)itST.next();
                            if(st.getGenes().size()>0)
                            {
                                printInfo.add("\t"+st.getStateName()+": "+st.getGenes().size());
                            }

                        }
                        printInfo.add("\n\n");
                    }else
                    {
                        printInfo.add("\n\nThey have the same genes");
                    }

                }
            }
            
            
            printInfo.add("\n");
        }

        return printInfo;
    }
    
    public static ArrayList<ArrayList<String>> comparisonMatrixStatesPrinting(Experiment exp1, Experiment exp2)
    {
        
        ArrayList<FlyGroup> fg1A = exp1.getFlyGroups();
        ArrayList<FlyGroup> fg2A = exp2.getFlyGroups();
        
        ArrayList<ArrayList<String>> matrices = new ArrayList();
        Iterator it = fg1A.iterator();
        while(it.hasNext()) 
        {
            
            FlyGroup fg1 = (FlyGroup) it.next();
            FlyGroup fg2 = searchFlyGroup(fg2A,fg1.getFlyGroupName());
            
            Integer[][] matrice = comparisonMatriceStates(fg1.getStates(),fg2.getStates());
            ArrayList<String> matricePrint = printMatriceComparison(fg1.getFlyGroupName(),matrice);

            matrices.add(matricePrint);
            
        }
        
        return matrices;
    }
    
    public static ArrayList<ArrayList<String>> matricesStudy(ArrayList<FlyGroup> fg1Array, ArrayList<FlyGroup> fg2Array)
    {
        ArrayList<ArrayList<String>> matricesInfo = new ArrayList();
        
        Iterator fgIt = fg1Array.iterator();
        
        while(fgIt.hasNext())
        {
            FlyGroup fg1 = (FlyGroup) fgIt.next();
            FlyGroup fg2 = searchFlyGroup(fg2Array,fg1.getFlyGroupName());
            ArrayList<String> matriceInfo = getMatriceInfo(fg1.getFlyGroupName(),fg1,fg2);
            matricesInfo.add(matriceInfo);
        }
        return matricesInfo;
    }
    
    public static ArrayList<String> getMatriceInfo (String flyGroupName, FlyGroup fg1, FlyGroup fg2)
    {
        ArrayList<String> printInfo = new ArrayList();
        printInfo.add("'"+flyGroupName+"'"+"\n\n");
        ArrayList<ArrayList<Integer>> statesNumber1 = new ArrayList();
        ArrayList<ArrayList<Integer>> statesNumber2 = new ArrayList();

        Integer [][] stateComparisonMatrice = comparisonMatriceStates(fg1.getStates(),fg2.getStates());
        //Averiguar Nombres de Los grupos cromatinicos del FlyGroups
        
        ArrayList<String> chromatinGroupsNames = loadCGNames(fg1.getChromatinGroups());

        Iterator chGNamesIt1 = chromatinGroupsNames.iterator();
        ArrayList<Integer> stateNumbers;
        
        while(chGNamesIt1.hasNext())
        {
            String chName = (String) chGNamesIt1.next();
            //Assign to each chromatin group the states that belong to them.
            System.out.println("Introduce the states that belongs to the " +chName+ "Chromatin Group in the 1 Experiment");
            System.out.println("Write the numbers one by one n Write 'stop' to stop introducing");
            stateNumbers = writeIntegerArrayList("stop");
            statesNumber1.add(stateNumbers);
            System.out.println("Introduce the states that belongs to the " +chName+ "Chromatin Group in the 2 Experiment");
            System.out.println("Write 'stop' to stop introducing");
            stateNumbers = writeIntegerArrayList("stop");
            statesNumber2.add(stateNumbers);
        }
        
        int matriceTotalGenes = 0;
        int matricecorrectness = 0;
        int matriceError = 0;
        
        ArrayList<String> statesInfo = new ArrayList();
        ArrayList<String> chromatinInfo = new ArrayList();
        
        //Para cada grupo de Cromatina
        for(int chromatinCont = 0; chromatinCont < chromatinGroupsNames.size(); chromatinCont++)
        {
            
            int totalChromatinGroupsGenes= 0;
            int chromatinGroupCorrectness = 0;
            int chromatinGroupError = 0;
            
            String chgn = chromatinGroupsNames.get(chromatinCont);
            printInfo.add("ChromatinGroup: "+chgn);
            printInfo.add("\nStates: \n");
            ArrayList<Integer> statesOfChromatinGroup1 = statesNumber1.get(chromatinCont);
            ArrayList<Integer> statesOfChromatinGroup2 = statesNumber2.get(chromatinCont);
            
            //Print the satates that belong to each Chromatin Group
            for(int cont = 0; cont<statesOfChromatinGroup1.size();cont++)
            {
                printInfo.add("State "+statesOfChromatinGroup1.get(cont)+"\t");
            }
            
            //For each row 
            ArrayList<Integer> statesLinesRow = searchStateLines(stateComparisonMatrice,statesOfChromatinGroup1,true);
            ArrayList<Integer> statesLinesCol = searchStateLines(stateComparisonMatrice,statesOfChromatinGroup2,false);
            
            
            for(int cont2 = 0; cont2<statesLinesRow.size(); cont2++)
            {
                //Add the genes of a row

                statesInfo.add("State "+ statesOfChromatinGroup1.get(cont2)+"\n");
                Integer matriceLine = statesLinesRow.get(cont2);
                int lineSum = sumLineMatrice(stateComparisonMatrice,matriceLine);
                int sumGenesCoincide = matriceLineCoincide(stateComparisonMatrice,matriceLine,statesLinesCol);
                int sumGenesDiffere = lineSum - sumGenesCoincide;
                statesInfo.add("\nTotalGenes: "+lineSum);
                statesInfo.add("\tCorrectness: "+(sumGenesCoincide/lineSum)*100);
                statesInfo.add("\tError: "+(sumGenesDiffere/lineSum)*100);
                
                totalChromatinGroupsGenes = totalChromatinGroupsGenes + lineSum;
                chromatinGroupCorrectness = chromatinGroupCorrectness + sumGenesCoincide;
                chromatinGroupError = chromatinGroupError + sumGenesDiffere;
                
                
                
            }
                
            chromatinInfo.add("\n"+chgn+" total genes: "+totalChromatinGroupsGenes);
            chromatinInfo.add(chgn+" correctness: " +(chromatinGroupCorrectness/totalChromatinGroupsGenes)*100);
            chromatinInfo.add(chgn+" Error: "+(chromatinGroupError/totalChromatinGroupsGenes)*100);
        
            matriceTotalGenes =+ totalChromatinGroupsGenes; 
            matricecorrectness =+ chromatinGroupCorrectness;
            matriceError =+chromatinGroupError;
        }

        jointArrayLists(printInfo,printMatriceComparison(fg1.getFlyGroupName(),stateComparisonMatrice));
        printInfo.add(flyGroupName+"matrice Total Genes: "+matriceTotalGenes);
        printInfo.add(flyGroupName+"matrice Correctness: "+(matricecorrectness/matriceTotalGenes)*100);
        printInfo.add(flyGroupName+"matriceError: "+(matriceError/matriceTotalGenes)*100);
        
        
        printInfo = jointArrayLists(printInfo,chromatinInfo);
        printInfo = jointArrayLists(printInfo,statesInfo);
        
        return printInfo;
    }
    
    public static void storeInformation(ArrayList<String> information) throws IOException
    {
        
        String option;
        while(true)
        {
            System.out.println("Introduce the Name of the File you want to save: ");
            String fileName = writeString();
            boolean findFile = arch1.searchFile(fileName);
            if(findFile)
            {
                System.out.println("The file allready exixt, do you want to overwrite it?");
                System.out.println("Y/N");
                option = writeString();
                if(option.contentEquals("Y"))
                {
                    arch1.overWrite(fileName, information);
                    break;
                }else
                {
                    break;
                }
                
            }else
            {
                arch1.storeFile(fileName, information);
                break;
            }            
        }
    }
    
}
