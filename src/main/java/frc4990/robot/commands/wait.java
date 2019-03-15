package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class wait extends TimedCommand {
	protected Double t;
	/**
	 * Makes robot wait.
	 * @param t Time to wait for in seconds
	 */
	public wait(double t) {
		super(t);
		this.t = t;
	}

	@Override
	public void initialize() {
		System.out.println("wait(" + t + " sec)");
	}
}
