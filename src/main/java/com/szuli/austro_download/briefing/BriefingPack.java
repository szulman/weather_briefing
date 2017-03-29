package com.szuli.austro_download.briefing;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class BriefingPack {

	
	private LinkedHashMap<String, Briefing> briefings = new LinkedHashMap<String, Briefing>();
	
	
	public BriefingPack() {
	}

	
	public void addBriefing(Briefing briefing) {
		if (briefings == null) {
			briefings = new LinkedHashMap<String, Briefing>();
		}
		briefings.put(briefing.getName(), briefing);
	}

	
	public Map<String, Briefing> getBriefings() {
		return briefings;
	}
	
	
	public Briefing getBriefing(String name) {
		return briefings.get(name);
	}

	
	public void setBriefings(LinkedHashMap<String, Briefing> briefings) {
		this.briefings = briefings;
	}
}
