package com.szuli.austro_download.briefing;

import java.util.ArrayList;
import java.util.List;

public class BriefingPack {

	
	private List<Briefing> briefings = new ArrayList<Briefing>();
	
	
	public BriefingPack() {
	}

	
	public void addBriefing(Briefing briefing) {
		if (briefings == null) {
			briefings = new ArrayList<Briefing>();
		}
		briefings.add(briefing);
	}

	
	public List<Briefing> getBriefings() {
		return briefings;
	}
	
	
	public void setBriefings(List<Briefing> briefings) {
		this.briefings = briefings;
	}
}
