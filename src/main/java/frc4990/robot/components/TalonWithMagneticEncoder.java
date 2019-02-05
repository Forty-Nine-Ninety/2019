package frc4990.robot.components;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class TalonWithMagneticEncoder extends WPI_TalonSRX implements PIDSource, Sendable {

    private int timeoutMs = 5;

    private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

    public enum SensorMode {PulseWidth(0), Quadrature(1);

        private int numVal;
    
        SensorMode(int numVal) {
            this.numVal = numVal;
        }
    
        public int get() {
            return numVal;
        }}
        
        /**
         * Initialize MagneticEncoder.
         * 
         * @param canID
         *            CAN bus ID of Talon with MagneticEncoder (0 to 63, set from the web dashboard)
         */
        public TalonWithMagneticEncoder(int CANID) {
            super(CANID);
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, timeoutMs); //Pulse-width
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 1, timeoutMs); //Quadrature
        }

       /**
       * Gets the current count. Returns the ErrorCode.
       *
       * @return the ErrorCode
       */
      public void setPosition(int sensorPosition) {
        setPosition(SensorMode.PulseWidth, sensorPosition);
        setPosition(SensorMode.Quadrature, sensorPosition);
      }

      /**
       * sets the current count. Returns the ErrorCode.
       *
       * @return the ErrorCode
       */
      public ErrorCode setPosition(SensorMode mode, int sensorPosition) {
        return setSelectedSensorPosition(sensorPosition, mode.get(), timeoutMs);
      }

       /**
       * Gets the current count. Returns the current count on the Encoder.
       *
       * @return Current count from the Encoder 
       */
      public int getPosition() {
        return getPosition(SensorMode.PulseWidth);
      }

      /**
       * Gets the current count. Returns the current count on the Encoder.
       *
       * @return Current count from the Encoder 
       */
      public int getPosition(SensorMode mode) {
        return getSelectedSensorPosition(mode.get());
      }
    
      /**
       * Reset the Encoder distance to zero. Resets the current count to zero on the encoder.
       */
      public void resetEncoder() {
        setSelectedSensorPosition(0, 0, timeoutMs);
        setSelectedSensorPosition(0, 1, timeoutMs);
      }

      /**
       * Sync the Encoder's Pulse Width (absolute) measurement with its Quadrature (not absolute) measurement.
       */
      public void syncPosition() {
        setSelectedSensorPosition(getSelectedSensorPosition(0), 1, timeoutMs);
      }
    
      /**
       * Get the current rate of the encoder. 
       *
       * @return The current rate of the encoder.
       */
      public double getRate(SensorMode mode) {
        return getSelectedSensorVelocity(mode.get());
      }

      /**
       * Get the current rate of the encoder. 
       *
       * @return The current rate of the encoder.
       */
      public double getRate() {
        return getRate(SensorMode.PulseWidth);
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
        builder.addDoubleProperty("PulseWidthAbsoluteSpeed", () -> getRate(SensorMode.PulseWidth), null);
        builder.addDoubleProperty("RelativeQuadratureSpeed", () -> getRate(SensorMode.Quadrature), null);
        builder.addDoubleProperty("PulseWidthAbsoluteDistance", () -> getPosition(SensorMode.PulseWidth), null);
        builder.addDoubleProperty("RelativeQuadratureDistance", () -> getPosition(SensorMode.Quadrature), null);
        super.initSendable(builder);
        builder.setSmartDashboardType("Encoder");
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