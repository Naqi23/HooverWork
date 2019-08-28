package com.shell.hoover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represents a Hoover Service, the class designed to minimise coupling having modular methods which can be controlled by the controller.
 *  
 */
import org.springframework.stereotype.Service;

@Service
public class HooverService {
	
	private int[] roomSize;
	private int[] coords;
	private int[][] patches;
	private String instructions;

	public String[][] room;
	public int patchesCleaned = 0;
	public int[] hooverEndCoords;
	public int[] hooverCurrentCoord;
	private Hoover hoover;
	
	/**
	 * Method to setup the room ready for cleaning.
	 */
	public void setUp() {
		createRoom(); // Create the room
		applyDirt(); // Add the dirt at the locations
		setHooverStartingPoint(); // Set the hoover inital starting position
	}
	
	/**
	 * Methods takes userinput of where the dirt are located and places it in the corresponding location in the room.
	 */
	public void applyDirt() {
		ArrayList<int[]> list = new ArrayList<int[]>();
		for (int i = 0; i < patches.length; i++) {
			list.add(patches[i]);
		}

		for (int[] array : list) {
			if(validMove(array[0], array[1])) {
				room[array[0]][array[1]] = "d";
			}
		}
	}
	
	/**
	 * Method takes the userinput and places the hoover at the initial starting position
	 */
	public void setHooverStartingPoint() {
		if(validMove(coords[0], coords[1])) {
			if (room[coords[0]][coords[1]] == "d") {
				// User is placing hoover starting point at a location with dirt
				patchesCleaned++;
			}
			room[coords[0]][coords[1]] = "h";
			hooverCurrentCoord = coords;
		}
	}
	
	/**
	 * This method contains the main business logic for the hoover to move North, East, South adn West by following the user input.
	 * If a dirt location is found we store it for later user.
	 */
	public void cleanRoom() {
		printRoom();

		// Get an ArrayList of Chars of the user instructions by using streams and lamda
		ArrayList<Character> charList = new ArrayList<>(
				instructions.chars().mapToObj(e -> (char) e).collect(Collectors.toList()));

		// iterate over char in the arrayList using streams
		charList.stream().forEach(ch -> {
			int x = hooverCurrentCoord[0];
			int y = hooverCurrentCoord[1];

			switch (ch) {
			case 'N':
				moveNorth(x, y);
				printRoom();
				break;

			case 'E':
				moveEast(x, y);
				printRoom();
				break;

			case 'S':
				moveSouth(x, y);
				printRoom();
				break;

			case 'W':
				moveWest(x, y);
				printRoom();
				break;
			default:
				// Do nothing ignore this random character
			}
		});

//		System.out.println(this.patchesCleaned);
	}
	
	/**
	 * Method allows the Hoover to navigate west in the room
	 * @param x coordinate
	 * @param y coordinate
	 */
	private void moveWest(int x, int y) {
		if (validMove(x - 1, y)) { // Is moving North a valid move ?
			if (room[x - 1][y] == "d") { // Does the next move have dirt patch ?
				this.patchesCleaned++; // dirt patch cleaned
			}
			room[x][y] = null; // clear the hoover current positon in the room
			room[x - 1][y] = "h"; // set the new hoover position in the room + NORTH
			hooverCurrentCoord[0] = hooverCurrentCoord[0] - 1; // Set the hoovers current position for next iteration
		}
	}

	/**
	 * Method allows the Hoover to navigate East in the room
	 * @param x coordinate
	 * @param y coordinate
	 */
	private void moveEast(int x, int y) {
		if (validMove(x + 1, y)) { // Is moving North a valid move ?
			if (room[x + 1][y] == "d") { // Does the next move have dirt patch ?
				this.patchesCleaned++; // dirt patch cleaned
			}
			room[x][y] = null; // clear the hoover current positon in the room
			room[x + 1][y] = "h"; // set the new hoover position in the room + NORTH
			hooverCurrentCoord[0] = hooverCurrentCoord[0] + 1; // Set the hoovers current position for next iteration
		}
	}

