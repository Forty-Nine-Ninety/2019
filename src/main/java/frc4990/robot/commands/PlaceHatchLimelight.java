package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc4990.robot.RobotMap;
import frc4990.robot.commands.TurretTurn.TurretPoint;
import frc4990.robot.components.CLimelight;
import frc4990.robot.subsystems.HatchClaw.HatchPosition;

public class PlaceHatchLimelight extends CommandGroup {

	public static final int HATCH_ACCURACY_THRESHOLD = 5;//Random arbitrary number.  Can be changed.
	public static final int TURRET_ACCURACY_THRESHOLD = 15;

	public PlaceHatchLimelight() {
		if (! CLimelight.hasValidTarget()) return;
		int turnPosition = (int) ((double)RobotMap.turret.getEncoderDistance() % 4096d / 4096d * 180d);//in degrees
		TurretPoint target;
		if (turnPosition > 225 + TURRET_ACCURACY_THRESHOLD && turnPosition < 315 - TURRET_ACCURACY_THRESHOLD) {
			//cargo (l)
			addSequential(new TurretTurn(0.8, (target = TurretPoint.Left)));
		}
		else if (turnPosition > 45 + TURRET_ACCURACY_THRESHOLD && turnPosition < 135 - TURRET_ACCURACY_THRESHOLD) {
			//cargo (r)
			addSequential(new TurretTurn(0.8, (target = TurretPoint.Right)));
		}
		else if ((turnPosition > 315 + TURRET_ACCURACY_THRESHOLD && turnPosition <= 360) || (turnPosition > 0 && turnPosition <= 45 - TURRET_ACCURACY_THRESHOLD)) {
			//rocket (f)
			addSequential(new TurretTurn(0.8, (target = TurretPoint.Forward)));
		}
		else if (turnPosition > 135 + TURRET_ACCURACY_THRESHOLD && turnPosition < 225 - TURRET_ACCURACY_THRESHOLD) {
			//rocket (b)
			addSequential(new TurretTurn(0.8, (target = TurretPoint.Back)));
		}
		else {
			System.out.println("I'm sorry; I couldn't figure out which side you were trying to place the hatch on.");
			return;//driver, you done messed up.  You had 90 - 2 * TURRET_ACCURACY_THRESHOLD per side and you couldn't manage to stop the hatch in that spot even with the setpoints?
		}
		addSequential(new LimelightCorrection(HATCH_ACCURACY_THRESHOLD, target));
		addSequential(new ToggleHatchPneumatic());
		addSequential(new SetHatchPosition(HatchPosition.Relaxed, 0.2));
		addSequential(new ToggleHatchPneumatic());
	}
}
