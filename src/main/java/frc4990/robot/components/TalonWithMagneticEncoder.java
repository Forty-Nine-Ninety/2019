package frc4990.robot.components;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class TalonWithMagneticEncoder extends WPI_TalonSRX implements PIDSource, Sendable {

    private int timeoutMs = 3;

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
            syncPosition();
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
      public ErrorCode setPosition(SensorMode mode, int sensorPos) {
        return (mode.get() == 1) ? this.getSensorCollection().setQuadraturePosition(sensorPos, timeoutMs) : this.setSelectedSensorPosition(sensorPos, mode.get(), timeoutMs);
      }

       /**
       * Gets the current count. Returns the current count on the Encoder.
       *
       * @return Current count from the Encoder (PULSE WIDTH/Absolute)
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
        return (mode.get() == 1) ? this.getSensorCollection().getQuadraturePosition() : getSelectedSensorPosition();
      }
    
      /**
       * Reset the Encoder distance to zero. Resets the current count (pulse width and Quadrature) to zero on the encoder. 
       */
      public void resetEncoder() {
        setPosition(0);
      }

      /**
       * Sync the Encoder's Pulse Width (absolute) measurement with its Quadrature (relative) measurement.
       */
      public void syncPosition() {
        setPosition(SensorMode.Quadrature, getPosition(SensorMode.PulseWidth));
      }
    
      /**
       * Get the current rate of the encoder. 
       *
       * @return The current rate of the encoder.
       */
      public double getRate(SensorMode mode) {
        return (mode.get() == 1) ? this.getSensorCollection().getQuadratureVelocity() : getSelectedSensorVelocity();
      }

      /**
       * Get the current rate of the encoder. 
       *
       * @return The current rate of the encoder. (PULSE WIDTH/Absolute)
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
        builder.setSmartDashboardType(""); //to use read-only table view
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