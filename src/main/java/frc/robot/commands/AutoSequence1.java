// 16-feb-2023 stole from Load Sequence  this is from bunnybot 2021.

// try to drop a cube onto the upper deck.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Sweeper;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoSequence1 extends SequentialCommandGroup {
  /** Creates a new AutoSequence1. */ 

  public AutoSequence1(DriveTrain m_driveSubsystem, Sweeper m_sweeperSubsystem) {

    addCommands(

        new SequentialCommandGroup(
            new Drive2Tag(m_driveSubsystem, 0.6).withTimeout(6), // drive up to tote.
            new ParallelCommandGroup(
                new Drive4Time(m_driveSubsystem, .4, 0).withTimeout(1.8), // drive into tote, catch bunny
                new Sweep(m_sweeperSubsystem, -0.6).withTimeout(1.8) // intake bunny if it fell in front
            ),
            new Drive4Time(m_driveSubsystem, -0.7, 0.0).withTimeout(3.0), // drive back toward home
            new Drive4Time(m_driveSubsystem, 0, 0.7).withTimeout(1.5), // turn 180
            new Drive4Time(m_driveSubsystem, 0.5, 0.0).withTimeout(2), // drive into home zone
            new Sweep(m_sweeperSubsystem, 0.6).withTimeout(2), // discharge bunny
            new Drive4Time(m_driveSubsystem, -0.5, 0.0).withTimeout(2) // back away from bunny

        )

    );
  }
}
