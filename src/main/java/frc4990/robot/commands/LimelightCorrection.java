package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.commands.TurretTurn.TurretPoint;
import frc4990.robot.components.CLimelight;
import frc4990.robot.subsystems.DriveTrain;

public class LimelightCorrection extends Command {

	private int accuracy;
	private double kP = 0.1;
	private TurretPoint target;

	public LimelightCorrection(int a, TurretPoint target) {
		requires(RobotMap.driveTrain);
		accuracy = a;
		//requires(RobotMap.driveTrain);
	}

	public void initialize() {}

	public void execute() {
		double error = CLimelight.getCrosshairHorizontalOffset();
		double speed = 0;
		//TODO add forward and back stuff (see http://docs.limelightvision.io/en/latest/cs_aimandrange.html)
        if (error > accuracy) {
            switch(target) {
				case Left:
					speed += kP * error;
					break;
				case Right:
					speed -= kP * error;
					break;
				default:
					break;
			}
        }
        else if (error < -1 * accuracy) {
            switch(target) {
				case Left:
					speed -= kP * error;
					break;
				case Right:
					speed += kP * error;
					break;
				default:
					break;
			}
		}
		RobotMap.driveTrain.setSpeed(speed);
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

}
