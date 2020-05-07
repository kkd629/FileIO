package javastory.club.stage3.step4.store;

import java.util.List;

import javastory.club.stage3.step1.entity.board.Posting;

public interface PostingStore {
	//
	public String create(Posting posting); 
	public Posting retrieve(String postingId);
	public List<Posting> retrieveByBoardId(String boardId);
	public List<Posting> retrieveByTitle(String title);
	public void update(Posting posting); 
	public void delete(String posingId); 
	
	public boolean exists(String postingId); 
}