/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//wpilibj.buttons
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.commands.TankDrive;
import frc.robot.commands.ControlPanelRevolutions;
import frc.robot.commands.ManualPowerCell;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PowerCell;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  final DriveTrain m_drivetrain = new DriveTrain();
  final PowerCell m_powercell = new PowerCell();
  final ControlPanel m_controlPanel = new ControlPanel();
  private Compressor m_compressor;

  // Configure the button bindings
  final Joystick controller = new Joystick(0);
  final DoubleSupplier leftsupply = () -> controller.getRawAxis(1);
  final DoubleSupplier rightsupply = () -> controller.getRawAxis(5);

  final DoubleSupplier controlPanelSupplier = () -> controller.getRawAxis(0); 

  final DoubleSupplier intakeSuppier = () -> {
    if (controller.getRawButton(3)) { // Hold triangle for elevator
      return 0;
    } else {
      if (controller.getRawButton(1)) { // (Hold X to reverse)
        return -(controller.getRawAxis(3) + 1) / 2;
      } else {
        return (controller.getRawAxis(3) + 1) / 2;
      }
    }
  };

  final DoubleSupplier outputSupplier = () -> {
    if (controller.getRawButton(3)) {
      return 0;
    } else {
      if (controller.getRawButton(3)) { // (Hold Triangle to reverse)
        return -(controller.getRawAxis(4) + 1) / 2;
      } else {
        return (controller.getRawAxis(4) + 1) / 2;
      }
    }
  };

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();

    m_drivetrain.setDefaultCommand(new TankDrive(leftsupply, rightsupply, m_drivetrain));
    m_powercell.setDefaultCommand(new ManualPowerCell(intakeSuppier, outputSupplier, m_powercell));
    m_compressor.setClosedLoopControl(true);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(controller, 3).whileHeld(new ControlPanelRevolutions(m_controlPanel));
    new JoystickButton(controller, 1).whileHeld(() -> {
      new PowerCell().run_belt(-0.5);
    });
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
