// Drive4Time  copied from Driv2Tag

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Drive4Time extends CommandBase {
  /** Creates a new Drive2Tag. */
  private final DriveTrain m_driveTrain;

  double m_drive, m_turn;

  public Drive4Time(DriveTrain subsystem, double fwdDrive, double turnDrive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveTrain = subsystem;
    addRequirements(m_driveTrain);
    m_drive = fwdDrive;
    m_turn = turnDrive;
    }
 // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_driveTrain.drive(m_drive, m_turn);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveTrain.drive(0,0);  // stop
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
