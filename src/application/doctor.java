package application;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class doctor extends firstController{
	
	@FXML
	TextField testType = new TextField();
	@FXML
	TextField testPrice = new TextField();
	
	public void orderTest(ActionEvent event) throws Exception{
		try
		{
			error.setText("");
			
			int bill=0;
			String id = patientId.getText();
						
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection(url, user, pass);
            
            Statement stt = (Statement) con.createStatement();
            
            stt.execute("USE hospital_management");
            
            PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM patient WHERE id = ?");
            prep.setInt(1, Integer.parseInt(id));
            
            ResultSet res = prep.executeQuery();
            
            while (res.next())
            {
         	    bill = res.getInt(11);
            }
            
            prep = (PreparedStatement) con.prepareStatement("UPDATE patient SET bill=? WHERE id=?");
            prep.setInt(1, bill+Integer.parseInt(testPrice.getText()));
            prep.setInt(2, Integer.parseInt(id));
            prep.executeUpdate();
            
            testType.setText("");
			testPrice.setText("");
            error.setText("Test ordered Successfully.");
		}
		catch (NumberFormatException e)
        {
        	error.setText("*All fields must be correctly filled");
        }
		catch (Exception e)
        {
        	error.setText("*Refresh the list");
        }		
	}
	
	public void testPatientSelected() throws Exception{
		try
		{
			testType.setText("");
			testPrice.setText("");
			error.setText("");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection(url, user, pass);
            
            Statement stt = (Statement) con.createStatement();
            
            stt.execute("USE hospital_management");
            
            PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM patient WHERE name = ?");
            prep.setString(1, patientList.getSelectionModel().getSelectedItem().toString());
            
            rupee.setVisible(true);
            
            ResultSet res = prep.executeQuery();
            
            while (res.next())
            {
         	       patientId.setText(String.valueOf(res.getInt(1)));
            	   patientName.setText(res.getString(2));
            	   patientDisease.setText(res.getString(7));
            }
		}
		catch (Exception e)
        {
        	error.setText("*Refresh the list");
        }	
	}
}