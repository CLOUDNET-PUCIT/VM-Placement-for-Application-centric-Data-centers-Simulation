/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationvmplacement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author abdullah
 */
public class ConfigrationGenrator {
    
    
    public void genrateVMsConfigration(int noOfApp, int MaximumVMs,int minCpu,int maxCpu, int minMem,int maxMem, int minDisk, int maxDisk,int minBandwidth,int maxBandwidth) throws IOException
    {
        
            BufferedWriter vb = new BufferedWriter(new FileWriter("VMs.txt"));
            BufferedWriter appb = new BufferedWriter(new FileWriter("APPs.txt"));
            int totalCpu=0, totalMem=0, totalDisk=0, totalVM=0;
            for(int i=0;i<noOfApp;i++)
            {
                int k=i+1;
            
                int noOfVMs = ThreadLocalRandom.current().nextInt(1,MaximumVMs  + 1);
                totalVM+=noOfVMs;
                for(int j=0;j<noOfVMs;j++)
                {
                    int l=j+1;
                    appb.write("App"+k+" "+"VM"+l+" ");
                    vb.write("App"+k+"_VM"+l+" ");
                    int cpu=ThreadLocalRandom.current().nextInt(minCpu,maxCpu  + 1);
                    int mem=ThreadLocalRandom.current().nextInt(minMem,maxMem  + 1);
                    int disk=ThreadLocalRandom.current().nextInt(minDisk,maxDisk  + 1);
                    totalCpu+=cpu;
                    totalMem+=mem;
                    totalDisk+=disk;
                    appb.write(cpu+" "+mem+" "+disk+" ");
                    vb.write(cpu+" "+mem+" "+disk+" ");
                    
                     int links=ThreadLocalRandom.current().nextInt(0,noOfVMs);
                int li=1;
                ArrayList<Integer> temp=new ArrayList<Integer>();
                while(li<=links)
                {
                    
                    int r=ThreadLocalRandom.current().nextInt(1,noOfVMs+ 1);
                    if(!(temp.contains(r) ||  r==l))
                    {
                        temp.add(r);
                        li++;
                    }
                    
                }
                appb.write(links+" ");
                vb.write(links+" ");
                for(int m=0;m<temp.size();m++)
                {
                    appb.write("VM"+temp.get(m)+" ");
                   vb.write("VM"+temp.get(m)+" ");
                    
                }
                for(int m=0;m<temp.size();m++)
                {
                       appb.write(ThreadLocalRandom.current().nextInt(minBandwidth,maxBandwidth+ 1)+" ");
                   vb.write(ThreadLocalRandom.current().nextInt(minBandwidth,maxBandwidth+ 1)+" ");
                    
                }                    

                appb.newLine();
                    vb.newLine();
               
            
                
                }
                
                       
            }
            vb.close();
            appb.close();
            System.out.println("TotalVMs: "+totalVM+" total CPU : "+totalCpu+" totalMemory "+totalMem+" totalDisk "+totalDisk);
    }
}
