package testCases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class changeExistingWorkingDateTimes {

	//existing id
	private static int[] employeeid = {1,2,3,4,5};
	
	
	@Before
	public void setUp() throws Exception {
		//checkExistingIDTest(1);
	}
	
	public boolean checkEmployeeIDTest(int userInput) {
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
	public void changeExistingTime(){
		idExistsTest();
		
		
	}
	
	

	

}
