package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatic extends Subsystem {

	private Solenoid Solenoid;

	/**
	 * Should be self explanatory. Creates a double solenoid.
	 * 
	 * @param forward Port number for input
	 * @param reverse Port number for output
	 * @author MajikalExplosions
	 */
	public Pneumatic(int channel) {
		Solenoid = new Solenoid(0,channel);
		Solenoid.set(false);
	}

	public void togglePneumatics() {
		Solenoid.set(Solenoid.get() ? false : true);
		/*switch(doubleSolenoid.get()) {
			case kForward:
				doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
				break;
			case kReverse:
				doubleSolenoid.set(DoubleSolenoid.Value.kForward);
				break;
			case kOff:
				doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
				break;
		}*/
	}

	@Override
	protected void initDefaultCommand() {}
}
