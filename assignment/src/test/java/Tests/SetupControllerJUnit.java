package test.java.Tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gui.SetupController;
import program.BusinessMenu;
import program.Controller;

public class SetupControllerJUnit {
	Controller program = new Controller();
	SetupController setupC = new SetupController();
	BusinessMenu bMenu = new BusinessMenu();
	@Test
	public void assignOpenClosingTimesToWeekDays1()
	{
		setupC.assignOpenClosingTimesToWeekDays("06:30","07:30");
	}
	@Test
	public void splitTimeIntoThreeBlocks1()
	{
		String [] times = program.splitTimeIntoThreeBlocks("08:12", "10:00");
		assertEquals(times[0],"08:12");
		assertEquals(times[1],"08:48");
		assertEquals(times[2],"09:24");
		assertEquals(times[3],"10:00");
	}
	@Test
	public void splitTimeIntoThreeBlocks2()
	{
		String [] times = program.splitTimeIntoThreeBlocks("10:00", "10:59");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks3()
	{
		String [] times = program.splitTimeIntoThreeBlocks("11:00", "10:00");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks4()
	{
		String [] times = program.splitTimeIntoThreeBlocks("24:00", "24:30");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks5()
	{
		String [] times = program.splitTimeIntoThreeBlocks("00:10", "00:00");
		assertEquals(times[0],"");
		assertEquals(times[1],"");
		assertEquals(times[2],"");
		assertEquals(times[3],"");
	}
	@Test
	public void splitTimeIntoThreeBlocks6()
	{
		String [] times = program.splitTimeIntoThreeBlocks("16:12", "20:33");
		assertEquals(times[0],"16:12");
		assertEquals(times[1],"17:39");
		assertEquals(times[2],"19:06");
		assertEquals(times[3],"20:33");
	}
	@Test
	public void splitTimeIntoThreeBlocks7()
	{
		String [] times = program.splitTimeIntoThreeBlocks("00:00", "01:00");
		assertEquals(times[0],"00:00");
		assertEquals(times[1],"00:20");
		assertEquals(times[2],"00:40");
		assertEquals(times[3],"01:00");
	}
}
