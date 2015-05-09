package org.pandawarrior

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 * Created by jt on 5/9/15.
 */
class App extends Application{

    public static void main(String[] args) {
        launch(App.class, args)
    }

    @Override
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/app.fxml"));
        primaryStage.setTitle "Hello World"
        primaryStage.setScene(new Scene(root, 600, 400))
        primaryStage.show()
    }
}
