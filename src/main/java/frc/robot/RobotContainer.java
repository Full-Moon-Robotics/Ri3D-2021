/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
//wpilibj.buttons
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DefaultControlPanel;
import frc.robot.commands.DrivePath;
import frc.robot.commands.Collect;
import frc.robot.commands.ResetDrivePose;
import frc.robot.commands.Shoot;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.util.JoystickAxis;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  final Drivetrain m_drivetrain = new Drivetrain();
  final Intake m_intake = new Intake();
  final ControlPanel m_controlPanel = new ControlPanel();
  final Shooter m_shooter = new Shooter();

  final Compressor m_compressor = new Compressor();

  final Joystick controller = new Joystick(0);

  // joystick axes
  final JoystickAxis throttleAxis = new JoystickAxis(controller, Constants.THROTTLE_AXIS, -Constants.DRIVE_TOP_SPEED,
      20, 0, 0.2);
  final JoystickAxis turnAxis = new JoystickAxis(controller, Constants.TURN_AXIS, -5 * Math.PI, 73, 0, 0.1);

  final JoystickAxis controlPanelAxis = new JoystickAxis(controller, Constants.CONTROL_PANEL_AXIS, 0.5, 0, 0, 0.1);

  // axis suppliers
  final DoubleSupplier throttleSupply = () -> throttleAxis.get();
  final DoubleSupplier turnSupply = () -> turnAxis.get();

  final DoubleSupplier controlPanelSupplier = () -> controlPanelAxis.get();

  final Trigger intakeTrigger = new Trigger(() -> {
    return controller.getRawAxis(Constants.INTAKE_AXIS) > 0.1;
  });

  final Trigger shootTrigger = new Trigger(() -> {
    return controller.getRawAxis(Constants.SHOOT_AXIS) > 0.1;
  });

  final Trigger compressorTrigger = new Trigger(() -> {
    return controller.getRawButton(4);
  });

  TrajectoryConfig m_trajConfig = new TrajectoryConfig(Constants.AUTO_MAX_VELOCITY, Constants.AUTO_MAX_ACCEL);

  SendableChooser<List<Pose2d>> m_autoChooser = new SendableChooser<List<Pose2d>>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureButtonBindings();

    // add trajectory constraints
    m_trajConfig.addConstraint(new DifferentialDriveKinematicsConstraint(m_drivetrain.getKinematics(), 3.6));
    m_trajConfig.addConstraint(new CentripetalAccelerationConstraint(Constants.AUTO_MAX_CENTRIPETAL_ACCEL));

    // set up autonomous trajectories
    m_autoChooser.setDefaultOption("None", null);
    m_autoChooser.addOption("Straight Line",
        Arrays.asList(new Pose2d(0, 0, new Rotation2d()), new Pose2d(3, 0, new Rotation2d())));
    m_autoChooser.addOption("Curve", Arrays.asList(new Pose2d(0, 0, new Rotation2d()),
        new Pose2d(2+0.2032, 2-0.1524, new Rotation2d()), new Pose2d(3+0.2032, 2-0.1524, new Rotation2d())));
    m_autoChooser.addOption("Bumps",
        Arrays.asList(new Pose2d(0, 0, new Rotation2d()), new Pose2d(1.6, 0.94, Rotation2d.fromDegrees(45)),
            new Pose2d(3.152, 1.736, new Rotation2d()), 
            new Pose2d(4.484, 1.736, new Rotation2d()),
            new Pose2d(5.152, 0.942, Rotation2d.fromDegrees(-90)),
            new Pose2d(4.382, 0, Rotation2d.fromDegrees(-180)),
            new Pose2d(3.828, 0.942, Rotation2d.fromDegrees(-225)),
            new Pose2d(2.521, 1.436, Rotation2d.fromDegrees(-180)),
            new Pose2d(0.526, 1.436, Rotation2d.fromDegrees(-180))));

    SmartDashboard.putData(m_autoChooser);

    // set default commands
    m_drivetrain.setDefaultCommand(new TankDrive(throttleSupply, turnSupply, m_drivetrain));
    m_controlPanel.setDefaultCommand(new DefaultControlPanel(m_controlPanel, controlPanelSupplier));

    // enable compressor
    m_compressor.setClosedLoopControl(true);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    intakeTrigger.whileActiveOnce(new Collect(m_intake));
    shootTrigger.whileActiveOnce(new Shoot(m_shooter));

    compressorTrigger.whenActive(() -> {
      m_compressor.setClosedLoopControl(!m_compressor.getClosedLoopControl());
    });
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    List<Pose2d> waypoints = m_autoChooser.getSelected();

    if (waypoints != null) {

      Trajectory traj = TrajectoryGenerator.generateTrajectory(waypoints, m_trajConfig);

      return new ResetDrivePose(m_drivetrain).andThen(new DrivePath(traj, m_drivetrain)).andThen(() -> {
        m_drivetrain.stop();
      });

    } else {
      return null;
    }

  }
}
