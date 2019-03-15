package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.PrintCommand;
import frc4990.robot.RobotMap;
import frc4990.robot.components.CLimelight;
import frc4990.robot.components.CLimelight.Pipeline;

public class GrabHatchLimelight extends CommandGroup {

	public GrabHatchLimelight() {
		addSequential(new InstantCommand(() -> CLimelight.setPipeline(Pipeline.Vision.get())));
		addSequential(new PrintCommand("Limelight looking for target..."));
		addSequential(new ConditionalCommand(
			//on TRUE
			new CommandGroup() {{
				addSequential(new PrintCommand("Target found.  Placing..."));
				addSequential(new LimelightCorrection(RobotMap.turret.findNearestTurretPoint())); 
				addSequential(new manualIntakeSequence());
			}},
			//on FALSE
			new CommandGroup() {{
				addSequential(new PrintCommand("Target NOT FOUND."));
				addSequential(new InstantCommand(() -> CLimelight.setPipeline(Pipeline.Driver.get())));
			}}) {
				@Override
				protected boolean condition() {
					return CLimelight.hasValidTarget();
				}
		});
	}
}
