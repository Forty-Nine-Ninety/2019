package griffin.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import griffin.util.Constants;

/**
 * Class that controls an individual talon
 * 
 * @author MajikalExplosions
 */
public class MotorTalon extends WPI_TalonSRX {

    /**
     * Initializes talon
     * @param can CAN bus ID of Talon (0 to 63)
     */
    public MotorTalon(int can) {
        super(can);
        configFactoryDefault();
    }
    //TODO make sure this comment is correct
    /**
     * Configures the talon with constants and parameters
     * @param inverted Whether the motor is inverted or not
     * @param phase Whether the output will be inverted or not
     */
    public void configure(boolean inverted, boolean phase) {
        setInverted(inverted);
        setSensorPhase(phase);

        configNeutralDeadband(Constants.TALON_DEADBAND, Constants.TALON_TIMEOUT);
        configPeakOutputForward(Constants.TALON_PEAK_OUTPUT, Constants.TALON_TIMEOUT);
        configPeakOutputForward(-1 * Constants.TALON_PEAK_OUTPUT, Constants.TALON_TIMEOUT);

        selectProfileSlot(0, 0);
		config_kP(0, Constants.TALON_kP, Constants.TALON_TIMEOUT); 
		config_kI(0, Constants.TALON_kI, Constants.TALON_TIMEOUT); 
		config_kD(0, Constants.TALON_kD, Constants.TALON_TIMEOUT); 
		config_kF(0, Constants.TALON_kF, Constants.TALON_TIMEOUT);
		config_IntegralZone(0, Constants.TALON_INTEGRAL_ZONE, Constants.TALON_TIMEOUT); 
		configClosedLoopPeakOutput(0, Constants.TALON_CLOSED_LOOP_PEAK_OUTPUT, Constants.TALON_TIMEOUT); 
		configAllowableClosedloopError(0, Constants.TALON_ALLOWABLE_ERROR, Constants.TALON_TIMEOUT);
    }
}