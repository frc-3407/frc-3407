package org.usfirst.frc.team3407.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	//These values are used to control the power to the individual motors so that arcade works correctly.
	double leftMotorConst = 1;
	double rightMotorConst = 0.8;//change this value for motor tuning must be < 1 and > 0
	
	public double conLimit(double num) {
		if(num>1) return 1;
		if(num<-1) return -1;
		return num;
	}

	public void ArcadeDrive(double moveValue, double rotateValue){
	    
	    double leftMotorSpeed;
	    double rightMotorSpeed;

	    moveValue = conLimit(moveValue);
	    rotateValue = conLimit(rotateValue);

	    if (moveValue > 0.0) {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = Math.max(moveValue, rotateValue);
	      } else {
	        leftMotorSpeed = Math.max(moveValue, -rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      }
	    } else {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      } else {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	      }
	    }

	    straight.setLeftRightMotorOutputs(leftMotorSpeed * leftMotorConst, rightMotorSpeed * rightMotorConst);
	}
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	//SpeedController scoop; //Controls the arm on the scoop pwm:2
	RobotDrive straight;
	Joystick xboxGreen;
	Victor feederRight;
	Victor feederLeft;
	Victor scoop;
    
	public void robotInit() {
		this.
		straight = new RobotDrive(0,1);
		straight.setExpiration(0.1);
		xboxGreen = new Joystick(0);
    	
    	feederRight = new Victor(2);
    	feederLeft = new Victor(3);
    	scoop = new Victor(4);
    }

    /**
     * This function is called periodically during autonomous
     */
    
    public void autonomousPeriodic() {
    	straight.setSafetyEnabled(true);
    	for(int i=0; i < 1; i++){
    		straight.arcadeDrive(-0.5, 0.5);
    		Timer.delay(0.005);
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    	
    	straight.setSafetyEnabled(true);
        
        while(isOperatorControl() && isEnabled()){
        	
        	//Sets axis for LT and RT to control forward speed
        	double speedForward = xboxGreen.getRawAxis(2);
        	double speedReverse = xboxGreen.getRawAxis(3);
        	
        	//Sets axis for scooper
        	double LY = xboxGreen.getRawAxis(1);
        	
        	//Sets axis for turning the robot
        	double turn = -xboxGreen.getRawAxis(4);
        	
        	
        	//dpad? maybe
        	//double dPad = xboxGreen.getRawAxis(5);
        	
        	//Sets the LB button
        	boolean feederUp = xboxGreen.getRawButton(4);
        	
        	//Sets the RB button
        	boolean feederDown = xboxGreen.getRawButton(5);
        	
        	//SmartDashboard.putNumber("Slider 0", xboxGreen.getRawAxis(5));
        	//Sets drive mode to arcade drive with speed being the forward speed and turn being rotation
        	if(speedForward > 0){
        		ArcadeDrive(speedForward, turn);
        	}
        	else if(speedReverse > 0){
        		ArcadeDrive(-speedReverse, turn);
        	}
        	else{
        		ArcadeDrive(0, turn);
        	}
        	
        	
        	//Rotate scooper to pick up and release the ball.
        	if (LY > 0) {
        		scoop.set(1);
        		
        	}
        	else if (LY < 0) {
        		scoop.set(-1);
        	}
        	        	
        	//Lifts or lowers the feeder to allow for ball pickup
        	if (feederUp) {
        		feederRight.set(1);
            	feederLeft.set(1);
        	}
        	else if (feederDown) {
        		feederRight.set(-1);
            	feederLeft.set(-1);
        	}
        	else {
        		feederRight.set(0);
            	feederLeft.set(0);
        	}
        	
        	//Delays robot in order to allow processes to execute
        	Timer.delay(0.005);
        
        	
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
