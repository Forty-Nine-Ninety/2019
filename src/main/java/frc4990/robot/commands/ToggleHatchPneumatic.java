package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.RobotMap;

public class ToggleHatchPneumatic extends InstantCommand {
	

	public ToggleHatchPneumatic() {
		requires(RobotMap.hatchClaw);
	}
	
	public void initialize() {
		RobotMap.hatchPneumatic.toggle();
	}
}