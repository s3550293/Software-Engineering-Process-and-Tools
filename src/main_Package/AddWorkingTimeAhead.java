package main_Package;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class AddWorkingTimeAhead {
	
	
	
	public boolean employeeIDCheck(int id) throws SQLException {
		String EmployeeIDquery = "SELECT * FROM employeedetails";
		Statement stmt = null;
		String url = "jdbc:sqlite:db/company.db";
		
		try(Connection connect = DriverManager.getConnection(url)){
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(EmployeeIDquery);
			
			//loop result set
			while(rs.next()){
				int empID = rs.getInt("emID");
				if(empID == id){
					System.out.println("id retrieved is " + id);
					return true;
				}
			}
			rs.close();
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return false;

	}
	
	
	@Before
	public void setup(){
	
	}
	
	
	@Test
	public void testEmpID() throws SQLException{
		AddWorkingTimeAhead awt = new AddWorkingTimeAhead();	
		assertFalse(awt.employeeIDCheck(112));
		
		//I have ID #223 in my db
		//Change the input to fit yours
		assertTrue(awt.employeeIDCheck(223));
	}
	
}
