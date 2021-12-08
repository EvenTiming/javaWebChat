package test;

import java.util.Scanner;
import java.util.Random;
public class Code {

	public String getRV() {
		
		char[] x={'3','4','5','6','7','8','q','w','e','r','t','y','u','i','p','a','s','d','f','h','j','k','x','c','v','b','n','m','Q','W','E','R','T','Y','U','I','P','A','S','D','F','G','H','J','K','X','C','V','B','N','M'};
		
		Scanner scn=new Scanner(System.in);
		Random random=new Random();
		char a[]=new char[4];
		for(int i=0;i<4;i++){
			int j=random.nextInt(51);
			a[i]=x[j];
		}
		String Code=String.valueOf(a);
		return Code;
		
	}

}
