package javastory.club.stage3.step4;

import java.util.Optional;

import javastory.club.stage3.step1.entity.club.CommunityMember;
import javastory.club.stage3.step1.entity.club.TravelClub;
import javastory.club.stage3.step4.da.map.ClubStoreMapLycler;
import javastory.club.stage3.step4.da.map.MemberMapStore;
import javastory.club.stage3.step4.da.map.io.MemoryMap;
import javastory.club.stage3.step4.logic.ClubServiceLogic;
import javastory.club.stage3.step4.logic.ServiceLogicLycler;
import javastory.club.stage3.step4.service.dto.ClubMembershipDto;
import javastory.club.stage3.step4.ui.menu.MainMenu;
import javastory.club.stage3.util.ConsoleUtil;
import javastory.club.stage3.util.Narrator;
import javastory.club.stage3.util.TalkingAt;

public class StoryAssistant {
	//
	private void startStory() {
		// 
		MainMenu mainMenu = new MainMenu();
		mainMenu.show();
	}
	
	public static void main(String[] args) {
		//
		MemoryMap.getInstance().getMemberMap().put("test@test.co.kr", new CommunityMember("test@test.co.kr", "test", "123"));
		//ClubStoreMapLycler.getInstance().requestClubStore().create(new TravelClub("test", "testestststestsetset"));
		//ServiceLogicLycler.shareInstance().createClubService().addMembership(new ClubMembershipDto("00001", "test@test.co.kr"));
		StoryAssistant assistant = new StoryAssistant(); 
		assistant.startStory();
	
	}
}