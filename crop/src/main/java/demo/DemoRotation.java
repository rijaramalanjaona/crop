package demo;

import java.io.File;

import crop.AutoCropAndRotate;

public class DemoRotation {

    private static final String DIR_PDF_TEST = "C:\\projetCrop\\pdfTest\\";

    public static void main(String[] args) throws Exception {
	File rep = new File(DIR_PDF_TEST);
	for (File file : rep.listFiles()) {
	    AutoCropAndRotate autoCropAndRotate = new AutoCropAndRotate(file);
	    autoCropAndRotate.go();
	}
	System.out.println("fin...");
    }
}
