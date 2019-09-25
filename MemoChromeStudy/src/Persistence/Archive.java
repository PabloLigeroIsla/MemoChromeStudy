/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;
import static Persistence.Support.*;
import Pojos.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


/**
 *
 * @author Pablo
 */
public class Archive 
{
    
    private String experimentName;
    private String flyGroupName;
    private String dirOutput;
    private String dirExperiment;
    private String dirStates;
    private String dirChromatinGroups;
    
    // Constructor
    
    public Archive(String name)
    {
        setBasicInfoFiles(name);
    }
    
    // MemoChromeStudy Methods
    
    public static void setExperimentsInput() throws IOException
    {
        //First we erase everithing inside the experiment info
        File file1 = new File("files/Experiment1");
        File file2 = new File("files/Experiment2");
        
        deleteDyrectory(file1);
        deleteDyrectory(file2);
        
        file1.mkdir();
        file2.mkdir();
       
        //Then we lead the new Data
        String dirInput = "files/Input";
        File dirImput = new File(dirInput);
        String[] expNames = dirImput.list();
        //For each Experiment
        for(int i = 0; i<expNames.length;i++)
        {
            String exp2Save = "files/Experiment"+(i+1);
            
            File expDir = new File(dirInput+"/"+expNames[i]);
            String [] experimentGroups = expDir.list();
            for(int j = 0; j<experimentGroups.length;j++)
            {
                String groupName = experimentGroups[j];
                if(groupName.contains(".state.mean.plots"))//Load States
                {
                    groupName = groupName.replace(".state.mean.plots","");
                    File groupSave = new File(exp2Save+"/"+groupName);
                    groupSave.mkdir();
                    File flyGroupStates = new File(exp2Save+"/"+groupName+"/States");
                    flyGroupStates.mkdir();
                    File flyGroupChromatinGroups = new File(exp2Save+"/"+groupName+"/ChromatinGroups");
                    flyGroupChromatinGroups.mkdirs();
                    
                    //Find the associated folder that contain the chormatin Group
                    String chromatinMKDirName = findAssociatedFolder(groupName,experimentGroups);
                    File chromatinMKDir = new File(expDir,chromatinMKDirName);
                    //Loading Chromatin Groups
                    String[] filesChromatin = chromatinMKDir.list();
        
                    for(int l = 0; l<filesChromatin.length;l++)
                    {
                        File fileCh = new File(dirInput+"/"+expNames[i]+"/"+chromatinMKDirName+"/"+filesChromatin[l]);
                        if(filesChromatin[l].contains("simp.vit.genes.state"))
                        {
                            String finalFileRename = filesChromatin[l].replace(groupName+".simp.vit.genes.state.","");
                            File finalFile = new File(flyGroupChromatinGroups,finalFileRename);
                            finalFile.createNewFile();
                            Files.copy(Paths.get(fileCh.getAbsolutePath()),Paths.get(finalFile.getAbsolutePath()),StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                    //Loading States
                    File flyGroup = new File(dirInput+"/"+expNames[i]+"/"+experimentGroups[j]);
                    String[] filesOfFlyGroup = flyGroup.list();
                    for(int k = 0; k<filesOfFlyGroup.length;k++)
                    {
                        File file = new File(dirInput+"/"+expNames[i]+"/"+experimentGroups[j]+"/"+filesOfFlyGroup[k]);
                        if(filesOfFlyGroup[k].contains(groupName+".state"))
                        {
                            //If we found a file that correspond a state
                            File finalFile = new File(flyGroupStates,filesOfFlyGroup[k]);
                            finalFile.createNewFile();
                            Files.copy(Paths.get(file.getAbsolutePath()),Paths.get(finalFile.getAbsolutePath()),StandardCopyOption.REPLACE_EXISTING);

                        }

                    }
                }
            }
        }
    }
    
    public void setBasicInfoFiles(String experimentName)
    {
        //This method set the basic information about the experiment and the paths of directories
        setExperimentName(experimentName);
        setDirExperiment("files/"+experimentName);
        setDirOutput("files/Output");
    }
    
    public void setFGInfoFile(String fgName)
    {
        // This method sets the flyGroup names and create paths for dyrectories to stroe files 
        setFlyGroupName(fgName);
        setDirStates("files/"+getExperimentName()+"/"+getFlyGroupName()+"/States");
        setDirChromatinGroups("files/"+getExperimentName()+"/"+getFlyGroupName()+"/ChromatinGroups");
    }
    
    public Experiment loadExperiment() throws IOException
    {
        //This method loads the Experiment to the program in order to study the data 
        Experiment exp = new Experiment();
        
        exp.setNameExperiment(getExperimentName());
        exp.setFlyGroups(loadFlyGroups());
        
        return exp;
        
    }
    
    public ArrayList <FlyGroup> loadFlyGroups() throws IOException
    {
        // This method is responsible of loading the States, Chromatin groups and genes of each Fly Group
        ArrayList<FlyGroup> fgs = new ArrayList();
        ArrayList<String> flyGroupsNames = readDirectoryContent(dirExperiment,false);
        Iterator it = flyGroupsNames.iterator();
        while(it.hasNext())
        {
            String fgName = (String) it.next();
            setFGInfoFile(fgName);
            
            FlyGroup fg = new FlyGroup();
        
            fg.setFlyGroupName(flyGroupName);
            ArrayList<State> statesFG = loadFlyGroupStates();
            fg.setStates(statesFG);
            fg.setChromatinGroups(loadFlyGroupChromarinGroups(statesFG));
            fg.setGenes(loadFlyGroupGenes(fg.getChromatinGroups(),fg.getStates()));
            
            fgs.add(fg);
        }
        
        
        return fgs;
    }
    
    public ArrayList<State> loadFlyGroupStates() throws IOException
    {
        //This method loads the sates of the Fly Group
        ArrayList<State> states = new ArrayList();
        ArrayList<String> statesNames = readDirectoryContent(dirStates,false);
        Iterator it = statesNames.iterator();
        while(it.hasNext())//Data Cleaning
        {
            String fileName = (String) it.next();
            String fileNameFinal = fileName.replace(".genes.txt","");
            fileNameFinal = fileNameFinal.replace("."," ");
            fileNameFinal = fileNameFinal.replace("NaRUT"," ");
            fileNameFinal = fileNameFinal.replace("NaWT"," ");
            fileNameFinal = fileNameFinal.replace("TrRUT"," ");
            fileNameFinal = fileNameFinal.replace("TrWT"," ");
            State state = new State(fileNameFinal,loadFile(dirStates,fileName));
            states.add(state);
        }
        return states;
    }
    
    public ArrayList<ChromatinGroup>loadFlyGroupChromarinGroups(ArrayList<State> states) throws IOException
    {
        ArrayList<ChromatinGroup> chromatinGroups = new ArrayList();
        ArrayList<String> chromatinGroupsNames = readDirectoryContent(dirChromatinGroups,false);
        Iterator it = chromatinGroupsNames.iterator();
        while(it.hasNext())
        {
            String fileName = (String) it.next();
            String fileNameFinal = fileName.replace(".genes.txt","");
            ArrayList<String> genes =loadFile(dirChromatinGroups,fileName);
            ArrayList<Integer> statesNumbers = searchStatesGenes(genes,states);
            ChromatinGroup ch = new ChromatinGroup(fileNameFinal,genes,statesNumbers);
            chromatinGroups.add(ch);
        }
        return chromatinGroups;
    }
    
    public ArrayList<Gene> loadFlyGroupGenes(ArrayList<ChromatinGroup> chromatinGroups, ArrayList<State> states) throws IOException
    {
        
        //Read all the names of the Genes (We load the genes from the states and from the chromatin groups)
        
        ArrayList<String> geneNames = new ArrayList();
        
        Iterator chgIter = chromatinGroups.iterator();
        Iterator stIter = states.iterator();
        
        //Genes from the ChromatinGroups
        while(chgIter.hasNext())
        {
            ChromatinGroup ch = (ChromatinGroup) chgIter.next();
            geneNames = jointArrays(geneNames,ch.getGenes());
        }
        //Genes from the States
        
        while(stIter.hasNext())
        {
            State st = (State) stIter.next();
            geneNames = jointArrays(geneNames,st.getGenes());
        }
        //Set the Gene object information
        
        ArrayList<Gene> genes = new ArrayList();
        
        Iterator chIterator = chromatinGroups.iterator(); //ArrayList input of Chromatin Objects
        Iterator stIterator = states.iterator();//Array input of States Object
        
        Iterator geneNamesIterator = geneNames.iterator(); 
        
        while(geneNamesIterator.hasNext())//Para cada gen
        {
            String geneName = (String) geneNamesIterator.next();
            String chName = "Null";
            String stName = "Null";
            
            while(chIterator.hasNext())
            {
                ChromatinGroup ch = (ChromatinGroup) chIterator.next();
                String chromatinGroupName = ch.getGroupName();
                if(ch.getGenes().contains(geneName))
                {
                    chName = chromatinGroupName;
                    break;
                }
            }
            
            while(stIterator.hasNext())//busco el estado
            {
                State st = (State) stIterator.next();
                String stateName = st.getStateName();
                if(st.getGenes().contains(geneName))
                {
                    stName = stateName;
                    break;
                }
            }
            
            Gene gene = new Gene(geneName,chName,stName);
            genes.add(gene);
        }
        
        return genes;
    }
    
    //General Methods 
    
    public static ArrayList<String> readDirectoryContent(String dirName,boolean printOption)
    {
        if(printOption)
        {
            System.out.println("Directory: "+dirName+"\n");
        }
        File dir = new File(dirName);
        ArrayList<String> arrayList = new ArrayList();
        File [] ficheros = dir.listFiles();
        for(int c = 0; c< ficheros.length; c++)
        {
            String string = ficheros[c].getName();
            if(printOption)
            {
                System.out.println("    "+string);
            }
            
            arrayList.add(string);
        }
        
        return arrayList;
    }
    
    public static ArrayList<String> loadFile(String directory, String fileName) throws FileNotFoundException, IOException
    {
        ArrayList<String> a = new ArrayList();
        File file = new File(directory,fileName);
        FileReader fileReader = new FileReader(file);
        String string;
        BufferedReader bufReader = new BufferedReader(fileReader);
        while ((string = bufReader.readLine()) != null) {
                a.add(string);
            }
            bufReader.close();
            fileReader.close();
        return a;
    }
    
    public void storeFile(String name, ArrayList<String> information) throws IOException
    {
        File file = new File(dirOutput,name);

        FileWriter fileWriter = new FileWriter(file,true);
        BufferedWriter out = new BufferedWriter(fileWriter);
        Iterator it = information.iterator();
        while(it.hasNext())
        {
            out.write((String)it.next());
            out.newLine();
        }
        out.close();
        fileWriter.close();
        

    }
    
    public boolean searchFile(String fileName)
    {
        ArrayList<String> files = readDirectoryContent(getDirOutput(),false);
        return checkString(files,fileName);
    }
    
    public void overWrite(String name, ArrayList<String> information) throws IOException
    {
        File file = new File(name,getDirOutput());
        file.delete();
        storeFile(name,information);
        
    }
    
    private ArrayList<String> jointArrays(ArrayList<String>arr1,ArrayList<String>arr2)
    {
        //This methods joint arrays without repetitions
        Iterator it1 = arr2.iterator();
        while(it1.hasNext())
        {
            String gene = (String) it1.next();
            if(!arr1.contains(gene))
            {
                arr1.add(gene);
            }
        }
        return arr1;
    }
    
    private static void deleteDyrectory(File pArchivo) { 

    if (pArchivo.isDirectory()) { 
        for (File f : pArchivo.listFiles()) { 
            deleteDyrectory(f);  } 
    }
    pArchivo.delete(); 
}
    
    private static String findAssociatedFolder(String groupName,String[] expfold)
    {
        String string = "";
        for(int i = 0;i<expfold.length;i++)
        {
            if((expfold[i].contains(groupName))&&(expfold[i].contains("all.test.2")))
            {
                string = expfold[i];
                break;
            }
        }
        return string;
    }
    
    // Getters and Setters 
    
    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getFlyGroupName() {
        return flyGroupName;
    }

    public void setFlyGroupName(String flyGroupName) {
        this.flyGroupName = flyGroupName;
    }

    public String getDirOutput() {
        return dirOutput;
    }

    public void setDirOutput(String dirOutput) {
        this.dirOutput = dirOutput;
    }

    public String getDirExperiment() {
        return dirExperiment;
    }

    public void setDirExperiment(String dirExperiment) {
        this.dirExperiment = dirExperiment;
    }

    public String getDirStates() {
        return dirStates;
    }

    public void setDirStates(String dirStates) {
        this.dirStates = dirStates;
    }

    public String getDirChromatinGroups() {
        return dirChromatinGroups;
    }

    public void setDirChromatinGroups(String dirChromatinGroups) {
        this.dirChromatinGroups = dirChromatinGroups;
    }
    
    
}
