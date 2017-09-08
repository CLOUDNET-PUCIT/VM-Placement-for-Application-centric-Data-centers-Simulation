/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationvmplacement;

import java.util.ArrayList;

/**
 *
 * @author abdullah
 */
public class VirtualMachine extends Machine{
    
    public String VirtualMachine_Id;
    PhysicalMachine allocated_PM;
    ArrayList<VirtualMachine> connectedVMs=new ArrayList<VirtualMachine> ();
    ArrayList<Integer> LinkedBandwidth=new ArrayList<Integer> ();
    
    
}
