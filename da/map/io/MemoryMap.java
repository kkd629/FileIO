package javastory.club.stage3.step4.da.map.io;

import java.util.LinkedHashMap;
import java.util.Map;

import javastory.club.stage3.step1.entity.board.Posting;
import javastory.club.stage3.step1.entity.board.SocialBoard;
import javastory.club.stage3.step1.entity.club.CommunityMember;
import javastory.club.stage3.step1.entity.club.TravelClub;

public class MemoryMap {
	//
	private static MemoryMap singletonMap; 
	
	private Map<String,CommunityMember>  memberMap; 
	private Map<String,TravelClub>  clubMap; 
	private Map<String,SocialBoard> boardMap; 
	private Map<String,Posting> postingMap; 
	private Map<String,Integer> autoIdMap; 
	
	private MemoryMap() {
		// 
		this.memberMap = new LinkedHashMap<>(); 
		this.clubMap = new LinkedHashMap<>(); 
		this.boardMap = new LinkedHashMap<>(); 
		this.postingMap = new LinkedHashMap<>();
		this.autoIdMap = new LinkedHashMap<>(); 
	}
	
	public static MemoryMap getInstance() {
		// 
		if (singletonMap == null) {
			singletonMap = new MemoryMap(); 
		}
		
		return singletonMap; 
	}
	
	public Map<String,Integer> getAutoIdMap() {
		// 
		return this.autoIdMap; 
	}
	
	public Map<String,CommunityMember> getMemberMap() {
		return memberMap; 
	}
	
	public Map<String,TravelClub> getClubMap() {
		return clubMap; 
	}
	
	public Map<String,SocialBoard> getBoardMap() {
		return boardMap; 
	}
	
	public Map<String,Posting> getPostingMap() {
		return postingMap; 
	}
}