package griffin.subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import griffin.util.TalonEncoderMode;

/**
 * Class that controls an individual talon with a magnetic encoder
 * 
 * @author MajikalExplosions
 */
public class MotorTalonEncoder extends MotorTalon implements PIDSource {
    //TODO finish this class
    /**
     * Initializes the talon with a magnetic encoder
     * @param can CAN bus ID of Talon (0 to 63)
     */
    public MotorTalonEncoder(int can) {
        super(can);

        //configSelectedFeedbackSensor((defaultSensorMode.get() == 0) ? FeedbackDevice.CTRE_MagEncoder_Absolute : FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.TALON_TIMEOUT_MS);
        //syncPosition();
    }

    public void configure(boolean inverted, boolean phase) {
        super.configure(inverted, phase);
    }



    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public double pidGet() {
        return 0;
    }
    
}