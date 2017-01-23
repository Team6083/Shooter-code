	
package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    Spark motor=new Spark(0);
    Joystick joy=new Joystick(0);
    PowerDistributionPanel pdp = new PowerDistributionPanel(0);
    double speed=0,ready_current = 4.5;
    DigitalOutput do1 = new DigitalOutput(1);
    
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        SmartDashboard.putNumber("Max Speed", 0.55);
        SmartDashboard.putNumber("ready_current", ready_current);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	SmartDashboard.putBoolean("Enabled", true);
    	double maxspeed = SmartDashboard.getNumber("Max Speed");//reading Max Speed
    	double pdp15 = pdp.getCurrent(15);//reading current
    	boolean ready = false;
    	ready_current = SmartDashboard.getNumber("ready_current");//reading ready_current
    	if(joy.getRawButton(5)){
    		if(speed<maxspeed){
     		   speed=speed+0.01;
    		}
    	}//when pressed speedup slowly to avoid the overloaded current
       if(!joy.getRawButton(5)&&speed>0){
    	   do{
    		   speed=speed-0.05;
    	   }while(!joy.getRawButton(5)&&speed>0);
       }//slow down slowly avoid the damage of the motor
       
       motor.set(speed);
       SmartDashboard.putNumber("ShooterSpeed", motor.get());
       SmartDashboard.putNumber("Current",pdp.getTotalCurrent());
       SmartDashboard.putNumber("Current 15", pdp15);
       if(pdp15<ready_current&&pdp15!=0) ready = true;//if the current if below the ready_current then turn on the ready light
       else ready = false;
       SmartDashboard.putBoolean("Ready", ready);
       do1.set(ready);
       
    }
    
    public void disabledInit(){
    	SmartDashboard.putBoolean("Enabled", false);
    }
    
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
