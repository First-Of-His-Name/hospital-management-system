package application;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class staff extends firstController{
	
	@FXML
	TextField staffId = new TextField();
	@FXML
	TextField staffName = new TextField();
	@FXML
	TextField staffMobile = new TextField();
	
	@FXML
	TextArea staffAddress = new TextArea();
	@FXML
	SplitMenuButton staffPosition = new SplitMenuButton();
	
	@FXML
	ListView<String> staffList = new ListView<>();
	
	public void addStaff(ActionEvent event) throws Exception{
		try{
			error.setText("");
			
			male.setToggleGroup(gender);
			female.setToggleGroup(gender);
			
			male.setUserData("male");
			female.setUserData("female");
			
			long mob = Long.parseLong(staffMobile.getText().toString());
			if(String.valueOf(mob).length()!=10)
	    		throw new MyException("*Enter a valid 10 digit mobile number");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
	        
	        Statement stt = (Statement) con.createStatement();
	        stt.execute("USE hospital_management");
	       
	        PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT COUNT(*) FROM staff WHERE name=?");
	        prep.setString(1, staffName.getText().toString());
            
            ResultSet res = prep.executeQuery();
            
            res.next();
         	if(res.getInt(1)>0)
         		throw new MyException("*Entry Already exists");

         	prep = (PreparedStatement) con.prepareStatement("INSERT into staff (name, gender, position, mobile, address) VALUES (?,?,?,?,?)");
	        
	        prep.setString(1, staffName.getText().toString());
	        prep.setString(2, gender.getSelectedToggle().getUserData().toString());
	        prep.setString(3, staffPosition.getText().toString());
	        prep.setString(4, String.valueOf(mob));
	        prep.setString(5, staffAddress.getText().toString());
	
	        prep.executeUpdate();   
	        
	        staffId.setText("");
	        staffName.setText("");
	        staffPosition.setText("Select");
	        staffAddress.setText("");
	        staffMobile.setText("");
	        
	        error.setText("Successfully Added");
		}
		catch(NumberFormatException e)
	    {
	    	error.setText("*Enter valid mobile number");
	    }
		catch (MyException e)
        {
        	error.setText(e.toString());
        }
        catch (Exception e)
        {
        	error.setText("*All Fields should be correctly filled");
        }
	}
	
	public void positionSelected(ActionEvent event) throws Exception{
		error.setText("");
		
		MenuItem menu = (MenuItem) event.getSource();
		if(menu.getId().equals("janitor"))
			staffPosition.setText("Janitor");
		else if(menu.getId().equals("nurse"))
			staffPosition.setText("Nurse");
		else
			staffPosition.setText("Doctor");  
	}
	
	public void loadStaffList(ActionEvent event) throws Exception{
		error.setText("");
		
		ObservableList<String> items =FXCollections.observableArrayList ();

		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
        
        Statement stt = (Statement) con.createStatement();
        
        stt.execute("USE hospital_management");
        
        PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM staff");
      
        ResultSet res = prep.executeQuery();
        while(res.next())
        	items.add(res.getString("name")); 
        
		staffList.setItems(items);	
	}
	
	public void staffSelected() throws Exception{
		try{
			error.setText("");
			
			staffId.setDisable(true);
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
	        
	        Statement stt = (Statement) con.createStatement();
	        
	        stt.execute("USE hospital_management");
	        
	        PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM staff WHERE name=?");
	        prep.setString(1, staffList.getSelectionModel().getSelectedItem().toString());
	        
	        ResultSet res = prep.executeQuery();
	        
	        while (res.next())
	        {
	        	staffId.setText(String.valueOf(res.getInt(1)));
	     	    staffName.setText(res.getString(2));
	     	    staffMobile.setText(res.getString(5));
	     	    staffAddress.setText(res.getString(6));
	     	    staffPosition.setText(res.getString(4));
	     	    if(res.getString(3).equals("male"))
	     	 	    male.setSelected(true);
	     	    else
	     		    female.setSelected(true);
	        }
		}
		catch(Exception e){
			error.setText("*Refresh the list");
		}
	}
	
	public void updateStaff(ActionEvent event) throws Exception{
		try{
			error.setText("");
			
			male.setToggleGroup(gender);
			female.setToggleGroup(gender);
			male.setUserData("male");
			female.setUserData("female");
			
			long mob = Long.parseLong(staffMobile.getText().toString());
			if(String.valueOf(mob).length()!=10)
	    		throw new MyException("*Enter a valid 10 digit mobile number");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
	        
	        Statement stt = (Statement) con.createStatement();
	        
	        stt.execute("USE hospital_management");
			
			if(staffId.isDisable()){
				PreparedStatement prep = (PreparedStatement) con.prepareStatement("UPDATE staff SET name=?, gender=?, position=?, mobile=?, address=? WHERE id=?");
				prep.setString(1, staffName.getText().toString());
		        prep.setString(2, gender.getSelectedToggle().getUserData().toString());
		        prep.setString(3, staffPosition.getText().toString());
		        prep.setString(4, String.valueOf(mob));
		        prep.setString(5, staffAddress.getText().toString());
		        prep.setString(6, staffId.getText().toString());
		        prep.executeUpdate();
		        error.setText("Successfully Updated. Refresh the list");
			}
			else
				error.setText("*Refresh the list and Select a staff");
		}
		catch(NumberFormatException e)
	    {
	    	error.setText("*Enter valid mobile number");
	    }
		catch (MyException e)
        {
        	error.setText(e.toString());
        }
        catch (Exception e)
        {
        	error.setText("*All fields should be properly filled");
        }
	}
	
	public void deleteStaff(ActionEvent event) throws Exception{
		try{
			error.setText("");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
	        
	        Statement stt = (Statement) con.createStatement();
	        
	        stt.execute("USE hospital_management");
				
	        if(staffId.isDisable()){
				Alert alert = new Alert(AlertType.CONFIRMATION, "This will permanently erase the record. Do you Wish to Continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.YES) {
					PreparedStatement prep = (PreparedStatement) con.prepareStatement("DELETE from staff WHERE id=?");
					prep.setString(1, staffId.getText().toString());
			        
			        prep.executeUpdate();
			        error.setText("Successfully Deleted. Refresh the list");
				}
			}
			else
				error.setText("*Refresh the list and Select a staff");	
		}
        catch (Exception e)
        {
        	error.setText("*All fields should be properly filled");
        }
	}
}