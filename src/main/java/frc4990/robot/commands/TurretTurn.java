package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.F310Gamepad;

/**
 * Press 'START' button on Drive or OP controller to see which is which
 * 
 * @author Benjamin and Class of '21 (created in 2018 season)
 */

public class TurretTurn extends InstantCommand {
	
	
	public TurretTurn() {
		super();
	}
	
	public void initialize() {
		this.start();
	}
	
	
}
