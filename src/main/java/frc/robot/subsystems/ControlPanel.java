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

/**
 * Provides an interface for the Control Panel manipulation system.
 */
public class ControlPanel extends SubsystemBase {

  private DoubleSolenoid controlSolenoid;

  private CANSparkMax spinnerMotor;

  /**
   * Creates a new ControlPanel, preparing its solenoid and motor.
   */
  public ControlPanel() {
    super();

    controlSolenoid = new DoubleSolenoid(Constants.CONTROL_SOLENOID_ID_FWD, Constants.CONTROL_SOLENOID_ID_REV);
    controlSolenoid.set(Value.kReverse);
    
    spinnerMotor = new CANSparkMax(Constants.SPINNER_MOTOR_ID, MotorType.kBrushed);
    spinnerMotor.restoreFactoryDefaults();
  }

  /**
   * Pushes the system upwards.
   */
  public void extend() {
    controlSolenoid.set(Value.kForward);
  }

  /**
   * Brings the system back near the frame.
   */
  public void retract() {
    controlSolenoid.set(Value.kReverse);
  }
  /**
   * Sets a new angular velocity to run the motor at.
   * Ranges from -1.0 to 1.0, with 0 being no rotation.
   * @param speed the intended direction (sign) and percent of maximum motor speed (0 to 1) 
   */
  public void setSpeed(double speed) {
    spinnerMotor.set(speed); // half power magic number
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
