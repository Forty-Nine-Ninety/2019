package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.commands.TurretTurn.TurretPoint;
import frc4990.robot.components.CLimelight;
import frc4990.robot.subsystems.DriveTrain;

public class LimelightCorrection extends Command {

	private int accuracy;
	private double kP = -0.1, kPD = -0.1;
	private double speed;
	private TurretPoint target;

	public LimelightCorrection(int a, TurretPoint target, double s) {
		requires(RobotMap.driveTrain);
		accuracy = a;
		speed = s;
		//requires(RobotMap.driveTrain);
	}

	public void initialize() {}

	public void execute() {
		double hError = CLimelight.getCrosshairHorizontalOffset() * -1, dError = CLimelight.getCrosshairVerticalOffset() * -1;
		double speedL = speed, speedR = speed;
        if (hError > accuracy) {
            switch(target) {
				case Forward:
					speedL += dError * kPD;
					speedR -= dError * kPD;
				case Left:
					speedL += kP * hError;
					speedR -= kP * speedL;
					break;
				case Back:
					speedL -= dError * kPD;
					speedR += dError * kPD;
				case Right:
					speedL -= kP * hError;
					speedR += speedL;
					break;
				default:
					break;
			}
        }
        else if (hError < -1 * accuracy) {
            switch(target) {
				case Forward:
					speedL -= dError * kPD;
					speedR += dError * kPD;
				case Left:
					speedL -= kP * hError;
					speedR += kP * speedL;
					break;
				case Back:
					speedL += dError * kPD;
					speedR -= dError * kPD;
				case Right:
					speedL += kP * hError;
					speedR -= speedL;
					break;
				default:
					break;
			}
		}
		RobotMap.driveTrain.setSpeed(clamp(speedL, -1, 1), clamp(speedR, -1, 1));
	}
	
	public void end() {
		RobotMap.driveTrain.setSpeed(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return Math.abs(CLimelight.getCrosshairHorizontalOffset()) < accuracy;
	}

	private static double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}
}
