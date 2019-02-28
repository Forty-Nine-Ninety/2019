package frc4990.robot.commands;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Dashboard;
import frc4990.robot.subsystems.HatchClaw.HatchPosition;
public class ToggleHatchPneumatic extends InstantCommand {
	

	public ToggleHatchPneumatic() {
		requires(RobotMap.hatchClaw);
	}
	
	public void initialize() {
		RobotMap.hatchPneumatic.toggle(RobotMap.hatchPneumatic);
	}
}
