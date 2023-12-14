// Turn2Home copied from Drive4Time

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turn2Home extends CommandBase {
  /** Creates a new Drive2Tag. */
  private final DriveTrain m_driveTrain;
  int fidNumber;

  public Turn2Home(DriveTrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveTrain = subsystem;
    addRequirements(m_driveTrain);
    }
 // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    fidNumber = (int)SmartDashboard.getNumber( "Tag #", 2);  // %r6

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if ((fidNumber == 1) || (fidNumber == 3))
      m_driveTrain.drive(0, 0.7);  // rotate clockwise
    else
      m_driveTrain.drive(0, -0.7); // rotate counter-clockwise
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
