package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.RobotMap;
import frc4990.robot.components.CLimelight;

public class GrabHatchLimelight extends CommandGroup {

	public GrabHatchLimelight() {
		System.out.println("Limelight looking for target...");
		if (! CLimelight.hasValidTarget()) return;
		System.out.println("Target found.  Placing...");
		addSequential(new LimelightCorrection(RobotMap.turret.findNearestTurretPoint()));
		addSequential(new manualIntakeSequence());
	}
}
