package frc4990.robot.components;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class TalonSRXGroup extends SpeedControllerGroup {

    private final WPI_TalonSRX[] talons;
    public ControlMode mode = ControlMode.PercentOutput;
    public Double coeff;

    public TalonSRXGroup(WPI_TalonSRX... talons) {
        super(talons[0], Arrays.copyOfRange(talons, 1, talons.length));
        this.talons = new WPI_TalonSRX[talons.length];
        for (int i = 0; i < talons.length; i++) {
            this.talons[i] = talons[i];
        }
    }

    public TalonSRXGroup(ControlMode mode, Double coeff, WPI_TalonSRX... talons) {
        this(talons);
        this.mode = mode;
        this.coeff = coeff;
    }

    @Override
    public void set(double speed) {
        for (WPI_TalonSRX talon : talons) {
            talon.set(mode, (super.getInverted() ? -speed : speed) * coeff);
          }
    }
}