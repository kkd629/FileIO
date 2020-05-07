package javastory.club.stage3.step4.ui.menu;

import java.util.Scanner;

import javastory.club.stage3.step4.ui.console.ClubMembershipConsole;
import javastory.club.stage3.util.Narrator;
import javastory.club.stage3.util.TalkingAt;

public class ClubMembershipMenu {
	//
	private ClubMembershipConsole clubMembershipConsole;

	private Scanner scanner;
	private Narrator narrator; 

	public ClubMembershipMenu() {
		//
		this.clubMembershipConsole = new ClubMembershipConsole();  
		this.scanner = new Scanner(System.in); 
		this.narrator = new Narrator(this, TalkingAt.Left); 
	}

	public void show() {
		//
		int inputNumber = 0;

		while(true){
			displayMenu();
			inputNumber = selectMenu();

			switch (inputNumber) {
			//
			case 1:
				clubMembershipConsole.findClub(); 
				break;
			case 2:
				clubMembershipConsole.add();
				break;
			case 3:
				clubMembershipConsole.find();
				break;
			case 4:
				clubMembershipConsole.modify();
				break;
			case 5:
				clubMembershipConsole.remove();
				break;
			case 0:
				return;

			default:
				narrator.sayln("Choose again!");
			}
		}
	}

	private void displayMenu() {
		//
		narrator.sayln("");
		narrator.sayln("..............................");
		if (clubMembershipConsole.hasCurrentClub()) {
			narrator.sayln(" Membership menu for[" + clubMembershipConsole.requestCurrentClubName() + "]");
		} else {
			narrator.sayln(" Membership menu ");
		}
		narrator.sayln("..............................");
		narrator.sayln(" 1. Find a club");
		narrator.sayln(" 2. Add a membership");
		narrator.sayln(" 3. Find a membership");
		narrator.sayln(" 4. Modify a membership");
		narrator.sayln(" 5. Remove a membership");
		narrator.sayln("..............................");
		narrator.sayln(" 0. Previous");
		narrator.sayln("..............................");
	}

	private int selectMenu() {
		//
		narrator.say("Select: ");
		int menuNumber = scanner.nextInt();

		if (menuNumber >= 0 && menuNumber <= 5) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln("It's a invalid number --> " + menuNumber);
			return -1;
		}
	}
}