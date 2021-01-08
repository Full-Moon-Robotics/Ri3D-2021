/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

//Spin the control panel
public class LowerControlPanel extends CommandBase {
  private final ControlPanel controlPanel;
  /**
   * Creates a new ControlPanelRevolutions.
   */
  public LowerControlPanel(ControlPanel controlPanel) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.controlPanel = controlPanel;
    addRequirements(controlPanel);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.controlPanel.retract();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.controlPanel.retract();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.controlPanel.retract();
    this.controlPanel.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
