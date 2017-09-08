/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationvmplacement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abdullah
 */
public class PhysicalMachine extends Machine {
    
    public String PhysicalMachine_Id;
    ArrayList<VirtualMachine> allocated_VMs=new ArrayList<VirtualMachine>();
    
    
   
    
    public Capacity AllocatedCapacity()
    {
        Capacity c=new Capacity();
        c.cpu=0;
        c.memory=0;
        c.disk=0;
 
        for(int i=0;i<allocated_VMs.size();i++)
        {
            c.cpu+= allocated_VMs.get(i).capacity.cpu;
            c.disk+=allocated_VMs.get(i).capacity.disk;
            c.memory+=allocated_VMs.get(i).capacity.memory;
        }
        
        return c;
        
    }
    public Capacity FreeCapacity()
    {
        Capacity c=new Capacity();
         c.cpu=capacity.cpu-AllocatedCapacity().cpu;
        c.memory=capacity.memory-AllocatedCapacity().memory;                    
        c.disk=capacity.disk-AllocatedCapacity().disk;       
        return c;
        
    }    
}
