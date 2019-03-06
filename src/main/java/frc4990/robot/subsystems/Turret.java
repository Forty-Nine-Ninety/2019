package frc4990.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc4990.robot.OI;
import frc4990.robot.RobotMap;
import frc4990.robot.components.JoystickAnalogButton;
import frc4990.robot.components.TalonWithMagneticEncoder;

public class Turret extends Subsystem implements PIDSource, PIDOutput {
    
	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

	public enum TurretPoint { Forward(-3200), Left(-12000), Right(5600), Back(14500), Safe(0), BackLeft(-21000), BackRight(14500); 

		private int value;
    
		TurretPoint(int value) {
				this.value = value;
		}

		public int get() {
				return value;
		}}
	
	public Turret() {
    super("Turret");
		RobotMap.turretTalon.syncPosition();
		initalizeTurretPID();
	}
    
    @Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSourceType;
    }
    
    public InstantCommand setTurretSpeed(double speed) { 
		return new InstantCommand("SetTurretSpeed", this, () -> RobotMap.turret.setSpeed(speed));
	}

	public Command setTurretSpeed(JoystickAnalogButton axis) { 
		return new Command("setTurretSpeed", this) {
			protected void execute() {
				RobotMap.turret.setSpeed(0.4 * axis.getRawAxis() * axis.getRawAxis() * -Math.signum(axis.getRawAxis()));
			}

			@Override
			protected boolean isFinished() {
				return false;
			}
		};
	}
    
    /**
	 * Returns right encoder value, in feet.
	 */
	public double pidGet() {
		if (pidSourceType == PIDSourceType.kDisplacement) {
			return getEncoderDistance();
		} else {
			return getEncoderRate();
		}
    }

    @Override
    public void pidWrite(double output) {
        setSpeed(output);
	}
	/**
	 * Sets speed of turret motor.
	 * @param value speed to set, [-1 to 1]
	 */

    public void setSpeed(double value) {
		RobotMap.turretTalon.set(value);
    }
    
  /**
	 * Returns encoder value, in unknown units.
	 */

	public double getEncoderDistance() {
		return RobotMap.turretTalon.getPosition();
	}
	
	/**
	 * Returns encoder rate, in unknown units. 
	 */

	public double getEncoderRate() {
		return RobotMap.turretTalon.getRate();
    }

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(this.setTurretSpeed(OI.turretTurn));
	}
    
    @Override
		public void periodic() {}
		
	protected void initalizeTurretPID() {
		TalonWithMagneticEncoder talon = RobotMap.turretTalon;
		talon.configFactoryDefault();
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 5);
		talon.syncPosition();
		talon.setSensorPhase(true);
		talon.setInverted(false);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 5);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 10, 5);
		talon.configNominalOutputForward(0, 5);
		talon.configNominalOutputReverse(0, 5);
		talon.configPeakOutputForward(1, 5);
		talon.configPeakOutputReverse(-1, 5);
			
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

	public double getTarget(TurretPoint turretPoint) {
		switch(turretPoint) {
			case Back:
				return (RobotMap.turretTalon.getPosition() > TurretPoint.Forward.get()) ? 
					TurretPoint.BackRight.get() : TurretPoint.BackLeft.get(); 
			default:
				return turretPoint.get();
		}
	}

	public double getMidPoint(TurretPoint pointA, TurretPoint pointB) {
		return (pointA.get() + pointB.get())/2;
	}

	public TurretPoint findNearestTurretPoint() {
		double encoderPos = RobotMap.turretTalon.getPosition();
		if (encoderPos < getMidPoint(TurretPoint.BackLeft, TurretPoint.Left)) {
			return TurretPoint.BackLeft;
		} else if (encoderPos > getMidPoint(TurretPoint.BackLeft, TurretPoint.Left) && encoderPos < getMidPoint(TurretPoint.Left, TurretPoint.Forward)) {
			return TurretPoint.Left;
		} else if (encoderPos > getMidPoint(TurretPoint.Left, TurretPoint.Forward) && encoderPos < getMidPoint(TurretPoint.Right, TurretPoint.Forward)) {
			return TurretPoint.Forward;
		} else if (encoderPos > getMidPoint(TurretPoint.Right, TurretPoint.Forward) && encoderPos < getMidPoint(TurretPoint.Right, TurretPoint.BackRight)) {
			return TurretPoint.Right;
		} else if (encoderPos > getMidPoint(TurretPoint.BackRight, TurretPoint.Right)) {
			return TurretPoint.BackRight;
		} else {
			return TurretPoint.Safe;
		}
	}
}