	/**
	 * Method allows the Hoover to navigate South in the room
	 * @param x coordinate
	 * @param y coordinate
	 */
	private void moveSouth(int x, int y) {
		if (validMove(x, y - 1)) { // Is moving North a valid move ?
			if (room[x][y - 1] == "d") { // Does the next move have dirt patch ?
				this.patchesCleaned++; // dirt patch cleaned
			}
			room[x][y] = null; // clear the hoover current positon in the room
			room[x][y - 1] = "h"; // set the new hoover position in the room + NORTH
			hooverCurrentCoord[1] = hooverCurrentCoord[1] - 1; // Set the hoovers current position for next iteration
		}
	}

	/**
	 * Method allows the Hoover to navigate North in the room
	 * @param x coordinate
	 * @param y coordinate
	 */
	private void moveNorth(int x, int y) {
		if (validMove(x, y + 1)) { // Is moving North a valid move ?
			if (room[x][y + 1] == "d") { // Does the next move have dirt patch ?
				this.patchesCleaned++; // dirt patch cleaned
			}
			room[x][y] = null; // clear the hoover current positon in the room
			room[x][y + 1] = "h"; // set the new hoover position in the room + NORTH
			hooverCurrentCoord[1] = hooverCurrentCoord[1] + 1; // Set the hoovers current position for next iteration
		}
	}
	
	/**
	 * Create the room by using the user input
	 */
	public void createRoom( ) {
		this.room = new String[roomSize[0]][roomSize[1]];
	}
	
	/**
	 * Method return the content at location (x,y) in the room
	 * @param x coordinate
	 * @param y coordinate
	 * @return content at (x, y)
	 */
	
	public String getContentFromRoomLocation(int x, int y) {
		return room[x][y];
	}
	
	
	/**
	 * Method checks if a given location is a valid move to take
	 * @param x coordinate
	 * @param y coordinate
	 * @return boolean if move is valid
	 */
	private boolean validMove(int x, int y) {

		if (x < 0 || y < 0 || x >= room.length || y >= room[x].length) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * Get Room array
	 * @return room
	 */
	public int[] getRoomSize() {
		return roomSize;
	}
	
	/**
	 * 
	 * @param roomSize
	 */

	public void setRoomSize(int[] roomSize) {
		this.roomSize = roomSize;
	}

	public int[] getCoords() {
		return coords;
	}
	

	public void setCoords(int[] coords) {
		this.coords = coords;
	}
	

	public int[][] getPatches() {
		return patches;
	}
	

	public void setPatches(int[][] patches) {
		this.patches = patches;
	}
	

	public String getInstructions() {
		return instructions;
	}
	

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	

	public String[][] getRoom() {
		return room;
	}
	

	public void setRoom(String[][] room) {
		this.room = room;
	}
	

	public int getPatchesCleaned() {
		return patchesCleaned;
	}
	

	public void setPatchesCleaned(int patchesCleaned) {
		this.patchesCleaned = patchesCleaned;
	}
	

	public int[] getHooverEndCoords() {
		return hooverEndCoords;
	}
	

	public void setHooverEndCoords(int[] hooverEndCoords) {
		this.hooverEndCoords = hooverEndCoords;
	}
	

	public int[] getHooverCurrentCoord() {
		return hooverCurrentCoord;
	}
	

	public void setHooverCurrentCoord(int[] hooverCurrentCoord) {
		this.hooverCurrentCoord = hooverCurrentCoord;
	}
	

	public Hoover getHoover() {
		return hoover;
	}
	

	public void setHoover(Hoover hoover) {
		this.hoover = hoover;
	}
	
	/**
	 * Method print a string representation of the current state of the room
	 */

	private void printRoom() {
		for (String[] row : room) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println("-----------------------------------");
	}
	
	
	public Hoover getHooverPosition() {
		
		return new Hoover(getHooverCurrentCoord());
	}
	
	
	public Hoover getNoOfPatchesCleaned() {
		
		return new Hoover(getPatchesCleaned());
	}

	
	public Hoover getCleaningDetails() {
		
		return new Hoover(getHooverCurrentCoord(), getPatchesCleaned());
	}

}
