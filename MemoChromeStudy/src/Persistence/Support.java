/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Pojos.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Pablo
 */
public class Support 
{
    
    //General Methods
    
    public static ArrayList<Integer> writeIntegerArrayList(String stopSignal)
    {
        //This method returns an arrayList with all the numbers that the user introduce
        ArrayList<Integer> integerArrayList = new ArrayList();
        Integer number = 0;
        while(true)
        {
            String userInput = writeString();
            if(userInput.equals(stopSignal))
            {
                break;
            }else
            {
                if(checkString2Integer(userInput))
                {
                    number = Integer.parseInt(userInput);
                }else
                {
                    System.out.println("Introduce only numbers");
                }
                integerArrayList.add(number);
            }
        }
        
        return integerArrayList;
    }
    
    public static Integer writeInteger()
    {
        Integer number = null;
        while(true)
        {
            String userIntroduce = writeString();
            if(checkString2Integer(userIntroduce))
            {
                number = Integer.parseInt(userIntroduce);
                break;
            }else
            {
                System.out.println("Only Numbers");
            }
        }
        return number;
    }
    
    public static int writeInteger(int lowerLim, int upperLim)
    {
        //P Method used to set one limit, the upper limit
	int numIntro = -1;
	boolean out = false;
	String stringNumber = "";
	try
	{
		while((numIntro > upperLim) || (numIntro < lowerLim))
		{
			 do 
                	 {
                            System.out.printf("Introduce the Number between %d and %d\n",lowerLim,upperLim);
	                    stringNumber = writeString();
	                    if (checkString2Integer(stringNumber)) 
	                    {
                                numIntro = Integer.parseInt(stringNumber);
	                        out = true;
	                    }
			                
                   	 } while (!out);//Mientras que no me introduzca un numero, no le dejo salir
						
			if((numIntro > upperLim) || (numIntro < 0))//si hay 5 opciones no puedes poner 6
			{	
				System.out.println("Out of established limits ["+upperLim+","+lowerLim+"]\n");
			}
		}
	}catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("Error Introducing the values");
	}
	return numIntro;
    }
    
    public static ArrayList<String> writeStringArrayList(String stopSignal)
    {
        ArrayList<String> stringArrayList = new ArrayList();
        while(true)
        {
            String string2Add = writeString();
            if(string2Add.equals(stopSignal))
            {
                break;
            }else
            {
                stringArrayList.add(string2Add);
            }
        }
        
        return stringArrayList;
    }
    
    public static String writeString()
    {
        String string = "";
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        try {
            string = c.readLine();
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        return string;
    }
    
    public static boolean writeBoolean()
    {
        boolean a = false;
        boolean change = false;
        String option;
        
        System.out.println("Introduce Y or N\n");
        option = writeString();
        
        while(!change)
        {
                if(option.compareTo("Y") == 0 || option.compareTo("y") == 0)
                {
                        a = true;
                        change = true;
                }

                if(option.compareTo("N") == 0 || option.compareTo("n") == 0)		
                {
                        a = false;
                        change = true;
                }

                if(!change)
                {
                        System.out.println("\n Y or N\n");
                        option = writeString();
                }

        }

        return a;
    }
    
    public static boolean checkString2Integer(String val) 
    {
        try 
        {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException nfe) 
        {
            return false;
        }

    }
    
    public static ArrayList<String> compareArrayListsSimilarity(ArrayList<String>ex1,ArrayList<String>ex2)
    {
        //We return the strings that are in both sets
        /*We check the amount of genes and if they are the same*/
        ArrayList<String> a = new ArrayList();
        Iterator it1 = ex1.iterator();
        String gene;
        
        while(it1.hasNext())
        {
            gene = (String) it1.next();
            if(ex2.contains(gene))
            {
                a.add(gene);
            }
        }
        
        return a;
    }
    
    public static ArrayList<String> compareArrayListDifferences(ArrayList<String>ex1,ArrayList<String>ex2)
    {
        //We return the strings that are in one set but not in the other one 
        ArrayList<String> a = new ArrayList();
        Iterator it1 = ex1.iterator();
        String gene;
        
        while(it1.hasNext())
        {
            gene = (String) it1.next();
            if(!ex2.contains(gene))
            {
                a.add(gene);
            }
        }

        return a;
    }
        
    public static ArrayList<String> jointArrayLists(ArrayList<String> arr1, ArrayList<String>arr2)
    {
        ArrayList<String>arr = arr1;
        Iterator it2 = arr2.iterator();
        while(it2.hasNext())
        {
            arr.add((String)it2.next());
        }
        return arr;
    }
    
    public static ArrayList<String> jointArrayListOfArrayList(ArrayList<ArrayList<String>> arrayLists)
    {
        ArrayList<String> finalArray = new ArrayList();
        Iterator it = arrayLists.iterator();
        while(it.hasNext())
        {
            ArrayList<String> a = (ArrayList<String>) it.next();
            jointArrayLists(finalArray,a);
        }
        return finalArray;
    }
    
    public static void wait4Enter(boolean print) throws IOException
    {
        if(print)
        {
            System.out.println("Press Enter....");
        }
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String a = bufferedReader.readLine();
    }
    
    //Print
    
    public static void printArrayList(ArrayList<String> a)
    {
        Iterator it = a.iterator();
        while(it.hasNext())
        {
            System.out.print(it.next());
        }
        
    }
    
    public static void printArrayListOfArrayList(ArrayList<ArrayList<String>> a)
    {
        Iterator it = a.iterator();
        while(it.hasNext())
        {
            printArrayList((ArrayList<String>)it.next());
        }
    }

    // Specific Software Methods
    
        //Search
    
    public static FlyGroup searchFlyGroup(ArrayList<FlyGroup>flyGroups, String name)
    {
        //This method return a FlyGroup with an Specific Name
        FlyGroup finalFG = null;
        Iterator it = flyGroups.iterator();
        while(it.hasNext())
        {
            FlyGroup a = (FlyGroup) it.next();
            if(a.getFlyGroupName().equals(name))
            {
                finalFG = a;
                break;
            }
                
        }
        return finalFG;
    }
    
    public static ChromatinGroup searchChromatinGroup(ArrayList<ChromatinGroup> chromatinGroups, String search)
    {
        ChromatinGroup ch = new ChromatinGroup();
        Iterator it = chromatinGroups.iterator();
        while(it.hasNext())
        {
            ch = (ChromatinGroup) it.next();
            if(ch.getGroupName().equals(search))
            {
                break;
            }
        }
        return ch;
    }

    public static ArrayList<ChromatinGroup> searchGenesChromatinGroups(ArrayList<String> differences,ArrayList<ChromatinGroup>ChromatinGroups)
    {
        ArrayList<ChromatinGroup> chG = new ArrayList();
        
        Iterator it = ChromatinGroups.iterator();
        while(it.hasNext())
        {
            ChromatinGroup chComp = (ChromatinGroup) it.next();
            ChromatinGroup ch = new ChromatinGroup(chComp.getGroupName(),compareArrayListsSimilarity(differences,chComp.getGenes()));
            chG.add(ch);
        }
        
        return chG;
    }
    
    public static ArrayList<State> searchGenesStates(ArrayList<String>differences, ArrayList<State>states)
    {
        ArrayList<State> stG = new ArrayList();
        Iterator it = states.iterator();
        while(it.hasNext())
        {
            State stComp = (State) it.next();
            State st = new State(stComp.getStateName(),compareArrayListsSimilarity(differences,stComp.getGenes()));
            stG.add(st);
        }
        return stG;
    }
    
    public static ArrayList<Integer> searchStatesGenes(ArrayList<String>genes, ArrayList<State>states)
    {

        //This method return the number of the states that contains these genes
        ArrayList<Integer> statesNumber = new ArrayList();
        Iterator itStates = states.iterator();
        boolean containGenes;
        while(itStates.hasNext())
        {
            State st = (State) itStates.next();
            containGenes = genes.containsAll(st.getGenes());
            if(containGenes)
            {
                String stateName = st.getStateName();
                stateName = stateName.replace("state","");
                stateName = stateName.replace(" ","");
                Integer stateNumber = Integer.parseInt(stateName);
                statesNumber.add(stateNumber);
                break;
            }

            
            
        }
        return statesNumber;
    }
    
    public static ArrayList<Integer> searchStateLines (Integer[][] matrice, ArrayList<Integer> statesNumber,boolean row)
    {
        //This methods returns the lines or columnes (depends of the boolean) that corresponds to the states that we give as input
        ArrayList<Integer> positions = new ArrayList();
        
        int matriceSize = matrice.length;
        if(row)
        {
            //rows
            for(int cont = 1; cont < matriceSize; cont++)
            {
                Integer stateOfMatrice = matrice[cont][0];
                //Recorremos el aaray que contiene los estados que buscamos
                for(int cont2 = 0;cont2 < statesNumber.size();cont2++)
                {
                    Integer state2Find = statesNumber.get(cont2);
                    if((stateOfMatrice.compareTo(state2Find))==0)//Si son el mismo numero
                    {
                        positions.add(cont);
                    }
                }
            }
        }else
        {
            //column
            for(int cont = 1; cont <matriceSize; cont++)
            {
                Integer stateOfMatrice = matrice[0][cont];
                for(int cont2 = 0;cont2 < statesNumber.size();cont2++)
                {
                    Integer state2Find = statesNumber.get(cont2);
                    if((stateOfMatrice.compareTo(state2Find))==0)
                    {
                        positions.add(cont);
                    }
                }
            }
        }
        return positions;
    }
    
    public static Integer [][] comparisonMatriceStates(ArrayList<State> states1, ArrayList<State>states2)
    {

        int sizeRows = states1.size();
        int sizeCols = states2.size();
        Integer [][] matrice = new Integer[sizeRows+1][sizeCols+1];
        //Set the states numbers
        for(int i = 1;i<=sizeRows;i++)
        {
            matrice[i][0] = states1.get(i-1).getStateNumber();
        }
        for(int j = 1; j<= sizeCols;j++)
        {
            matrice[0][j] = states2.get(j-1).getStateNumber();
        }
        //Set the matrix conten
        for(int i=1; i<=sizeRows;i++)
        {
            for(int j = 1;j<=sizeCols;j++)
            {
                matrice[i][j] = (compareArrayListsSimilarity(states1.get(i-1).getGenes(),states2.get(j-1).getGenes())).size();
            }
        }
        return matrice;
    }
    
    public static int sumLineMatrice(Integer[][]matrice,Integer matriceLine)
    {
        //this method sum a specific line of a matrix
        int sum = 0;
        for(int cont =1; cont < matrice.length; cont++)
        {
            sum = sum + matrice[matriceLine][cont];
        }
        return sum;
    }
    
    public static int matriceLineCoincide(Integer[][] matrice, int matriceLine, ArrayList<Integer> columnes)
    {
        //This method returns the sum of the intersection of one row of a matrix with the different columns
        int sum = 0;
        for(int cont = 0; cont<columnes.size();cont++)
        {
            sum = sum + matrice[matriceLine][columnes.get(cont)];
        }
        return sum;
    }
    
    public static ArrayList<String> printMatriceComparison(String fgName, Integer[][] matrice)
    {
        ArrayList<String> printInfo = new ArrayList();
        ArrayList<String> printLine = new ArrayList();
        ArrayList<String> printCol = new ArrayList();
        int matriceSize = matrice.length;
        printInfo.add("\n\n"+fgName +" Matrice\n");
        for(int rowIndex = 0; rowIndex < matriceSize; rowIndex++)
        {
            for(int colIndex = 0; colIndex<matriceSize;colIndex++)
            {
                
                if(rowIndex == 0 || colIndex == 0)
                {
                    printCol.add("State ");
                }
                printCol.add(""+matrice[rowIndex][colIndex]+"\t");
                
            }
            printLine.add("\n");
            jointArrayLists(printLine,printCol);
            jointArrayLists(printInfo,printLine);
            printCol.clear();
            printLine.clear();
        }
        
        return printInfo;
    }
    
        //Check

    public static boolean checkExperimentInfo(Experiment exp1, Experiment exp2)
    {
        //This function check that we have the same number of, and each one of
        //them, has the same number of states and Chromatin groups
        boolean resp = true;
        
        ArrayList<FlyGroup> flyGroup1 = exp1.getFlyGroups();
        ArrayList<FlyGroup> flyGroup2 = exp2.getFlyGroups();
        
        Iterator it1 = flyGroup1.iterator();
        
        //We check we have the same FlyGroups
        if(flyGroup1.size() != flyGroup2.size())
        {
            resp = false;
        }
        if(resp == true)
        {  
            //We check that the FlyGroups contain the same Names
            
            ArrayList<String> fg1Names = loadFGNames(flyGroup1);
            ArrayList<String> fg2Names = loadFGNames(flyGroup2);
            resp = fg1Names.containsAll(fg2Names);
            if(resp)
            {   
                //Now that we know that we have the same names, we check the number
                //of states and chromatin groups
            
                //FlyGroups while
                while(it1.hasNext())
                {
                    FlyGroup fg1 = (FlyGroup) it1.next();
                    FlyGroup fg2 = searchFlyGroup(flyGroup2,fg1.getFlyGroupName());
                    
                    if(!checkStatesSize(fg1.getStates(),fg2.getStates()) || !checkChromatinGroupsInfo(fg1.getChromatinGroups(),fg2.getChromatinGroups()))
                    {
                        resp = false;
                    }
                }
            }
        }
        
        return resp;
    }
    
    public static boolean checkStatesSize(ArrayList<State>states1, ArrayList<State>states2)
    {
        //This function checks that both states have the same size
        boolean resp = true;
        
        if(states1.size()!=states1.size())
        {
            resp = false;
        }
        return resp;
    }
    
    public static boolean checkChromatinGroupsInfo(ArrayList<ChromatinGroup>exp1CHG,ArrayList<ChromatinGroup>exp2CHG)
    {
        boolean answer = true;
        //first we check if they have the same number of chromatin groups
        if(exp1CHG.size() != exp2CHG.size())
        {
           answer = false;
        }
        //Now we check that the have the SAME chromatin groups
        if(!checkCHGNames(exp1CHG,exp2CHG))
        {
            answer = false;
        }
        return answer;
    }
    
    public static boolean checkCHGNames(ArrayList<ChromatinGroup>exp1CHG,ArrayList<ChromatinGroup>exp2CHG)
    {
        boolean answer = true;
        ArrayList<String> namesExp1 = new ArrayList();
        ArrayList<String> namesExp2 = new ArrayList();
        Iterator exp1CHGIt = exp1CHG.iterator();
        Iterator exp2CHGIt = exp2CHG.iterator();
        
        while(exp1CHGIt.hasNext())
        {
        namesExp1.add(((ChromatinGroup) exp1CHGIt.next()).getGroupName());
        }
        while(exp2CHGIt.hasNext())
        {
        namesExp2.add(((ChromatinGroup) exp2CHGIt.next()).getGroupName());
        }
        
        Iterator it = namesExp1.iterator();
        while(it.hasNext())
        {
            String name = (String)it.next();
            if(!namesExp2.contains(name))
            {
                answer = false;
                break;
            }
        }
        
        return answer;
    }
    
    public static boolean checkString(ArrayList<String> stringList, String string)
    {
        return stringList.contains(string);
    }
    
    public static ArrayList<String> loadFGNames(ArrayList<FlyGroup> fg)
    {
        //This function return the name of all the Flygroups in an String ArrayList
        ArrayList<String> a = new ArrayList();
        Iterator it = fg.iterator();
        while(it.hasNext())
        {
            FlyGroup fgo = (FlyGroup) it.next();
            a.add(fgo.getFlyGroupName());
        }
        return a;
    }
    
    public static ArrayList<String> loadCGNames(ArrayList<ChromatinGroup> cg)
    {
        ArrayList<String> a = new ArrayList();
        Iterator it = cg.iterator();
        
        while(it.hasNext())
        {
            ChromatinGroup cg1 = (ChromatinGroup)it.next();
            a.add(cg1.getGroupName());
        }
        return a;
    }
}   


