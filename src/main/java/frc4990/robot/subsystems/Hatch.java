package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc4990.robot.Robot;
import frc4990.robot.RobotMap;
import frc4990.robot.ShuffleboardController;

public class Hatch extends Subsystem implements PIDSource, PIDOutput {
    
	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	/*
	private Pneumatic pneumatic;
	private TalonMotorController talon;
	*/
	private int mPosition = 0;
	private int mRate;
	private Counter mCounter;

    public Hatch(Counter counter) {
		super("Hatch");
		mCounter = counter;
	}
	
	public void resetCounter() {
		mPosition = 0;
		mRate = 0;
		mCounter.reset();
	}

    /**
	 * Configures the open-loop ramp rate of throttle output to the default value. As of 1/25/19, it's 0.3.
	 */
	public void configOpenloopRamp() {
        RobotMap.turretTalon.configOpenloopRamp(ShuffleboardController.getConst("Turret/rampDownTime", 0.3), 0);
    }
    
    @Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSourceType;
    }
    
    public InstantCommand togglePneumatic(Pneumatic pneumatic) { 
		return new InstantCommand("TogglePneumatic", this) {
			public void initialize() {
				pneumatic.toggle();
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

    public void setSpeed(double value) {
        RobotMap.turretTalon.set(value);
    }
    
    /**
	 * Returns raw average left/right encoder value, in unknown units. Use pidGet()
	 * to return distance in feet.
	 */

	public double getEncoderDistance() {
		return mPosition;
	}
	
	/**
	 * Returns raw average left/right encoder value, in unknown units. Use pidGet()
	 * to return distance in feet.
	 */

	public double getEncoderRate() {
		return mRate;
    }

	@Override
	protected void initDefaultCommand() {
		
    }
	
	@Override
    public void periodic() {
		mPosition += (mRate = (RobotMap.hatchMotor.getPower() > 0) ? mCounter.get() : -1 * mCounter.get());
		mCounter.reset();
	 }
}
