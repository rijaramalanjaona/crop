package crop;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

public class AutoCropAndRotate {
	private static final int MAX_WIDTH_PDF = 500;
	private static final double MAX_SCALED_DOWN_WIDTH = 500.0;

	private static final String DIR_PDF_TEST = "C:\\projetCrop\\pdfTest\\";
	private static final String DIR_OUTPUT_PNG_FILE = "C:\\projetCrop\\outputPng\\";
	private static final String DIR_OUTPUT_SCALED_PNG_FILE = "C:\\projetCrop\\outputScaledPng\\";
	private static final String DIR_PDF_RESULT = "C:\\projetCrop\\pdfResult\\";

	private File inputPdfFile;

	public AutoCropAndRotate(File inputPdfFile) {
		this.inputPdfFile = inputPdfFile;
	}

	@SuppressWarnings("unchecked")
	private void go() throws Exception {

		List<PDPage> pages;

		PDDocument docIn = null;
		Document docOut = null;
		PdfWriter writerOut = null;

		try {
			// Read the pdf into a BufferedImage
			docIn = PDDocument.load(inputPdfFile);
			pages = docIn.getDocumentCatalog().getAllPages();

			BufferedImage currentImage;
			String currentFileName = null;
			int numPage = 0;
			File outputPngFile = null;
			File outputScaledPngFile = null;
			File outputPdfFile = null;

			for (PDPage currentPage : pages) {
				try {
					numPage++;
					currentFileName = StringUtils.substringBefore(inputPdfFile.getName(), ".pdf");
					System.out.println(">>>>>>>>>> Debut traitement de : " + inputPdfFile.getName() + " page "
							+ numPage);

					currentImage = currentPage.convertToImage(BufferedImage.TYPE_INT_RGB, 300);
					System.out.println("img size : " + currentImage.getWidth() + " * " + currentImage.getHeight());

					// Automatically crop the image excluding any bad edges
					CropWhitespace cropper = new CropWhitespace();
					currentImage = cropper.crop(currentImage);

					// Deskew image
					Deskew dsk = new Deskew();
					double angle = dsk.doIt(currentImage);
					System.out.println("angle dsk = " + angle);

					// debug : to visually double check the image
					outputPngFile = new File(DIR_OUTPUT_PNG_FILE + currentFileName + "-page" + numPage
							+ "-outputPngFile.png");
					ImageIO.write(currentImage, "png", outputPngFile);

					// Scale down via Java to reduce file size
					int w = currentImage.getWidth();
					int h = currentImage.getHeight();
					int largestDimension = Math.max(w, h);
					double scale = MAX_SCALED_DOWN_WIDTH / largestDimension;
					BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
					AffineTransform at = new AffineTransform();
					at.scale(scale, scale);
					AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
					currentImage = scaleOp.filter(currentImage, scaled);
					int rightEdge = currentImage.getWidth() - (int) (currentImage.getWidth() * scale);
					int bottomEdge = currentImage.getHeight() - (int) (currentImage.getHeight() * scale);
					currentImage = cropper.cropPure(currentImage, 0, rightEdge, bottomEdge, 0);

					// debug : to visually double check the image
					outputScaledPngFile = new File(DIR_OUTPUT_SCALED_PNG_FILE + currentFileName + "-page" + numPage
							+ "-outputScaledPngFile.png");
					ImageIO.write(currentImage, "png", outputScaledPngFile);

					// write out the image to PDF
					outputPdfFile = new File(DIR_PDF_RESULT + currentFileName + "-page" + numPage
							+ "-outputPdfFile.pdf");
					FileOutputStream outputStream = new FileOutputStream(outputPdfFile);

					docOut = new Document();
					writerOut = PdfWriter.getInstance(docOut, outputStream);
					writerOut.setFullCompression();
					docOut.open();

					Image pdfImage = Image.getInstance(writerOut, currentImage, 1.0f);
					pdfImage.setRotationDegrees(Double.valueOf(angle).floatValue());
					if (Math.max(currentImage.getWidth(), currentImage.getHeight()) > MAX_WIDTH_PDF) {
						pdfImage.scaleToFit(MAX_WIDTH_PDF, MAX_WIDTH_PDF);
					}

					docOut.add(pdfImage);

					System.out.println(">>>>>>>>>> Fin traitement de : " + inputPdfFile.getName() + " page " + numPage);

				} catch (Exception e) {
					System.out.println("Erreur currentPage : " + currentFileName + "-page" + numPage + " >>> " + e);
				} finally {
					if (docOut != null) {
						docOut.close();
					}
					if (writerOut != null) {
						writerOut.close();
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Erreur dans go() : " + e);
		} finally {
			if (docIn != null) {
				docIn.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// File pdf = new File(DIR_PDF_TEST +
		// "RV_2_8b4a2f77-4285-4b0a-b277-cc7f062e6f0f.pdf");
		File pdf = new File(DIR_PDF_TEST + "RV_1_034baf66-46ce-494d-998f-767bc9930dc3_RV.pdf");
		AutoCropAndRotate autoCropAndRotate = new AutoCropAndRotate(pdf);
		autoCropAndRotate.go();
		System.out.println("finished");
	}

}
