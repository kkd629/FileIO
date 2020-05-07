package javastory.club.stage3.step4.da.map;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javastory.club.stage3.step1.entity.club.CommunityMember;
import javastory.club.stage3.step4.da.map.io.MemoryMap;
import javastory.club.stage3.step4.store.MemberStore;
import javastory.club.stage3.step4.util.MemberDuplicationException;
import namoosori.fileserver.step1.folder.FileStore;
import namoosori.fileserver.step1.folder.NamooFile;
import namoosori.fileserver.step1.folder.NamooFileFinder;

public class MemberMapStore implements MemberStore {
	//

	private String[] storePaths;
	private String fileName;

	private NamooFile namooFile;
	private JSONObject jsonObject;
	private JSONArray memberArray;
	
	private CommunityMember communityMember;
	private List<CommunityMember> members;
	
	public MemberMapStore() {
		//  
		this.storePaths = new String[] {"resource", "member", "step1"};
		this.fileName = "member.json";

		this.namooFile = new NamooFileFinder().find(storePaths, fileName);
		namooFile.create();
		this.jsonObject = new JSONObject();
		this.memberArray = new JSONArray();
	}
	
	
	@Override
	public String create(CommunityMember member) {
		//
		JSONObject memberInfo = new JSONObject();
		
		memberInfo.put("phoneNumber", member.getPhoneNumber());
		memberInfo.put("name", member.getName());
		memberInfo.put("email", member.getEmail());
		
		memberArray.add(memberInfo);
		jsonObject.put("members", memberArray);

		namooFile.write(jsonObject.toJSONString());
		
		return member.getId();
	}

	@Override
	public CommunityMember retrieve(String memberId) {
		// 
		JSONParser parser = new JSONParser();

		try {
			
			Object obj = parser.parse(String.valueOf(namooFile.read()));
			JSONObject jsonObject = (JSONObject) obj;
			Gson gson = new Gson();
			
			memberArray = (JSONArray)jsonObject.get("members");
			for(int i =0; i<memberArray.size(); i++) {
				jsonObject = (JSONObject)memberArray.get(i);
				
				if(jsonObject.get("email").equals(memberId)) {

					String member = jsonObject.toString();
					communityMember = gson.fromJson(member, CommunityMember.class);

				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return communityMember;
	}
	
	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
		JSONParser parser = new JSONParser();

		try {
			
			Object obj = parser.parse(String.valueOf(namooFile.read()));
			JSONObject jsonObject = (JSONObject) obj;
			Gson gson = new Gson();
			
			List<CommunityMember> members = new ArrayList<CommunityMember>();
			
			memberArray = (JSONArray)jsonObject.get("members");
			for(int i =0; i<memberArray.size(); i++) {
				jsonObject = (JSONObject)memberArray.get(i);
				
				if(jsonObject.get("name").equals(name)) {
					
					String member = jsonObject.toString();
					communityMember = gson.fromJson(member, CommunityMember.class);
					members.add(communityMember);
					
//					System.out.println(gson.fromJson(a, CommunityMember.class));
//					System.out.println(jsonObject);
//					System.out.println("equal");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	@Override
	public void update(CommunityMember member) {
		//
		NamooFile namooFile = new NamooFileFinder().find(storePaths, fileName);
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(String.valueOf(namooFile.read()));
			JSONObject jsonObject = (JSONObject) obj;
			Gson gson = new Gson();
			
			memberArray = (JSONArray)jsonObject.get("members");
			String deleteMember = null;
			
			for(int i =0; i<memberArray.size(); i++) {
				jsonObject = (JSONObject)memberArray.get(i);
				
				if(jsonObject.get("email").equals(member.getEmail())) {
					memberArray.remove(member.getEmail());
					memberArray.add(member);
					deleteMember = jsonObject.toJSONString();
					namooFile.write(deleteMember);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String memberId) {
		
		NamooFile namooFile = new NamooFileFinder().find(storePaths, fileName);
		JSONParser parser = new JSONParser();
		
		Object obj;
		try {
			
			obj = parser.parse(String.valueOf(namooFile.read()));
			JSONObject jsonObject = (JSONObject) obj;

			Gson gson = new Gson();
			String deleteMember = null;
			memberArray = (JSONArray)jsonObject.get("members");
			
			for(int i =0; i<memberArray.size(); i++) {
				jsonObject = (JSONObject)memberArray.get(i);
				
				if(!jsonObject.get("email").equals(memberId)) {
					memberArray.remove(i);
					
					String member = jsonObject.toString();
					communityMember = gson.fromJson(member, CommunityMember.class);
					deleteMember = jsonObject.toJSONString();
					namooFile.write(deleteMember);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean exists(String memberId) {
		//
		if(communityMember.getEmail().equals(memberId)) {
			return true;
		} else {
			return false;
		}
	}

}

