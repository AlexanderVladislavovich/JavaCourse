package com.example.lab1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    Pane pane1;
    @FXML
    Label label1;
    @FXML
    Label score;
    int score_;
    @FXML
    Circle bigCircle;
    @FXML
    Circle smallCircle;
    @FXML
    Circle bullet;
    @FXML
    Button pause;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    int numOfShots;
    boolean isPaused = false;
    @FXML
    void clickM(MouseEvent evnt) {
        if (bullet == null) {
            numOfShots += 1;
            bullet = new Circle();
            bullet.setRadius(1);bullet.setStroke(Color.BLACK);
            bullet.setLayoutX(0);bullet.setLayoutY(evnt.getY());
            pane1.getChildren().add(bullet);
        } else { return; }
        //bigCircle.setLayoutY(evnt.getY());
        //bigCircle.setLayoutX(evnt.getX());
    }

    Thread thread1;
    //double yCoord = 125; double xCoord = 100;
    @FXML
    void startgame() {
        numOfShots = 0; score_ = 0;
        bullet = new Circle();
        bullet.setRadius(1);bullet.setStroke(Color.BLACK);
        bullet.setLayoutX(0);bullet.setLayoutY(100);
        pane1.getChildren().add(bullet);
        //pane1 = new Pane();
        //label1 = new Label();
        //bigCircle = new Circle();
        //smallCircle = new Circle();
        score.setText("score");
        bigCircle.setLayoutX(500);bigCircle.setLayoutY(0);
        bigCircle.setRadius(20);bigCircle.setStroke(Color.BLACK);
        smallCircle.setLayoutX(650);smallCircle.setLayoutY(0);
        smallCircle.setRadius(10);smallCircle.setStroke(Color.BLACK);
        label1.setText("sdjaio");
        //pane1.getChildren().add(bigCircle);
        //pane1.getChildren().add(smallCircle);
//        bigCircle.setLayoutX(xCoord);
//        bigCircle.setLayoutY(yCoord);
      //smallCircle.setLayoutY();
        if (thread1 != null) { return;}
        thread1 = new Thread(() ->{
           while (true ) {
               try {
                if (isPaused) {
                    synchronized (this) {
                        this.wait();
                    }
                    isPaused = false;
                }
               Platform.runLater(() -> {
                    moveBigCircle();
                    moveSmallCircle();
                    if(bullet != null) {
                        moveBullet();
                        collisionCheck();
                    }
                    if ( bullet != null && bullet.getLayoutX() >= 700) {
                        pane1.getChildren().remove(bullet);
                        bullet = null;
                    }


//                    double speed = 1;
//                    if (bigCircle.getLayoutY() >= 300 ) { bigCircle.setLayoutY(0);}
//                    if (smallCircle.getLayoutY() >= 300) {smallCircle.setLayoutY(0);}
//                    if (bullet.getLayoutX() >= 700) {
//                        pane1.getChildren().remove(bullet);
//                        bullet = null;}
//                    if (bullet != null) bullet.setLayoutX(bullet.getLayoutX() + 4);
//                    bigCircle.setLayoutY(bigCircle.getLayoutY() + 3 );
//                    smallCircle.setLayoutY(smallCircle.getLayoutY() + 6);
                   //score.setText(Double.toString(bigCircle.getLayoutX()));
                   score.setText("score: " + Integer.toString(score_));
                   //label1.setText( "Y" + Double.toString(bigCircle.getLayoutY()));
                   label1.setText("number of shots: " + Integer.toString(numOfShots));
//                    System.out.println(bigCircle.getLayoutX());
               });

                   thread1.sleep(20);
               } catch (InterruptedException exc) {}
           }
        });
        thread1.start();
    }

    @FXML
    void pause() {
//        try {
//            synchronized (thread1) {
//                 if (!isPaused) {
//                     if (thread1.isAlive()) thread1.wait();
//                     isPaused = true;
//                 }
//                 else {
//                     thread1.notify();
//                     isPaused = false;
//                 }
//            }
//        } catch (InterruptedException exc) {}
        if ( !isPaused ) {
            isPaused = true;
            pause.setText("Continue");
        }
        else {
            synchronized (this) {
                notifyAll();
            }
            pause.setText("Pause");
        }
    }

    void moveBigCircle() {
        if (bigCircle.getLayoutY() >= 300 ) { bigCircle.setLayoutY(0);}
        bigCircle.setLayoutY(bigCircle.getLayoutY() + 3 );
    }
    void moveSmallCircle() {
        if (smallCircle.getLayoutY() >= 300) {smallCircle.setLayoutY(0);}
        smallCircle.setLayoutY(smallCircle.getLayoutY() + 6);
    }
    void moveBullet() {

            bullet.setLayoutX(bullet.getLayoutX() + 4);

    }
    void collisionCheck() {
        double x = smallCircle.getLayoutX() - bullet.getLayoutX();
        double y = smallCircle.getLayoutY() - bullet.getLayoutY();
        double rad1 = bullet.getRadius();
        double rad2 = smallCircle.getRadius();
        if ((x * x + y * y) <= (rad1 + rad2) * (rad1 + rad2)) {
            score_ += 2;
            pane1.getChildren().remove(bullet);
            bullet = null;
            return;
        }
        //////
         x = bigCircle.getLayoutX() - bullet.getLayoutX();
         y = bigCircle.getLayoutY() - bullet.getLayoutY();
         rad1 = bullet.getRadius();
         rad2 = bigCircle.getRadius();
        if ((x * x + y * y) <= (rad1 + rad2) * (rad1 + rad2)) {
            score_ += 1;
            pane1.getChildren().remove(bullet);
            bullet = null;
            return;
        }

    }

}