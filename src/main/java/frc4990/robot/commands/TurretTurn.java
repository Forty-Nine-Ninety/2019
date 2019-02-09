package frc4990.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;

public class TurretTurn extends Command {
	public enum TurretPoint {
		Forward,
		Left,
		Right,
		Back,
		Safe
	};

	private double speed;
	private double target;//in degrees
	private boolean isDone;

	public TurretTurn(double speed, TurretPoint point) {
		this.speed = speed;
		isDone = false;

		switch(point) {
			case Forward:
				target = 0;
				break;
			case Left:
				target = 270d * 4096d / 180d;
				break;
			case Right:
				target = 90d * 4096d / 180d;
				break;
			case Back:
				target = 180d * 4096d / 180d;
				break;
			case Safe:
				target = 45d * 4096d / 180d;
				break;
			default:
				break;
		}
	}

	public void initialize() {
		System.out.println("Initalizing TurretTurn");
		this.setName("Turret", "TurretTurn");
	}

	public void execute() {
		double eVal = RobotMap.turret.getEncoderDistance() % 4096;
		double cSpeed = speed;
		if (Math.abs(eVal - target) < 256) {
			cSpeed /= 2;
		}
		else if (Math.abs(eVal - target) < 128) {
			cSpeed /= 4;
		}
		else if (Math.abs(eVal - target) < 16) {
			cSpeed = 0;
			isDone = true;
		}
		else if (eVal > target) {
			cSpeed *= -1;
		}
		RobotMap.turretTalon.set(cSpeed);
	}
	
	public void end() {
		RobotMap.turretTalon.set(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return isDone ? true : this.isTimedOut();
	}
}
