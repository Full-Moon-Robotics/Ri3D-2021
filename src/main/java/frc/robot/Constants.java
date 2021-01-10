/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // IDs
    public static final int CONTROL_SOLENOID_ID = 3;
    public static final int LEFT_MOTOR_ID = 5;
    public static final int LEFT_MOTOR_1_ID = 6;
    public static final int RIGHT_MOTOR_ID = 8;
    public static final int RIGHT_MOTOR_1_ID = 9;
    public static final int BELT_MOTOR_ID = 11;
    public static final int INTAKE_MOTOR_ID = 12;
    public static final int SPINNER_MOTOR_ID = 13;
    public static final int OUTPUT_MOTOR_ID = 14;

    // Axes
    public static final int THROTTLE_AXIS = 1;
    public static final int TURN_AXIS = 2;
    public static final int CONTROL_PANEL_AXIS = 0;
    public static final int INTAKE_AXIS = 3;

    // Drivetrain
    public static final double DRIVE_GEAR_RATIO = 7/58;
    public static final double DRIVE_WHEEL_DIAMETER = 3.961;
    public static final double DRIVE_TRACK_WIDTH = 1.0; // TODO CHARACTERIZE

    public static final double DRIVE_KS = 0; // TODO CHARACTERIZE
    public static final double DRIVE_KV = 0; // TODO CHARACTERIZE

    public static final double DRIVE_TOP_SPEED = (12 - DRIVE_KS) / DRIVE_KV;    
}
