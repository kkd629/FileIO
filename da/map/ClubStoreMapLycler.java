package javastory.club.stage3.step4.da.map;

import javastory.club.stage3.step4.store.BoardStore;
import javastory.club.stage3.step4.store.ClubStore;
import javastory.club.stage3.step4.store.ClubStoreLycler;
import javastory.club.stage3.step4.store.MemberStore;
import javastory.club.stage3.step4.store.PostingStore;

public class ClubStoreMapLycler implements ClubStoreLycler {
	//
	private static ClubStoreLycler lycler; 
	
	private ClubStoreMapLycler() {
	}; 
	
	public static ClubStoreLycler getInstance() {
		// 
		if (lycler == null) {
			lycler = new ClubStoreMapLycler(); 
		}
		
		return lycler; 
	}
	
	@Override
	public MemberStore requestMemberStore() {
		// 
		return new MemberMapStore();
	}

	@Override
	public ClubStore requestClubStore() {
		// 
		return new ClubMapStore();
	}

	@Override
	public BoardStore requestBoardStore() {
		// 
		return new BoardMapStore();
	}

	@Override
	public PostingStore requestPostingStore() {
		// 
		return new PostingMapStore();
	}
}