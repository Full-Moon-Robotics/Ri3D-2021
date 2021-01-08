/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorDisplay;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.ColorDisplay.ControlPanelWedge;

//Spin the control panel to a specific color
public class ControlPanelPosition extends CommandBase {
  ControlPanel controlPanel;
  ColorDisplay colorDisplay;
  Vision vision;
  boolean finished = false;
  /**
   * Creates a new ControlPanelPosition.
   */
  public ControlPanelPosition(ControlPanel controlPanel, ColorDisplay colorDisplay, Vision vision) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.vision = vision;
    this.controlPanel = controlPanel;
    this.colorDisplay = colorDisplay;
    addRequirements(this.controlPanel, this.colorDisplay);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.controlPanel.extend();
    ControlPanelWedge color = colorDisplay.getFMSColor();
    if(color == null){
      //Not ready!
      finished = true;
      DriverStation.reportWarning("Position Not Ready", false);
      return;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(vision.color[3] != null) {
    if(ControlPanelWedge.values()[vision.color[3]].next().next().equals(colorDisplay.getFMSColor())){
      finished = true;
    } else{
      controlPanel.spin();
    }
  }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    controlPanel.stop();
    this.controlPanel.retract();
    DriverStation.reportWarning("Position Done", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
