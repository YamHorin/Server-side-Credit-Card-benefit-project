package Application.logic;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import Application.business_logic.javaObjects.UserId;


public class AdminMain {

	@Value("${server.port}")
	public static RestClient setPort(int port) {
		return  RestClient.create("http://localhost:" + port + "/superapp");
	}
	
	public static void main(String[] args)
	 {
		 RestClient restClient = null;
		 int port = 8085;
		 restClient = setPort(port);
		
		 adminFunctions adminFunctions = new adminFunctions();
		ClubFunctions clubFunctions = new ClubFunctions();
		storeFunctions storeFunctions = new storeFunctions();
		benefitsFunctions benefitsFunctions = new benefitsFunctions();
		
		String email_user = preMenu(restClient);
		
		UserId userid = new UserId("2024B.gal.angel", email_user );
		
		System.out.println(port);
		int choise = 0;
		while (choise!=7)
		{
			choise = menu();
			switch (choise) {
			case 1: {
				//TODO finish the function delete a club
				
				
				break;
			}
			case 2: {
				//TODO finish the function delete a store

				
				break;
			}
			case 3: {
				//TODO finish the function print All Benefits Of Store

				
				break;
			}
			case 4: {
				//TODO finish the function print All Benefit Of Club

				
				break;
			}
			case 5: {
				//TODO finish the function add Benefit To Club

				
				break;
			}
			case 6: {
				//TODO finish the function add a new Benefit

				
				break;
			}
			case 7: {
				System.out.println("bye bye... :( ");
				break;
			}
			default:
				System.out.println("Unexpected value: " + choise);
			}
		}
			
	 }

	private static String preMenu(RestClient restClient) {
		// TODO a menu do you have an account yes or no? and if yes what is the email...
		return null;
	}

	private static int menu() {
		// TODO Auto-generated method stub
		 Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.println("==================================");
        System.out.println("           MAIN MENU             ");
        System.out.println("==================================");
        System.out.println("|1. delete a club:");
        System.out.println("|2. delete a store:");
        System.out.println("|3. print All Benefits Of Store:");
        System.out.println("|4. print All Benefit Of Club:");
        System.out.println("|5. add Benefit To Club:");
        System.out.println("|6. add a new Benefit:");
        System.out.println("|7. exit:");
        System.out.println("==================================");
        System.out.print("Please enter your choice: ");
        int choise = Integer.parseInt(scanner.next());
        scanner.close();
		return choise;
	}
	private static String makeANewAccount(RestClient restClient)
	{
		// TODO a function to create a new account to post it 

		return null;
		
	}

}
