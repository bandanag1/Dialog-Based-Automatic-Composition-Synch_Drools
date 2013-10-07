package com.sample;

public class UserModel {
	
	public Location getCurrentLocation() {
		return new Location("Berlin","Germany");
	}
	
	public Boolean speaksLanguageOf(Location location) {
		return false;
	}
	
	public Boolean isInTheSameCityAs(Location location) {
		return location.getCity().equals("Berlin");
	}
	
	public Topic getCurrentTopic() {
		return new Topic("Floods");
	}
	
	public Topics getCurrentTopics() {
		return new Topics(new String[]{"Floods", "Social Feeds", "FirstLineSupport"});
	}
	
	/*public Boolean isNumberOfTopicsOne(Topics topics) {
		System.out.println("topics length -- > " + topics.getTopicsLength());
		Boolean bOneTopic = false;
		if(!(topics.getFirstTopic().equals("")) && (topics.getTopicsLength() < 1))
			bOneTopic = true;		
		return bOneTopic;
	}*/
	
	public Boolean isNumberOfTopicsOne(Topics topics) {
		return topics.getTopicsLength() >= 1;
	}
	
	public Boolean isNumberOfTopicsTwo(Topics topics) {
		return topics.getTopicsLength() >= 2;
	}
	
	public Boolean isNumberOfTopicsThree(Topics topics) {
		return topics.getTopicsLength() >= 3;
	}
}
