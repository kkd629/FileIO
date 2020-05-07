package javastory.club.stage3.step4.service.dto;

import java.util.ArrayList;
import java.util.List;

import javastory.club.stage3.step1.entity.club.ClubMembership;
import javastory.club.stage3.step1.entity.club.TravelClub;
import javastory.club.stage3.util.DateUtil;

public class TravelClubDto {
	//
	private String usid;
	private String name;
	private String intro;
	private String foundationDay;
	
	private List<ClubMembershipDto> membershipList;
	
	private TravelClubDto() {
		//
		this.membershipList = new ArrayList<ClubMembershipDto>();
	}
	
	public TravelClubDto(String name, String intro) {
		//
		this();
		this.name = name;
		this.intro = intro;
		this.foundationDay = DateUtil.today();
	}
	
	public TravelClubDto(TravelClub club) {
		//
		this();
		usid = club.getUsid();
		name = club.getName();
		intro = club.getIntro();
		foundationDay = club.getFoundationDay();
		
		for (ClubMembership membership : club.getMembershipList()) {
			//
			membershipList.add(new ClubMembershipDto(membership));
		}
	}

	public TravelClub toTravelClub() {
		//
		TravelClub travelClub = new TravelClub(name, intro);
		travelClub.setUsid(usid);
		travelClub.setFoundationDay(foundationDay);
		
		for (ClubMembershipDto membershipDto : membershipList) {
			//
			travelClub.getMembershipList().add(membershipDto.toMembership());
		} 
		return travelClub;
	}
	
	@Override
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();

		builder.append("Travel Club Id:").append(usid);
		builder.append(", name:").append(name);
		builder.append(", intro:").append(intro);
		builder.append(", foundation day:").append(foundationDay);
		int i=0; 
		for(ClubMembershipDto membership : membershipList) {
			builder.append(" ["+ i +"] Club member ").append(membership.toString()).append("\n");
			i++;
		}
		
		return builder.toString(); 
	}
	
	public String getUsid() {
		return usid; 
	}

	public void setUsid(String usid) {
		//
		this.usid = usid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getFoundationDay() {
		return foundationDay;
	}

	public void setFoundationDay(String foundationDay) {
		this.foundationDay = foundationDay;
	}

	public List<ClubMembershipDto> getMembershipList() {
		return membershipList;
	}
}
