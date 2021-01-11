/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * Have the robot drive tank style.
 */
public class TankDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final DoubleSupplier m_throttle;
  private final DoubleSupplier m_turn;
  private final DifferentialDriveKinematics m_kinematics;

  /**
   * Creates a new TankDrive command.
   *
   * @param throttle   The position of the throttle stick
   * @param turn       The position of the turning stick
   * @param drivetrain The drivetrain subsystem to drive
   * @see TankDrive
   */
  public TankDrive(DoubleSupplier throttle, DoubleSupplier turn, Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    m_throttle = throttle;
    m_turn = turn;
    m_kinematics = drivetrain.getKinematics();
    addRequirements(m_drivetrain);
  }

  /**
   * Called repeatedly to update the drivetrain using a vector of current controller inputs.
   */
  @Override
  public void execute() {
    m_drivetrain.driveClosedLoop(m_kinematics.toWheelSpeeds(
      new ChassisSpeeds(m_throttle.getAsDouble(), 0, m_turn.getAsDouble())
    ));
  }

  /**
   * As a default command, always returns false.
   * 
   * @see  edu.wpi.first.wpilibj2.command.Subsystem#setDefaultCommand
   */
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  /**
   * Ends any robot movement by stopping the drivetrain.
   * 
   */
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.stop();
  }
}
