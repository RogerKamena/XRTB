package com.xrtb.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xrtb.pojo.BidRequest;

/**
 * A class that implements a campaign. Provide the campaign with evaluation
 * nodes (a stack) and a bid request, and this campaign will determine if the
 * bid request in question matches this campaign.
 * @author Ben M. Faul
 *
 */

public class Campaign implements Comparable {
	
	/** The id (name) of the campaign */
	public String adId = "default-campaign";
	/** The default price associated with the campaign */
	public double price = 0.01;
	/** The default ad domain */
	public String adomain = "default-domain";
	/** An empty template for the exchange formatted message */
	public Map template = new HashMap();
	/** The list of constraint nodes for this campaign */
	public List<Node> nodes = new ArrayList<Node>();
	/** The list of creatives for this campaign */
	public List<Creative> creatives = new ArrayList();
	
	
	/**
	 * Empty constructor, simply takes all defaults, useful for testing.
	 */
	public Campaign() {

	}
	
	
	/**
	 * Creates a copy of this campaign
	 * @return Campaign. A campaign that is an exact clone of this one
	 */
	public Campaign copy() throws Exception {
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		String str = g.toJson(this);
		Campaign x = g.fromJson(str,Campaign.class);
		return x;
	}
	
	/**
	 * Constructor with pre-defined node.
	 * @param id. String - the id of this campaign.
	 * @param nodes nodes. List<Mode> - the list of nodes to add.
	 */
	public Campaign(String id, List<Node> nodes) {
		this.adId = id;
		this.nodes.addAll(nodes);
	}
	
	/**
	 * Enclose the URL fields. GSON doesn't pick the 2 encoded fields up, so you have to make sure you encode them.
	 * This is an important step, the WIN processing will get mangled if this is not called before the campaign is used.
	 * Configuration.getInstance().addCampaign() will call this for you.
	 */
	public void encodeCreatives() {
		for (Creative c : creatives) {
			c.encodeUrl();
		}
	}
	
	/**
	 * Add an evaluation node to the campaign.
	 * @param node. Node - the evaluation node to be added to the set.
	 */
	public void add(Node node) {
		nodes.add(node);
	}

	/**
	 * The compareTo method to ensure that multiple campaigns
	 * don't exist with the same id.
	 * @param o. Object. The object to compare with.
	 * @return int. Returns 1 if the ids match, otherwise 0.
	 */
	@Override
	public int compareTo(Object o) {
		Campaign other = (Campaign)o;
		if (this.adId.equals(other.adId))
			return 1;
		
		return 0;
	}
	
	/**
	 * Returns this object as a JSON string
	 * @return String. The JSON representation of this object.
	 */
	public String toJson() {
		Gson g = new Gson();
		return g.toJson(this);
	}
}
