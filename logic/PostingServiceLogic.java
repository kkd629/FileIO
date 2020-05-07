package javastory.club.stage3.step4.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javastory.club.stage3.step1.entity.board.Posting;
import javastory.club.stage3.step4.service.PostingService;
import javastory.club.stage3.step4.service.dto.PostingDto;
import javastory.club.stage3.step4.store.BoardStore;
import javastory.club.stage3.step4.store.ClubStore;
import javastory.club.stage3.step4.store.PostingStore;
import javastory.club.stage3.step4.util.NoSuchBoardException;
import javastory.club.stage3.step4.util.NoSuchMemberException;
import javastory.club.stage3.step4.util.NoSuchPostingException;
import javastory.club.stage3.util.StringUtil;
import javastory.club.stage3.step4.da.map.ClubStoreMapLycler;


public class PostingServiceLogic implements PostingService {
	//
	private BoardStore boardStore;
	private PostingStore postingStore;
	private ClubStore clubStore;

	public PostingServiceLogic() {
		//
		this.boardStore = ClubStoreMapLycler.getInstance().requestBoardStore();
		this.postingStore = ClubStoreMapLycler.getInstance().requestPostingStore();
		this.clubStore = ClubStoreMapLycler.getInstance().requestClubStore();
	}

	@Override
	public String register(String boardId, PostingDto postingDto) {
		//
		Optional.ofNullable(clubStore.retrieve(boardId))
				.map(club->club.getMembershipBy(postingDto.getWriterEmail()))
				.orElseThrow(()->new NoSuchMemberException("In the club, No such member with email -->" + postingDto.getWriterEmail()));
		return Optional.ofNullable(boardStore.retrieve(boardId))
				.map(board->postingStore.create(postingDto.toPostingIn(board)))
				.orElseThrow(()->new NoSuchBoardException("No such board with id --> " + boardId));
	}

	@Override
	public PostingDto find(String postingId) {
		//
		return Optional.ofNullable(postingStore.retrieve(postingId))
				.map(posting->new PostingDto(posting))
				.orElseThrow(()->new NoSuchPostingException("No such a posting with id : " + postingId));
	}

	@Override
	public List<PostingDto> findByBoardId(String boardId) {
		//
		Optional.ofNullable(boardStore.retrieve(boardId)).orElseThrow(()->new NoSuchBoardException("No such board with id --> " + boardId));

		return postingStore.retrieveByBoardId(boardId).stream().map(posting->new PostingDto(posting)).collect(Collectors.toList());
	}

	@Override
	public void modify(PostingDto newPosting) {
		//
		String postingId = newPosting.getUsid();

		Posting targetPosting = Optional.ofNullable(postingStore.retrieve(postingId)).orElseThrow(()->new NoSuchPostingException("No such a posting with id : " + postingId));

		
		// modify if only user inputs some value.
		if (StringUtil.isEmpty(newPosting.getTitle())) {
			newPosting.setTitle(targetPosting.getTitle());
		}
		if (StringUtil.isEmpty(newPosting.getContents())) {
			newPosting.setContents(targetPosting.getContents());
		}
		
		
		postingStore.update(newPosting.toPostingIn(postingId, targetPosting.getBoardId()));
	}

	@Override
	public void remove(String postingId) {
		//
		if (!postingStore.exists(postingId)) {
			throw new NoSuchPostingException("No such a posting with id : " + postingId);
		}
		postingStore.delete(postingId);
	}

	@Override
	public void removeAllIn(String boardId) {
		//
		postingStore.retrieveByBoardId(boardId)
				.stream()
				.forEach(posting->postingStore.delete(posting.getId()));
	}
}
