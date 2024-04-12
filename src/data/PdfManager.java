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
	public PdfManager(BufferedImage convertedImage,String filePath,LinkedList<BufferedImage> captions) {
		try {
			writer=new PdfWriter(filePath);
			pdfDoc=new PdfDocument(writer);
			AddImagesPages();
			AddCaption(captions);
			doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void AddImagesPages() {
		File folder=new File("res\\pdfImages");
		for(int i=0;i<folder.list().length;i++) {
			pdfDoc.addNewPage();
		}
		doc=new Document(pdfDoc);
		for(int i=0;i<folder.list().length;i++) {
			String convertedImageFile="res\\pdfImages\\"+i+".png";
			if(new File(convertedImageFile).exists()) {
				ImageData data=null;
				try {
					data = ImageDataFactory.create(convertedImageFile);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Image img=new Image(data);
				doc.add(img);
			}
		}
	}
	
	private void AddCaption(LinkedList<BufferedImage> captions) {
		for(int i=0;i<captions.size();i++) {
			pdfDoc.addNewPage();
		}
		doc=new Document(pdfDoc);
		for(int i=0;i<captions.size();i++) {
			String captionImage="res\\pdfImages\\caption_"+i+".png";
			ImageData data=null;
			try {
				data = ImageDataFactory.create(captionImage);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Image img=new Image(data);
			doc.add(img);
		}
		
	}

}
