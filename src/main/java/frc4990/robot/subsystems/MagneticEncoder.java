package frc4990.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class MagneticEncoder extends SensorCollection implements PIDSource, Sendable {

    private int timeoutMs = 25;
    //private BaseMotorController internalMotorController;

    private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

    private String name, subsystem;
    
        /**
         * Initialize MagneticEncoder.
         * 
         * @param canID
         *            CAN bus ID of Talon with MagneticEncoder (0 to 63, set from the web dashboard)
         */
        public MagneticEncoder(int canID) {
            super(new TalonMotorController(canID));
            //internalMotorController = new TalonMotorController(canID);
        }
        
        /**
         * Initialize MagneticEncoder.
         * 
         * @param motorController
         *            Talon or Victor with Magnetic Encoder
         */
        public MagneticEncoder(BaseMotorController motorController) {
            super(motorController);
            //internalMotorController = motorController;
        }
      /**
       * Gets the current count. Returns the current count on the Encoder.
       *
       * @return Current count from the Encoder 
       */
      public int get() {
        return this.getPulseWidthPosition();
      }
    
      /**
       * Reset the Encoder distance to zero. Resets the current count to zero on the encoder.
       */
      public void reset() {
        setPulseWidthPosition(0, timeoutMs);
        setQuadraturePosition(0, timeoutMs);
      }

      /**
       * Sync the Encoder's Pulse Width (absolute) measurement with its Quadrature (not absolute) measurement.
       */
      public void sync() {
        setQuadraturePosition(getPulseWidthPosition(), timeoutMs);
      }
    
      /**
       * Get the distance the robot has driven since the last reset
       *
       * @return The distance driven since the last reset
       */
      public double getDistance() {
        return get();
      }
    
      /**
       * Get the current rate of the encoder. 
       *
       * @return The current rate of the encoder.
       */
      public double getRate() {
        return this.getPulseWidthVelocity();
      }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        pidSourceType = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSourceType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSubsystem() {
        return subsystem;
    }

    @Override
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Encoder");
        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::getDistance, null);
    }

    @Override
    public double pidGet() {
        if (pidSourceType == PIDSourceType.kRate) {
            return this.getRate();
        } else {
            return this.getDistance();
        }
	}
    }