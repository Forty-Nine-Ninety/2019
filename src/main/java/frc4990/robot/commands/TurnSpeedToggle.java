package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.subsystems.Dashboard;

/**
 * @author Benjamin and Class of '21 (created in 2018 season)
 *
 */

public class TurnSpeedToggle extends Command {
	public TurnSpeedToggle() {
	}
	
	public void initialize() {
		TeleopDriveTrainController.currentTurnSteepnessMultiplier = Dashboard.getConst("TurnSpeedToggle/lowTurnMultiplier",
				0.6);
		System.out.println("Turn Speed: " + TeleopDriveTrainController.currentTurnSteepnessMultiplier + "x");
	}
	
	public void end() {
		TeleopDriveTrainController.currentTurnSteepnessMultiplier = Dashboard.getConst("DriveDpiToggle/defaultTurnSpeedMultiplier",
				1.0);
		System.out.println("Turn Speed: " + TeleopDriveTrainController.currentTurnSteepnessMultiplier + "x");
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return false;
	}
}
