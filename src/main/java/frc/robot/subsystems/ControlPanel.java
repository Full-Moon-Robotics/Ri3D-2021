/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Solenoid;

public class ControlPanel extends SubsystemBase {

  private Solenoid controlSolenoid;

  private CANSparkMax spinnerMotor;

  /**
   * Creates a new ControlPanel.
   */
  public ControlPanel() {
    super();

    controlSolenoid = new Solenoid(Constants.CONTROL_SOLENOID_ID);

    spinnerMotor = new CANSparkMax(Constants.SPINNER_MOTOR_ID, MotorType.kBrushed);
    spinnerMotor.restoreFactoryDefaults();
  }

  public void extend() {
    controlSolenoid.set(true);
  }

  public void retract() {
    controlSolenoid.set(false);
  }

  public void setSpeed(double speed) {
    spinnerMotor.set(speed); // half power magic number
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
