
package org.usfirst.frc.team3407.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	SpeedController scoop; //Controls the arm on the scoop pwm:2
	RobotDrive swagBot;
	Joystick joystick1;
	Joystick joystick2;
	Joystick armstick;
	
    public void robotInit() {
    	swagBot = new RobotDrive(0,1);
    	scoop = new Talon(2);
    	swagBot.setExpiration(0.1);
    	joystick1 = new Joystick(0);
    	joystick2 = new Joystick(1);
    	armstick = new Joystick(2);
    }

    /**
     * This function is called periodically during autonomous
     */
    
    public void autonomousPeriodic() {
    	swagBot.setSafetyEnabled(true);
    	for(int i=0; i < 1; i++){
    		swagBot.tankDrive(-0.5, 0.5);
    		Timer.delay(1.0);
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        swagBot.setSafetyEnabled(true);
        scoop.setSafetyEnabled(true);
        while(isOperatorControl() && isEnabled()){
        	double leftSpeed = joystick1.getY();
        	double rightSpeed = joystick2.getY();
        	if (leftSpeed>=0){
        		leftSpeed = leftSpeed * leftSpeed;
        	}
        	else if (leftSpeed<0){
        		leftSpeed = leftSpeed * leftSpeed;
        		leftSpeed = -1 * leftSpeed;
        	}
        	if (rightSpeed>=0){
        		rightSpeed = rightSpeed * rightSpeed;
        	}
        	else if (rightSpeed<0){
        		rightSpeed = rightSpeed * rightSpeed;
        		rightSpeed = -1 * rightSpeed;
        	}
        	swagBot.tankDrive(leftSpeed, rightSpeed);
        	scoop.set(armstick.getY());
        	Timer.delay(0.005);
        	
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
