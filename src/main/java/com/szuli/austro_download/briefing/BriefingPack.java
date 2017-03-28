package com.szuli.austro_download.briefing;

import java.util.HashMap;
import java.util.Map;


public class BriefingPack {

	
	private Map<String, Briefing> briefings = new HashMap<String, Briefing>();
	
	
	public BriefingPack() {
	}

	
	public void addBriefing(Briefing briefing) {
		if (briefings == null) {
			briefings = new HashMap<String, Briefing>();
		}
		briefings.put(briefing.getName(), briefing);
	}

	
	public Map<String, Briefing> getBriefings() {
		return briefings;
	}
	
	
	public Briefing getBriefing(String name) {
		return briefings.get(name);
	}

	
	public void setBriefings(Map<String, Briefing> briefings) {
		this.briefings = briefings;
	}
}
