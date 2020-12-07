package com.sshot;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ShotToWordPad {
	static XWPFDocument doc = new XWPFDocument();
	static XWPFParagraph p = doc.createParagraph();
	static XWPFRun xwpfRun = p.createRun();
	static FileOutputStream out = null;
	static FileInputStream input = null;
	static XWPFTable table = doc.createTable();

	public static void saveImageToWord(String documentName, ArrayList<String> screens)
			throws Exception, FileNotFoundException, IOException {

		for (String imgFile : screens) {
			int format = XWPFDocument.PICTURE_TYPE_JPEG;
			xwpfRun.addBreak();
			input = new FileInputStream(imgFile);
			xwpfRun.addPicture(input, format, imgFile, Units.toEMU(400), Units.toEMU(200));

			input.close();
		}
		out = new FileOutputStream(documentName);
		doc.write(out);
		out.close();
		doc.close();
	}

	public static void addText(String documentName, int row, String text) throws IOException {
		out = new FileOutputStream(new File(documentName));
		// create first row
		table.setWidth(60);
		XWPFTableRow tableRow = table.getRow(row);
		
		tableRow.getCell(0).setText(text);
		doc.write(out);
		row++;
	}
	

	public static void takeScreeShot(String fileName) {
		try {
			Robot robot = new Robot();
			String format = "jpg";
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			ImageIO.write(screenFullImage, format, new File(fileName));
		} catch (AWTException | IOException ex) {
			System.err.println(ex);
		}

	}
}