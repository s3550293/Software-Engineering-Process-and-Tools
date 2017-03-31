package program.Tests;
import program.Booking;
import program.Controller;
import program.Main;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookingJunit {

	private static Logger log = Logger.getLogger(BookingJunit.class);
	Booking book1;
	Booking book2;
	Controller con = new Controller();
	
	

	@Before
	public void test() {
		
		//Add in details
		book1 = new Booking(1, 12, con.convertStringToDate("01/01/2018"), con.convertStringToTime("13:00"),
				con.convertStringToTime("14:00"), "active");
		
		book2 = new Booking(2, 23, con.convertStringToDate("10/10/2017"), con.convertStringToTime("08:00"),
				con.convertStringToTime("10:00"), "active");
		
	}
	
	@Test
	public void testBookingID(){
		
		assertEquals(1, book1.getBookingID());
		assertEquals(2, book2.getBookingID());
	}

	@Test
	public void testCustomerID(){
		
		assertEquals(1, book1.getBookingID());
		assertEquals(2, book2.getBookingID());
	}
	
	@Test
	public void testDate(){
		
		assertEquals(con.convertStringToDate("01/01/2018") , book1.getDate());
		assertEquals(con.convertStringToDate("10/10/2017") , book2.getDate());
		book1.setDate(con.convertStringToDate("10/10/2018"));
		book2.setDate(con.convertStringToDate("11/11/2018"));
		assertEquals(con.convertStringToDate("10/10/2018") , book1.getDate());
		assertEquals(con.convertStringToDate("11/11/2018") , book2.getDate());
	}
	
	@Test
	public void testStartTime(){

		assertEquals(con.convertStringToTime("13:00") , book1.getStartTime());
		assertEquals(con.convertStringToTime("08:00") , book2.getStartTime());
		book1.setStartTime(con.convertStringToTime("11:00"));
		book2.setStartTime(con.convertStringToTime("09:00"));
		assertEquals(con.convertStringToTime("11:00") , book1.getStartTime());
		assertEquals(con.convertStringToTime("09:00") , book2.getStartTime());
	}
	
	@Test
	public void testEndTime(){

		
		assertEquals(con.convertStringToTime("14:00"), book1.getEndTime());
		assertEquals(con.convertStringToTime("10:00"), book2.getEndTime());
		book1.setStartTime(con.convertStringToTime("12:00"));
		book2.setStartTime(con.convertStringToTime("11:00"));
		assertEquals(con.convertStringToTime("12:00") , book1.getStartTime());
		assertEquals(con.convertStringToTime("11:00") , book2.getStartTime());
	}
	
	@Test
	public void testDescription(){
		
		assertEquals("active", book1.getStatus());
		assertEquals("active", book2.getStatus());
		book1.setStatus("cancel");
		book2.setStatus("cancel");
		assertEquals("cancel", book1.getStatus());
		assertEquals("cancel", book2.getStatus());
	}
}
