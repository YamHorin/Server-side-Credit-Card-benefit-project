package Application.logic;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import Application._a_Presentation.Exceptions.BoundaryIsNotFilledCorrectException;
import Application.business_logic.Boundaies.NewUserBoundary;
import Application.business_logic.Boundaies.RoleEnumBoundary;
import Application.business_logic.Boundaies.UserBoundary;
import Application.business_logic.javaObjects.UserId;


public class AdminMain {

    private static Scanner scanner = new Scanner(System.in);
	
    private static String applicationName= "2024B.gal.angel";
    
	
	public static RestClient setPort(int port) {
		return  RestClient.create("http://localhost:" + port + "/superapp");
	}
	
	public static void main(String[] args)
	 {
		 RestClient restClient = null;
		 int port = 8085;
		 restClient = setPort(port);
		//TODO add logger (how eyal did this thing???)
		 AdminFunctions adminFunctions = new AdminFunctions();
		ClubFunctions clubFunctions = new ClubFunctions();
		storeFunctions storeFunctions = new storeFunctions();
		benefitsFunctions benefitsFunctions = new benefitsFunctions();
		
		UserId userid = preMenu(restClient);
		int choise = 0;
		while (choise!=7)
		{
			choise = menu();
			switch (choise) {
			case 1: {
				System.out.println("\nenter the club number you want to delete:");
				int clubNum = Integer.parseInt(scanner.next());
				try {
					adminFunctions.deleteClub(restClient, userid, clubNum);
					
				} catch (Exception e) {
				    System.out.println("Exception caught: " + e.getClass().getSimpleName());
				    System.out.println("Message: " + e.getMessage());
				}
				
				break;
			}
			case 2: {
				System.out.println("\nenter the store number you want to delete:");
				int storeNum = Integer.parseInt(scanner.next());
				try {
					adminFunctions.deleteStore(restClient, userid, storeNum);
					
				} catch (Exception e) {
				    System.out.println("Exception caught: " + e.getClass().getSimpleName());
				    System.out.println("Message: " + e.getMessage());
				}
				
				break;
				
			}
			case 3: {
				
				//TODO delete benefit
				System.out.println("\nenter the store number you want to see the benefits:");
				int storeNum = Integer.parseInt(scanner.next());
				try {
					storeFunctions.printAllBenefitsOfStore(restClient, userid, storeNum);
					
				} catch (Exception e) {
				    System.out.println("Exception caught: " + e.getClass().getSimpleName());
				    System.out.println("Message: " + e.getMessage());
				}
				
				break;
			}
			case 4: {				
				System.out.println("\nenter the club number you want to see the benefits:");
				int choiseClub = Integer.parseInt(scanner.next());
				try {
					clubFunctions.printAllBenefitOfClub(restClient, userid, choiseClub);
					
				} catch (Exception e) {
				    System.out.println("Exception caught: " + e.getClass().getSimpleName());
				    System.out.println("Message: " + e.getMessage());
				}
				
				break;
			}
			case 5: {
				//TODO int benefit string benefit check 
				System.out.println("\nenter the benefit number\name you want to see int the club \nif it's a new benefit enter 99:");
				int benefit = Integer.parseInt(scanner.next());
				System.out.println("add the club number:");
    			scanner.nextLine();

				int club = Integer.parseInt(scanner.next());
				if (benefit==99)
					benefit = benefitsFunctions.addBenefit(restClient, userid);
				
				
				try {
					clubFunctions.addBenefitToClub(restClient, userid, club, benefit);
					
				} catch (Exception e) {
				    System.out.println("Exception caught: " + e.getClass().getSimpleName());
				    System.out.println("Message: " + e.getMessage());
				}
				
				break;

				
			}
			case 6: {
				try {
					benefitsFunctions.addBenefit(restClient, userid);
					
				} catch (Exception e) {
				    System.out.println("Exception caught: " + e.getClass().getSimpleName());
				    System.out.println("Message: " + e.getMessage());
				}
				
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
        scanner.close();	
	 }

	private static UserId preMenu(RestClient restClient) {
		// TODO a menu do you have an account yes or no? and if yes what is the email...
	        System.out.println("\n\n");
	        System.out.println("==================================");
	        System.out.println("           WELCOME             ");
	        System.out.println("==================================");
	        System.out.println("do you have a user? ");
	        System.out.println("1-yes");
	        System.out.println("2-no let's make one");
	        int choise = Integer.parseInt(scanner.next());
	        if (choise==1)
	        {
	        	String email = null;
	        	UserBoundary user = null;
	        	boolean u = true;
	        	while (u)
	        	{
	        		try {
	        			scanner.nextLine();
	        			System.out.println("what is the email?");
	    	        	email = scanner.next();
						user = restClient.get().uri("/users/login/"
								+ "{superapp}/{email}",applicationName , email)
								.retrieve()
								.body(UserBoundary.class);
						u= false;
					} catch (Exception e) {
						System.out.println("user not found ");
					}
	        	}
	        	//TODO check is the email in the database?
	        	return user.getUserId();
	        }
	        return makeANewAccount( restClient);
	}

	private static int menu() {
		scanner.nextLine();

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
        return choise;
	}
	private static UserId makeANewAccount(RestClient restClient)
	{
		System.out.println("what is the email:"); 
		String email = scanner.next();
		System.out.println("what is the user avatar (url link):"); 
		String avatar = scanner.next();	
		System.out.println("what is the user name:"); 
		String user = scanner.next();		
		System.out.println("what is the role:"); 
		RoleEnumBoundary roleEnumBoundary = null;
		while (roleEnumBoundary == null) {
			
			String role = scanner.next();
			try {
				roleEnumBoundary = RoleEnumBoundary.valueOf(role.toUpperCase());
			} catch (IllegalArgumentException e) {
				System.out.println("the role is not define...");
			}
		}
		//sent a post of new user 
		NewUserBoundary newUserBoundary = new NewUserBoundary();
		newUserBoundary.setUsername(user);
		newUserBoundary.setEmail(email);
		newUserBoundary.setRole(roleEnumBoundary);
		newUserBoundary.setAvatar(avatar);
		UserBoundary user1 = null;
		try {
			user1  = restClient.post().uri("/users").body(newUserBoundary).retrieve().body(UserBoundary.class);
		}catch(BoundaryIsNotFilledCorrectException e){
			System.out.println(e.toString());
		}
		System.out.println("a new user has add: \n\n"+user1.toString());
		return user1.getUserId();
		
	}

}
