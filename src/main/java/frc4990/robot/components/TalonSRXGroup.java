package frc4990.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class TalonSRXGroup extends SpeedControllerGroup {

    private ControlMode mode = ControlMode.PercentOutput;
    private final WPI_TalonSRX[] talons;

    public TalonSRXGroup(WPI_TalonSRX talon, WPI_TalonSRX... talons) {
        super(talon, talons);
        this.talons = new WPI_TalonSRX[talons.length + 1];
        this.talons[0] = talon;
        for (int i = 0; i < talons.length; i++) {
            this.talons[i + 1] = talons[i];
        }
    }

    public TalonSRXGroup(ControlMode mode, WPI_TalonSRX talon, WPI_TalonSRX... talons) {
        this(talon, talons);
        setControlMode(mode);
    }

    public void setControlMode(ControlMode mode) {
        this.mode = mode;
    }
    
    public ControlMode getControlMode() {
        return mode;
    }

    @Override
    public void set(double speed) {
        for (WPI_TalonSRX talon : talons) {
            talon.set(mode, super.getInverted() ? -speed : speed);
          }
    }
}