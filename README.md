# VM-Placement-for-Application-centric-Data-centers-Simulation

# scenarios genration 
  In configrationGenrator( ); function calling from Main function pass these value according to deployment model experiments.

    15 for Micro deployment
    150 for Small deployment
    700 for Medium deployment
    1500 for Large deployment

# Physical Machines Information
create a file PM.txt with following line
    
    PM 16 20 100 10

    first value is PM representing physical machine.
    second value is CPU units count.
    third value is Memory units count.
    fourth value is Disk unit count.
    fifth value is number of PM.
possible fifth values in PM.txt file according to your experiments.
    
    For Micro deplyment experiments fifth value is 10.
    For Small deployment experiments fifth value is 100.
    For Medium deployment experiments fifth value is 500.
    For Large deployment experiments fifth value is 1000.

# experments result details

This project run the three iteration of each algorithm, and output the following values for each iteration against all discussed algorithms.

    time : (ms)
    Unallocated VMs: 
    waste CPU:  Total waste %
    waste Memory:  Total waste % 
    Waste Disk:  Total waste % 
    Free PM: 
    Total Bandwidth used 
   
    these values representing the following.
    
    1.time: placment time in (ms)
    2.unallocated VMs count
    3. waste CPU units and percentage
    4. waste Memory units and percentage
    5. waste Disk units and percentage
    6. completly free PM
    7. bandwidth utilized
if you need to test all experiments for all deployment models than you have to run this project four times, changing the configrationGenrator( ) parameter as define above and PM.txt file fifth value as explained above.    
 
