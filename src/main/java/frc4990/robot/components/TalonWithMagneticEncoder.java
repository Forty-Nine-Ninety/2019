package frc4990.robot.components;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc4990.robot.RobotMap;

public class TalonWithMagneticEncoder extends WPI_TalonSRX implements PIDSource, Sendable {

    private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

 
/**
 * | Parameter            | Absolute Mode (SensorMode 0)      | Relative Mode (SensorMode 1)      |
 * | -------------------- | --------------------------------- | --------------------------------- |
 * | Update rate (period) | 4 ms                              | 100 us (microseconds?)            |
 * | Max RPM              | 7,500 RPM                         | 15,000 RPM                        |
 * | Accuracy             | 12 bits (4096 units per rotation) | 12 bits (4096 units per rotation) |
 * | Software API         | Select Pulse Width                | Select Quadrature                 |
 * (Both modes wrap from 4095 => 4096 => 4097 when increasing and 0 => -1 => -2 when decreasing)
*/
    public enum SensorMode {
      Absolute(0), 
      Relative(1);

        private int numVal;
    
        SensorMode(int numVal) {
            this.numVal = numVal;
        }
    
        public int get() {
            return numVal;
        }}

    public SensorMode defaultSensorMode = SensorMode.Absolute;
        
        /**
         * Initialize MagneticEncoder.
         * 
         * @param canID
         *            CAN bus ID of Talon with MagneticEncoder (0 to 63, set from the web dashboard)
         */
        public TalonWithMagneticEncoder(int CANID) {
            super(CANID);
            configSelectedFeedbackSensor((defaultSensorMode.get() == 0) ? FeedbackDevice.CTRE_MagEncoder_Absolute : 
              FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.TALON_TIMEOUT_MS);
            syncPosition();
        }

         /**
         * Initialize MagneticEncoder.
         * 
         * @param canID
         *            CAN bus ID of Talon with MagneticEncoder (0 to 63, set from the web dashboard)
         */
        public TalonWithMagneticEncoder(int CANID, SensorMode defaultSensorMode) {
          this(CANID);
          this.defaultSensorMode = defaultSensorMode;
      }

       /**
       * Gets the current count. Returns the ErrorCode.
       *
       * @return the ErrorCode
       */
      public void setPosition(int sensorPosition) {
        setPosition(SensorMode.Absolute, sensorPosition);
        setPosition(SensorMode.Relative, sensorPosition);
      }

      /**
       * sets the current count. Returns the ErrorCode.
       *
       * @return the ErrorCode
       */
      public ErrorCode setPosition(SensorMode mode, int sensorPos) {
        return (mode == defaultSensorMode) ?  this.setSelectedSensorPosition(sensorPos, 0, RobotMap.TALON_TIMEOUT_MS) : 
          (mode == SensorMode.Absolute) ? this.getSensorCollection().setPulseWidthPosition(sensorPos, RobotMap.TALON_TIMEOUT_MS) : 
          this.getSensorCollection().setQuadraturePosition(sensorPos, RobotMap.TALON_TIMEOUT_MS);
      }

       /**
       * Gets the current count. Returns the current count on the Encoder.
       *
       * @return Current count from the Encoder (PULSE WIDTH/Absolute)
       */
      public int getPosition() {
        return getPosition(defaultSensorMode);
      }

      /**
       * Gets the current count. Returns the current count on the Encoder.
       *
       * @return Current count from the Encoder 
       */
      public int getPosition(SensorMode mode) {
        return (mode == defaultSensorMode) ?  this.getSelectedSensorPosition() : 
        (mode == SensorMode.Absolute) ? this.getSensorCollection().getPulseWidthPosition() : 
        this.getSensorCollection().getQuadraturePosition();
      }
    
      /**
       * Reset the Encoder distance to zero. Resets the current count (pulse width and Relative) to zero on the encoder. 
       */
      public void resetEncoder() {
        setPosition(0);
      }

      /**
       * Sync the Encoder's Pulse Width (absolute) measurement with its Relative (relative) measurement.
       */
      public void syncPosition() {
        setPosition(SensorMode.Relative, getPosition(SensorMode.Absolute));
      }
    
      /**
       * Get the current rate of the encoder. 
       *
       * @return The current rate of the encoder.
       */
      public double getRate(SensorMode mode) {
        return (mode == defaultSensorMode) ?  this.getSelectedSensorVelocity() : 
        (mode == SensorMode.Absolute) ? this.getSensorCollection().getPulseWidthVelocity() : 
        this.getSensorCollection().getQuadratureVelocity();
      }

      /**
       * Get the current rate of the encoder. 
       *
       * @return The current rate of the encoder. (PULSE WIDTH/Absolute)
       */
      public double getRate() {
        return getRate(defaultSensorMode);
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
    public void initSendable(SendableBuilder builder) {
      builder.addDoubleProperty("Encoder Speed", this::getRate, null);
      builder.addDoubleProperty("Encoder Position", this::getPosition, (double position) -> this.setPosition((int) position));
      builder.addDoubleProperty("Value", this::get, this::set);
      builder.setSmartDashboardType("");
      builder.setSafeState(this::stopMotor);
    }

    @Override
    public double pidGet() {
        if (pidSourceType == PIDSourceType.kRate) {
            return getRate();
        } else {
            return getPosition();
        }
	  }
}