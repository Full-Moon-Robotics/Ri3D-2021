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
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ControlPanel extends SubsystemBase {

  private DoubleSolenoid controlSolenoid;

  private CANSparkMax spinnerMotor;

  /**
   * Creates a new ControlPanel.
   */
  public ControlPanel() {
    super();

    controlSolenoid = new DoubleSolenoid(Constants.CONTROL_SOLENOID_ID_FWD, Constants.CONTROL_SOLENOID_ID_REV);
    controlSolenoid.set(Value.kReverse);
    
    spinnerMotor = new CANSparkMax(Constants.SPINNER_MOTOR_ID, MotorType.kBrushed);
    spinnerMotor.restoreFactoryDefaults();
  }

  public void extend() {
    controlSolenoid.set(Value.kForward);
  }

  public void retract() {
    controlSolenoid.set(Value.kReverse);
  }

  public void setSpeed(double speed) {
    spinnerMotor.set(speed); // half power magic number
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
