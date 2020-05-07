package javastory.club.stage3.step4.service;

import java.util.List;

import javastory.club.stage3.step4.service.dto.ClubMembershipDto;
import javastory.club.stage3.step4.service.dto.TravelClubDto;

public interface ClubService {
	//
	public void registerClub(TravelClubDto club); 
	public TravelClubDto findClub(String clubId); 
	public TravelClubDto findClubByName(String name);
	public void modify(TravelClubDto club); 
	public void remove(String clubId); 
	
	void addMembership(ClubMembershipDto membershipDto); 
	public ClubMembershipDto findMembershipIn(String clubId, String memberId);
	//public List<ClubMembershipDto> findAllMembershipsIn(String clubId);
	public void modifyMembership(String clubId, ClubMembershipDto member);
	public void removeMembership(String clubId, String memberId);
}