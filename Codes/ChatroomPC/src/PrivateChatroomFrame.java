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
		System.out.println("OK");
	}
}
