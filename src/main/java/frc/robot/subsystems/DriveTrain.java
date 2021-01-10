/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive; import edu.wpi.first.wpilibj2.command.SubsystemBase; import frc.robot.Constants; 
public class DriveTrain extends SubsystemBase {
  /**
   * Drive Train.
   */

  private CANSparkMax m_leftMotor;
  private CANSparkMax m_leftMotor_1;
  private CANSparkMax m_rightMotor;
  private CANSparkMax m_rightMotor_1;
  private DifferentialDrive m_drive;

  public DriveTrain() {
    super();

    m_leftMotor = new CANSparkMax(Constants.LEFT_MOTOR_ID, MotorType.kBrushless);
    m_leftMotor_1 = new CANSparkMax(Constants.LEFT_MOTOR_1_ID, MotorType.kBrushless);

    m_rightMotor = new CANSparkMax(Constants.RIGHT_MOTOR_ID, MotorType.kBrushless);
    m_rightMotor_1 = new CANSparkMax(Constants.RIGHT_MOTOR_1_ID, MotorType.kBrushless);

    m_drive = new DifferentialDrive(m_rightMotor, m_leftMotor);

    m_leftMotor.restoreFactoryDefaults();
    m_leftMotor.getEncoder(EncoderType.kHallSensor, 4096);
    m_leftMotor_1.restoreFactoryDefaults();
    m_leftMotor_1.getEncoder(EncoderType.kHallSensor, 4096);
    m_leftMotor_1.follow(m_leftMotor);

    m_rightMotor.restoreFactoryDefaults();
    m_rightMotor.getEncoder(EncoderType.kHallSensor, 4096);
    m_rightMotor_1.restoreFactoryDefaults();
    m_rightMotor_1.getEncoder(EncoderType.kHallSensor, 4096);
    m_rightMotor_1.follow(m_rightMotor);

  }

  public void drive(double throttle, double turn) {
    if (m_drive != null) {
      m_drive.arcadeDrive(throttle, turn);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
