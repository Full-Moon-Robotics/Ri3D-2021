/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import frc.robot.Constants;

/**
 * Provides an interface for the drivetrain, which moves the robot.
 */
public class Drivetrain extends SubsystemBase {

  private AnalogGyro m_gyro = new AnalogGyro(0);

  private CANSparkMax m_leftMotor;
  private CANSparkMax m_leftMotor_1;
  private CANSparkMax m_rightMotor;
  private CANSparkMax m_rightMotor_1;

  private CANEncoder m_leftEncoder;
  private CANEncoder m_rightEncoder;

  private PIDController m_leftController;
  private PIDController m_rightController;

  private SimpleMotorFeedforward m_leftFF;
  private SimpleMotorFeedforward m_rightFF;

  private DifferentialDriveKinematics m_kinematics;

  private DifferentialDriveOdometry m_odometry;
  
  /**
   * Creates a new Drivetrain, configuring all involved motors and encoders.
   * 
   * @see Drivetrain
   */
  public Drivetrain() {
    super();

    m_leftMotor = new CANSparkMax(Constants.LEFT_MOTOR_ID, MotorType.kBrushless);
    m_leftMotor_1 = new CANSparkMax(Constants.LEFT_MOTOR_1_ID, MotorType.kBrushless);

    m_rightMotor = new CANSparkMax(Constants.RIGHT_MOTOR_ID, MotorType.kBrushless);
    m_rightMotor_1 = new CANSparkMax(Constants.RIGHT_MOTOR_1_ID, MotorType.kBrushless);

    m_leftMotor.restoreFactoryDefaults();
    m_leftMotor_1.restoreFactoryDefaults();

    m_leftMotor.setSmartCurrentLimit(Constants.DRIVE_MOTOR_CURRENT_LIMIT);
    m_leftMotor_1.setSmartCurrentLimit(Constants.DRIVE_MOTOR_CURRENT_LIMIT);

    m_leftMotor.setIdleMode(IdleMode.kBrake);
    m_leftMotor_1.setIdleMode(IdleMode.kBrake);

    m_leftMotor_1.follow(m_leftMotor);

    m_rightMotor.restoreFactoryDefaults();
    m_rightMotor_1.restoreFactoryDefaults();

    m_rightMotor.setSmartCurrentLimit(Constants.DRIVE_MOTOR_CURRENT_LIMIT);
    m_rightMotor_1.setSmartCurrentLimit(Constants.DRIVE_MOTOR_CURRENT_LIMIT);

    m_rightMotor.setIdleMode(IdleMode.kBrake);
    m_rightMotor_1.setIdleMode(IdleMode.kBrake);

    m_rightMotor.setInverted(true);

    m_rightMotor_1.follow(m_rightMotor);

    m_leftEncoder = m_leftMotor.getEncoder();
    m_rightEncoder = m_rightMotor.getEncoder();

    // set up encoder conversion factor
    double conversionFactor = Constants.DRIVE_GEAR_RATIO * 0.3239;

    m_leftEncoder.setVelocityConversionFactor(conversionFactor/60);
    m_leftEncoder.setPositionConversionFactor(conversionFactor);

    m_rightEncoder.setVelocityConversionFactor(conversionFactor/60);
    m_rightEncoder.setPositionConversionFactor(conversionFactor);
    
    m_leftController = new PIDController(0.4, 0, 0);
    m_rightController = new PIDController(0.4, 0, 0);
    
    m_leftFF = new SimpleMotorFeedforward(0.1765, 3.3, 0.341);
    m_rightFF = new SimpleMotorFeedforward(0.1835, 3.24, 0.3645);
    
    m_kinematics = new DifferentialDriveKinematics(Constants.DRIVE_TRACK_WIDTH);
    
    m_odometry = new DifferentialDriveOdometry(getGyroRotation());
  }

  /**
   * Drives the robot using commanded chassis speeds. Call repeatedly.
   */
  public void driveClosedLoop(DifferentialDriveWheelSpeeds speeds) {

    double left = speeds.leftMetersPerSecond;
    double right = speeds.rightMetersPerSecond;

    double leftVoltage = m_leftFF.calculate(left) + m_leftController.calculate(m_leftEncoder.getVelocity(), left);
    double rightVoltage = m_rightFF.calculate(right) + m_rightController.calculate(m_rightEncoder.getVelocity(), right);

    m_leftMotor.setVoltage(leftVoltage);
    m_rightMotor.setVoltage(rightVoltage);
  }

  /**
   * Stops all movement.
   */
  public void stop() {
    m_leftMotor.stopMotor();
    m_rightMotor.stopMotor();
  }

  /**
   * Gets the current posisition of the drivetrain.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Gets the drivetrain's kinematic model.
   */
  public DifferentialDriveKinematics getKinematics() {
    return m_kinematics;
  }

  /**
   * Resets the drivetrain's stored pose and encoder values.
   */
  public void resetPose() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);

    m_odometry.resetPosition(new Pose2d(), getGyroRotation());
  }

  private Rotation2d getGyroRotation() {
    return m_gyro.getRotation2d();//Rotation2d.fromDegrees(-m_gyroFilter.calculate(m_gyro.getAngle()));
  }

  /**
   * Updates position and odometry information every period.
   */
  @Override
  public void periodic() {
    // update the drivetrain's position estimate
    m_odometry.update(getGyroRotation(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());

    SmartDashboard.putNumber("left_enc", m_leftEncoder.getPosition());
    SmartDashboard.putNumber("right_enc", m_rightEncoder.getPosition());
    
    // publish debug odometry values
    SmartDashboard.putNumber("odometry_x", m_odometry.getPoseMeters().getX());
    SmartDashboard.putNumber("odometry_y", m_odometry.getPoseMeters().getY());
    SmartDashboard.putNumber("odometry_theta", m_odometry.getPoseMeters().getRotation().getDegrees());
  }
}
