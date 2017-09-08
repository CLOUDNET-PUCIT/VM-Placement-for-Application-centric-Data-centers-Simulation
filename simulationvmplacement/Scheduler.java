/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationvmplacement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author abdullah
 */
public class Scheduler {
    
   public void FirstFit(ArrayList<PhysicalMachine> PMs,ArrayList<VirtualMachine> VMs) throws IOException
    {
        for(int i=0;i<VMs.size();i++)
        {
            for(int j=0;j<PMs.size();j++)
            {
                
                if(isAllowedForAllocation(VMs.get(i),PMs.get(j)))
                {
                    VMs.get(i).allocated_PM=PMs.get(j);
                    PMs.get(j).allocated_VMs.add(VMs.get(i));
                    break;
                }
             
            }
        }
        
    }
    public void PlacementAlgorithmFirstFit(ArrayList<PhysicalMachine> PMs,ArrayList<VirtualMachine> VMs) throws IOException
    {
        long startTime=System.currentTimeMillis();
        
       FirstFit(PMs,VMs);
        
        long endTime=System.currentTimeMillis();
        long time=endTime-startTime;
        
        
        BufferedWriter fb = new BufferedWriter(new FileWriter("FirstFit_Allocation.txt"));
        int unallocated=0;
            int wasteCPU=0;
            int wasteMem=0;
            int wasteDisk=0;
             int TotalCPU=0;
            int TotalMem=0;
            int TotalDisk=0;
            int freePhysicalMachines=0;
            int TotalBandwidthUse=0;
            
            for(int h=0;h<VMs.size();h++)
            {
                for(int x=0;x<VMs.get(h).connectedVMs.size();x++)
                {
                    if(VMs.get(h).allocated_PM!=VMs.get(h).connectedVMs.get(x).allocated_PM)
                    {
                        TotalBandwidthUse+=VMs.get(h).LinkedBandwidth.get(x);
                    }
                    
                }
            
            }
            
                for(int i=0;i<VMs.size();i++)
                {
                        int k=i+1;
                    if (VMs.get(i).allocated_PM==null)
                    {

                     // System.out.println("VM No. "+k+" "+VMs.get(i).VirtualMachine_Id + " is not allocated on any Pysical Machine");
                      fb.write(VMs.get(i).VirtualMachine_Id.split("_")[0]+" "+VMs.get(i).VirtualMachine_Id.split("_")[1]+" 0");
                      unallocated++; 
                    }
                    else
                    {
                        //System.out.println("VM No. "+k+" "+VMs.get(i).VirtualMachine_Id + " is allocated on  " + VMs.get(i).allocated_PM.PhysicalMachine_Id );
                    fb.write(VMs.get(i).VirtualMachine_Id.split("_")[0]+" "+VMs.get(i).VirtualMachine_Id.split("_")[1]+" "+ VMs.get(i).allocated_PM.PhysicalMachine_Id);
                    }
                    fb.newLine();
                }  
                 fb.close();
                for(int j=0;j<PMs.size();j++)
                {
                    //System.out.println(PMs.get(j).PhysicalMachine_Id + " Waste capaity: "+ PMs.get(j).FreeCapacity().cpu+" "+PMs.get(j).FreeCapacity().memory+ " "+PMs.get(j).FreeCapacity().disk  );
                
                    if(PMs.get(j).FreeCapacity().cpu==PMs.get(j).capacity.cpu&& (PMs.get(j).FreeCapacity().memory)==PMs.get(j).capacity.memory&& (PMs.get(j).FreeCapacity().disk)==PMs.get(j).capacity.disk)
                    {
                           freePhysicalMachines++;
                    }
                    else
                    {
                            wasteCPU+=PMs.get(j).FreeCapacity().cpu;
                    wasteMem+=PMs.get(j).FreeCapacity().memory;
                    wasteDisk+=PMs.get(j).FreeCapacity().disk;
                     TotalCPU+=PMs.get(j).capacity.cpu;
                    TotalMem+=PMs.get(j).capacity.memory;
                    TotalDisk+=PMs.get(j).capacity.disk;
                    }
                            
                }
               
                System.out.println("Summary First Fit ");
                System.out.println("Time: (ms) " +time);
                 System.out.println("Unallocated VMs: "+unallocated);
                System.out.println("waste CPU: "+wasteCPU+ " Total waste % "+((double)wasteCPU/(double)TotalCPU)*100.0);
                System.out.println("waste Memory: "+wasteMem+ " Total waste % "+((double)wasteMem/(double)TotalMem)*100.0);
                System.out.println("Waste Disk: "+wasteDisk+ " Total waste % "+((double)wasteDisk/(double)TotalDisk)*100.0);
                System.out.println("Free PM: "+freePhysicalMachines++);
                System.out.println("Total Bandwidth used "+TotalBandwidthUse);
    }

