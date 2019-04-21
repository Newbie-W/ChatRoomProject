import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class PrivateChatroomFrame extends ChatFrame {
	
	PrivateChatroomFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		super.initFrame();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
