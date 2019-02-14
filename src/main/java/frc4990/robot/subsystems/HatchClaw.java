package frc4990.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.StartCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc4990.robot.RobotMap;
import frc4990.robot.commands.SetHatchPosition;

public class HatchClaw extends Subsystem implements PIDSource, PIDOutput {

	public static enum HatchPosition { Engaged, Relaxed }
	public HatchPosition hatchPosition = HatchPosition.Relaxed;
    
	public PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

	private int mPosition = 0;
	private int mRate;

	@Override
    public void periodic() {
		mPosition += (mRate = (RobotMap.hatchMotor.get() > 0) ? RobotMap.hatchMotorCounter.get() : -1 * RobotMap.hatchMotorCounter.get());
		RobotMap.hatchMotorCounter.reset();
	 }
	
	public static void resetCounter() {
		RobotMap.hatchClaw.mPosition = 0;
		RobotMap.hatchClaw.mRate = 0;
		RobotMap.hatchMotorCounter.reset();
	}

    /**
	 * Configures the open-loop ramp rate of throttle output to the default value.
	 */
	public void configOpenloopRamp() {
        RobotMap.turretTalon.configOpenloopRamp(Dashboard.getConst("Hatch/rampDownTime", 0), 0);
    }
    
    @Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.pidSourceType;
    }
    
    public static StartCommand toggleMotor() { 
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

    public static void setSpeed(double value) {
        RobotMap.turretTalon.set(value);
    }
    
    /**
	 * Returns encoder value, in unknown units. 
	 */

	public static double getEncoderDistance() {
		return RobotMap.hatchClaw.mPosition;
	}
	
	/**
	 * Returns encoder rate, in unknown units.
	 */

	public static double getEncoderRate() {
		return RobotMap.hatchClaw.mRate;
    }

	@Override
	protected void initDefaultCommand() {}
}
