package griffin.util;

import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Home for constants and PID variables. Also acts as a configuration file for Griffin.
 * @author MajikalExplosions
 */
public class Constants {
    //TODO optimize these values?
    public static final int TALON_TIMEOUT = 5;
    public static final double TALON_PEAK_OUTPUT = 1.0d;
    public static final double TALON_CLOSED_LOOP_PEAK_OUTPUT = TALON_PEAK_OUTPUT;
    public static final double TALON_DEADBAND = 0.001d;
    public static final int TALON_INTEGRAL_ZONE = 50;
    public static final int TALON_ALLOWABLE_ERROR = 0;
    //TODO tune talon PID
    public static final double TALON_kP = 0.4;
    public static final double TALON_kI = 0;
    public static final double TALON_kD = 0.1;
    public static final double TALON_kF = 0.5;

    public static final TalonEncoderMode TALON_DEFAULT_SENSOR_MODE = TalonEncoderMode.Absolute;
    public static final PIDSourceType TALON_DEFAULT_PID_SOURCE_TYPE = PIDSourceType.kDisplacement;

    //Put custom constants and PID vars here, preferably with some form of organization.

    public Constants() {}
}