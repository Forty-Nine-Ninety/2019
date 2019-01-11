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
		TeleopDriveTrainController.stickShapingMode = TeleopDriveTrainController.StickShapingMode.values()[(TeleopDriveTrainController.stickShapingMode.ordinal() + 1) % 3];
		System.out.println("StickShaping Method:" + TeleopDriveTrainController.stickShapingMode.toString());

	}
	
}