package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "TeleOp: Mecanum + Shooter + Intake Button Control", group = "Linear Opmode")
public class TeleOpCode extends LinearOpMode {


    // Drive motors
    private DcMotor frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive;


    // Intake motors
    private DcMotor intakeMotor1, intakeMotor2;


    // Shooter motors
    private DcMotor shooterMotor1, shooterMotor2;


    // Speed tuning
    private final double DRIVE_SPEED = 0.85; // fixed max speed
    private final double INTAKE_POWER = 1.0;
    private final double SHOOTER_POWER = 0.60;


    @Override
    public void runOpMode() {




        frontLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_drive"); // same as driver's hub
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive   = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive  = hardwareMap.get(DcMotor.class, "back_right_drive");


        shooterMotor1 = hardwareMap.get(DcMotor.class, "shooter1");
        shooterMotor2 = hardwareMap.get(DcMotor.class, "shooter2");


        intakeMotor1  = hardwareMap.get(DcMotor.class, "intake");
        intakeMotor2  = hardwareMap.get(DcMotor.class, "intake2");




        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);




        shooterMotor1.setDirection(DcMotor.Direction.FORWARD);
        shooterMotor2.setDirection(DcMotor.Direction.REVERSE); // compliant wheels


        intakeMotor1.setDirection(DcMotor.Direction.REVERSE); // button-controlled
        intakeMotor2.setDirection(DcMotor.Direction.REVERSE); // always ON


        telemetry.addLine("TeleOp Ready");
        telemetry.update();


        waitForStart();


        while (opModeIsActive()) {




            double drive  = -gamepad1.left_stick_y;  // forward/back
            double strafe = gamepad1.left_stick_x;   // left/right corrected
            double turn   = gamepad1.right_stick_x;  // rotation


            if (Math.abs(drive) < 0.05) drive = 0;
            if (Math.abs(strafe) < 0.05) strafe = 0;
            if (Math.abs(turn) < 0.05) turn = 0;




            double fl = drive + strafe + turn;
            double fr = drive - strafe - turn;
            double bl = drive - strafe + turn;
            double br = drive + strafe - turn;


            double max = Math.max(1.0,
                    Math.max(Math.abs(fl),
                            Math.max(Math.abs(fr),
                                    Math.max(Math.abs(bl), Math.abs(br)))));


            frontLeftDrive.setPower((fl / max) * DRIVE_SPEED);
            frontRightDrive.setPower((fr / max) * DRIVE_SPEED);
            backLeftDrive.setPower((bl / max) * DRIVE_SPEED);
            backRightDrive.setPower((br / max) * DRIVE_SPEED);




            intakeMotor2.setPower(INTAKE_POWER); // always ON


            if (gamepad1.right_bumper) {         // first intake on button
                intakeMotor1.setPower(INTAKE_POWER);
            } else {
                intakeMotor1.setPower(0);
            }




            shooterMotor1.setPower(SHOOTER_POWER);
            shooterMotor2.setPower(SHOOTER_POWER);






            telemetry.addData("Front Left", frontLeftDrive.getPower());
            telemetry.addData("Front Right", frontRightDrive.getPower());
            telemetry.addData("Back Left", backLeftDrive.getPower());
            telemetry.addData("Back Right", backRightDrive.getPower());
            telemetry.addData("Shooter 1", shooterMotor1.getPower());
            telemetry.addData("Shooter 2", shooterMotor2.getPower());
            telemetry.addData("Intake 1", intakeMotor1.getPower());
            telemetry.addData("Intake 2", intakeMotor2.getPower());
            telemetry.update();
        }
    }
}

