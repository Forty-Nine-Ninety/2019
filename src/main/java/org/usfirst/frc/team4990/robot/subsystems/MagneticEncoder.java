package org.usfirst.frc.team4990.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
public class MagneticEncoder extends Encoder {

private SensorCollection sensorCollection;
private double timeoutMs = 25.0;

	/**
	 * Initialize MagneticEncoder.
	 * 
	 * @param canID
	 *            CAN bus ID of Talon with MagneticEncoder (0 to 63, set from the web dashboard)
	 */
	public MagneticEncoder(int canID) {
		sensorCollection = new SensorCollection(new Talon(canID));
	}
	
	/**
	 * Initialize MagneticEncoder.
	 * 
	 * @param motorController
	 *            Talon or Victor with Magnetic Encoder
	 */
	public MagneticEncoder(BaseMotorController motorController) {
		sensorCollection = new SensorCollection(motorController);
	}
	
	/**
   * Gets the raw value from the encoder. The raw value is the actual count unscaled by the 1x, 2x,
   * or 4x scale factor.
   *
   * @return Current raw count from the encoder
   */
  public int getRaw() {
    return get();
  }

  /**
   * Gets the current count. Returns the current count on the Encoder.
   *
   * @return Current count from the Encoder 
   */
  @Override
  public int get() {
    return sensorCollection.getPulseWidthPosition();
  }

  /**
   * Reset the Encoder distance to zero. Resets the current count to zero on the encoder.
   */
  @Override
  public void reset() {
    sensorCollection.setPulseWidthPosition(0, timeoutMs);
  }

  /**
   * Get the distance the robot has driven since the last reset as scaled by the value from {@link
   * #setDistancePerPulse(double)}.
   *
   * @return The distance driven since the last reset
   */
  public double getDistance() {
    return EncoderJNI.getEncoderDistance(m_encoder);
  }

  /**
   * Get the current rate of the encoder. Units are distance per second as scaled by the value from
   * setDistancePerPulse().
   *
   * @return The current rate of the encoder.
   */
  public double getRate() {
    return sensorCollection.getPulseWidthVelocity();
  }

  /**
   * Set the minimum rate of the device before the hardware reports it stopped.
   *
   * @param minRate The minimum rate. The units are in distance per second as scaled by the value
   *                from setDistancePerPulse().
   */
  public void setMinRate(double minRate) {
    EncoderJNI.setEncoderMinRate(m_encoder, minRate);
  }

  /**
   * Set the distance per pulse for this encoder. This sets the multiplier used to determine the
   * distance driven based on the count value from the encoder. Do not include the decoding type in
   * this scale. The library already compensates for the decoding type. Set this value based on the
   * encoder's rated Pulses per Revolution and factor in gearing reductions following the encoder
   * shaft. This distance can be in any units you like, linear or angular.
   *
   * @param distancePerPulse The scale factor that will be used to convert pulses to useful units.
   */
  public void setDistancePerPulse(double distancePerPulse) {
    EncoderJNI.setEncoderDistancePerPulse(m_encoder, distancePerPulse);
  }

  /**
   * Get the distance per pulse for this encoder.
   *
   * @return The scale factor that will be used to convert pulses to useful units.
   */
  public double getDistancePerPulse() {
    return EncoderJNI.getEncoderDistancePerPulse(m_encoder);
  }

  /**
   * Set the direction sensing for this encoder. This sets the direction sensing on the encoder so
   * that it could count in the correct software direction regardless of the mounting.
   *
   * @param reverseDirection true if the encoder direction should be reversed
   */
  public void setReverseDirection(boolean reverseDirection) {
    EncoderJNI.setEncoderReverseDirection(m_encoder, reverseDirection);
  }

  /**
   * Set the Samples to Average which specifies the number of samples of the timer to average when
   * calculating the period. Perform averaging to account for mechanical imperfections or as
   * oversampling to increase resolution.
   *
   * @param samplesToAverage The number of samples to average from 1 to 127.
   */
  public void setSamplesToAverage(int samplesToAverage) {
    EncoderJNI.setEncoderSamplesToAverage(m_encoder, samplesToAverage);
  }

  /**
   * Get the Samples to Average which specifies the number of samples of the timer to average when
   * calculating the period. Perform averaging to account for mechanical imperfections or as
   * oversampling to increase resolution.
   *
   * @return SamplesToAverage The number of samples being averaged (from 1 to 127)
   */
  public int getSamplesToAverage() {
    return EncoderJNI.getEncoderSamplesToAverage(m_encoder);
  }

  /**
   * Set which parameter of the encoder you are using as a process control variable. The encoder
   * class supports the rate and distance parameters.
   *
   * @param pidSource An enum to select the parameter.
   */
  @Override
  public void setPIDSourceType(PIDSourceType pidSource) {
    m_pidSource = pidSource;
  }

  @Override
  public PIDSourceType getPIDSourceType() {
    return m_pidSource;
  }

  /**
   * Implement the PIDSource interface.
   *
   * @return The current value of the selected source parameter.
   */
  @Override
  public double pidGet() {
    switch (m_pidSource) {
      case kDisplacement:
        return getDistance();
      case kRate:
        return getRate();
      default:
        return 0.0;
    }
  }

  /**
   * Set the index source for the encoder. When this source is activated, the encoder count
   * automatically resets.
   *
   * @param channel A DIO channel to set as the encoder index
   */
  public void setIndexSource(int channel) {
    setIndexSource(channel, IndexingType.kResetOnRisingEdge);
  }

  /**
   * Set the index source for the encoder. When this source is activated, the encoder count
   * automatically resets.
   *
   * @param source A digital source to set as the encoder index
   */
  public void setIndexSource(DigitalSource source) {
    setIndexSource(source, IndexingType.kResetOnRisingEdge);
  }

  /**
   * Set the index source for the encoder. When this source rises, the encoder count automatically
   * resets.
   *
   * @param channel A DIO channel to set as the encoder index
   * @param type    The state that will cause the encoder to reset
   */
  public void setIndexSource(int channel, IndexingType type) {
    if (m_allocatedI) {
      throw new AllocationException("Digital Input for Indexing already allocated");
    }
    m_indexSource = new DigitalInput(channel);
    m_allocatedI = true;
    addChild(m_indexSource);
    setIndexSource(m_indexSource, type);
  }

  /**
   * Set the index source for the encoder. When this source rises, the encoder count automatically
   * resets.
   *
   * @param source A digital source to set as the encoder index
   * @param type   The state that will cause the encoder to reset
   */
  public void setIndexSource(DigitalSource source, IndexingType type) {
    EncoderJNI.setEncoderIndexSource(m_encoder, source.getPortHandleForRouting(),
        source.getAnalogTriggerTypeForRouting(), type.value);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    if (EncoderJNI.getEncoderEncodingType(m_encoder) == EncodingType.k4X.value) {
      builder.setSmartDashboardType("Quadrature Encoder");
    } else {
      builder.setSmartDashboardType("Encoder");
    }

    builder.addDoubleProperty("Speed", this::getRate, null);
    builder.addDoubleProperty("Distance", this::getDistance, null);
    builder.addDoubleProperty("Distance per Tick", this::getDistancePerPulse, null);
  }
}