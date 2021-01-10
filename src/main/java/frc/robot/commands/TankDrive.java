/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveTrain;

/**
 * Have the robot drive tank style.
 */
public class TankDrive extends CommandBase {
  private final DriveTrain m_drivetrain;
  private final DoubleSupplier m_throttle;
  private final DoubleSupplier m_turn;

  /**
   * Creates a new TankDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param drivetrain The drivetrain subsystem to drive
   */
  public TankDrive(DoubleSupplier throttle, DoubleSupplier turn, DriveTrain drivetrain) {
    m_drivetrain = drivetrain;
    m_throttle = throttle;
    m_turn = turn;
    addRequirements(m_drivetrain);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_drivetrain.drive(m_throttle.getAsDouble(), m_turn.getAsDouble());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.drive(0, 0);
  }
}
