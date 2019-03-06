package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.RobotMap;
import frc4990.robot.components.CLimelight;

public class PlaceHatchLimelight extends CommandGroup {

	public static final int HATCH_ACCURACY_THRESHOLD = 5;//Random arbitrary number.  Can be changed.

	public PlaceHatchLimelight() {
		if (! CLimelight.hasValidTarget()) return;
		addSequential(new LimelightCorrection(HATCH_ACCURACY_THRESHOLD, RobotMap.turret.findNearestTurretPoint(), 0.5));
		addSequential(RobotMap.turretPneumatic.toggleCommand());
		addSequential(RobotMap.hatchPneumatic.toggleCommand());
		addSequential(RobotMap.turretPneumatic.toggleCommand());
	}
}
