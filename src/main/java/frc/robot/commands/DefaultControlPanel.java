/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

//Spin the control panel
public class DefaultControlPanel extends CommandBase {
  private final ControlPanel m_controlPanel;
  private final DoubleSupplier m_axis; 
  private final Timer debounceTimer = new Timer();

  /**
   * Creates a new ControlPanelRevolutions.
   */
  public DefaultControlPanel(ControlPanel controlPanel, DoubleSupplier axis) {
    super();

    m_controlPanel = controlPanel;
    m_axis = axis;
    addRequirements(controlPanel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_controlPanel.retract();
    m_controlPanel.setSpeed(0);
    debounceTimer.reset();
    debounceTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_axis.getAsDouble() != 0) {
      // if the control panel axis is nonzero
      debounceTimer.stop();
      debounceTimer.reset();

      m_controlPanel.extend();
      m_controlPanel.setSpeed(m_axis.getAsDouble());
    } else if (debounceTimer.get() < 1.0) {
      // if we are within the debounce window, stop the motor but do not retract yet
      // also, start the debounce timer if it is not running
      if(debounceTimer.get() == 0) debounceTimer.start();
      m_controlPanel.setSpeed(0);
    } else {
      // if the control panel is not being actuated, retract and stop the wheel
      m_controlPanel.retract();
      m_controlPanel.setSpeed(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_controlPanel.retract();
    m_controlPanel.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
