package frc4990.robot.components;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import frc4990.robot.subsystems.Dashboard.FunctionalInterface;

public class SendableObject extends SendableBase {

	private FunctionalInterface supplier;
	private BooleanSupplier boolSupplier;
	private Boolean hasType = false;
	/**
	 * 
	 * @param data The Supplier<?> you want to store
	 * @author MajikalExplosions
	 */

	public SendableObject(FunctionalInterface data) {
		this.supplier = data;
	}

	public SendableObject(BooleanSupplier bool) {
		boolSupplier = bool;
		hasType = true;
	}

	public double getDouble() {
		return (double) supplier.get();
	}

	public boolean getBoolean() {
		return (boolean) supplier.get();
	}

	public java.lang.String getString() {
		return supplier.get().toString();
	}

	public double[] getDoubleArray() {
		return (double[]) supplier.get();
	}

	public boolean[] getBooleanArray() {
		return (boolean[]) supplier.get();
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		if(hasType) {
			try {
				builder.addBooleanProperty("value", boolSupplier, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
		try {
			if (supplier.get().getClass() == boolean.class) {
					builder.addBooleanProperty("value", this::getBoolean, null);
				} else if (supplier.get().getClass() == double.class) {
					builder.addDoubleProperty("value", this::getDouble, null);
				} else if (supplier.get().getClass() == "".getClass()) {
					builder.addStringProperty("value", this::getString, null);
				} else if (supplier.get().getClass() == boolean[].class) {
					builder.addBooleanArrayProperty("value", this::getBooleanArray, null);
				} else if (supplier.get().getClass() == double[].class) {
					builder.addDoubleArrayProperty("value", this::getDoubleArray, null);
				} else if (supplier.get().toString().getClass() == "".getClass()) {//catches most other things
					builder.addStringProperty("value", this::getString, null);
				} else {
					throw new IllegalArgumentException("Value of type " + supplier.getClass().getName() + " cannot be put into a SendableObject");
				}
				builder.setSmartDashboardType("RobotPreferences");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}