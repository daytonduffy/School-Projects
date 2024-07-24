import java.util.Scanner;

public class Salary {
	
   public static int magicWay(int userNum) {
	   
	   int magicThree; 
	   
	   magicThree = userNum * 2;
	   magicThree = magicThree + 9;
	   magicThree = magicThree - 3;
	   magicThree = magicThree / 2;
	   magicThree = magicThree - userNum;
	   
	   return magicThree;
   }

   
   public static int dumbWay(int userNum) {
	   
	   int magicThree;
	   
	   magicThree = userNum + 3;
	   magicThree = magicThree - userNum;
	   
	   return magicThree;
   }
   
   
   public static int cheatWay(int userNum) {
	   
	   int magicThree = 3;
	   
	   return magicThree;
   }
	
   
   public static void main(String[] args) {
	   
	   Scanner scnr = new Scanner(System.in);
		int userNum = 0;
		
		System.out.println("Enter any number and something Magical will happen!");
		userNum = scnr.nextInt();
		System.out.println("You entered: " + userNum);
		
		//complicated one
		System.out.println("Magically your " + userNum + " became a " + magicWay(userNum));
		//dumb one
		//System.out.println("Magically your " + userNum + " became a " + dumbWay(userNum));
		//cheating one
		//System.out.println("Magically your " + userNum + " became a " + cheatWay(userNum));
	
	   return;
	   
   }
}

	
	

