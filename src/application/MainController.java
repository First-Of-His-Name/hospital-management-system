package application;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	
    Stage primaryStage = new Stage();

	@FXML 
	private TextField inputUsername = new TextField();
	@FXML 
	private TextField inputPassword = new TextField();
	@FXML
	Label error = new Label();
	
    public void store(ActionEvent event)
    {
    	String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String pass = "";
        boolean flag = false;
        
        try
        {
        	error.setText("");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection(url, user, pass);
            
            Statement stt = (Statement) con.createStatement();
            
            stt.execute("USE hospital_management");
            
            PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM login WHERE username = ? and password = ?");
            prep.setString(1, inputUsername.getText().toString());
            prep.setString(2, inputPassword.getText().toString());

            ResultSet res = prep.executeQuery();
            while (res.next())
            {
            	Parent root;
            	flag = true;
            	if (inputUsername.getText().equals("admin")) 
            		root = FXMLLoader.load(getClass().getResource("/application/firstScene.fxml"));
            	else 
            		root = FXMLLoader.load(getClass().getResource("/application/thirdScene.fxml"));
     
        		Scene scene = new Scene(root);
        		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        		primaryStage.setScene(scene);
       			primaryStage.show();       			
            }
            if(!flag){
            	prep = (PreparedStatement) con.prepareStatement("SELECT * FROM staff WHERE name = ? and mobile = ? and position = ?");
                prep.setString(1, inputUsername.getText().toString());
                prep.setString(2, inputPassword.getText().toString());
                prep.setString(3, "Doctor");
                
                res = prep.executeQuery();
                while (res.next())
                {
                	Parent root;
                	flag = true;
                	root = FXMLLoader.load(getClass().getResource("/application/secondScene.fxml"));
         
            		Scene scene = new Scene(root);
            		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            		primaryStage.setScene(scene);
           			primaryStage.show();
                }
            }
            
            if(!flag)
            	error.setText("*Username or Password, incorrect");
                      
            res.close();
            stt.close();
            prep.close();
            con.close();
        }
        catch (Exception e)
        {
        	error.setText("*Some Error Occured in the System");
        }
    }
}

