package frc4990.robot.commands;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc4990.robot.subsystems.Pneumatic;

public class TogglePneumatic extends InstantCommand {

	Pneumatic subsystem;

	public TogglePneumatic(Pneumatic subsystem) {
		this.subsystem = subsystem;
	}

	public void initialize() {
		subsystem.togglePneumatics();
	}
}
