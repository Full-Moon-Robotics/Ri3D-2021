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

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  /**
   * Elevator.
   */
  
    private  CANSparkMax m_leftMotor;
    private  CANSparkMax m_rightMotor;

    private  DifferentialDrive m_elevator;
    
  public Elevator() {
    super();
    if(!SubsystemConstants.HAS_ELEVATOR){
      return;
    }
    m_leftMotor = new CANSparkMax(10, MotorType.kBrushless);

    m_rightMotor = new CANSparkMax(11, MotorType.kBrushless);
    
    m_elevator = new DifferentialDrive(m_leftMotor, m_rightMotor);

    m_leftMotor.restoreFactoryDefaults();
    m_leftMotor.getEncoder(EncoderType.kHallSensor, 4096);
    
    m_rightMotor.restoreFactoryDefaults();
    m_rightMotor.getEncoder(EncoderType.kHallSensor, 4096);

  }
  public void run_elevator(double speed){
    if(m_elevator != null){
      m_elevator.tankDrive(speed, speed);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
