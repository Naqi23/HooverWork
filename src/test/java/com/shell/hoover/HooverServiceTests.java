package com.shell.hoover;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
 

public class HooverServiceTests {
	public HooverService hooverService;
	
	@Before
	public void setUp() throws Exception {
		hooverService = new HooverService();
		hooverService.setRoomSize(new int[]{5, 5});
		hooverService.setCoords(new int[]{1, 2});
		hooverService.setPatches(new int[][] {
            									{1, 0},
            									{2, 2},
            									{2, 3}
											});
		hooverService.setInstructions("NNESEESWNWW");
		hooverService.createRoom();
	}
	
	@Test
	public void testDirtPatchesAddedCorrectly() throws Exception {
		hooverService.applyDirt();
		assertEquals("d", hooverService.getContentFromRoomLocation(1, 0));
		assertEquals("d", hooverService.getContentFromRoomLocation(2, 2));
		assertEquals("d", hooverService.getContentFromRoomLocation(2, 3));
	}
	
	@Test 
	public void testInvalidPatchLocationAreNotAdded() throws Exception {
		hooverService.setPatches(new int[][] {
			{4, 0},
			{2, 3},
			{12, 6}
		});
		hooverService.applyDirt();
		
		assertEquals("d", hooverService.getContentFromRoomLocation(4, 0));
		assertEquals(2, dirtCount(hooverService.getRoom()));
	}
	
	@Test
	public void testHooverStartingPoint()throws Exception {
		hooverService.setHooverStartingPoint();
		assertEquals("h", hooverService.getContentFromRoomLocation(1, 2));
	}
	
	@Test 
	public void testHooverStartingPointWithDirt() throws Exception {
		hooverService.applyDirt();
		hooverService.setHooverStartingPoint();
		assertEquals("h", hooverService.getContentFromRoomLocation(1, 2));
		assertEquals(3, dirtCount(hooverService.getRoom()));
	}
	
	@Test
	public void testHooverStartingPointInvalidCoords() throws Exception {
		hooverService.setCoords(new int[]{11, 2});
		hooverService.setHooverStartingPoint();
		assertEquals(false, doesHooverExists(hooverService.getRoom()));
	}
	
	@Test
	public void testHappyPathCleanRoom() throws Exception {
		hooverService.applyDirt();
		hooverService.setHooverStartingPoint();
		hooverService.cleanRoom();
		
		assertEquals(1, hooverService.getPatchesCleaned());
	}
	
	private int dirtCount(String [][] room) {
		int noOfPatchsFound = 0;
		for(int i=0; i<room.length; i++) {
	        for(int j=0; j<room[i].length; j++) {
	        	if(room[i][j] == "d") {
	        		noOfPatchsFound++;
	        	}
	        }
	    }
		return noOfPatchsFound;
	}
	
	private boolean doesHooverExists(String [][] room) {
		boolean hooverFound = false;
		for(int i=0; i<room.length; i++) {
	        for(int j=0; j<room[i].length; j++) {
	        	if(room[i][j] == "h") {
	        		hooverFound = true;
	        	}
	        }
	    }
		return hooverFound;
	}
	

}
