package com.daniel.sanou.vcfpdf;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import net.sourceforge.cardme.vcard.VCard;

public class VCardHelper {
	
	public static String getName(VCard vc){
		String value = vc.getN() != null ? vc.getN().getFamilyName() : "Inconu";
		return value;//"Nom : "+value;
	}
	
	public static String getGiveName(VCard vc){
		String value = vc.getN() != null ? vc.getN().getGivenName() : "Inconu";
		return value;//"Prénom : "+value;
	}
	
	public static String getNickName(VCard vc){ 
		String value = vc.getNicknames() != null ? Arrays.toString(vc.getNicknames().getNicknames().toArray()) : "";		
		return "Surnom : "+value;
	}
	
	public static String getPhoto(VCard vc){
		String value = vc.getN() != null ? vc.getN().getGivenName() : "Inconu";
		return "Photo : "+value;
	}
	
	public static String getBDay(VCard vc){
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Calendar bDay = vc.getBDay() != null ? vc.getBDay().getBirthday() : null;
		String value = bDay != null ? format.format(bDay.getTime()) : "";
		return "Anniversaire : "+value;
	}
	
	public static String getAdr(VCard vc){
		String value = "";	
		if(vc.getAdrs() != null){
			for (int i = 0; i < vc.getAdrs().size(); i++) {
				value += vc.getAdrs().get(i).getStreetAddress()+"; ";
			}
		}
		return "Adresses : "+value;	
	}
	
	public static String getTelephone(VCard vc){
		String value = "";
		if(vc.getTels() != null){
			for (int i = 0; i < vc.getTels().size(); i++) {
				value += vc.getTels().get(i).getTelephone()+"; ";
			}
		}
		return "Téléphones : "+value;		
	}
	
	public static String getMail(VCard vc){
		String value = "";
		if(vc.getEmails() != null){
			for (int i = 0; i < vc.getEmails().size(); i++) {
				value += vc.getEmails().get(i).getEmail()+"; ";
			}
		}
		return "Mails : "+value;
	}
	
	public static String getMailingProgramme(VCard vc){
		String value = vc.getMailer() != null ? vc.getMailer().getMailer() : "";
		return "Programme de mail : "+value;
	}
	
	public static String getTimeZone(VCard vc){
		String value = vc.getTz() != null ? vc.getTz().getTimeZone().getDisplayName() : "";
		return "Time Zone : "+value;
	}
	
	public static String getGeoPositionnement(VCard vc){
		String value = vc.getGeo() != null ?"Longitude"+ vc.getGeo().getLongitude()+ "; Latitude"+ vc.getGeo().getLatitude() : "";
		return "Position géographique : "+value;
	}
	
	public static String getTitle(VCard vc){
		String value = vc.getTitle() != null ? vc.getTitle().getTitle() : "";
		return "Titre : "+value;
	}
	
	public static String getFonction(VCard vc){	
		String value = vc.getRole() != null ? vc.getRole().getRole() : "";
		return "Fonction : "+value;
	}
	
	public static String getLogo(VCard vc){
		//todo
		return "";
	}
	
	public static String getAgent(VCard vc){
		String value ="";
		if(vc.getAgents() != null){
			for (int i = 0; i < vc.getAgents().size(); i++) {
				value += vc.getAgents().get(i).getAgent().toString()+"; ";
			}
		}
		return "Agent : "+value;
	}
	
	public static String getOrganisation(VCard vc){
		String value = vc.getOrg() != null ? vc.getOrg().getOrgName() : "";
		return "Organisation : "+value;
	}	
	
	public static String getCategorie(VCard vc){
		String value = "";
		if(vc.getCategories() != null){
			for (int i = 0; i < vc.getCategories().getCategories().size(); i++) {
				value += vc.getCategories().getCategories().get(i) + "; ";
			}
		}
		return "Catégorie : "+value;
	}	
	
	public static String getNotes(VCard vc){
		String value ="";
		if(vc.getNotes() != null){
			for (int i = 0; i < vc.getNotes().size(); i++) {
				value += vc.getNotes().get(i) + "\n |-------| ";
			}
		}		
		return "URL : "+value;
	}	
	
	public static String getRevision(VCard vc){
		String value = vc.getRev() != null ? vc.getRev().getRevision().toString() : "";
		return "Revision : "+value;
	}	
	
	public static String getSortString(VCard vc){
		String value = vc.getSortString() != null ? vc.getSortString().getSortString() : "";
		return "Sort String : "+value;
	}		
	
	public static String getSound(VCard vc){
		//todo 
		return "";
	}		
	
	public static String getURL(VCard vc){
		String value ="";
		if(vc.getUrls() != null){
			for (int i = 0; i < vc.getUrls().size(); i++) {
				value += vc.getUrls().get(i).getRawUrl() + "; ";
			}
		}		
		return "URL : "+value;
	}		
	
	public static String getUID(VCard vc){
		String value = vc.getUid() != null ? vc.getUid().getUid() : "";
		return "UID : "+value;
	}		
	
	public static String getVersion(VCard vc){
		String value = vc.getVersion() != null ? vc.getVersion().getVersion().getVersion() : "";
		return "Version : "+value;
	}					
	
	public static String getKey(VCard vc){
		String value = "";
		if(vc.getKeys() != null){
			for (int i = 0; i < vc.getKeys().size(); i++) {
				value += vc.getKeys().get(i).getKey().toString() + "; ";
			}
		}
		return "Clés : "+value;
	}			
}
