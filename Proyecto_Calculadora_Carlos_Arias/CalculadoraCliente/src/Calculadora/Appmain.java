
package Calculadora;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Appmain extends Application{
     
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root= FXMLLoader.load(getClass().getResource("Calculadora.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Calculadora");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){

        launch(args);
        
    }
    
    
    
}
