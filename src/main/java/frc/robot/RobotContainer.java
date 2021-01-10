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
import edu.wpi.first.wpilibj2.command.button.Trigger;
//wpilibj.buttons
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.commands.TankDrive;
import frc.robot.commands.DefaultControlPanel;
import frc.robot.commands.Intake;

import frc.robot.commands.ManualPowerCell;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PowerCell;
import frc.robot.util.JoystickAxis;

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
  final Compressor m_compressor = new Compressor();


  final Joystick controller = new Joystick(0);

  // joystick axes
  final JoystickAxis throttleAxis = new JoystickAxis(controller, Constants.THROTTLE_AXIS, 1, 0, 0, 0);
  final JoystickAxis turnAxis = new JoystickAxis(controller, Constants.TURN_AXIS, 1, 0, 0, 0);

  final JoystickAxis controlPanelAxis = new JoystickAxis(controller, Constants.CONTROL_PANEL_AXIS, 0.5, 0, 0, 0.1);

  // axis suppliers
  final DoubleSupplier throttleSupply = () -> throttleAxis.get();
  final DoubleSupplier turnSupply = () -> turnAxis.get();

  final DoubleSupplier controlPanelSupplier = () -> controlPanelAxis.get(); 

  final Trigger intakeTrigger = new Trigger(() -> {
    return controller.getRawAxis(Constants.INTAKE_AXIS) > 0.1;
  });

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

    m_drivetrain.setDefaultCommand(new TankDrive(throttleSupply, turnSupply, m_drivetrain));
    m_controlPanel.setDefaultCommand(new DefaultControlPanel(m_controlPanel, controlPanelSupplier));
    m_compressor.setClosedLoopControl(true);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    intakeTrigger.whileActiveOnce(new Intake(m_powercell));

    new JoystickButton(controller, 3).whileHeld(new DefaultControlPanel(m_controlPanel, controlPanelSupplier));
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
