package javastory.club.stage3.step4.service;

import java.util.List;

import javastory.club.stage3.step4.service.dto.PostingDto;

public interface PostingService {
	//
	public String register(String boardId, PostingDto posting); 
	public PostingDto find(String postingId); 
	public List<PostingDto> findByBoardId(String boardId);
	public void modify(PostingDto newPosting);
	public void remove(String postingId);
	public void removeAllIn(String boardId);
}