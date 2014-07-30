package cn.jframe;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CheckBoxDemo extends JPanel implements ItemListener {

	JCheckBox chin;
	JCheckBox glass;
	JCheckBox hire;
	JCheckBox teeth;

	StringBuffer choice;
	JLabel labelPicture;

	public CheckBoxDemo() {

		super(new BorderLayout());

		// ������ѡ�򰴼��������ÿ�ݼ�����ѡ��
		chin = new JCheckBox("chin");
		//���õ�ǰģ���ϵļ������Ƿ������Ƿ���ĳ�ּ���������۵���������η���ͨ���� Alt�����ʱ�������������ڴ˰�ť���ȴ����е�ĳ���ط���������˰�ť�� 
//		chin.setMnemonic(KeyEvent.VK_C);
		chin.setSelected(true);

		glass = new JCheckBox("glass");
		glass.setMnemonic(KeyEvent.VK_G);
		glass.setSelected(true);

		hire = new JCheckBox("hire");
		hire.setMnemonic(KeyEvent.VK_H);
		hire.setSelected(true);

		teeth = new JCheckBox("teeth");
		teeth.setMnemonic(KeyEvent.VK_T);
		teeth.setSelected(true);

		// ����һ��panel������ѡ�����ͬһ��panel
		JPanel checkPanel = new JPanel(new GridLayout(0, 1));
		checkPanel.add(chin);
		checkPanel.add(glass);
		checkPanel.add(hire);
		checkPanel.add(teeth);

		// ��Ӹ�ѡ��ļ����¼�
		chin.addItemListener(this);
		glass.addItemListener(this);
		hire.addItemListener(this);
		teeth.addItemListener(this);

		// ����ѡ���panel��ӵ��������
		add(checkPanel, BorderLayout.WEST);

		// ����ͼƬ��ʾ��
		labelPicture = new JLabel();
		// ��ͼƬ��ʾ���ŵ�����м�
		add(labelPicture, BorderLayout.CENTER);

		// �������ı߽磬ʹ�ÿؼ��ܹ���߽���һ������
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// ���ó�ʼͼƬ
		choice = new StringBuffer("cght");
		// ��ʾ����ͼƬ
		upDatePicture();
	}

	private void upDatePicture() {
		ImageIcon ii = createImageIcon(File.separator + "images"
				+ File.separator + "geek" + File.separator + "geek-"
				+ choice.toString() + ".gif");
		labelPicture.setIcon(ii);
	}

	private ImageIcon createImageIcon(String string) {
		URL url = CheckBoxDemo.class.getResource(string);
		if (url != null)
			return new ImageIcon(url);
		else
			System.out.println("image " + string + "not exist!");
		return null;
	}

	public static void createAndShowGUI() {
		JFrame frame = new JFrame("��ѡ�����");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JComponent panel = new CheckBoxDemo();

		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	// ���ܴ���ѡ�����¼�
	@Override
	public void itemStateChanged(ItemEvent e) {
		// ��ȡ�ı�ĸ�ѡ����
		Object source = e.getItemSelectable();
		int index = -1;
		char c = '-';
		if (source == chin) {
			index = 0;
			c = 'c';
		} else if (source == glass) {
			index = 1;
			c = 'g';
		} else if (source == hire) {
			index = 2;
			c = 'h';
		} else if (source == teeth) {
			index = 3;
			c = 't';
		}

		// �жϸı�İ����ĸı���״̬
		if (e.getStateChange() == ItemEvent.DESELECTED)
			c = '-';

		choice.setCharAt(index, c);

		upDatePicture();
	}
}
