package griffin.subsystems;

/**
 * Class that controls an individual talon with a magnetic encoder
 * 
 * @author MajikalExplosions
 */
public class MotorTalonEncoder extends MotorTalon {
    //TODO finish this class
    /**
     * Initializes the talon with a magnetic encoder
     * @param can CAN bus ID of Talon (0 to 63)
     */
    public MotorTalonEncoder(int can) {
        super(can);
    }

    public void configure(boolean inverted, boolean phase) {
        super.configure(inverted, phase);
    }
}