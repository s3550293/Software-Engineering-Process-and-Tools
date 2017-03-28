package program.Tests;
import program.Booking;
import program.Controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookingJunit {

	Booking book;
	Controller con = new Controller();
	
	

	@Test
	public void test() {
		
		//Add in detials
		book = new Booking(1, 12, con.convertStringToDate("01/01/2018"), con.convertStringToTime("13:00"),
				con.convertStringToTime("14:00"), "this is my first booking");
		
		//Test booking id
		assertEquals(1, book.getBookingID());
		//Test customer id
		assertEquals(12, book.getCustomerId());
		//Test booking date
		assertEquals(con.convertStringToDate("01/01/2018") , book.getDate());
		//Test start time
		assertEquals(con.convertStringToTime("13:00") , book.getStartTime());
		//Test end time
		assertEquals(con.convertStringToTime("14:00"), book.getEndTime());
		//Test description
		assertEquals("this is my first booking", book.getDesc());
	}

}
