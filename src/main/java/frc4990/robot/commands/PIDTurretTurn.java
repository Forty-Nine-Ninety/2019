package frc4990.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.components.TalonWithMagneticEncoder;
import frc4990.robot.subsystems.Dashboard;

public class PIDTurretTurn extends Command {
	public enum TurretPoint {
		Forward,
		Left,
		Right,
		Back,
		Safe
	};

	private double target;//in degrees


	public PIDTurretTurn(double speed, TurretPoint point) {
		super("PIDTurretTurn", RobotMap.turret);
		target = getTarget(point);
		TalonWithMagneticEncoder talon = RobotMap.turretTalon;
		
		/* Set Motion Magic gains in slot0 - see documentation */
		talon.selectProfileSlot(0, 0);
		talon.config_kF(0, 0, 5);
		talon.config_kP(0, Dashboard.getConst("PIDTurretTurn/p", 0.3), 5);
		talon.config_kI(0, Dashboard.getConst("PIDTurretTurn/i", 0), 5);
		talon.config_kD(0, Dashboard.getConst("PIDTurretTurn/d", 0), 5);

		/* Set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(150, 5);
		talon.configMotionAcceleration(600, 5);

		
	}

	public void initialize() {
		System.out.println("Initalizing PIDTurretTurn");
		this.setSubsystem("Turret");
		RobotMap.turretTalon.set(ControlMode.MotionMagic, target);
	}

	public void execute() {
		//Dashboard.putConst("PIDTurretTurn/error", this.getPIDController().getError());
	}
	
	public void end() {
		RobotMap.turretTalon.set(0);
	}
	
	public void interrupted() {
		end();
	}
	
	public boolean isFinished() {
		return RobotMap.turretTalon.getClosedLoopError(0) < RobotMap.turretTalon.getClosedLoopTarget(0);
	}

	protected double returnPIDInput() {
		return RobotMap.turretTalon.getPosition();
	}

	protected void usePIDOutput(double output) {
		RobotMap.turret.setSpeed(-output);
	}

	protected double getTarget(TurretPoint turretPoint) {
		double n = 0;
		switch(turretPoint) {
			case Forward:
				n = 0;
				break;
			case Left:
				n = 270d * 4096d / 180d;
				break;
			case Right:
				n = 90d * 4096d / 180d;
				break;
			case Back:
				n = 180d * 4096d / 180d;
				break;
			case Safe:
				n = 45d * 4096d / 180d;
				break;
			default:
				n = 0;
		}
		return n;
	}	
}