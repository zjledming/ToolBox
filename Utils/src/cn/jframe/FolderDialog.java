package cn.jframe;

import javax.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;//�������¼��ر�����
import java.awt.event.ActionListener;//�������¼��ر�����

/**
 * �ļ����ϴ�dialog
 * 
 * @author ximing.fu
 * 
 */
public class FolderDialog {
	// extends JPanel implements ActionListener
	static JFrame frame = new JFrame("");
	static JPanel p = new JPanel();
	JButton jb = new JButton("�ϴ�");
	JFileChooser chooser;
	String choosertitle = "ѡ���ļ���";

	public static void main(String s[]) {
		new FolderDialog().Show();
	}

	public void Show() {
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result;
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(choosertitle);
				System.out.println("---" + choosertitle);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(p) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): "
							+ chooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : "
							+ chooser.getSelectedFile());
				} else {
					System.out.println("No Selection ");
				}
			}
		});
		// frame.addWindowListener(new WindowAdapter() {
		// public void windowClosing(WindowEvent e) {
		// System.exit(0);
		// }
		// });
		p.add(jb);
		frame.add(p, "Center");
		frame.setSize(320, 240);
		frame.setVisible(true);
	}
}