package com.daniel.sanou.vcfpdf;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.exceptions.VCardBuildException;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.pdfbox.exceptions.COSVisitorException;

@ManagedBean
@SessionScoped
public class FileUploadBean implements Serializable {

    private static final long serialVersionUID = 1L; 
    
    private UploadedFile  vcfFile;

    private String pdfFileName;

    public String redirect(){
    	return "index.xhtml?faces-redirect=true";
    }
    
    public List<VCard> getContent() {    	
    	List<VCard> li = null;
    	try {
			li = Helper.getVCardList(new String(vcfFile.getBytes()));
		} 
    	catch (IOException | VCardBuildException | VCardParseException e) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Un problème est survenu lors de la lecture de votre fichier, il se peut qu'il soit invalide"));
			e.printStackTrace();
		}
    	
    	return li;
    }
    
    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName( String pdfFileName ) {
        this.pdfFileName = pdfFileName;
    }

	public UploadedFile getVcfFile() {
		return vcfFile;
	}

	public void setVcfFile(UploadedFile vcfFile) {
		this.vcfFile = vcfFile;
	}
	
	public void validateFileName(FacesContext context, UIComponent component, Object value)
			throws ValidatorException{
		String name = (String) value;
		FacesMessage msg;
		
		if(name.length()>40){
			msg = new FacesMessage("le nom de fichier ne peut avoir plus de 40 caractères");
            throw new ValidatorException(msg);
		}
		
		if(name != null && !"".equals(name) && !StringUtils.isWhitespace(name)){
			//^(?!(COM[0-9]|LPT[0-9]|CON|PRN|AUX|CLOCK\$|NUL)$)[^./\\:*?\"<>|]+$
			String[] ILLEGAL_CHARACTERS = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "\\", "<", ">", "|", "\"", ":" };
			for (String illegal : ILLEGAL_CHARACTERS) {
				if(name.contains(illegal)){
					String illegalString = "/, \\n (Entrée), \\r (Entrée), \\t (Tab), \\0 (Fin de chaine), \\f (Saut de page), `, ?, *, \\, <, >, |, \\ ";
					msg = new FacesMessage("le nom de fichier ne peut pas contenir les caractères suivants : " + illegalString);
		            throw new ValidatorException(msg);
				}
			}
			
			String[] ILLEGAL_NAMES = {"CON", "PRN", "AUX", "NUL","COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8",
									  "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
			for (String illegal : ILLEGAL_NAMES) {
				if(name.trim().equalsIgnoreCase(illegal) ){
					String illegalString = "CON, PRN, AUX, NUL, COM1, COM2, COM3, COM4, COM5, COM6, COM7, COM8, COM9, LPT1, LPT2, LPT3, LPT4, LPT5, LPT6, LPT7, LPT8, LPT9";
					msg = new FacesMessage("le nom de fichier ne peut pas être l'un des suivants : " + illegalString);
		            throw new ValidatorException(msg);
				}
			}
		}
		else{
			setPdfFileName("");
		}
		
	}
	
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		UploadedFile file = (UploadedFile) value;
		FacesMessage msg; 
		
		if(file == null){
            msg = new FacesMessage("Aucun fichier sélectionné");
            throw new ValidatorException(msg);
        }
		
		if(file.getSize() > Helper.maxSizeFile){
            msg = new FacesMessage("La taille de votre fichier dépasse "+getMaxSizeFile()+"Mo");
            throw new ValidatorException(msg);
        }
		
		if(file.getContentType().equalsIgnoreCase("application/vcard") || !FilenameUtils.isExtension(file.getName(), "vcf")){
            msg = new FacesMessage("le fichier choisi '"+file.getName()+"' n'est pas de type vCard (.vcf) ou est invalide. \n "
            						+ "Veuillez choisir un fichier de type vCard valide");
            throw new ValidatorException(msg);
        }
		
		
	} 
	
	public void convert() {
		try {
			Helper.convert(pdfFileName,new String(getVcfFile().getBytes()) );
		}catch (COSVisitorException |IOException |VCardBuildException | VCardParseException e) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("global-messages","Un problème est survenu lors de la création du fichier pdf, veuillez recharger le fichier vcf et reéssayer"));
			e.printStackTrace();
			}
	}

	public int getMaxSizeFile() {
		return Helper.maxSizeFile/8000000;
	}
	
	public int getMaxCharForName(){
		return Helper.maxCharForName;
	}
	
	public String getPdfNameMessage(){
		return pdfFileName == null || "".equals(pdfFileName) ? 
				"Vous n'avez pas entré de nom pour le fichier pdf qui sera généré, un nom générique lui sera donc attribué"
				: "Vous avez entré comme nom pour le fichier pdf '"+pdfFileName+"'";
	}

}