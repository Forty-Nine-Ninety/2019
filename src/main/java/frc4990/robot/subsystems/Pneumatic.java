package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatic extends Subsystem {

	private DoubleSolenoid doubleSolenoid;

	/**
	 * Should be self explanatory. Creates a double solenoid.
	 * 
	 * @param forward Port number for input
	 * @param reverse Port number for output
	 * @author MajikalExplosions
	 */
	public Pneumatic(int forward, int reverse) {
		doubleSolenoid = new DoubleSolenoid(forward, reverse);
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void togglePneumatics() {
		switch(doubleSolenoid.get()) {
			case kForward:
				doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
				break;
			case kReverse:
				doubleSolenoid.set(DoubleSolenoid.Value.kForward);
				break;
			case kOff:
				doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
				break;
		}
	}

	@Override
	protected void initDefaultCommand() {}
}
