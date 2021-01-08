/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Elevator;


/**
 * Have the robot drive tank style.
 */
public class MoveElevator extends CommandBase {
  private final Elevator m_elevator;
  private final DoubleSupplier m_speed;
  public MoveElevator(DoubleSupplier speed, Elevator elevator) {
    m_elevator = elevator;
    m_speed = speed;
    addRequirements(m_elevator);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_elevator.run_elevator(m_speed.getAsDouble());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_elevator.run_elevator(0);
  }
}
