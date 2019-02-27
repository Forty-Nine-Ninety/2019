package frc4990.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc4990.robot.RobotMap;
import frc4990.robot.components.TalonWithMagneticEncoder;

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
		talon.config_kF(0, 0.553, 5);
		talon.config_kP(0, 0.7, 5);
		talon.config_kI(0, 0.003, 5);
		talon.config_kD(0, 4, 5);

		/* Set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(1600, 5);
		talon.configMotionAcceleration(1800, 5);
		talon.configMotionSCurveStrength(2);

		/* misc other configs */
		talon.config_IntegralZone(0,80);
		talon.configAllowableClosedloopError(0, 15);

	}

	public void initialize() {
		System.out.println("Initalizing PIDTurretTurn with target " + target);
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
		return RobotMap.turretTalon.getClosedLoopError(0) < 20;
	}

	protected double returnPIDInput() {
		return RobotMap.turretTalon.getPosition();
	}

	protected void usePIDOutput(double output) {
		RobotMap.turret.setSpeed(-output);
	}

	protected double getTarget(TurretPoint turretPoint) {
		switch(turretPoint) {
			case Forward:
				target = -3200;//0;
				break;
			case Left:
				target = -12000; //270d * 4096d / 180d;
				break;
			case Right:
				target = 5600;//90d * 4096d / 180d;
				break;
			case Back:
				target = (RobotMap.turretTalon.getPosition() > -3200) ? 14500 : -21000; //180d * 4096d / 180d;
				break;
			case Safe:
				target = 0; //45d * 4096d / 180d;
				break;
			default:
				break;
		}
		return target;

		/* tested values:
		 * case Forward:
				target = 15000;//0;
				break;
			case Left:
				target = 7000; //270d * 4096d / 180d;
				break;
			case Right:
				target = 24000;//90d * 4096d / 180d;
				break;
			case Back:
				target = -2000; //180d * 4096d / 180d;
				break;
			case Safe:
				target = 45d * 4096d / 180d;
				break;
			default:
				break;
		 */
	}	
}