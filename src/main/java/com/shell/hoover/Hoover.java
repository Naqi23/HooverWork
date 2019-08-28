package com.shell.hoover;

/**
 * This class represents the response back to client when calling the clean room API
 * Class Contains 3 constructors for the controller to utilise
 */
public class Hoover {
	
	public int[] coords;
	public int patches;
	
	public Hoover(int[] coords) {
		this.coords = coords;
	}

	public Hoover(int patches) {
		this.patches = patches;
	}

	public Hoover(int[] coords, int patches) {
		this.coords = coords;
		this.patches = patches;
	}

}
