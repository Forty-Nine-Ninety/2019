package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.F310Gamepad;

/**
 * Press 'START' button on Drive or OP controller to see which is which
 * 
 * @author Benjamin and Class of '21 (created in 2018 season)
 */

public class ControllerCheck extends InstantCommand {
	
	private F310Gamepad gamepad;
	
	public ControllerCheck(F310Gamepad gamepad) {
		super();
    	this.gamepad = gamepad;
	}
	
	public void initialize() {
		if (gamepad.equals(RobotMap.driveGamepad)) {
    		System.out.println("Button 7 Pressed on DRIVE GAMEPAD");
    	} else if (gamepad.equals(RobotMap.opGamepad)) {
    		System.out.println("Button 7 Pressed on OP GAMEPAD");
    	}
	}
	
}
