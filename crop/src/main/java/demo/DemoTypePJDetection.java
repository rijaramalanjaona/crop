package demo;

import java.io.File;

import crop.DetectionTypePJ;

public class DemoTypePJDetection {
    private static final String DIR_OCR_OUT = "C:\\projetCrop\\outOCR\\";

    public static void main(String[] args) throws Exception {
	File rep = new File(DIR_OCR_OUT);
	String text;
	String type;
	for (File file : rep.listFiles()) {
	    text = DetectionTypePJ.getTextFromOCR(file);
	    type = DetectionTypePJ.processDetectType(text);
	    System.out.println(file.getName() + " -> " + type);
	}
    }

}