    public boolean isAllowedForAllocation(VirtualMachine v, PhysicalMachine p) {
                
            if(v.capacity.cpu <= p.FreeCapacity().cpu && v.capacity.memory <= p.FreeCapacity().memory && v.capacity.disk <= p.FreeCapacity().disk)
            {
                return true;
            }
            return false;
    }
    
    
   public void PlacementAlgorithmKnapsack(ArrayList<PhysicalMachine> PMs,ArrayList<VirtualMachine> VMs) throws IOException
   {
       long startTime=System.currentTimeMillis();
        
                Knapsack(PMs,VMs);
        long endTime=System.currentTimeMillis();
        long time=endTime-startTime;
        
                BufferedWriter fb = new BufferedWriter(new FileWriter("Knapsack_Allocation.txt"));
        int unallocated=0;
            int wasteCPU=0;
            int wasteMem=0;
            int wasteDisk=0;
             int TotalCPU=0;
            int TotalMem=0;
            int TotalDisk=0;
            int freePhysicalMachines=0;
            int TotalBandwidthUse=0;
            
            for(int h=0;h<VMs.size();h++)
            {
                for(int x=0;x<VMs.get(h).connectedVMs.size();x++)
                {
                    if(VMs.get(h).allocated_PM!=VMs.get(h).connectedVMs.get(x).allocated_PM)
                    {
                        TotalBandwidthUse+=VMs.get(h).LinkedBandwidth.get(x);
                    }
                    
                }
            
            }
                for(int i=0;i<VMs.size();i++)
                {
                        int k=i+1;
                    if (VMs.get(i).allocated_PM==null)
                    {

                     // System.out.println("VM No. "+k+" "+VMs.get(i).VirtualMachine_Id + " is not allocated on any Pysical Machine");
                      fb.write(VMs.get(i).VirtualMachine_Id.split("_")[0]+" "+VMs.get(i).VirtualMachine_Id.split("_")[1]+" 0");
                      unallocated++; 
                    }
                    else
                    {
                    //    System.out.println("VM No. "+k+" "+VMs.get(i).VirtualMachine_Id + " is allocated on  " + VMs.get(i).allocated_PM.PhysicalMachine_Id );
                    fb.write(VMs.get(i).VirtualMachine_Id.split("_")[0]+" "+VMs.get(i).VirtualMachine_Id.split("_")[1]+" "+ VMs.get(i).allocated_PM.PhysicalMachine_Id);
                    }
                    fb.newLine();
                }  
                 fb.close();
                for(int j=0;j<PMs.size();j++)
                {
                    //System.out.println(PMs.get(j).PhysicalMachine_Id + " Waste capaity: "+ PMs.get(j).FreeCapacity().cpu+" "+PMs.get(j).FreeCapacity().memory+ " "+PMs.get(j).FreeCapacity().disk  );
                    
                    if(PMs.get(j).FreeCapacity().cpu==PMs.get(j).capacity.cpu&& (PMs.get(j).FreeCapacity().memory)==PMs.get(j).capacity.memory&& (PMs.get(j).FreeCapacity().disk)==PMs.get(j).capacity.disk)
                    {
                           freePhysicalMachines++;
                    }
                    else
                    {
                        wasteCPU+=PMs.get(j).FreeCapacity().cpu;
                    wasteMem+=PMs.get(j).FreeCapacity().memory;
                    wasteDisk+=PMs.get(j).FreeCapacity().disk;
                     TotalCPU+=PMs.get(j).capacity.cpu;
                    TotalMem+=PMs.get(j).capacity.memory;
                    TotalDisk+=PMs.get(j).capacity.disk;
                    }
                            
                }
                System.out.println("Summary KnapSack ");
                System.out.println("time : (ms)"+time);
                System.out.println("Unallocated VMs: "+unallocated);
                System.out.println("waste CPU: "+wasteCPU+ " Total waste % "+((double)wasteCPU/(double)TotalCPU)*100.0);
                System.out.println("waste Memory: "+wasteMem+ " Total waste % "+((double)wasteMem/(double)TotalMem)*100.0);
                System.out.println("Waste Disk: "+wasteDisk+ " Total waste % "+((double)wasteDisk/(double)TotalDisk)*100.0);
                System.out.println("Free PM: "+freePhysicalMachines++);
                
                System.out.println("Total Bandwidth used "+TotalBandwidthUse);
                
   }
   public void Knapsack( ArrayList<PhysicalMachine> PMs,ArrayList<VirtualMachine> vms)
    {
        ArrayList<VirtualMachine> temp=new ArrayList<VirtualMachine>();
        int count=0;
        for(int i=0;i<vms.size();i++)
        {
            if(vms.get(i).allocated_PM!=null)
            {
                count++;
            }
        }
        if(count==vms.size())
            return;
        for(int m=0;m<PMs.size();m++)
        {   
        int cpu=PMs.get(m).FreeCapacity().cpu;
        int memory=(int)PMs.get(m).FreeCapacity().memory;
        int disk=(int)PMs.get(m).FreeCapacity().disk;
        
        int[][][][] dynNoRep;
        dynNoRep = new int[vms.size() + 1][cpu + 1][memory + 1][disk+1];
        System.gc();
        for(int j = 0; j <= cpu; j++) {
            dynNoRep[0][j][0][0] = 0;
        }
        for(int i = 0; i <= memory; i++) {
            dynNoRep[0][0][i][0] = 0;
        }
        for(int i = 0; i <= vms.size(); i++) {
            dynNoRep[i][0][0][0] = 0;
        }
        for(int i = 0; i <= disk; i++) {
            dynNoRep[0][0][0][i] = 0;
        }
        for(int i = 1; i <=vms.size(); i++){
            if (vms.get(i-1).allocated_PM==null){
                
            for(int j = 0; j <= cpu; j++) {
                for(int h = 0; h <= memory; h++) {
                    for(int k=0;k<=disk;k++){
                    
                        if( vms.get(i-1).capacity.cpu > j)
                        // If the item i is too big, I  can't put it and the solution is the same of the problem with i - 1 items
                        dynNoRep[i][j][h][k] = dynNoRep[i - 1][j][h][k];  
                
                   else if(vms.get(i-1).capacity.memory > h)
                        // If the item i is too voluminous, I can't put it and the solution is the same of the problem with i - 1 items
                        dynNoRep[i][j][h][k] = dynNoRep[i - 1][j][h][k];
                   else if (vms.get(i-1).capacity.disk > k)
                        dynNoRep[i][j][h][k] = dynNoRep[i - 1][j][h][k];
                    else {
                        // The item i could be useless and the solution is the same of the problem with i - 1 items, or it could be 
                        // useful and the solution is "(solution of knapsack of size j - item[i].size and volume h - item[i].volume) + item[i].value" 
                        dynNoRep[i][j][h][k] = Math.max(dynNoRep[i - 1][j][h][k], dynNoRep[i - 1][j - vms.get(i-1).capacity.cpu][h - (int)vms.get(i-1).capacity.memory][k-(int)vms.get(i-1).capacity.disk] + 1);
                    }
                    
                 }
                    
            }
        }
        }
        }
        int j = PMs.get(m).FreeCapacity().cpu, h =(int) PMs.get(m).FreeCapacity().memory , k=(int) PMs.get(m).FreeCapacity().disk, finalSize = 0, finalValue = 0, finalVolume = 0,finalxyz=0;
       //   System.out.print("Items picked (value, size, volume) for 0/1 problems without repetitions: ");

         for(int i = vms.size(); i > 0; i--) {
            if(dynNoRep[i][j][h][k] != dynNoRep[i - 1][j][h][k]) {
         //       System.out.print("(" + 1 + "," + vms.get(i-1).capacity.cpu + ", " + (int) vms.get(i-1).capacity.memory+ ","+ (int) vms.get(i-1).capacity.disk  + ") ");
                finalSize += vms.get(i-1).capacity.cpu;
                finalValue += 1;
                finalVolume += (int) vms.get(i-1).capacity.memory;
                finalxyz+=(int) vms.get(i-1).capacity.disk;
                
                vms.get(i-1).allocated_PM=PMs.get(m);
                PMs.get(m).allocated_VMs.add(vms.get(i-1));   
                 k -= (int) vms.get(i-1).capacity.disk;
                j -= vms.get(i-1).capacity.cpu;
                h -= (int) vms.get(i-1).capacity.memory;
               
               
            }
        }
        //System.out.println("");
        
        
        for(VirtualMachine v : vms)
        {
            if(v.allocated_PM!=null)
            {
                temp.add(v);
                //vms.remove(v);

            }
        }
        
        for(int i=0;i<vms.size();i++)
        {
            if(vms.get(i).allocated_PM!=null)
            {       
                vms.remove(i);
                i--;
            }
        }
        }
        
        for(VirtualMachine v: temp)
        {
            vms.add(v);
           // temp.remove(v);
        }
      
        
    }
public void ApplicationLevelPlacement(ArrayList<PhysicalMachine> PMs,ArrayList<Application> applications) throws IOException
{
        ArrayList<PhysicalMachine> t=getFreePMS(PMs);
     long startTime=System.currentTimeMillis();
        
    for(int i=applications.size()-1;i>=0;i--)
    {
        
        if(t.size()>0){
                Knapsack(t,applications.get(i).VMs);
                for(int j=0;j<t.size();j++)
                {
                    if(t.get(j).allocated_VMs.size()!=0)
                    {
                        PMs.add(t.remove(j));
                        j--;
                    }
            
                }
                for(int k=0;k<applications.get(i).VMs.size();k++)
                {
                    if(applications.get(i).VMs.get(k).allocated_PM==null)
                    {
                        Knapsack(PMs,applications.get(i).VMs);
                        break;
                    }
                    
                }
        }
        else
        {
            Collections.sort(PMs, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    
                    PhysicalMachine p1=(PhysicalMachine) o1;
                    PhysicalMachine p2=(PhysicalMachine) o2;
                  if(Integer.compare(p1.FreeCapacity().cpu, p2.FreeCapacity().cpu)!=0)
                  {
                      return Integer.compare(p1.FreeCapacity().cpu, p2.FreeCapacity().cpu);
                  }
                  else if(Double.compare(p1.FreeCapacity().memory, p2.FreeCapacity().memory)!=0)
                  {
                      return Double.compare(p1.FreeCapacity().memory, p2.FreeCapacity().memory);
                  }
                  else
                  {
                      return Double.compare(p1.FreeCapacity().disk, p2.FreeCapacity().disk);
                  }
                    
                }
                

        
                
            });
                    Collections.reverse(PMs);
               Knapsack(PMs,applications.get(i).VMs);
                
        }
        
    }
    
        long endTime=System.currentTimeMillis();
        long time=endTime-startTime;

            for(int j=0;j<t.size();j++)
                {
                    
                        PMs.add(t.get(j));
                        
                    
            
                }
    
             BufferedWriter fb = new BufferedWriter(new FileWriter("ApplicationLevel_Allocation.txt"));
        int unallocated=0;
            int wasteCPU=0;
            int wasteMem=0;
            int wasteDisk=0;
            int freePhysicalMachines=0;
            int TotalCPU=0;
            int TotalMem=0;
            int TotalDisk=0;
             int TotalBandwidthUse=0;
            
             
            for(int h=0;h<applications.size();h++)
            {
                for(int x=0;x<applications.get(h).VMs.size();x++)
                {
                    for(int y=0;y<applications.get(h).VMs.get(x).connectedVMs.size();y++)
                    {
                        if(applications.get(h).VMs.get(x).allocated_PM!=null){
                        if(!(applications.get(h).VMs.get(x).allocated_PM.equals(applications.get(h).VMs.get(x).connectedVMs.get(y).allocated_PM)) )
                        {
                            TotalBandwidthUse+=applications.get(h).VMs.get(x).LinkedBandwidth.get(y);
                            
                        }}
                    }
                }
            
            }
            
                for(int i=0;i<applications.size();i++)
                {
                   
                    for(int j=0;j<applications.get(i).VMs.size();j++)
                    {
                          if(applications.get(i).VMs.get(j).allocated_PM!=null){
                     // System.out.println(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" is allocated on "+applications.get(i).VMs.get(j).allocated_PM.PhysicalMachine_Id );
                        fb.write(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" "+applications.get(i).VMs.get(j).allocated_PM.PhysicalMachine_Id);
                          }
                          else
                          {
                       //   System.out.println(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" is not allocated on any Physical Machine" );
                        fb.write(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" 0");
                          unallocated++; 
                          }
                    fb.newLine();
                    }
                   
                
                }
                 fb.close();
                for(int j=0;j<PMs.size();j++)
                {
                   // System.out.println(PMs.get(j).PhysicalMachine_Id + " Waste capaity: "+ PMs.get(j).FreeCapacity().cpu+" "+PMs.get(j).FreeCapacity().memory+ " "+PMs.get(j).FreeCapacity().disk  );
          
                    
                    if(PMs.get(j).FreeCapacity().cpu==PMs.get(j).capacity.cpu&& (PMs.get(j).FreeCapacity().memory)==PMs.get(j).capacity.memory&& (PMs.get(j).FreeCapacity().disk)==PMs.get(j).capacity.disk)
                    {
                           freePhysicalMachines++;
                    }  
                    else
                    {
                                  wasteCPU+=PMs.get(j).FreeCapacity().cpu;
                    wasteMem+=PMs.get(j).FreeCapacity().memory;
                    wasteDisk+=PMs.get(j).FreeCapacity().disk;
                   TotalCPU+=PMs.get(j).capacity.cpu;
                    TotalMem+=PMs.get(j).capacity.memory;
                    TotalDisk+=PMs.get(j).capacity.disk;
                    }
                            
                }
                System.out.println("Summary Application_Level with knapsack ");
                System.out.println("Time: (ms)"+time);
                System.out.println("Unallocated VMs: "+unallocated);
                System.out.println("waste CPU: "+wasteCPU+ " Total waste % "+((double)wasteCPU/(double)TotalCPU)*100.0);
                System.out.println("waste Memory: "+wasteMem+ " Total waste % "+((double)wasteMem/(double)TotalMem)*100.0);
                System.out.println("Waste Disk: "+wasteDisk+ " Total waste % "+((double)wasteDisk/(double)TotalDisk)*100.0);
                System.out.println("Free PM: "+freePhysicalMachines++);
                
                System.out.println("Total Bandwidth used "+TotalBandwidthUse);
                
                
                
   
    
    
}        
    
