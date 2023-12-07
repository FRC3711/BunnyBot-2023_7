// bunnybot2023  Team 3711
// 3-dec added auto modes.  added pigeon-gyro (commented out)
// 2-dec save from rod#6 test
// 1-dec fixed shooter drive(speed) to 0.6
// 1-dec implemented autosequence1 to capture bunny
// 30-nov tried GitHub> Init Repository>
// 29-nov changed sweeper to 30:1 set speeds to 0.6
// 29-nov rev 71 integrate rev 5 changes.
// made simple autonomous.
// 27-nov rev 7  switched LED Ring 2 cameras.  PDP on Shuffleboard.
// 24-nov rev 6  2 pipelines and 2 cameras
// 19-nov rev 5  Renamed DriveWithCamera to Drive2Tag  (april tag)
// Changed Arcade Drive to Squared Inputs.
// 18-nov rev 4  Added camera and feeder photoeye.  Make Drive2Bucket
// 12-oct rev 3  Added Sweeper, Shooter, Feeder controls and test buttons
// 31-oct rev 1
// got 4 CIM drive running with Talon SRX controllers 4,5,6,7
// copied Clamp.java from 2023 swerveBot and renamed Shooter
//



// Structure from RobotBuilder Version: 4.0  rod 2023
// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.PowerDistribution;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();
  private final PowerDistribution m_pdp = new PowerDistribution();

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
  // The robot's subsystems
  public final Shooter m_shooter = new Shooter();
  public final Sweeper m_sweeper = new Sweeper();
  public final DriveTrain m_driveTrain = new DriveTrain();

  // Joysticks
  private final Joystick joystick = new Joystick(0);

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  private RobotContainer() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Smartdashboard Subsystems

    // SmartDashboard Buttons
    // SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    // SmartDashboard.putData("Drive with joystick", new Drivewithjoystick(
    // m_driveTrain ));
    // SmartDashboard.putData("Down", new Down( m_shooter ));
    // SmartDashboard.putData("up", new up( m_shooter ));
    // SmartDashboard.putData("dump", new dump( m_shooter ));
    // SmartDashboard.putData("load 1", new load1( m_shooter ));
    // SmartDashboard.putData("reject 1", new reject1( m_shooter ));
  
    // SmartDashboard.putData("Drive 2 Tag", new Drive2Tag(m_driveTrain, 0.6)); // try drive to target.
    // SmartDashboard.putData("Drive 2 Bucket", new Drive2Bucket(m_driveTrain, 0.5)); // try drive to target.
    
    //**************************************************************
    SmartDashboard.putNumber("3711 bBot Rev ", 2023.73); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    SmartDashboard.putNumber("Tag #",2);  // 
    SmartDashboard.putData("PDP", m_pdp);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND
    m_driveTrain.setDefaultCommand(new Drivewithjoystick(m_driveTrain));

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND
   // m_shooter.setDefaultCommand(new hold(m_shooter)); // %rod2

    // Configure autonomous sendable chooser
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    m_chooser.setDefaultOption("Tote Tag Auto", new AutoSequence1(m_driveTrain, m_sweeper));
    m_chooser.addOption("Out/Back Auto", new AutonomousCommand(m_driveTrain));
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
    // Create some buttons
   
  // Basic test buttons for BBot 2023.
  
  final JoystickButton shootButton = new JoystickButton(joystick, 1);
  shootButton.whileTrue( new Shoot(m_shooter).withInterruptBehavior(InterruptionBehavior.kCancelSelf));    
  final JoystickButton feedButton = new JoystickButton(joystick, 2);
  feedButton.whileTrue( new Feed(m_shooter).withInterruptBehavior(InterruptionBehavior.kCancelSelf));    
  final JoystickButton sweepInButton = new JoystickButton(joystick, 11);
  sweepInButton.whileTrue( new Sweep(m_sweeper, -0.6).withInterruptBehavior(InterruptionBehavior.kCancelSelf));    
  final JoystickButton sweepOutButton = new JoystickButton(joystick, 12);
  sweepOutButton.whileTrue( new Sweep(m_sweeper, 0.6).withInterruptBehavior(InterruptionBehavior.kCancelSelf));    
  final JoystickButton tagDriveButton = new JoystickButton(joystick, 7);
  tagDriveButton.whileTrue( new Drive2Tag(m_driveTrain, 0.6));    
  final JoystickButton TestAutoButton = new JoystickButton(joystick, 8);
  TestAutoButton.whileTrue(new AutonomousCommand(m_driveTrain));    
  final JoystickButton BucketDriveButton = new JoystickButton(joystick, 9);
  BucketDriveButton.whileTrue( new Drive2Bucket(m_driveTrain, 0.5));    
  final JoystickButton auto1Button = new JoystickButton(joystick, 10);
  auto1Button.whileTrue( new AutoSequence1(m_driveTrain, m_sweeper).withTimeout(11));    
  

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
  }

  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
  public Joystick getJoystick() {
    return joystick;
  }

  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    return m_chooser.getSelected();
  }

}
