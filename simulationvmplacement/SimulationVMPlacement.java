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

/**
 *
 * @author abdullah
 */
public class SimulationVMPlacement {

    /**
     * @param args the command line arguments
     */
    
               ArrayList<PhysicalMachine> pysical_machines= new ArrayList<PhysicalMachine>();       
               ArrayList<VirtualMachine> virtual_machines=new ArrayList<VirtualMachine>();
               ArrayList<Application> applications=new ArrayList<Application>();
     public void DataReading() throws FileNotFoundException, IOException
     {
               
               BufferedReader pm= null;
               BufferedReader  vm= null;
               BufferedReader  app= null;
               
               pm = new BufferedReader(new FileReader("PM.txt"));
               vm = new BufferedReader(new FileReader("VMs.txt")); 
               app = new BufferedReader(new FileReader("APPs.txt")); 
           
                int cpu=0;
                int mem=0;
                int disk= 0;
                int totalNumberofPM=0;
                     String l =pm.readLine();
                while(l!=null)
                {
                    String []s=l.split(" ");
                    cpu=Integer.parseInt(s[1]);
                    mem=Integer.parseInt(s[2]);
                    disk=Integer.parseInt(s[3]);
                    totalNumberofPM=Integer.parseInt(s[4]);
                    
                    l =pm.readLine();
                }    
                pm.close();
                for(int i=1;i<=totalNumberofPM;i++)
                {
                    PhysicalMachine p=new PhysicalMachine();
                    p.PhysicalMachine_Id="PM"+i;
                    p.capacity.cpu=cpu;
                    p.capacity.memory=mem;
                    p.capacity.disk=disk;
                    pysical_machines.add(p);
                }
                
                 l=vm.readLine();
                
                while(l!=null)
                {
                    String []s=l.split(" ");
                     VirtualMachine p=new VirtualMachine();
                    p.VirtualMachine_Id=s[0];
                    p.capacity.cpu=Integer.parseInt(s[1]);
                    p.capacity.memory=Double.parseDouble(s[2]);
                    p.capacity.disk=Double.parseDouble(s[3]);
                    
                    virtual_machines.add(p);
                    l =vm.readLine();
                }    
                vm.close();
                
                vm = new BufferedReader(new FileReader("VMs.txt")); 
                l=vm.readLine();
                
                while(l!=null)
                {
                    String []s=l.split(" ");
                    int links=Integer.parseInt(s[4]);
                    int m=0;
                    while(m<links)
                    {
                        for(int n=0;n<virtual_machines.size();n++)
                        {
                            if(virtual_machines.get(n).VirtualMachine_Id.equals(s[0]))
                            {
                                    for(int o=0;o<virtual_machines.size();o++)
                                    {
                                         if(virtual_machines.get(o).VirtualMachine_Id.split("_")[1].equals(s[4+m+1])&& virtual_machines.get(o).VirtualMachine_Id.split("_")[0].equals(virtual_machines.get(n).VirtualMachine_Id.split("_")[0]))
                                        {
                                            
                                            virtual_machines.get(n).connectedVMs.add(virtual_machines.get(o));
                                            virtual_machines.get(n).LinkedBandwidth.add(Integer.parseInt(s[4+m+1+links]));
                                        }
                                    }
                        
                            }
                        }
                     m++;   
                        
                    }
                    l =vm.readLine();
                } 
                
                
                 l=app.readLine();
                String temp=null;
                Application a=new Application();
                int count=0;
                while(l!=null)
                {
                    String []s=l.split(" ");
                    
                    
                    if(s[0].equals(temp) || temp==null){
                        count++;
                        VirtualMachine p=new VirtualMachine();
                        p.VirtualMachine_Id=s[1];
                        p.capacity.cpu=Integer.parseInt(s[2]);
                        p.capacity.memory=Double.parseDouble(s[3]);
                        p.capacity.disk=Double.parseDouble(s[4]);
                        a.VMs.add(p);
                        temp=s[0];
                    }
                    else
                    {
                        a.app_name=temp;
                        a.noOfRequiredVMs=count;
                        applications.add(a);
                        temp=s[0];
                        a=new Application();
                       VirtualMachine p=new VirtualMachine();
                        p.VirtualMachine_Id=s[1];
                        p.capacity.cpu=Integer.parseInt(s[2]);
                        p.capacity.memory=Double.parseDouble(s[3]);
                        p.capacity.disk=Double.parseDouble(s[4]);
                        a.VMs.add(p);
                        count=1;
                                
                    }
                    l =app.readLine();
                }
                a.app_name=temp;
                a.noOfRequiredVMs=count;
                applications.add(a);
                app.close();
                app = new BufferedReader(new FileReader("APPs.txt")); 
                 l=app.readLine();
                while(l!=null)
                {
                    String []s=l.split(" ");
                    int links=Integer.parseInt(s[5]);
                    int m=0;
                    while(m<links)
                    {
                        for(int q=0;q<applications.size();q++)
                        {
                            if(applications.get(q).app_name.equals(s[0]))
                            {
                                for(int n=0;n<applications.get(q).VMs.size();n++)
                                {
                                    if(applications.get(q).VMs.get(n).VirtualMachine_Id.equals(s[1]))
                                    {
                        
                                        for(int r=0;r<applications.size();r++)
                                 {
                            if(applications.get(r).app_name.equals(s[0]))
                            {
                                    for(int p=0;p<applications.get(r).VMs.size();p++)
                                    {
                                        if(applications.get(r).VMs.get(p).VirtualMachine_Id.equals(s[5+m+1]))
                                        {
                                           applications.get(q).VMs.get(n).connectedVMs.add(applications.get(r).VMs.get(p));
                                           applications.get(q).VMs.get(n).LinkedBandwidth.add(Integer.parseInt(s[5+m+1+links]));
                                     }
                                 }
                                
                            }        
                        }
                                    }
                                }
                                
                            }        
                        }
                        m++;   
                        
                    }
                    l =app.readLine();
                }
                
      
               
                applications.sort(new Comparator<Application>() {
                @Override
                 public int compare(Application p0, Application p1) {
                            return  Integer.compare(p0.noOfRequiredVMs,p1.noOfRequiredVMs) ;
                }
            });
                       
         
     }
    public void simulation()throws FileNotFoundException, IOException
    {
        
                     
                Scheduler s=new Scheduler();
                
                DataReading();
                System.out.println();
                System.out.println("Fisrt Fit");
                System.out.println();
                s.PlacementAlgorithmFirstFit(pysical_machines, virtual_machines);
             
            
                pysical_machines= new ArrayList<PhysicalMachine>();       
                virtual_machines=new ArrayList<VirtualMachine>();
                applications=new ArrayList<Application>();
                System.gc();
                 DataReading();
                System.out.println();
                 System.out.println("Round Robin");
                System.out.println();
                 s.PlacementAlgorithmRoundRobin(pysical_machines, virtual_machines);

                pysical_machines= new ArrayList<PhysicalMachine>();       
                virtual_machines=new ArrayList<VirtualMachine>();
                applications=new ArrayList<Application>();
                System.gc();   
                 DataReading();
                System.out.println();
                 System.out.println("Knapscak");
                              System.out.println();
                 s.PlacementAlgorithmKnapsack(pysical_machines, virtual_machines);


                pysical_machines= new ArrayList<PhysicalMachine>();       
                virtual_machines=new ArrayList<VirtualMachine>();
                applications=new ArrayList<Application>();
                System.gc();
                 DataReading();
                                 System.out.println();  
                 System.out.println("Application Level Knapsack");
                             System.out.println();
                 s.ApplicationLevelPlacement(pysical_machines,applications);


                pysical_machines= new ArrayList<PhysicalMachine>();       
                virtual_machines=new ArrayList<VirtualMachine>();
                applications=new ArrayList<Application>();
                System.gc();
                 DataReading();
                                 System.out.println();
                 System.out.println("Application Level First Fit ");
                            System.out.println();
                 s.ApplicationLevelPlacementWithFirstFit(pysical_machines,applications);
    }
    
    public void configrationGenrator(int noOfApps) throws IOException
    {
        ConfigrationGenrator g=new ConfigrationGenrator();
        g.genrateVMsConfigration(noOfApps, 4, 2, 6, 3, 7, 5, 25,1,50);
    }
    public static void main(String[] args) throws IOException 
    {
        // TODO code application logic here
      
        for(int i=1;i<4;i++)
        {
            System.out.println();
            System.out.println("Iteration # "+i);
            System.out.println();
            SimulationVMPlacement s=new SimulationVMPlacement();
            s.configrationGenrator(15);
            s.simulation();
        }       
       
    }
    
}
