package testCases;

import static org.junit.Assert.*;

import java.text.*;
import java.util.*;
import org.junit.*;

public class changeExistingWorkingDateTimes {

	//existing id
	private static int[] employeeid = {1,2,3,4,5};
	private static String startinghour = "08:00 AM";
	private static String endinghour = "05:00 PM";
	
	 
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	public Boolean checkOutofBoundWorkinghours(String start, String end) throws ParseException{
		
		SimpleDateFormat formatTime = new SimpleDateFormat ("hh:mm aa");
		Date startBound = formatTime.parse(startinghour);
		Date endBound = formatTime.parse(endinghour);
		Date startTime = formatTime.parse(start);
		Date endTime = formatTime.parse(end);
		
		//check if input time is within regular working hours 8:00 AM - 5:00 PM
		if((startTime.getTime() - startBound.getTime() >= 0) 
				&& (endBound.getTime() - endTime.getTime() >= 0)){
			return true;
		}
		return false;
	}
	
	public boolean checkEmployeeIDTest(int userInput) {
		
		//check if id matches
		for (int i : employeeid){
			if(i == userInput){
				return true;
			}
		}
		return false;
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void idNotFoundTest(){
		assertFalse(checkEmployeeIDTest(6));
	}
	
	@Test
	public void idExistsTest(){
		assertTrue(checkEmployeeIDTest(3));
	}
	
	@Test
	public void checkTimeOutofBound() throws ParseException{
		String start = "09:00 AM";
		String end = "04:00 PM";
		assertTrue(checkOutofBoundWorkinghours(start, end));
	}
	@Test
	public void changeExistingTime() throws ParseException{
		idExistsTest();
		checkTimeOutofBound();
		
	}
	
	
}
