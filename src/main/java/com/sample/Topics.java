package com.sample;

public class Topics {
	
	private String[] topics;
	
	public Topics() {
		
	}
	
	public Topics(String[] topics) {
		this.topics = topics;		
	}
	
	public String[] getTopics() {
		return this.topics;
	}
	
	public int getTopicsLength()
	{
		return this.topics.length;
	}
	
	public String getSpecificTopic(int indx) {		
		try {
			if( indx <= topics.length)
				return this.topics[indx];
			else
				return "Sorry no more topics, proceed further";
		}
		catch(Exception e) {			
			return "Sorry no more topics, proceed further";
		}
	}
	
	public String getFirstTopic() {		
		try {
			return this.topics[0];
		}
		catch(Exception e) {			
			return "Blank Topic";
		}
	}
	
	public String getSecondTopic() {		
		try {
			return this.topics[1];
		}
		catch(Exception e) {			
			return "Blank Topic";
		}
	}
	
	public String getThirdTopic() {		
		try {
			return this.topics[2];
		}
		catch(Exception e) {			
			return "Blank Topic";
		}
	}
	
	public void setTopics(String[] topics) {
		this.topics = topics;
	}	
}
