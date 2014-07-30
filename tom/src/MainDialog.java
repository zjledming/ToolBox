import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fxm.tom.util.Tomcat;

public class MainDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	// ��panel
	private JPanel contentPane;
	// �ı���
	private JTextField tomcatPath = new JTextField();
	private JTextField projectPath = new JTextField();

	// ѡ����
	JFileChooser chooser;

	// x,y,w,h - ��ʼ��С
	int ix = 400;
	int iy = 200;
	int iwidth = 480;
	int iheight = 230;

	// ��һ�е�һ�б��ͷ��λ��25, 30, 110, 15
	int tx = 25;
	int ty = 30;
	int twidth = 80;
	int theight = 21;

	// �ı�����
	int inputwidth = 250;
	// select but ���
	int swidth = 60;

	/**
	 * ���ñ��ͷ����ÿ��һ�У�x���䣬y+40��w,h����
	 * 
	 * @param herdText
	 *            �ı�
	 * @param index
	 *            �ڼ���ͷ������index=1��ʼ
	 */
	public void setHeader(String herdText, int index) {
		// ����n��
		int n = index - 1;
		JLabel label = new JLabel(herdText);
		label.setBounds(tx, ty + 40 * n, twidth, theight);
		this.contentPane.add(label);
	}

	/**
	 * ���ñ���ı���ÿ��һ�У�x���䣬y+40��w,h����
	 * 
	 * @param jTextField
	 *            �����ؼ�
	 * @param index
	 *            �ڼ����ı�����index=1��ʼ
	 */
	public void setText(JTextField jTextField, int index, String text) {
		// ����n��
		int n = index - 1;
		// jTextField = new JTextField();
		// xλ��Ϊ�߽�+��ͷ�����
		jTextField.setBounds(tx + twidth, ty + 40 * n, inputwidth, theight);
		// ���ô� TextField �е�������Ȼ����֤����
		jTextField.setColumns(10);
		jTextField.setText(text);
		this.contentPane.add(jTextField);
	}

	/**
	 * ���ñ��ť��ÿ��һ�У�x���䣬y+40��w,h����
	 * 
	 * @param butText
	 *            ��ť����
	 * @param index
	 *            �ڼ��а�ť����index=1��ʼ
	 */
	public void setSelect(String butText, final int index) {
		JButton jb = new JButton(butText);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invoke(index);
			}
		});
		// ����n��
		int n = index - 1;
		// xλ��Ϊ�߽�+��ͷ�����+�ı�����+10������һ�����룩
		jb.setBounds(tx + twidth + inputwidth + 10, ty + 40 * n, swidth,
				theight);
		this.contentPane.add(jb);
	}

	/**
	 * ���"ѡ��"��ť�¼�
	 * 
	 * @param index
	 */
	public String invoke(int index) {
		try {
			if (index == 10000) {
				// ����
				if ((MainDialog.this.projectPath.getText().length() > 0)
						&& (MainDialog.this.tomcatPath.getText().length() > 0)) {
					new Tomcat().config(MainDialog.this.tomcatPath.getText(),
							MainDialog.this.projectPath.getText());
					JOptionPane.showMessageDialog(MainDialog.this,"����ɹ���");
					invoke(10001);
				} else {
					// ��ʾ��
					JOptionPane.showMessageDialog(MainDialog.this,
							"��ѡ��tomcat·������Ŀ·����");
				}
			}else if (index == 10001) {
				// ������
				if ((MainDialog.this.projectPath.getText().length() > 0)
						&& (MainDialog.this.tomcatPath.getText().length() > 0)) {
					try {
						new Tomcat().clear(MainDialog.this.tomcatPath.getText());
						JOptionPane.showMessageDialog(MainDialog.this,
								"������ɹ���" );
					} catch (Exception e) {
						JOptionPane.showMessageDialog(MainDialog.this,
								"������ʧ�ܣ��������쳣��" + e.getMessage());
					}
				} else {
					// ��ʾ��
					JOptionPane.showMessageDialog(MainDialog.this,
							"��ѡ��tomcat·����");
				}
			} else {
				// ѡ��
				JTextField jField = null;
				String currentDirectory = "";
				String dialogTitle = "";
				if (index == 1) {
					jField = MainDialog.this.tomcatPath;
					currentDirectory = fxm.tom.util.Tomcat.tomcatPath;
					dialogTitle = "��ѡ��tomcat·��";
				} else if (index == 2) {
					jField = MainDialog.this.projectPath;
					currentDirectory = fxm.tom.util.Tomcat.projectPath;
					dialogTitle = "��ѡ����Ŀ·��";
				}
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(currentDirectory));
				chooser.setDialogTitle(dialogTitle);
				// ���� JFileChooser���������û�ֻѡ���ļ���ֻѡ��Ŀ¼�����߿�ѡ���ļ���Ŀ¼
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ����ѡ��Ŀ¼
				// ȷ���Ƿ� AcceptAll FileFilter ������ѡ��������б���һ������ѡ�
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					// ����ѡ�е��ļ�
					File selectfile = chooser.getSelectedFile();
					// ��tomcat·����ֵ
					jField.setText(selectfile.getAbsolutePath());
				} else {
					System.out.println("No Selection ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(MainDialog.this,
					"����ʧ�ܣ��������쳣��" + e.getMessage());
		}
		return null;
	}

	/**
	 * ������ť<br>
	 * ���ð�ť�������ڶ��У����ж���
	 * 
	 * @param butText
	 */
	public void setButton(String butText) {
		// ���ð�ť���,ÿ2����ռ60
		int bwidth = (butText.length() / 2) * 60;

		JButton button = new JButton(butText);
		/*
		 * ��һ�� ActionListener ��ӵ���ť��;
		 * ���ڽ��ղ����¼����������ӿڡ��Դ�������¼�����Ȥ�������ʵ�ִ˽ӿڣ���ʹ�ø��ഴ���Ķ����ʹ������� addActionListener
		 * ����������ע�ᡣ�ڷ��������¼�ʱ�����øö���� actionPerformed ������
		 */
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invoke(10000);
			}
		});
		System.out.println(iheight);
		// button.setBounds(ix+iwidth/2-bwidth/2, iy+iheight-2*theight, bwidth,
		// theight);
		// ��ť��x.y������Դ��屾��߽磬������ʾ���ı߽磨��ȥtitle�Ŀ�ȣ�
		button.setBounds(iwidth / 2 - bwidth / 2 - 5, iheight - 30 - 4
				* theight, bwidth, theight);
		this.contentPane.add(button);
	}
	
	int butWidth = 90;
	/**
	 * �����ť<br>
	 * ���ð�ť�������ڶ��У����ж���
	 * @param butText
	 */
	public void setButton(String[] butText) {
		int butx0 = (iwidth - butText.length * butWidth - 30) / 2;
		for (int i = 0; i < butText.length; i++) {
			JButton button = new JButton(butText[i]);
			/*
			 * ��һ�� ActionListener ��ӵ���ť��;
			 * ���ڽ��ղ����¼����������ӿڡ��Դ�������¼�����Ȥ�������ʵ�ִ˽ӿڣ���ʹ�ø��ഴ���Ķ����ʹ�������
			 * addActionListener ����������ע�ᡣ�ڷ��������¼�ʱ�����øö���� actionPerformed ������
			 */
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("��	  ��")) {
						invoke(10000);
					}else if (e.getActionCommand().equals("������")) {
						invoke(10001);
					}
				}
			});
			// ��ť��x.y������Դ��屾��߽磬������ʾ���ı߽磨��ȥtitle�Ŀ�ȣ�
			System.out.println(butx0);
			button.setBounds(butx0 + i * (butWidth + 30), iheight - 30 - 4
					* theight, butWidth, theight);
			this.contentPane.add(button);

		}

	}

	/**
	 * ��Ȩ˵��
	 */
	public void setMadeBy() {
		JLabel label = new JLabel("made by ximing.fu ��Ȩ����.");
		label.setBounds((int) (iwidth * 0.57), iheight - 30 - 2 * theight, 185,
				theight);
		this.contentPane.add(label);
	}

	public static void main(String[] args) {
		/*
		 * ���1�������е�����ȫ���ҽ�ʡ�ڴ棩
		 * ʹ��eventqueue.invokelater()�ô����Զ��׼��ģ��������������Ϻ����ᱻ���٣���Ϊ�����ڲ�������Ϊ��ʱ�������ڵģ�
		 * ����������ڴ��ڴ�ʱ�ᱻ�ͷ�
		 * ���������ֻ��Ҫ��һ���ط�ʹ��ʱ���Խ�ʡ�ڴ棬����������ǲ����Ա������ķ�������ʹ�õģ�ֻ�ܱ�EventQueue
		 * .invokeLater(
		 * )��ʹ�á����������Ҫһ���ںܶ�ط������õ����࣬������ֻ��ĳһ����������߷������õĻ�������������ڲ�����Ȼ�ǲ���ȡ�ġ�
		 * �ǣ�runnable�Ǹ��߳���ص��ࡣ
		 * 
		 * ���2�� ������¼���new Runnable())��ӵ�awt���¼������̵߳���ȥ
		 * awt���¼������̻߳ᰴ�ն��е�˳�����ε���ÿ����������¼�������
		 * ʹ�ø÷�ʽ��ԭ���ǣ�awt�ǵ��߳�ģʽ�ģ�����awt�����ֻ����(�Ƽ���ʽ)�¼������߳��з��ʣ��Ӷ���֤���״̬�Ŀ�ȷ���ԡ�
		 * 
		 * ע��awt:���󴰿ڹ��߰� ��Abstract Windowing Toolkit��
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainDialog frame = new MainDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainDialog() {
		setTitle("tomcat���𹤾�");
		// ���ô˴����Ƿ�����û�������С��
		setResizable(true);
		// �����û��ڴ˴����Ϸ��� "close" ʱĬ��ִ�еĲ���
		setDefaultCloseOperation(3);
		/*
		 * (int x, int y, int width, int height)�ƶ�������������С��ʹ������µ��н���� r���� r.x ��
		 * r.y ָ���������λ�ã��� r.width �� r.height ָ��������´�С ��� r.width ֵ�� r.height
		 * ֵС��֮ǰ���� setMinimumSize ָ������С��С��������ֵ���Զ����ӡ�
		 */
		setBounds(ix, iy, iwidth, iheight);
		this.contentPane = new JPanel();
		// setBorder
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// ���� contentPane ����
		setContentPane(this.contentPane);
		// ���ô������Ĳ��ֹ�����
		this.contentPane.setLayout(null);

		// ��һ��ͷ��
		setHeader("tomcat·����", 1);
		// �ڶ���ͷ��
		setHeader("��Ŀ·����", 2);

		// ��һ���ı�
		setText(this.tomcatPath, 1, "E:\\tomcat-6.0");
		// �ڶ����ı�
		setText(this.projectPath, 2, "E:\\workspace\\hyxfs\\creatorepp");

		// ��һ�а�ť
		setSelect("ѡ��", 1);
		setSelect("ѡ��", 2);

		// ���ð�ť
		//setButton();
		setButton(new String[] {"��	  ��", "������" });

		// made by
		setMadeBy();
	}
}