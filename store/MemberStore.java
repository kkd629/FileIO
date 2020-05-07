package javastory.club.stage3.step4.store;

import java.util.List;

import javastory.club.stage3.step1.entity.club.CommunityMember;

public interface MemberStore {
	//
	public String create(CommunityMember member); 
	public CommunityMember retrieve(String email); 
	public List<CommunityMember> retrieveByName(String name); 
	public void update(CommunityMember member); 
	public void delete(String email); 
	
	public boolean exists(String email); 
}
