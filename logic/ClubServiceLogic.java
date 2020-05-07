package javastory.club.stage3.step4.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javastory.club.stage3.step1.entity.club.ClubMembership;
import javastory.club.stage3.step1.entity.club.CommunityMember;
import javastory.club.stage3.step1.entity.club.RoleInClub;
import javastory.club.stage3.step1.entity.club.TravelClub;
import javastory.club.stage3.step4.service.ClubService;
import javastory.club.stage3.step4.service.dto.ClubMembershipDto;
import javastory.club.stage3.step4.service.dto.TravelClubDto;
import javastory.club.stage3.step4.store.ClubStore;
import javastory.club.stage3.step4.store.MemberStore;
import javastory.club.stage3.step4.util.ClubDuplicationException;
import javastory.club.stage3.step4.util.MemberDuplicationException;
import javastory.club.stage3.step4.util.NoSuchClubException;
import javastory.club.stage3.step4.util.NoSuchMemberException;
import javastory.club.stage3.util.StringUtil;
import javastory.club.stage3.step4.da.map.ClubStoreMapLycler;

public class ClubServiceLogic implements ClubService {
	//
	private ClubStore clubStore;
	private MemberStore memberStore;

	public ClubServiceLogic() {
		//
		this.clubStore = ClubStoreMapLycler.getInstance().requestClubStore(); //教臂沛 按眉 积己
		this.memberStore = ClubStoreMapLycler.getInstance().requestMemberStore();
	}

	@Override
	public void registerClub(TravelClubDto clubDto) {
		//
		Optional.ofNullable(clubStore.retrieveByName(clubDto.getName())).ifPresent(dto->{throw new ClubDuplicationException("It is already exist the club name:" + clubDto.getName());});

		TravelClub club = clubDto.toTravelClub();
		String clubId = clubStore.create(club);

		clubDto.setUsid(clubId);


	}

	@Override
	public TravelClubDto findClub(String clubId) {
		//
		return Optional.ofNullable(clubStore.retrieve(clubId))
				.map(club->new TravelClubDto(club))
				.orElseThrow(()-> new NoSuchClubException(" club with id: " + clubId));

	}

	@Override
	public TravelClubDto findClubByName(String name) {
		//
		return Optional.ofNullable(clubStore.retrieveByName(name))
				.map(club->new TravelClubDto(club))
				.orElseThrow(()-> new NoSuchClubException("No such a club with name: " + name));

	}

	@Override
	public void modify(TravelClubDto clubDto) {
		//
		Optional.ofNullable(clubStore.retrieveByName(clubDto.getName())).ifPresent(club->{throw new ClubDuplicationException("It is already exist the club name:" + clubDto.getName());} );
		TravelClub targetClub = Optional.ofNullable(clubStore.retrieve(clubDto.getUsid())).orElseThrow(()->new NoSuchClubException("No such a club with id: " + clubDto.getUsid()));
		
		if(StringUtil.isEmpty(clubDto.getName())) {
			clubDto.setName(targetClub.getName());
		}
		
		if(StringUtil.isEmpty(clubDto.getIntro())) {
			clubDto.setIntro(targetClub.getIntro());
		}
		
		clubStore.update(clubDto.toTravelClub());
	}

	@Override
	public void remove(String clubId) {
		//
		if (!clubStore.exists(clubId)) {
			throw new NoSuchClubException("No such a club with id: " + clubId);
		}

		clubStore.delete(clubId);
	}

	// Membership
	@Override
	public void addMembership(ClubMembershipDto membershipDto) {
		//
		// check existing member
		String memberId = membershipDto.getMemberEmail();

		CommunityMember member = Optional.ofNullable(memberStore.retrieve(memberId)).orElseThrow(()->new NoSuchClubException("No such a member with email: " + memberId));
		
		// check existing membership in the club
		TravelClub club = clubStore.retrieve(membershipDto.getClubId());
		for (ClubMembership membership : club.getMembershipList()) {
			if(memberId.equals(membership.getMemberEmail())) {
				throw new MemberDuplicationException("There is member in club already -->" + memberId);
			}
		}

		// add membership
		ClubMembership clubMembership = membershipDto.toMembership();
		club.getMembershipList().add(clubMembership);
		clubStore.update(club);
		member.getMembershipList().add(clubMembership);
		memberStore.update(member);
	}

	@Override
	public ClubMembershipDto findMembershipIn(String clubId, String memberId) {
		//
		TravelClub club = clubStore.retrieve(clubId);

		ClubMembership membership = getMembershipIn(club, memberId);

		return new ClubMembershipDto(membership);
	}

//	@Override
//	public List<ClubMembershipDto> findAllMembershipsIn(String clubId) {
//		//
//		TravelClub club = clubStore.retrieve(clubId);
//
//		return club.getMembershipList()
//				.stream()
//				.map(membership->new ClubMembershipDto(membership))
//				.collect(Collectors.toList());
//	}

	@Override
	public void modifyMembership(String clubId, ClubMembershipDto newMembership) {
		//
		String targetEmail = newMembership.getMemberEmail();
		RoleInClub newRole = newMembership.getRole();

		// modify membership of the club.
		TravelClub targetClub = clubStore.retrieve(clubId);
		ClubMembership membershipOfClub = getMembershipIn(targetClub, targetEmail);
		membershipOfClub.setRole(newRole);
		clubStore.update(targetClub);

		// modify membership of the member.
		CommunityMember targetMember = memberStore.retrieve(targetEmail);
		targetMember.getMembershipList().stream().forEach(membershipOfMember->{
			if(membershipOfMember.getClubId().equals(clubId)) membershipOfMember.setRole(newRole);
		});
		memberStore.update(targetMember);

	}

	@Override
	public void removeMembership(String clubId, String memberId) {
		//
		TravelClub foundClub = clubStore.retrieve(clubId);
		CommunityMember foundMember = memberStore.retrieve(memberId);
		ClubMembership clubMembership = getMembershipIn(foundClub, memberId);

		// remove membership
		foundClub.getMembershipList().remove(clubMembership);
		clubStore.update(foundClub);
		foundMember.getMembershipList().remove(clubMembership);
		memberStore.update(foundMember);

	}

	private ClubMembership getMembershipIn(TravelClub club, String memberEmail) {
		//
		for (ClubMembership membership : club.getMembershipList()) {
			if(memberEmail.equals(membership.getMemberEmail())) {
				return membership;
			}
		}

		String message = String.format("No such a member[email:%s] in club[name:%s]", memberEmail, club.getName());
		throw new NoSuchMemberException(message);
	}
}
