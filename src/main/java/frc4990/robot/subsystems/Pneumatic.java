package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatic extends Subsystem {

	public Solenoid solenoid;

	/**
	 * Creates a single solenoid.
	 * 
	 * @param pcm Port number PCM
	 * @param channel Port number for solenoid
	 * @author MajikalExplosions
	 */
	public Pneumatic(int pcm, int channel) {
		solenoid = (new Solenoid(pcm, channel));
		solenoid.set(false);
	}

	public void togglePneumatics() {
		solenoid.set(solenoid.get() ? false : true);
	}

	public void clearStickyFaults() {
		if (solenoid.isBlackListed()) solenoid.clearAllPCMStickyFaults();
	}

	public InstantCommand clearStickyFaults(Pneumatic subsystem) {
		return new InstantCommand("clearStickyFaults", () -> subsystem.clearStickyFaults());
	}

	@Override
	protected void initDefaultCommand() {}
}
