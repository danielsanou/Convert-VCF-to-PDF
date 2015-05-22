package com.daniel.sanou.vcfpdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.exceptions.VCardBuildException;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Helper{

	public static final int maxSizeFile = 8388608; //1Mo
	public static final int maxCharForName = 40;
	public static List<VCard> getVCardList(String vcards) throws IOException, VCardParseException, VCardBuildException{
		
		File file = new File("temp.vcf");
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		
 		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(vcards);
		bw.close();
		
		List<VCard> vcardList;
		vcardList = new VCardEngine().parseMultiple(file);
		
		/*VCardWriter vw = new VCardWriter();
		for (VCard vCard : vcardList) {
			vw.setVCard(vCard);
			//content += vw.buildVCardString() + "----------";
		}*/	
		return vcardList;
	}
	
	public static void addStringToPdf(String text, PDPageContentStream pageCs, float x, float y) throws IOException{
		pageCs.beginText();
			pageCs.moveTextPositionByAmount(x, y);
			pageCs.drawString(text);
		pageCs.endText();
	}
	
	public static void convertToPdf(String fileName, String cards) throws COSVisitorException, IOException, VCardParseException, VCardBuildException{
		
		List<VCard> vcards = getVCardList(cards);
		
		// Create a document and add a page to it
		PDDocument document = new PDDocument();
		
		for(VCard vc : vcards ){
			
			float x=40, y=780;
			
			PDPage page = new PDPage();
			document.addPage( page );
			// Create a new font object selecting one of the PDF base fonts
			PDFont font = PDType1Font.HELVETICA_BOLD;
			// Start a new content stream which will "hold" the to be created content
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			
			//griffe
			contentStream.setFont( font, 8 );			
			addStringToPdf("Powered by Daniel SANOU - http://daniel-sanou.olympe.in", contentStream, x, y);
			y -=60;
			
			// Define a text content stream using the selected font, moving the cursor and drawing the text 
			//nom prenom
			contentStream.setFont( font, 18 );
			
			addStringToPdf("< "+ VCardHelper.getGiveName(vc) + " - " + VCardHelper.getName(vc) +" >", contentStream, x, y);
			y -=20;
			
			contentStream.setFont( font, 12 );
			
			//surnom
			addStringToPdf(VCardHelper.getNickName(vc), contentStream, x, y);
			y -= 20;			
			//telphone
			addStringToPdf(VCardHelper.getTelephone(vc), contentStream, x, y);
			y -=20;			
			//mail
			addStringToPdf(VCardHelper.getMail(vc), contentStream, x, y);
			y -=20;			
			//mail programme
			addStringToPdf(VCardHelper.getMailingProgramme(vc), contentStream, x, y);
			y -=20;			
			//adresse
			addStringToPdf(VCardHelper.getAdr(vc), contentStream, x, y);
			y -=20;			
			//anniversaire
			addStringToPdf(VCardHelper.getBDay(vc), contentStream, x, y);
			y -=20;			
			//time zone
			addStringToPdf(VCardHelper.getTimeZone(vc), contentStream, x, y);
			y -=20;			
			//geopositionnement
			addStringToPdf(VCardHelper.getGeoPositionnement(vc), contentStream, x, y);
			y -=20;			
			//titre
			addStringToPdf(VCardHelper.getTitle(vc), contentStream, x, y);
			y -=20;			
			//fonction
			addStringToPdf(VCardHelper.getFonction(vc), contentStream, x, y);
			y -=20;			
			//logo
			addStringToPdf(VCardHelper.getLogo(vc), contentStream, x, y);
			y -=20;			
			//agent
			addStringToPdf(VCardHelper.getAgent(vc), contentStream, x, y);
			y -=20;			
			//organisation
			addStringToPdf(VCardHelper.getOrganisation(vc), contentStream, x, y);
			y -=20;			
			//categorie
			addStringToPdf(VCardHelper.getCategorie(vc), contentStream, x, y);
			y -=20;			
			//note
			addStringToPdf(VCardHelper.getNotes(vc), contentStream, x, y);
			y -=20;			
			//revision
			addStringToPdf(VCardHelper.getRevision(vc), contentStream, x, y);
			y -=20;			
			//sortString
			addStringToPdf(VCardHelper.getSortString(vc), contentStream, x, y);
			y -=20;			
			//sound
			addStringToPdf(VCardHelper.getSound(vc), contentStream, x, y);
			y -=20;			
			//url
			addStringToPdf(VCardHelper.getURL(vc), contentStream, x, y);
			y -=20;			
			//uid
			addStringToPdf(VCardHelper.getUID(vc), contentStream, x, y);
			y -=20;
			//version
			addStringToPdf(VCardHelper.getVersion(vc), contentStream, x, y);
			y -=20;			
			//key
			addStringToPdf(VCardHelper.getKey(vc), contentStream, x, y);
			y -=20;
			
			// Make sure that the content stream is closed:
			contentStream.close();
		}

		// Save the results and ensure that the document is properly closed:
		document.save( fileName);
		document.close();
		
		//return new FileInputStream(fileName);
		
	}

	public static void convert(String fileName, String cards) throws COSVisitorException, IOException, VCardParseException, VCardBuildException{
		
		//fileName ="BlankPage.pdf"; 		
		String uiid = UUID.randomUUID().toString().replace("-", "");
		
		fileName = "".equals(fileName) ? uiid+"contacts.pdf" : uiid+fileName;
		fileName = fileName.endsWith(".pdf") ? fileName : fileName+".pdf";
		
		convertToPdf(fileName,cards);
		
		// Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        File file = new File(fileName);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), 10240);

            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), 10240);

            // Write file contents to response.
            byte[] buffer = new byte[10240];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
	}
	
	private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it. It may be useful to 
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();
            }
        }
    }

}
