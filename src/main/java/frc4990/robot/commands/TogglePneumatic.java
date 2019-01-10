package frc4990.robot.commands;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.RobotMap;

public class TogglePneumatic extends InstantCommand {

	public void initialize() {
		RobotMap.pneumatic.togglePneumatics();
	}
}
