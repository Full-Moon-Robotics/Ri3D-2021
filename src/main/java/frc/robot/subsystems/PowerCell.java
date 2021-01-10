package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Compressor;

public class PowerCell extends SubsystemBase {
  /**
   * Intake.
   */

  private CANSparkMax intakeMotor;
  private CANSparkMax beltMotor;
  private CANSparkMax outputMotor;

  public PowerCell() {
    super();

    intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR_ID, MotorType.kBrushed); // changed to reflect final motor
    beltMotor = new CANSparkMax(Constants.BELT_MOTOR_ID, MotorType.kBrushed);
    outputMotor = new CANSparkMax(Constants.OUTPUT_MOTOR_ID, MotorType.kBrushed);

    intakeMotor.restoreFactoryDefaults();

    beltMotor.restoreFactoryDefaults();
    beltMotor.setInverted(true);

    outputMotor.restoreFactoryDefaults();
  }

  public void run_intake(double speed) {
    if (intakeMotor != null) {
      intakeMotor.set(-speed);
      outputMotor.set(speed / 2);
    }
  }

  public void run_output(double speed) {
    if (intakeMotor != null) {
      outputMotor.set(-speed);
    }
  }

  public void run_belt(double speed) {
    if (intakeMotor != null) {
      beltMotor.set(speed);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}