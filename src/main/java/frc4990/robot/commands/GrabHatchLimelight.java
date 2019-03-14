package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.RobotMap;
import frc4990.robot.components.CLimelight;

public class GrabHatchLimelight extends CommandGroup {

	public GrabHatchLimelight() {
		if (! CLimelight.hasValidTarget()) return;
		addSequential(new LimelightCorrection(RobotMap.turret.findNearestTurretPoint()));
		addSequential(new manualIntakeSequence());
	}
}
