package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Set;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

public class PdfManager {
	
	PdfWriter writer;
	PdfDocument pdfDoc;
	Document doc;
	public PdfManager(String filePath,LinkedList<BufferedImage> captions) {
		try {
			writer=new PdfWriter(filePath);
			pdfDoc=new PdfDocument(writer);
			AddImages(captions);
			doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void AddImages(LinkedList<BufferedImage> captions) {
		File folder=new File("res\\pdfImages");
		for(int i=0;i<folder.list().length;i++) {
			pdfDoc.addNewPage();
		}
		//pdfDoc.addNewPage();
		doc=new Document(pdfDoc);
		//AddCompleteImage();
		AddImagesPages(folder);
		AddCaption(captions);
	}

	private void AddCompleteImage() {
		addToDoc("res\\imagenReescalada.png");
	}

	private void AddImagesPages(File folder) {
		for(int i=0;i<folder.list().length;i++) {
			addToDoc("res\\pdfImages\\"+i+".png");
		}
	}
	
	private void AddCaption(LinkedList<BufferedImage> captions) {
		for(int i=0;i<captions.size();i++) {
			addToDoc("res\\pdfImages\\caption_"+i+".png");
		}
		
	}
	
	private void addToDoc(String filePath) {
		if(new File(filePath).exists()) {
			ImageData data=null;
			try {
				data = ImageDataFactory.create(filePath);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Image img=new Image(data);
			doc.add(img);
		}
	}

}
