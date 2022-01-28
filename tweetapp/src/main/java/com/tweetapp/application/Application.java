package com.tweetapp.application;

import com.tweetapp.application.constants.UserStatus;
import com.tweetapp.application.dto.TweetDetail;
import com.tweetapp.application.dto.UserDetail;
import com.tweetapp.application.entity.User;
import com.tweetapp.application.service.tweetmanager.TweetService;
import com.tweetapp.application.service.usermanager.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages="com.tweetapp.application.*")
@EnableJpaRepositories(basePackages="com.tweetapp.application.*")
@EntityScan(basePackages="com.tweetapp.application.*")
public class Application {
	private static UserService userService;
	private static TweetService tweetService;

	@Autowired
	private UserService userServiceBean;

	@Autowired
	private TweetService tweetServiceBean;

	@PostConstruct
	public void init() {
		Application.userService = userServiceBean;
		Application.tweetService = tweetServiceBean;
	}

	public static void printMenu(String[] options){
		for (String option : options){
			System.out.println(option);
		}
		System.out.print("Choose your option : ");
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("\n\n");
		boolean userLoggedIn = false;
		String[] optionsForLoggedOut = {"1- Register",
										"2- Login",
										"3- Forgot Password",
										"0- Exit",
										};
		String[] optionsForLoggedIn = {"1- Post Tweet",
				"2- View my Tweets",
				"3- View all Tweets",
				"4- View all Users",
				"5- Reset Password",
				"6- Log Out",
				"0- Exit",
		};
		Scanner scanner = new Scanner(System.in);
		int option = 1;
		while (option!=0){
			if(userLoggedIn) {
				printMenu(optionsForLoggedIn);
			} else {
				printMenu(optionsForLoggedOut);
			}
			if(userLoggedIn) {
				try {
					option = scanner.nextInt();
					switch (option){
						case 1: postTweet(); break;
						case 2: viewMyTweets(); break;
						case 3: viewAllTweets(); break;
						case 4: viewAllUsers(); break;
						case 5: resetPassword(); break;
						case 6: {logOut(); userLoggedIn = false; break;}
						case 0: System.exit(0);
					}
				}
				catch (Exception ex){
					System.out.println("Please enter an integer value between 1 and " + optionsForLoggedIn.length);
					scanner.next();
				}
			} else {
				try {
					option = scanner.nextInt();
					switch (option){
						case 1: register(); break;
						case 2: userLoggedIn = logIn(); break;
						case 3: forgetPassword(); break;
						case 4: System.exit(0);
					}
				}
				catch (Exception ex){
					System.out.println("Something went wrong : " + ex.getMessage());
					continue;
				}
			}

		}
	}
	// Options
	private static void postTweet() {
		System.out.println("Type in your Tweet : \n");
		Scanner scanner = new Scanner(System.in);
		String tweetDescription = scanner.nextLine();
		TweetDetail tweetDetail = new TweetDetail(tweetDescription);
		tweetService.postTweet(tweetDetail);
		System.out.println("Tweet has been posted\n");
	}
	private static void viewMyTweets() {
		List<TweetDetail> tweetDetails = tweetService.getAllLoggedInUserTweets();
		System.out.println("Total Tweets : " + tweetDetails.size());
		tweetDetails.forEach(tweetDetail -> {
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println("User : " + tweetDetail.getUserName() + "\nDate: " + tweetDetail.getLastUpdatedOn() + "\nTweet : " + tweetDetail.getDescription());
		});
		System.out.println("----------------------------------------------------------------------------------------------------");
	}
	private static void viewAllTweets() {
		List<TweetDetail> tweetDetails = tweetService.getAllTweets();
		System.out.println("Total Tweets : " + tweetDetails.size());
		tweetDetails.forEach(tweetDetail -> {
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println("User : " + tweetDetail.getUserName() + "\nDate: " + tweetDetail.getLastUpdatedOn() + "\nTweet : " + tweetDetail.getDescription());

		});
		System.out.println("----------------------------------------------------------------------------------------------------");
	}
	private static void viewAllUsers() {
		List<UserDetail> userDetails = userService.getAllUsers();
		userDetails.forEach(userDetail -> {
			System.out.println("\nUser Name : " + userDetail.getUserName() + "\nCreated Date: " + userDetail.getCreatedOn() +"\n");
		});
	}
	private static void resetPassword() {
		System.out.println("Type UserName : \n");
		Scanner scanner = new Scanner(System.in);
		String userName = scanner.next();
		System.out.println("Type Password : \n");
		String password = scanner.next();
		UserDetail userDetail = new UserDetail(userName,password);
		if(userService.authenticateUser(userDetail)) {
			System.out.println("Type New Password : \n");
			String newPassword = scanner.next();
			userService.resetPassword(userDetail, newPassword);
			System.out.println("Password has been reset\n");
		} else {
			System.out.println("Incorrect Username or Password\n");
		}
	}
	private static void logOut() {
		User user = userService.getLoggedInUser();
		userService.updateUserStatus(user.getUserName(), UserStatus.LOGGED_OUT);
		System.out.println("Successfully Logged Out");
	}
	private static void register() {
		System.out.println("Type UserName : \n");
		Scanner scanner = new Scanner(System.in);
		String userName = scanner.next();
		System.out.println("Type Password : \n");
		String password = scanner.next();
		UserDetail userDetail = new UserDetail(userName,password);
		userService.registerUser(userDetail);
		System.out.println("User has been registered\n");
	}
	private static boolean logIn() {
		System.out.println("Type UserName : \n");
		Scanner scanner = new Scanner(System.in);
		String userName = scanner.next();
		System.out.println("Type Password : \n");
		String password = scanner.next();
		UserDetail userDetail = new UserDetail(userName,password);
		if(userService.authenticateUser(userDetail)) {
			userService.updateUserStatus(userDetail.getUserName(), UserStatus.LOGGED_IN);
			System.out.println("Login Successful!\n");
			return true;
		} else {
			System.out.println("Incorrect Username or Password\n");
			return false;
		}
	}
	private static void forgetPassword() {
		System.out.println("Type UserName : \n");
		Scanner scanner = new Scanner(System.in);
		String userName = scanner.next();
		if(userService.existsUser(userName)) {
			UserDetail userDetail = new UserDetail(userName,null);
			System.out.println("Type New Password : \n");
			String newPassword = scanner.next();
			userService.resetPassword(userDetail, newPassword);
			System.out.println("Password has been reset\n");
		} else {
			System.out.println("Provided UserName does not exist");
		}
	}

}
