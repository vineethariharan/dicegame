package org.greatlearning.dicegame.model;

import lombok.Data;

import java.util.Random;

@Data
public class Dice {
	Random random;
	int faces;

	public Dice(int faces){
		this.faces = faces;
		random = new Random(System.currentTimeMillis());
	}


	public int roll(){
		return random.nextInt(faces)+1;
	}
}
