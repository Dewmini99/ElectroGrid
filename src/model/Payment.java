package model;
import java.sql.*;
public class Payment
{ //A common method to connect to the DB- connection creation
private Connection connect()
 {
 Connection con = null;
 try
 {
 Class.forName("com.mysql.jdbc.Driver");

 //Provide the correct details: DBServer/DBName, username, password
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");
 }
 catch (Exception e)
 {e.printStackTrace();}
 return con;
 }
public String insertPayment(String paymentNo, String companyName, String amount, String units)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for inserting."; }
 // create a prepared statement
 String query = " insert into payment_company(`paymentID`,`paymentNo`,`companyName`,`amount`,`units`)"
 + " values (?, ?, ?, ?, ?)";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setInt(1, 0);
 preparedStmt.setString(2, paymentNo);
 preparedStmt.setString(3, companyName);
 preparedStmt.setDouble(4, Double.parseDouble(amount));
 preparedStmt.setString(5, units);
 // execute the statement
 
 preparedStmt.execute();
 con.close();
 output = "Inserted successfully";
 }
 catch (Exception e)
 {
 output = "Error while inserting the item.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String readPayments()
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for reading."; }
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>paymentNo</th><th>companyName</th>" +
 "<th>amount</th>" +
 "<th>units</th>" +
 "<th>Update</th><th>Remove</th></tr>";

 String query = "select * from payment_company";
 Statement stmt = con.createStatement();
 ResultSet rs = stmt.executeQuery(query);
 // iterate through the rows in the result set
 while (rs.next())
 {
 String paymentID = Integer.toString(rs.getInt("paymentID"));
 String paymentNo = rs.getString("paymentNo");
 String companyName = rs.getString("companyName");
 String amount = Double.toString(rs.getDouble("amount"));
 String units = rs.getString("units");
 // Add into the html table
 output += "<tr><td>" + paymentNo + "</td>";
 output += "<td>" + companyName + "</td>";
 output += "<td>" + amount + "</td>";
 output += "<td>" + units + "</td>";
 // buttons
 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
 + "<td><form method='post' action='payment_company.jsp'>"
 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
 + "<input name='paymentID' type='hidden' value='" + paymentID
 + "'>" + "</form></td></tr>";
 }
 con.close();
 // Complete the html table
 output += "</table>";
 }
 catch (Exception e)
 {
 output = "Error while reading the items.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String updatePayment(String ID, String paymentNo, String companyName, String amount, String units)
{
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 String query = "UPDATE payment_company SET paymentNo=?,companyName=?,amount=?,units=?WHERE paymentID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1, paymentNo);
	 preparedStmt.setString(2, companyName);
	 preparedStmt.setDouble(3, Double.parseDouble(amount));
	 preparedStmt.setString(4, units);
	 preparedStmt.setInt(5, Integer.parseInt(ID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Updated successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while updating the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	public String deletePayment(String paymentID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from payment_company where paymentID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(paymentID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the payment.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	} 
