package com.appirio.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DisclaimerStore {
	
	//private Map<String, List<DisclaimerWrapper>> disclaimersMetadataMap = new  HashMap<String, List<DisclaimerWrapper>> ();

	public List<DisclaimerWrapper> disclaimerWrapperList = new ArrayList<DisclaimerWrapper> (); 
	
	public void createDisclaimerWrapper(String disclaimerText,
			String outputLocation, String proposalType, String marketName,
			String mediaType, String notes, String sequence, String shippingInstruction) {
		
		DisclaimerWrapper wrapper = new DisclaimerWrapper(disclaimerText,outputLocation,
				proposalType,marketName,mediaType,notes, sequence,shippingInstruction);
		
		disclaimerWrapperList.add(wrapper);
		
	}
	
	public Set<String> getValidDisclaimers (String flightName,String flightMarket, String flightMediaCategory) {
		Set<String> validDisclaimersTextSet = new HashSet<String>();
		for(DisclaimerWrapper wrapper : disclaimerWrapperList) {
			System.out.println(" getValidDisclaimers  sequence " + wrapper.sequence + 
					" wrapper.market --> " + wrapper.market + 
					 " flightMarket : " + flightMarket + 
					 " wrapper.mediaCategory -->" + wrapper.mediaCategory + 
					 " flightMediaCategory --> " + flightMediaCategory 
					 + "  disclaimer text " + wrapper.disclaimerText);
			//System.out.println(" getValidDisclaimers 222222222222 disclaimer " + wrapper.toString());
			if(wrapper.isMatchingMarket(flightMarket) && wrapper.isMatchingMediaCategory(flightMediaCategory)) {
				System.out.println(" disclaimer matched flightMarket " +flightMarket + " flightMediaCategory "
						+ flightMediaCategory + " disclaimer " + wrapper.disclaimerText);
				validDisclaimersTextSet.add(wrapper.disclaimerText);
			}
			
		}
		return validDisclaimersTextSet ;
		
	}
	
	public Set<String> getAutoDisclaimers (String flightName,String flightMarket, String flightMediaCategory) {
		Set<String> autoDisclaimersTextSet = new HashSet<String>();
		for(DisclaimerWrapper wrapper : disclaimerWrapperList) {
			//System.out.println(" getAutoValidDisclaimers  wrapper.market --> " + wrapper.market + 
				//	" wrapper.mediaCategory -->" + wrapper.mediaCategory + " disclaimer text " + 
				//	wrapper.disclaimerText);
			//if(wrapper.market != null && wrapper.market.trim().equalsIgnoreCase("None") 
					//&& wrapper.mediaCategory != null && wrapper.mediaCategory.trim().equalsIgnoreCase("None")) 
			if(wrapper.isShippingInstruction()) {
				//System.out.println(" GOT AUTO Disclaimer " + wrapper.disclaimerText);
				autoDisclaimersTextSet.add(wrapper.disclaimerText);
			}
			
		}
		return autoDisclaimersTextSet ;
		
	}
	public List<DisclaimerWrapper> getDisclaimersList() {
		return disclaimerWrapperList;
	}
	
	
	
	
	
	
	public class DisclaimerWrapper {
	
		public String disclaimerText;
		public String proposalType ;
		public String market;
		public String mediaCategory ;
		public String outputLocation ;
		public String notes ;
		public String sequence; 
		public String shippingInstr;
		
		public DisclaimerWrapper() {
			this.disclaimerText = "";	
			this.outputLocation = "";
			this.proposalType = "";
			this.market = "";
			this.mediaCategory = "";
			this.notes = "";
			this.sequence = "";
			this.shippingInstr = "";
			
		}
		public DisclaimerWrapper(String disclaimerText, String outputLocation,
				String proposalType, String marketName, String mediaCategory,
				String notes2, String sequence, String shippingInst) {
			
			this.disclaimerText = disclaimerText;	
			this.outputLocation = outputLocation;
			this.proposalType = proposalType;
			this.market = marketName;
			this.mediaCategory = mediaCategory;
			this.notes = notes2;
			this.sequence = sequence;
			this.shippingInstr = shippingInst;
		}
		public boolean isShippingInstruction() {
			return this.shippingInstr != null && this.shippingInstr != "" && this.shippingInstr.equalsIgnoreCase("true");
		}

		public boolean isMatchingMarket(String marketName) {
			boolean isMarketMatched = false;
			System.out.println(" flight market to be matched " + marketName +
					 " rule market " + this.market);
			//if rule market not present it means All in disclaimer rule and is valid, no check on market required
			//also if flight market is null then also all rules are valid;.
			if(this.market == null || this.market.trim() == "" && (!isShippingInstruction())) {
				isMarketMatched = true;
			//this means multiple markets
			} else if(this.market.contains(";")) {
				this.market.split(";");
				Set<String> marketNamesSet = new HashSet<String>(Arrays.asList(market.split(";")));
				if(marketNamesSet.contains(marketName)) {
					//System.out.println(" flight market matched in marketNamesSet ");
					isMarketMatched = true;
				}else {
					//System.out.println(" flight market NOT matched in marketNamesSet ");
					isMarketMatched = false;	
				}
			}else {
				if(this.market != null && marketName !=null ) {
					isMarketMatched = this.market.trim().equalsIgnoreCase(marketName.trim());
					System.out.println(" else market check ");
				}
				
			}
			System.out.println(" flight market to be matched " + marketName +
					 " rule market " + this.market + " isMarketMatched " + isMarketMatched);
			return isMarketMatched;
		}
			
		public boolean isMatchingMediaCategory(String mediaCat) {
			boolean isMediaCategoryMatched = false;
			System.out.println(" flight MediaCategory to be matched " + mediaCat +
			" rule mediaCategory " + this.mediaCategory);
			//if market not present in disclaimer rule or if All present in rule then is valid, no check on market req  
			if(this.mediaCategory == null || this.mediaCategory.trim() == "" && (!isShippingInstruction()) )  {
						isMediaCategoryMatched = true;
			//this means multiple markets
			} else if(this.mediaCategory.contains(";")) {
				this.mediaCategory.split(";");
				Set<String> mediaCategorySet = new HashSet<String>(Arrays.asList(mediaCategory.split(";")));
				if(mediaCategorySet.contains(mediaCat)) {
					System.out.println(" flight MediaCategory matched in mediaCategorySet ");
					isMediaCategoryMatched = true;
				}else {
				System.out.println(" flight MediaCategory NOT matched in mediaCategorySet ");
					isMediaCategoryMatched = false;	
				}
			}else {
				if(this.mediaCategory != null && mediaCat !=null ) {
					isMediaCategoryMatched = this.mediaCategory.trim().equalsIgnoreCase(mediaCat.trim());
					System.out.println(" else media cat check");
				}
				
			}
			System.out.println(" flight MediaCategory to be matched " + mediaCat +
					 " rule mediaCategory " + this.mediaCategory + " isMediaCategoryMatched " + isMediaCategoryMatched);
			
			return isMediaCategoryMatched;
		}
		

		
		public boolean isMatchingOutputLocation(String outputLocation) {
			return this.outputLocation.equalsIgnoreCase(outputLocation);
		}
		
		public boolean isMatchingProposalType(String proposal) {
			return this.proposalType.equalsIgnoreCase(proposal);
		}
		
		public String toString() {
			return " Disclaimer : " + this.disclaimerText + " Market " + this.market + 
					" Media Category " + this.mediaCategory +  " Output Loc " + this.outputLocation +
					" Proposal Type " + this.proposalType + " Notes  " + this.notes
					+ " Shipping Instr " + this.shippingInstr;
					 
		}
		}
}


