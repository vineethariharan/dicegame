package org.greatlearning.dicegame.utility;

import org.greatlearning.dicegame.utility.model.InputInterface;

import java.util.Scanner;

public class ConsoleInput implements InputInterface {

	Scanner sc;

	public ConsoleInput(){
		sc = new Scanner(System.in);
	}

	@Override
	public String readNextInput(String s) {
		System.out.print(s+" ");
		return sc.next();
	}
}
