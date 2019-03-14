package frc4990.robot.commands;

import com.chopshop166.chopshoplib.commands.CommandChain;

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
			new CommandChain(
				new PrintCommand("Target found.  Placing..."),
				new LimelightCorrection(RobotMap.turret.findNearestTurretPoint()), 
				new manualIntakeSequence()),
			//on FALSE
			new CommandChain(
				new PrintCommand("Target NOT FOUND."),
				new InstantCommand(() -> CLimelight.setPipeline(Pipeline.Driver.get())))){
		
			@Override
			protected boolean condition() {
				return CLimelight.hasValidTarget();
			}
		});
	}
}
