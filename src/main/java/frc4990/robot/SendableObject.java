package frc4990.robot;

import frc4990.robot.SmartDashboardController.CFunctionalInterface;

public class SendableObject {

	public CFunctionalInterface c;
	/**
	 * 
	 * @param fi The CFunctionalInterface you want to store
	 * @author MajikalExplosions
	 */
	public SendableObject(CFunctionalInterface fi) {
		c = fi;
	}
}