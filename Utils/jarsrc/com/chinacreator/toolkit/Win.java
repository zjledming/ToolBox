package com.chinacreator.toolkit;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Win {
	static JFrame f = new JFrame();
	static JButton jb = new JButton("�ϴ�");

	public void Show() {
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
					// ����������,�������Ի���,����ѡ��Ҫ�ϴ����ļ�,���ѡ����,�Ͱ�ѡ����ļ��ľ���·����ӡ����,���˾���·��,ͨ��JTextField��settext�������ý�ȥ��,�Ǹ���ûд
					System.out.println(jfc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		// ������Ĳ������� һЩ����
		f.add(jb);
		f.setLayout(new FlowLayout());
		f.setSize(480, 320);
		// f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Win().Show();
	}
}