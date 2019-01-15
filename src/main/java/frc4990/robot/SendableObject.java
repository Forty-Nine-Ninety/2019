package frc4990.robot;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SendableObject extends SendableBase {

	public Supplier<?> data;
	/**
	 * 
	 * @param data The Supplier<?> you want to store
	 * @author MajikalExplosions
	 */
	public SendableObject(Supplier<?> data) {
		if (NetworkTableEntry.isValidDataType(data)) this.data = data;
		else throw new IllegalArgumentException("Value of type " + data.getClass().getName() + " cannot be put into a SendableObject");
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		if (data instanceof BooleanSupplier) {
			builder.addBooleanProperty("value", (BooleanSupplier) data, null);
		  } else if (data instanceof Number) {
			builder.addDoubleProperty("value", (DoubleSupplier) data, null);
		  } else if (data instanceof String) {
			builder.addStringProperty("value", (Supplier<String>) data, null);
		  } else if (data instanceof Boolean[])  {
			builder.addBooleanArrayProperty("value", (Supplier<boolean[]>) data, null);
		  } else if (data instanceof Number[])  {
			builder.addDoubleArrayProperty("value", (Supplier<double[]>) data, null);
		  } else if (data instanceof String[])  {
			builder.addStringArrayProperty("value", (Supplier<String[]>) data, null);
		  }
	}
}