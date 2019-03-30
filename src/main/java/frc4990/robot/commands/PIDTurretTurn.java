package frc4990.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.OI;
import frc4990.robot.RobotMap;
import frc4990.robot.subsystems.Turret.TurretPoint;

public class PIDTurretTurn extends Command {
	private double target;//in degrees
	private TurretPoint point;


	public PIDTurretTurn(TurretPoint point) {
		super("PIDTurretTurn", RobotMap.turret);
		target = RobotMap.turret.getTarget(point);
		this.point = point;
	}

	public void initialize() {
		System.out.println("Initalizing PIDTurretTurn with target " + point.toString() + " (" + target + ") , at: " + RobotMap.turretTalon.getPosition());
		RobotMap.turretTalon.set(ControlMode.MotionMagic, target);
		OI.ld.end();
	}

	public void execute() {}
	
	public void end() {
		RobotMap.turretTalon.set(ControlMode.PercentOutput, 0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return Math.abs(RobotMap.turretTalon.getPosition() - target) < RobotMap.TURRET_TURN_ACCURACY;
	}	
}