/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.PowerCell;


/**
* Manipulate power cells manually 
*/
public class ManualPowerCell extends CommandBase {
  private final PowerCell m_powercell;
  private final DoubleSupplier m_inspeed;
  private final DoubleSupplier m_outspeed;
  public ManualPowerCell(DoubleSupplier inspeed, DoubleSupplier outspeed, PowerCell powerCell) {
    m_powercell = powerCell;
    m_inspeed = inspeed;
    m_outspeed = outspeed;
    addRequirements(m_powercell);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if (m_outspeed.getAsDouble() == 0){
      m_powercell.run_intake(m_inspeed.getAsDouble());
    }else if (m_inspeed.getAsDouble() == 0){
      m_powercell.run_output(m_outspeed.getAsDouble());
    }
    m_powercell.run_belt(Math.max(m_inspeed.getAsDouble(), m_outspeed.getAsDouble()));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_powercell.run_intake(0);
    m_powercell.run_output(0);
    m_powercell.run_belt(0);  
  }

}
