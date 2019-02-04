package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.StartCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc4990.robot.RobotMap;
import frc4990.robot.ShuffleboardController;
import frc4990.robot.commands.SetHatchPosition;

public class HatchClaw extends Subsystem implements PIDSource, PIDOutput {

	public static enum HatchPosition { Engaged, Relaxed }
	public HatchPosition hatchPosition = HatchPosition.Relaxed;
    
	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

	private int mPosition = 0;
	private int mRate;
	private Counter mCounter = new Counter();

	@Override
    public void periodic() {
		mPosition += (mRate = (RobotMap.hatchMotor.getPower() > 0) ? mCounter.get() : -1 * mCounter.get());
		mCounter.reset();
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
        RobotMap.turretTalon.configOpenloopRamp(ShuffleboardController.getConst("Hatch/rampDownTime", 0), 0);
    }
    
    @Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSourceType;
    }
    
    public StartCommand toggleMotor() { 
		return new StartCommand(new SetHatchPosition());
	}
    
    /**
	 * Returns right encoder value, in feet.
	 */
	public double pidGet() {
		if (pidSourceType == PIDSourceType.kDisplacement) return getEncoderDistance();
		else return getEncoderRate();
    }

    @Override
    public void pidWrite(double output) {
        setSpeed(output);
    }

    public void setSpeed(double value) {
        RobotMap.turretTalon.set(value);
    }
    
    /**
	 * Returns encoder value, in unknown units. 
	 */

	public double getEncoderDistance() {
		return mPosition;
	}
	
	/**
	 * Returns encoder rate, in unknown units.
	 */

	public double getEncoderRate() {
		return mRate;
    }

	@Override
	protected void initDefaultCommand() {}
}
