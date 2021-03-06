package program.Tests;
import program.Booking;
import program.Controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BookingJunit {

	Booking book1;
	Booking book2;
	Controller con = new Controller();
	
	

	@Before
	public void test() {
		
		//Add in details

		book1 = new Booking(1, 12,1, con.strToDate("01/01/2018"), con.strToTime("13:00"),con.strToTime("14:00"), 0,"active",2);

		

		book2 = new Booking(2, 23,1, con.strToDate("10/10/2017"), con.strToTime("08:00"),con.strToTime("10:00"), 0,"active",2);

		
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
		
		assertEquals(con.strToDate("01/01/2018") , book1.getDate());
		assertEquals(con.strToDate("10/10/2017") , book2.getDate());
		book1.setDate(con.strToDate("10/10/2018"));
		book2.setDate(con.strToDate("11/11/2018"));
		assertEquals(con.strToDate("10/10/2018") , book1.getDate());
		assertEquals(con.strToDate("11/11/2018") , book2.getDate());
	}
	
	@Test
	public void testStartTime(){

		assertEquals(con.strToTime("13:00") , book1.getStartTime());
		assertEquals(con.strToTime("08:00") , book2.getStartTime());
		book1.setStartTime(con.strToTime("11:00"));
		book2.setStartTime(con.strToTime("09:00"));
		assertEquals(con.strToTime("11:00") , book1.getStartTime());
		assertEquals(con.strToTime("09:00") , book2.getStartTime());
	}
	
	@Test
	public void testEndTime(){

		
		assertEquals(con.strToTime("14:00"), book1.getEndTime());
		assertEquals(con.strToTime("10:00"), book2.getEndTime());
		book1.setStartTime(con.strToTime("12:00"));
		book2.setStartTime(con.strToTime("11:00"));
		assertEquals(con.strToTime("12:00") , book1.getStartTime());
		assertEquals(con.strToTime("11:00") , book2.getStartTime());
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
