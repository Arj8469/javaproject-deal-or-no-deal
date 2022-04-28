package dealOrNoDeal;

import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class Console {
	 public static void setupClosing(JFrame frame) {
		frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		System.exit(0);
		}
	 });
	 } 
	 public static void 
	 run(JFrame frame, int width, int height) {
		 setupClosing(frame);
	 	frame.setSize(width, height);
	 	frame.setVisible(true);
	 }
}