public void ApplicationLevelPlacementWithFirstFit(ArrayList<PhysicalMachine> PMs,ArrayList<Application> applications) throws IOException
{
    
        ArrayList<PhysicalMachine> t=getFreePMS(PMs);
          long startTime=System.currentTimeMillis();
        
    for(int i=applications.size()-1;i>=0;i--)
    {
        
        if(t.size()>0){
                FirstFit(t,applications.get(i).VMs);
                for(int j=0;j<t.size();j++)
                {
                    if(t.get(j).allocated_VMs.size()!=0)
                    {
                        PMs.add(t.remove(j));
                        j--;
                    }
            
                }
                for(int k=0;k<applications.get(i).VMs.size();k++)
                {
                    if(applications.get(i).VMs.get(k).allocated_PM==null)
                    {
                        FirstFit(PMs,applications.get(i).VMs);
                        break;
                    }
                    
                }
        }
        else
        {
            
               FirstFit(PMs,applications.get(i).VMs);
                
        }
        
    }
        long endTime=System.currentTimeMillis();
        long time=endTime-startTime;
    
            for(int j=0;j<t.size();j++)
                {
                    
                        PMs.add(t.get(j));
                }
    
             BufferedWriter fb = new BufferedWriter(new FileWriter("ApplicationLevel_AllocationirsFirstFit.txt"));
            int unallocated=0;
            int wasteCPU=0;
            int wasteMem=0;
            int wasteDisk=0;
            int freePhysicalMachines=0;
            int TotalCPU=0;
            int TotalMem=0;
            int TotalDisk=0;
             int TotalBandwidthUse=0;
            
             
            for(int h=0;h<applications.size();h++)
            {
                for(int x=0;x<applications.get(h).VMs.size();x++)
                {
                    for(int y=0;y<applications.get(h).VMs.get(x).connectedVMs.size();y++)
                    {
                        if(applications.get(h).VMs.get(x).allocated_PM!=null){
                        if(!(applications.get(h).VMs.get(x).allocated_PM.equals(applications.get(h).VMs.get(x).connectedVMs.get(y).allocated_PM)) )
                        {
                            TotalBandwidthUse+=applications.get(h).VMs.get(x).LinkedBandwidth.get(y);
                            
                        }}
                    }
                }
            
            }
            
                for(int i=0;i<applications.size();i++)
                {
                   
                    for(int j=0;j<applications.get(i).VMs.size();j++)
                    {
                          if(applications.get(i).VMs.get(j).allocated_PM!=null){
                     // System.out.println(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" is allocated on "+applications.get(i).VMs.get(j).allocated_PM.PhysicalMachine_Id );
                        fb.write(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" "+applications.get(i).VMs.get(j).allocated_PM.PhysicalMachine_Id);
                          }
                          else
                          {
                       //   System.out.println(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" is not allocated on any Physical Machine" );
                        fb.write(applications.get(i).app_name+" "+applications.get(i).VMs.get(j).VirtualMachine_Id +" 0");
                          unallocated++; 
                          }
                    fb.newLine();
                    }
                   
                
                }
                 fb.close();
                for(int j=0;j<PMs.size();j++)
                {
                   // System.out.println(PMs.get(j).PhysicalMachine_Id + " Waste capaity: "+ PMs.get(j).FreeCapacity().cpu+" "+PMs.get(j).FreeCapacity().memory+ " "+PMs.get(j).FreeCapacity().disk  );
          
                    
                    if(PMs.get(j).FreeCapacity().cpu==PMs.get(j).capacity.cpu&& (PMs.get(j).FreeCapacity().memory)==PMs.get(j).capacity.memory&& (PMs.get(j).FreeCapacity().disk)==PMs.get(j).capacity.disk)
                    {
                           freePhysicalMachines++;
                    }  
                    else
                    {
                                  wasteCPU+=PMs.get(j).FreeCapacity().cpu;
                    wasteMem+=PMs.get(j).FreeCapacity().memory;
                    wasteDisk+=PMs.get(j).FreeCapacity().disk;
                   TotalCPU+=PMs.get(j).capacity.cpu;
                    TotalMem+=PMs.get(j).capacity.memory;
                    TotalDisk+=PMs.get(j).capacity.disk;
                    }
                            
                }
                System.out.println("Summary Application_Level With FirstFit ");
                System.out.println("Time: (ms)"+time);
                System.out.println("Unallocated VMs: "+unallocated);
                System.out.println("waste CPU: "+wasteCPU+ " Total waste % "+((double)wasteCPU/(double)TotalCPU)*100.0);
                System.out.println("waste Memory: "+wasteMem+ " Total waste % "+((double)wasteMem/(double)TotalMem)*100.0);
                System.out.println("Waste Disk: "+wasteDisk+ " Total waste % "+((double)wasteDisk/(double)TotalDisk)*100.0);
                System.out.println("Free PM: "+freePhysicalMachines++);
                
                System.out.println("Total Bandwidth used "+TotalBandwidthUse);
                
                
                
   
    
    
}        
    public void PlacementAlgorithmRoundRobin(ArrayList<PhysicalMachine> PMs,ArrayList<VirtualMachine> VMs) throws IOException
    {
        
        long startTime=System.currentTimeMillis();
       // System.out.println(startTime);
           int j=0; 
           int count=0;
        for(int i=0;i<VMs.size();i++)
        {
                      
                if(isAllowedForAllocation(VMs.get(i),PMs.get(j)))
                {
                    VMs.get(i).allocated_PM=PMs.get(j);
                    PMs.get(j).allocated_VMs.add(VMs.get(i));
                }
                else
                {
                    if(count<PMs.size()){
                        i--;
                        count++;
                    }
                    else
                    {
                       
                        count=0;
                    }
                }
               
                j=(j+1)%PMs.size();
                
        }
        
        long endTime=System.currentTimeMillis();
        long time=endTime-startTime;
        
         BufferedWriter fb = new BufferedWriter(new FileWriter("RoundRobin_Allocation.txt"));
        int unallocated=0;
            int wasteCPU=0;
            int wasteMem=0;
            int wasteDisk=0;
             int TotalCPU=0;
            int TotalMem=0;
            int TotalDisk=0;
            int freePhysicalMachines=0;
             int TotalBandwidthUse=0;
            
            for(int h=0;h<VMs.size();h++)
            {
                for(int x=0;x<VMs.get(h).connectedVMs.size();x++)
                {
                    if(VMs.get(h).allocated_PM!=VMs.get(h).connectedVMs.get(x).allocated_PM)
                    {
                        TotalBandwidthUse+=VMs.get(h).LinkedBandwidth.get(x);
                    }
                    
                }
            
            }
                for(int i=0;i<VMs.size();i++)
                {
                        int k=i+1;
                    if (VMs.get(i).allocated_PM==null)
                    {

                   //   System.out.println("VM No. "+k+" "+VMs.get(i).VirtualMachine_Id + " is not allocated on any Pysical Machine");
                      fb.write(VMs.get(i).VirtualMachine_Id.split("_")[0]+" "+VMs.get(i).VirtualMachine_Id.split("_")[1]+" 0");
                      unallocated++; 
                    }
                    else
                    {
                    //    System.out.println("VM No. "+k+" "+VMs.get(i).VirtualMachine_Id + " is allocated on  " + VMs.get(i).allocated_PM.PhysicalMachine_Id );
                    fb.write(VMs.get(i).VirtualMachine_Id.split("_")[0]+" "+VMs.get(i).VirtualMachine_Id.split("_")[1]+" "+ VMs.get(i).allocated_PM.PhysicalMachine_Id);
                    }
                    fb.newLine();
                }  
                 fb.close();
                for(int l=0;l<PMs.size();l++)
                {
                    //System.out.println(PMs.get(l).PhysicalMachine_Id + " Waste capaity: "+ PMs.get(l).FreeCapacity().cpu+" "+PMs.get(l).FreeCapacity().memory+ " "+PMs.get(l).FreeCapacity().disk  );
                   
                    if(PMs.get(l).FreeCapacity().cpu==PMs.get(l).capacity.cpu&& (PMs.get(l).FreeCapacity().memory)==PMs.get(l).capacity.memory&& (PMs.get(l).FreeCapacity().disk)==PMs.get(l).capacity.disk)
                    {
                           freePhysicalMachines++;
                    } 
                    else
                    {
                         wasteCPU+=PMs.get(l).FreeCapacity().cpu;
                    wasteMem+=PMs.get(l).FreeCapacity().memory;
                    wasteDisk+=PMs.get(l).FreeCapacity().disk;
                     TotalCPU+=PMs.get(l).capacity.cpu;
                    TotalMem+=PMs.get(l).capacity.memory;
                    TotalDisk+=PMs.get(l).capacity.disk;
                    }
                            
                }
                System.out.println("Summary Round Robin ");
                System.out.println("Time: (ms)"+time);
                System.out.println("Unallocated VMs: "+unallocated);
                System.out.println("waste CPU: "+wasteCPU+ " Total waste % "+((double)wasteCPU/(double)TotalCPU)*100.0);
                System.out.println("waste Memory: "+wasteMem+ " Total waste % "+((double)wasteMem/(double)TotalMem)*100.0);
                System.out.println("Waste Disk: "+wasteDisk+ " Total waste % "+((double)wasteDisk/(double)TotalDisk)*100.0);
                System.out.println("Free PM: "+freePhysicalMachines++);
               System.out.println("Total BandWidth Used: "+TotalBandwidthUse);
                
        
    }

    public  ArrayList<PhysicalMachine> getFreePMS(ArrayList<PhysicalMachine> PMs) 
    {
       
        ArrayList<PhysicalMachine> t=new ArrayList<PhysicalMachine>(); 
        for(int i=0;i<PMs.size();i++)
        {
            if(PMs.get(i).allocated_VMs.size()==0)
            {
                t.add(PMs.remove(i));
                i--;
            }
            
        }
        
        return t;
    }
}
