/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

/**
 * Spins the Control Panel to achieve Rotation Control.
 * <p>
 * See game manual section: 3.5.1 Control Panel
 */
public class DefaultControlPanel extends CommandBase {
  private final ControlPanel m_controlPanel;
  private final DoubleSupplier m_axis; 

  /**
   * Creates a new DefaultControlPanel.
   * 
   * @param controlPanel an instance of the control panel subsystem
   * @param axis the position of the left control stick
   * @see DefaultControlPanel
   */
  public DefaultControlPanel(ControlPanel controlPanel, DoubleSupplier axis) {
    super();

    m_controlPanel = controlPanel;
    m_axis = axis;
    addRequirements(controlPanel);
  }

  /**
   * Prepares to spin the Control Panel by extending upwards.
   * Called when the command is intially scheduled.
   */
  @Override
  public void initialize() {
    this.m_controlPanel.extend();
  }

  // Called every time the scheduler runs while the command is scheduled.
  /**
   * Spins the Control Panel engaging wheel.
   * Uses the left control stick to determine direction and speed.
   * Retracts and stops wheel if the left control stick is resting.
   */
  @Override
  public void execute() {
    if(m_axis.getAsDouble() != 0) {
      m_controlPanel.extend();
      m_controlPanel.setSpeed(m_axis.getAsDouble());
    } else {
      m_controlPanel.retract();
      m_controlPanel.setSpeed(0);
    }
  }

  /**
   * Retracts and stops wheel on command cancellation or interruption.
   * Cancellation and interruption have the same behavior.
   */
  @Override
  public void end(boolean interrupted) {
    m_controlPanel.retract();
    m_controlPanel.setSpeed(0);
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
}
