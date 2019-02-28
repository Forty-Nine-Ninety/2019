package frc4990.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;

public class PIDTurretTurn extends Command {
	public enum TurretPoint {
		Forward,
		Left,
		Right,
		Back,
		Safe
	};

	private double target;//in degrees
	private TurretPoint point;


	public PIDTurretTurn(TurretPoint point) {
		super("PIDTurretTurn", RobotMap.turret);
		target = getTarget(point);
		this.point = point;
	}

	public void initialize() {
		System.out.println("Initalizing PIDTurretTurn with target " + point.toString() + " (" + target + ") , at: " + RobotMap.turretTalon.getPosition());
		RobotMap.turretTalon.set(ControlMode.MotionMagic, target);
	}

	public void execute() {}
	
	public void end() {
		RobotMap.turretTalon.set(ControlMode.PercentOutput, 0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return Math.abs(RobotMap.turretTalon.getPosition() - target) < 20;
	}

	protected double getTarget(TurretPoint turretPoint) {
		switch(turretPoint) {
			case Forward:
				target = -3200;
				break;
			case Left:
				target = -12000; //270d * 4096d / 180d;
				break;
			case Right:
				target = 5600;//90d * 4096d / 180d;
				break;
			case Back:
				target = (RobotMap.turretTalon.getPosition() > -3200) ? 14500 : -21000; 
				break;
			case Safe:
				target = 0; 
				break;
			default:
				break;
		}
		return target;
	}	
}