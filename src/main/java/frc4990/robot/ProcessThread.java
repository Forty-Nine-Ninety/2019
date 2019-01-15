package frc4990.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ProcessThread extends Thread {
	
	private boolean runDashboardUpdate, runSensorReset;

	/**
	 * 
	 * @param dashboard Whether to update the dashboard
	 * @param sensor Whether to reset the sensors *once*
	 */
	public ProcessThread(boolean dashboard, boolean sensor) {
		runDashboardUpdate = dashboard;
		runSensorReset = sensor;
	}

	/**
	 * Resets sensors the next time it's run
	 */
	public synchronized void resetSensors() {
		runSensorReset = true;
	}

	public synchronized void toggleDashboardUpdate() {
		runDashboardUpdate = !runDashboardUpdate;
	}

	public void run() {
		while(true) {
			synchronized(this) { 
				if (runDashboardUpdate) 
					SmartDashboard.updateValues(); 
					SmartDashboardController.updateDashboard();
				}
			synchronized(this) { if (runSensorReset) { Robot.resetSensors(); runSensorReset = false; } }
		}
	}
}