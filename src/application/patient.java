package application;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class patient extends firstController{
	public void patientCheckOut(ActionEvent event) throws Exception{
		try{
			error.setText("");
			
			int id=Integer.parseInt(patientId.getText().toString());	
			
	        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date cd = sdf.parse(sdf.format(new Date()));
            Date doi = sdf.parse(patientDate.getText().toString());
            
			if(cd.after(doi)){
				String name="";
				int roomBill=0,totalBill=0;
		        int diffInDays = (int) ((cd.getTime() - doi.getTime()) / (1000 * 60 * 60 * 24));		        
		        
		        Alert alert = new Alert(AlertType.CONFIRMATION, "This will permanently erase the record. Do you Wish to Continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
		
				if (alert.getResult() == ButtonType.YES) {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
			        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
			        
			        Statement stt = (Statement) con.createStatement();
			        
			        stt.execute("USE hospital_management");
			        
			        PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM patient WHERE id = ?");
			        prep.setInt(1, id);
			        
			        ResultSet res = prep.executeQuery();
		            
		            while (res.next())
		            {
		            	if(res.getString(12).equals("ac"))
		            		roomBill=1000*diffInDays;
		            	else if(res.getString(12).equals("vip"))
		            		roomBill=2000*diffInDays;
		            	else
		            		roomBill=500*diffInDays;
		            	totalBill = roomBill + Integer.parseInt(res.getString(11));
		            	name = res.getString(2);
		            }
		            
		            Alert newAlert = new Alert(AlertType.INFORMATION);
		            newAlert.setTitle("Payment Receipt");
		            newAlert.setHeaderText(null);
		            newAlert.setContentText("Patient Name: "+name+"\n\nAdmission charge: ₹"+(roomBill/diffInDays)+"\nRoom charge: ₹"+roomBill
		            		+"\nLab Tests: ₹"+(totalBill-roomBill-(roomBill/diffInDays))+"\n\nTotal amount to be paid: ₹"+totalBill);

		            newAlert.showAndWait();
		            
		            prep = (PreparedStatement) con.prepareStatement("DELETE FROM patient WHERE id=?");
			        prep.setInt(1, id);
			        prep.executeUpdate();
			        
			        String query = "UPDATE rooms SET R"+patientRoom.getText().toString()+"=0 WHERE id=2";
		            prep = (PreparedStatement) con.prepareStatement(query);
		            
			        prep.executeUpdate();
					error.setText("Patient Checked Out. Refresh the list");
				}
			}
			else{
				error.setText("*Enter a valid CheckIn date");
			}		
		}
		catch(Exception e){
			error.setText("*Refresh the List and select a patient");
		}
	}
	public void addPatient(ActionEvent event) throws Exception{
		try
        {
			error.setText("");
			
			if(name.getText().equals("")||address.getText().equals("")||disease.getText().equals("")||building.getText().equals("Select"))
				throw new Exception("");
			
	    	long mob = Long.parseLong(mobile.getText().toString());
	    	if(String.valueOf(mob).length()!=10)
	    		throw new MyException("*Enter a valid 10 digit mobile number");
	    	
	    	male.setToggleGroup(gender);
			female.setToggleGroup(gender);
			
			ac.setToggleGroup(roomType);
			ac.setSelected(true);
			nonAc.setToggleGroup(roomType);
			vip.setToggleGroup(roomType);
			
			male.setUserData("male");
			female.setUserData("female");
			ac.setUserData("ac");
			nonAc.setUserData("nonAc");
			vip.setUserData("vip");
	    	
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection(url, user, pass);
            
            Statement stt = (Statement) con.createStatement();
            stt.execute("USE hospital_management");
           
            PreparedStatement prep = (PreparedStatement) con.prepareStatement("INSERT into patient (name, age, mobile, address, date"
            		+ ", disease, price, building, floor, room, bill, gender, roomType) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            prep.setString(1, name.getText().toString());
            prep.setInt(2, Integer.parseInt(age.getText().toString()));
            prep.setString(3, String.valueOf(mob));
            prep.setString(4, address.getText().toString());
            prep.setString(5, date.getValue().toString());
            prep.setString(6, disease.getText().toString());
            prep.setInt(7, Integer.parseInt(price.getText().toString()));
            prep.setString(8, building.getText().toString());
            prep.setInt(9, Integer.parseInt(floor.getText().toString()));
            prep.setInt(10, Integer.parseInt(room.getText().toString()));
            prep.setInt(11, Integer.parseInt(price.getText().toString()));
            prep.setString(12, gender.getSelectedToggle().getUserData().toString());
            prep.setString(13, roomType.getSelectedToggle().getUserData().toString());
            
            prep.executeUpdate();
            
            String query = "UPDATE rooms SET R"+room.getText().toString()+"=1 WHERE id=2";
            prep = (PreparedStatement) con.prepareStatement(query);
	        
	        prep.executeUpdate();
            
	        pid.setText("");
	        name.setText("");
	        age.setText("");
	        mobile.setText("");
	        address.setText("");
	        disease.setText("");
	        building.setText("Select");
	        floor.setText("Select");
	        room.setText("Select");
	        price.setText("");
	        date.setValue(null);
	        
	        error.setText("Patient Successfully Admitted");
	        rupee.setVisible(false);
	        
            stt.close();
            prep.close();
            con.close();
        }
	    catch(NumberFormatException e)
	    {
	    	error.setText("*Check your numbers");
	    }
		catch(MyException e)
	    {
			error.setText(e.toString());
	    }
        catch (Exception e)
        {
        	error.setText("*All Fields should be correctly filled");
        }
	}
	public void updateInfo() throws Exception{
		try
		{
			error.setText("");
			
			long mob = Long.parseLong(patientMobile.getText().toString());
			if(String.valueOf(mob).length()!=10)
	    		throw new MyException("*Enter a valid 10 digit mobile number");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection(url, user, pass);
            
            Statement stt = (Statement) con.createStatement();
            
            stt.execute("USE hospital_management");
            
            PreparedStatement prep = (PreparedStatement) con.prepareStatement("UPDATE patient SET name=?, age=?"
            		+ ", mobile=?, address=?, date=?, disease=?, gender=? WHERE id=?");
            
            prep.setString(1, patientName.getText().toString());
            prep.setInt(2, Integer.parseInt(patientAge.getText().toString()));
            prep.setString(3, String.valueOf(mob));
            prep.setString(4, patientAddress.getText().toString());
            prep.setString(5, patientDate.getText().toString());
            prep.setString(6, patientDisease.getText().toString());
            prep.setString(7, patientGender.getText().toString());
            prep.setInt(8, Integer.parseInt(patientId.getText().toString()));

            prep.executeUpdate();
            error.setText("Successfully Updated. Refresh the list");
		}
		catch(NumberFormatException e)
	    {
	    	error.setText("*Check your numbers");
	    }
		catch(MyException e)
	    {
			error.setText(e.toString());
	    }
		catch (Exception e)
        {
        	error.setText("*All fields should be correctly filled");
        }	
	}
}