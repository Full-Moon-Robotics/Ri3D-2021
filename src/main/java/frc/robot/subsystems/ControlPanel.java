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
import edu.wpi.first.wpilibj.Solenoid;

public class ControlPanel extends SubsystemBase {

  private Solenoid m_Solenoid3;

  private CANSparkMax spinnerMotor;
  // Whatever sensor to detect color

  /**
   * Creates a new ControlPanel.
   */
  public ControlPanel() {
    super();

    m_Solenoid3 = new Solenoid(3);

    spinnerMotor = new CANSparkMax(13, MotorType.kBrushed);
    spinnerMotor.restoreFactoryDefaults();
  }

  public void stop() {
    if (spinnerMotor != null) {
      spinnerMotor.set(0);
    }
  }

  public void extend() {
    m_Solenoid3.set(true);
  }

  public void retract() {
    m_Solenoid3.set(false);
  }

  public void spin() {
    spinnerMotor.set(.5); // half power magic number
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
