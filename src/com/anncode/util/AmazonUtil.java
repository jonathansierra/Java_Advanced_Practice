package com.anncode.util;

import java.util.Scanner;

public class AmazonUtil {
	
	public static int validateUserResponseMenu(int min, int max) {
		Scanner sc = new Scanner(System.in);
		while(!sc.hasNextInt()) {
			sc.next();
			System.out.println("No ingresaste una opción válida 1");
			System.out.println("Intenta otra vez 1");
		}
		int response = sc.nextInt();
		
		while(response < min || response > max) {
			System.out.println("No ingresaste una opción válida 2");
			System.out.println("Intenta otra vez 2");
			
			while(!sc.hasNextInt()) {
				sc.next();
				System.out.println("No ingresaste una opción válida 3");
				System.out.println("Intenta otra vez 3");
			}
			response = sc.nextInt();
		}
		System.out.println("Tu Respuesta fue: " + response + "\n");
		return response;
	}

}
