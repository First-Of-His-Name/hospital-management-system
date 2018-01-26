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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class firstController{

	String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String pass = "";
	
	@FXML
	public AnchorPane content;
    
    @FXML
	ListView<String> roomList = new ListView<>();
    @FXML
	ListView<String> patientList = new ListView<>();
	
    @FXML
	Label error = new Label();
    @FXML
	Label rupee = new Label();
    
	@FXML
	TextField buildingInfo = new TextField();
	@FXML
	TextField roomTypeInfo = new TextField();
	@FXML
	TextField roomNoInfo = new TextField();
	@FXML
	TextField priceInfo = new TextField();
	@FXML
	TextField nameInfo = new TextField();
	@FXML
	TextField patientId = new TextField();
	@FXML
	TextField patientName = new TextField();
	@FXML
	TextField patientGender = new TextField();
	@FXML
	TextField patientAge = new TextField();
	@FXML
	TextField patientMobile = new TextField();
	@FXML
	TextArea patientAddress = new TextArea();
	@FXML
	TextField patientDisease = new TextField();
	@FXML
	TextField patientBuilding = new TextField();
	@FXML
	TextField patientRoom = new TextField();
	@FXML
	TextField patientRoomType = new TextField();
	@FXML
	TextField patientDate = new TextField();
	@FXML
	TextField name = new TextField();
	@FXML
	TextField age = new TextField();
	@FXML
	TextField mobile = new TextField();
	@FXML
	TextField disease = new TextField();
	@FXML
	TextField pid = new TextField();
	@FXML
	TextField price = new TextField();
	
	@FXML
	DatePicker date = new DatePicker();
	@FXML
	TextArea address = new TextArea();
	
	@FXML
	SplitMenuButton building = new SplitMenuButton();
	@FXML
	SplitMenuButton floor = new SplitMenuButton();
	@FXML
	SplitMenuButton room = new SplitMenuButton();
	
	@FXML
	MenuItem one = new MenuItem();
	@FXML
	MenuItem two = new MenuItem();
	@FXML
	MenuItem three = new MenuItem();
	@FXML
	MenuItem four = new MenuItem();
	@FXML
	MenuItem menu = new MenuItem();

	final ToggleGroup gender = new ToggleGroup();
	final ToggleGroup roomType = new ToggleGroup();

	@FXML
	RadioButton male = new RadioButton();
	@FXML
	RadioButton female = new RadioButton();
	@FXML
	RadioButton ac = new RadioButton();
	@FXML
	RadioButton nonAc = new RadioButton();
	@FXML
	RadioButton vip = new RadioButton();
    
	public void adminMenu(ActionEvent event) throws Exception{		
		Parent loadScreen;
		Button btn = (Button) event.getSource();
		if(!btn.getId().equals("logout"))
		{
			if(btn.getId().equals("patientInfo"))
				loadScreen = FXMLLoader.load(getClass().getResource("/application/patientInfo.fxml")); 				
			else if(btn.getId().equals("addPatient"))
				loadScreen = FXMLLoader.load(getClass().getResource("/application/addPatient.fxml"));
			else if(btn.getId().equals("roomInfo"))
				 loadScreen = FXMLLoader.load(getClass().getResource("/application/roomInfo.fxml"));
			else if(btn.getId().equals("staffInfo"))
				 loadScreen = FXMLLoader.load(getClass().getResource("/application/staffInfo.fxml"));
			else if(btn.getId().equals("labTest"))
				 loadScreen = FXMLLoader.load(getClass().getResource("/application/labTests.fxml"));
			else 
				 loadScreen = FXMLLoader.load(getClass().getResource("/application/checkOut.fxml"));
			
			content.getChildren().clear();
	        content.getChildren().add(loadScreen); 
		}
		else
		{
			Node  source = (Node)  event.getSource(); 
		    Stage stage  = (Stage) source.getScene().getWindow();
		    stage.close();
		}
	}

	public void setRoomPrice(ActionEvent event) throws Exception{
		RadioButton roomPriceType = (RadioButton) event.getSource();
		if(roomPriceType.getId().equals("ac"))
			price.setText("1000");
		else if(roomPriceType.getId().equals("vip"))
			price.setText("2000");
		else
			price.setText("500");
		price.setDisable(true);
	}
	
	public void setRooms() throws Exception{
		one.setVisible(true);
		two.setVisible(true);
		three.setVisible(true);
		four.setVisible(true);
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
        
        Statement stt = (Statement) con.createStatement();
        
        stt.execute("USE hospital_management");
        
        PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM rooms");
      
        ResultSet res = prep.executeQuery();
        while (res.next())
        {
        	if(floor.getText().toString().equals("2"))
        	{
        		if(res.getInt(6)==0)
        			one.setText("201");
        		else 
        			one.setVisible(false);
        		if(res.getInt(7)==0)
        			two.setText("202");
        		else 
        			two.setVisible(false);
        		if(res.getInt(8)==0)
        			three.setText("203");
        		else 
        			three.setVisible(false);
        		if(res.getInt(9)==0)
        			four.setText("204");
        		else 
        			four.setVisible(false);
        	}
        	else if(floor.getText().toString().equals("3"))
        	{
        		if(res.getInt(10)==0)
        			one.setText("301");
        		else 
        			one.setVisible(false);
        		if(res.getInt(11)==0)
        			two.setText("302");
        		else 
        			two.setVisible(false);
        		if(res.getInt(12)==0)
        			three.setText("303");
        		else 
        			three.setVisible(false);
        		if(res.getInt(13)==0)
        			four.setText("304");
        		else 
        			four.setVisible(false);
        	}
        	else if(floor.getText().toString().equals("1")){
        		if(res.getInt(2)==0)
        			one.setText("101");
        		else 
        			one.setVisible(false);
        		if(res.getInt(3)==0)
        			two.setText("102");
        		else 
        			two.setVisible(false);
        		if(res.getInt(4)==0)
        			three.setText("103");
        		else 
        			three.setVisible(false);
        		if(res.getInt(5)==0)
        			four.setText("104");
        		else 
        			four.setVisible(false);
        	}
        	else{
        		one.setVisible(false);
        		two.setVisible(false);
        		three.setVisible(false);
        		four.setVisible(false);
        	}
        }	
	}
	
	public void menuSelected(ActionEvent event) throws Exception{
		error.setText("");
		MenuItem menu = (MenuItem) event.getSource();
		if(menu.getId().equals("A"))
	        building.setText("A");
		else if(menu.getId().equals("B"))
	        building.setText("B");
		else if(menu.getId().equals("C"))
	        building.setText("C");
		else if(menu.getId().equals("one"))
	        room.setText(one.getText().toString());
		else if(menu.getId().equals("two"))
	        room.setText(two.getText().toString());
		else if(menu.getId().equals("three"))
	        room.setText(three.getText().toString());
		else if(menu.getId().equals("four"))
	        room.setText(four.getText().toString());
		else if(menu.getId().equals("first"))
	        floor.setText("1");
		else if(menu.getId().equals("second"))
	        floor.setText("2");
		else
	        floor.setText("3");  
	}
	
	public void setList(ActionEvent event) throws Exception{
		try
		{
			error.setText("");
			
			ObservableList<String> items =FXCollections.observableArrayList ();

			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection(url, user, pass);
            
            Statement stt = (Statement) con.createStatement();
            
            stt.execute("USE hospital_management");
            
            PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM patient");
          
            ResultSet res = prep.executeQuery();
            while (res.next())
            {
            	items.add(res.getString("name"));        		
            }
			patientList.setItems(items);
		}
		catch (Exception e)
        {
			 System.out.println("error");
        }
	}
	
	public void itemSelected() throws Exception{
		try
		{
			error.setText("");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = (Connection) DriverManager.getConnection(url, user, pass);
            
            Statement stt = (Statement) con.createStatement();
            
            stt.execute("USE hospital_management");
            
            PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM patient WHERE name = ?");
            prep.setString(1, patientList.getSelectionModel().getSelectedItem().toString());
            
            ResultSet res = prep.executeQuery();
            
            while (res.next())
            {
         	       patientId.setText(String.valueOf(res.getInt(1)));
            	   patientName.setText(res.getString(2));
            	   patientGender.setText(res.getString(3));
            	   patientMobile.setText(res.getString(5));
            	   patientAge.setText(String.valueOf(res.getInt(4)));
            	   patientAddress.setText(res.getString(6));
            	   patientDisease.setText(res.getString(7));
            	   patientBuilding.setText(res.getString(8));
            	   patientRoom.setText(String.valueOf(res.getInt(10)));
            	   patientRoomType.setText(res.getString(12));
            	   patientDate.setText(res.getString(13));
            	   patientId.setDisable(true);
            	   patientBuilding.setDisable(true);
            	   patientRoom.setDisable(true);
            	   patientRoomType.setDisable(true);
            }
		}
		catch (Exception e)
        {
        	error.setText("*Refresh the list");
        }	
	}
	
	public void loadList(ActionEvent event) throws Exception{
		ObservableList<String> items =FXCollections.observableArrayList ();

		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
        
        Statement stt = (Statement) con.createStatement();
        
        stt.execute("USE hospital_management");
        
        PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM rooms");
      
        ResultSet res = prep.executeQuery();
        while(res.next())
        for(int i=2;i<=13;i++)
        	if(res.getInt(i)==1){
        		if(i>=2&&i<=5)
        			items.add("10"+(i-1));
	        	else if(i>=6&&i<=9)
	    			items.add("20"+(i-5));
		        else
		        	items.add("30"+(i-9));
        	}
		roomList.setItems(items);	
	}
	
	public void roomSelected() throws Exception{
		
		rupee.setVisible(true);
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = (Connection) DriverManager.getConnection(url, user, pass);
        
        Statement stt = (Statement) con.createStatement();
        
        stt.execute("USE hospital_management");
        
        PreparedStatement prep = (PreparedStatement) con.prepareStatement("SELECT * FROM patient WHERE room = ?");
        prep.setString(1, roomList.getSelectionModel().getSelectedItem().toString());
        
        ResultSet res = prep.executeQuery();
        
        while (res.next())
        {
        	if(res.getString(12).equals("ac"))
        		priceInfo.setText("1000");
        	else if(res.getString(12).equals("vip"))
        		priceInfo.setText("2000");
        	else
        		priceInfo.setText("500");
        	
     	    buildingInfo.setText(res.getString(8));
            roomTypeInfo.setText(res.getString(12));
        	roomNoInfo.setText(String.valueOf(res.getInt(10)));
        	nameInfo.setText(res.getString(2));
        }            
	}
}