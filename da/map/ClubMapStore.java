package javastory.club.stage3.step4.da.map;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import javastory.club.stage3.step1.entity.AutoIdEntity;
import javastory.club.stage3.step1.entity.club.CommunityMember;
import javastory.club.stage3.step1.entity.club.TravelClub;
import javastory.club.stage3.step4.da.map.io.MemoryMap;
import javastory.club.stage3.step4.store.ClubStore;
import javastory.club.stage3.step4.util.ClubDuplicationException;
import namoosori.fileserver.step1.folder.FileStore;
import namoosori.fileserver.step1.folder.NamooFile;
import namoosori.fileserver.step1.folder.NamooFileFinder;

public class ClubMapStore implements ClubStore {
	//
	private Map<String,TravelClub> clubMap; 
	
	private String[] storePaths;
	private String fileName;
	
	private FileStore fileStore;
	private NamooFile namooFile;
	private TravelClub travelClub;
	
	int keySequence = 1;
	private JSONArray clubArray;
	private JSONObject jsonObject;

	public ClubMapStore() {
		//  
		this.clubMap = MemoryMap.getInstance().getClubMap(); 
		
		this.storePaths = new String[] {"resource", "club", "step1"};
		this.fileName = "club.json";
		
		this.namooFile = new NamooFileFinder().find(storePaths, fileName);
		this.clubArray = new JSONArray();
		this.jsonObject = new JSONObject();
		this.fileStore = new FileStore("club");
	}

	@Override
	public String create(TravelClub club) {
		//
		Optional.ofNullable(club.getId()).ifPresent(targetClub->
		{throw new ClubDuplicationException("Club already exists with id: " + club.getId()); });
	
		String autoId = String.format("%05d", keySequence); 
		club.setAutoId(autoId);
		
		JSONObject clubInfo = new JSONObject();
		
		clubInfo.put("usid", keySequence);
		clubInfo.put("name", club.getName());
		clubInfo.put("intro", club.getIntro());
		
		clubArray.add(clubInfo);
		jsonObject.put("clubs", clubArray);
		keySequence++;
		
		namooFile.write(jsonObject.toJSONString());
		
		return club.getId();
	}

	@Override
	public TravelClub retrieve(String clubId) {
		// 
		return null;
	}

	@Override
	public TravelClub retrieveByName(String name) {
		//
JSONParser parser = new JSONParser();
		
		try {
			
			Object obj = parser.parse(String.valueOf(namooFile.read()));
			JSONObject jsonObject = (JSONObject) obj;
			Gson gson = new Gson();
			
			clubArray = (JSONArray)jsonObject.get("clubs");
			for(int i =0; i<clubArray.size(); i++) {
				jsonObject = (JSONObject)clubArray.get(i);
				
				System.out.println(jsonObject.get("name"));
				System.out.println(name);
				
				if(jsonObject.get("name").equals(name)) {

					String foundClub = jsonObject.toString();
					travelClub = gson.fromJson(foundClub, TravelClub.class);

				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return travelClub; 
	}

	@Override
	public void update(TravelClub club) {
		//
		clubMap.put(club.getId(), club);
	}

	@Override
	public void delete(String clubId) {
		// 
		clubMap.remove(clubId); 
	}

	@Override
	public boolean exists(String clubId) {
		//
		return Optional.ofNullable(clubMap.get(clubId)).isPresent();
	}
}