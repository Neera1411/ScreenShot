package com.sshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class shot {

	protected Shell shlrdrockcreation;
	private Text text;
	private int count=0;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			shot window = new shot();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public static String getDate() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("ddmmhhmmss");
		return ft.format(dNow);
	}

	ArrayList<String> screenshots = new ArrayList<String>();
	private Text textBeforeScreenShot;

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlrdrockcreation.open();
		shlrdrockcreation.layout();
		while (!shlrdrockcreation.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		deleteFiles(screenshots);
	}

	private static void deleteFiles(ArrayList<String> files) {
		for (String filename : files) {
			File file = new File(filename);
			try{
			if (file.exists()) {
				file.delete();
			}}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		}
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Map<Integer, String> contents = new HashMap<>();
		shlrdrockcreation = new Shell();
		shlrdrockcreation.setSize(650, 347);
		shlrdrockcreation.setText("ScreenShot");
		text = new Text(shlrdrockcreation, SWT.BORDER);

		text.setBounds(202, 17, 385, 37);
		
		// Stop button-------------
		Button stopButton = new Button(shlrdrockcreation, SWT.NONE);
		stopButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println(screenshots);
				try {
					
					ShotToWordPad.saveImageToWord(
							"C:/Users/Public/Pictures/" + text.getText()  + "_"+".docx", screenshots);
//							"C:/" + text.getText() + "__"  + ".docx", screenshots);
							
					shlrdrockcreation.close();
					JOptionPane.showMessageDialog(null, "CHECK : C:/Users/Public/Pictures/", "Saved",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		stopButton.setBounds(49, 185, 100, 21);
		stopButton.setText("STOP");
		
		// takeScreenShotButton button-------------
		Button takeScreenShotButton = new Button(shlrdrockcreation, SWT.NONE);
		takeScreenShotButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlrdrockcreation.setVisible(false);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String fileName = "C:/Users/Public/Pictures/Take" + getDate() + "-FullScreenshot.jpg";
				ShotToWordPad.takeScreeShot(fileName);
				shlrdrockcreation.setVisible(true);
				screenshots.add(fileName);
				
				
			}
		});
		takeScreenShotButton.setText("CAPTURE");
		takeScreenShotButton.setBounds(233, 185, 105, 21);

		// cancelScreenShotButton button-------------
		Button cancelScreenShotButton = new Button(shlrdrockcreation, SWT.NONE);
		cancelScreenShotButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					screenshots.remove(screenshots.size() - 1);
				} catch (ArrayIndexOutOfBoundsException e) {
					e.getMessage();
				}
			}
		});
		cancelScreenShotButton.setText("CANCEL");
		cancelScreenShotButton.setBounds(401, 185, 100, 21);
		Label lblDocument = new Label(shlrdrockcreation, SWT.NONE);
		lblDocument.setBounds(10, 20, 186, 37);
		lblDocument.setText("Document Name :");
		
		Label lblText = new Label(shlrdrockcreation, SWT.NONE);
		lblText.setBounds(10, 63, 186, 34);
		lblText.setText("Text Before Screenshot :");
		
		textBeforeScreenShot = new Text(shlrdrockcreation, SWT.BORDER);
		textBeforeScreenShot.setBounds(202, 60, 385, 37);
		
		
		Label lblRow = new Label(shlrdrockcreation, SWT.NONE);
		lblRow.setText("Row :");
		lblRow.setBounds(258, 132, 60, 29);
		
		Button btnNextrow = new Button(shlrdrockcreation, SWT.NONE);
		btnNextrow.addSelectionListener(new SelectionAdapter() {

			Label label = new Label(shlrdrockcreation, SWT.NONE);
			@Override
			public void widgetSelected(SelectionEvent e) {
				count++;
				label.setText(String.valueOf(count));
				label.setBounds(216, 67, 33, 15);
			}
		});	
		btnNextrow.setText("NextRow");
		btnNextrow.setBounds(366, 128, 85, 32);
		
		// btnEnterText button-------------
		Button btnEnterText = new Button(shlrdrockcreation, SWT.NONE);
		btnEnterText.setText("Enter Text");
		btnEnterText.setBounds(109, 127, 94, 34);
		btnEnterText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					System.out.println("sssssssss "+textBeforeScreenShot.getText());
					ShotToWordPad.addText("C:/Users/Public/Pictures/" + text.getText() , count, textBeforeScreenShot.getText());
					count++;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contents.put(Integer.valueOf(count), textBeforeScreenShot.getText());
			}
		});
	}
}
