package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.commands.TeleopDriveTrainController.StickShapingMode;

/**
 * Ported from old TeleopDriveController
 * @author benjamin and Class of '21 (created in 2019 season)
 *
 */

public class stickShapingToggle extends InstantCommand {

	public stickShapingToggle() {}
	
	public void initialize() {
		switch (TeleopDriveTrainController.stickShapingMode) {
			case NextThrottle:
				TeleopDriveTrainController.stickShapingMode = StickShapingMode.SquaredThrottle;
				break;
			case SquaredThrottle:
				TeleopDriveTrainController.stickShapingMode = StickShapingMode.DifferentialDrive;
				break;
			case DifferentialDrive:
				TeleopDriveTrainController.stickShapingMode = StickShapingMode.NextThrottle;
				break;
			default: 
				break;
		}

		System.out.println("StickShaping Method:" + TeleopDriveTrainController.stickShapingMode.toString());

	}
	
}