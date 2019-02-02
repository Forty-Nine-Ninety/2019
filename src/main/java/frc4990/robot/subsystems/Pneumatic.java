package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatic extends Subsystem {

	private Solenoid solenoid;

	/**
	 * Should be self explanatory. Creates a solenoid.
	 * 
	 * @param pcm Port number PCM
	 * @param channel Port number for solenoid
	 * @author MajikalExplosions
	 */
	public Pneumatic(int pcm, int channel) {
		solenoid = (new Solenoid(pcm, channel));
		solenoid.set(false);
	}

	public void toggle() {
		solenoid.set(! solenoid.get());
	}

	@Override
	protected void initDefaultCommand() {}
}
