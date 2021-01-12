/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    // IDs
    public static final int CONTROL_SOLENOID_ID_FWD = 3;
    public static final int CONTROL_SOLENOID_ID_REV = 2;
    public static final int LEFT_MOTOR_ID = 5;
    public static final int LEFT_MOTOR_1_ID = 6;
    public static final int RIGHT_MOTOR_ID = 8;
    public static final int RIGHT_MOTOR_1_ID = 9;
    public static final int INTAKE_MOTOR_ID = 12;
    public static final int SPINNER_MOTOR_ID = 13;
    public static final int FLYWHEEL_MOTOR_ID = 10;
    public static final int INDEX_MOTOR_ID = 14;

    // Axes
    public static final int THROTTLE_AXIS = 1;
    public static final int TURN_AXIS = 2;
    public static final int CONTROL_PANEL_AXIS = 0;
    public static final int INTAKE_AXIS = 3;
    public static final int SHOOT_AXIS = 4;

    // Drivetrain
    public static final double DRIVE_GEAR_RATIO = 0.120689;
    public static final double DRIVE_WHEEL_DIAMETER = 3.961;
    public static final double DRIVE_TRACK_WIDTH = 0.7;

    public static final double DRIVE_KS = 0.18;
    public static final double DRIVE_KV = 3.28;

    public static final double DRIVE_TOP_SPEED = 3.6;

    public static final double AUTO_MAX_VELOCITY = 1;
    public static final double AUTO_MAX_ACCEL = 0.5;
    public static final double AUTO_MAX_CENTRIPETAL_ACCEL = 1;
    
    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 40;

    // Shooter
    public static final double FLYWHEEL_TOLERANCE = 200;
    public static final double FLYWHEEL_KS = 0;
    public static final double FLYWHEEL_KV = 0;

    // Intake
    public static final double INTAKE_SPEED = 0.8;
}
