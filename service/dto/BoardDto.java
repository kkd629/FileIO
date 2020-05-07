package javastory.club.stage3.step4.service.dto;

import javastory.club.stage3.step1.entity.board.SocialBoard;
import javastory.club.stage3.util.DateUtil;

public class BoardDto {
	//
    private String clubId;		
    private String name;
    private String adminEmail;		
    private String createDate;
    
    public BoardDto(String clubId, String name, String adminEmail) {
        //  
        this.clubId = clubId; 
        this.name = name;
        this.adminEmail = adminEmail;
        this.createDate = DateUtil.today(); 
    }
    
    public BoardDto (SocialBoard socialBoard) {
    	//
    	if (socialBoard == null) {
    		return;
    	}
    	this.clubId = socialBoard.getClubId();
    	this.name = socialBoard.getName();
    	this.adminEmail = socialBoard.getAdminEmail();
    	this.createDate = socialBoard.getCreateDate();
    }

	public SocialBoard toBoard() {
		//
		SocialBoard socialBoard = new SocialBoard(clubId, name, adminEmail);
		socialBoard.setCreateDate(createDate);
		return socialBoard;
	}
	
    @Override
    public String toString() {
        //
        StringBuilder builder = new StringBuilder();
        
        builder.append("SocialBoard id: ").append(clubId);
        builder.append(", name: ").append(name); 
        builder.append(", admin email: ").append(adminEmail);
        builder.append(", creation date: ").append(createDate);
        
        return builder.toString();
    }
    
    public String getId() {
		//
		return this.clubId;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
