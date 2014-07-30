package fxm.patch.main;

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

import com.alibaba.fastjson.JSON;

import fxm.patch.util.PatchUtil;
import fxm.patch.util.Util;

public class MainDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	// ��panel
	private JPanel contentPane;
	// �ı���
	private JTextField patchName = new JTextField();
	private JTextField patchPath = new JTextField();
	private JTextField svnFilePath = new JTextField();
	private JTextField myeclipseFilePath = new JTextField();

	// ѡ����
	JFileChooser chooser;

	// x,y,w,h - ��ʼ��С
	int ix = 400;
	int iy = 200;
	int iwidth = 480;
	int iheight = 300;

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
	 * ����index���ض�Ӧ���ı���ؼ�
	 * 
	 * @param index
	 * @return
	 */
	public JTextField getJTextFieldByIndex(int index) {
		switch (index) {
		case 2:
			return this.patchPath;
		case 3:
			return this.svnFilePath;
		case 4:
			return this.myeclipseFilePath;
		}
		return this.patchPath;
	}

	/**
	 * �����ť�¼�
	 * 
	 * @param index
	 */
	public String invoke(int index) {
		try {
			if (index == 10000) {
				// ���ɲ�����
				if (MainDialog.this.patchName.getText().length() <= 0) {
					JOptionPane.showMessageDialog(MainDialog.this, "�����벹��������");
					return "";
				}
				if ((MainDialog.this.patchPath.getText()
						.replace("��λ���洢���������ļ���(����)", "").length() > 0)
						&& (MainDialog.this.svnFilePath.getText()
								.replace("��λ��svn�����ļ��µ���Ŀ(hnszwfw-core)", "")
								.length() > 0)
						&& (MainDialog.this.myeclipseFilePath.getText()
								.replace("��λ��myeclipse�µ���Ŀ(hnszwfw-core)", "")
								.length() > 0)) {
					PatchUtil patch = new PatchUtil(
							MainDialog.this.patchName.getText(),
							MainDialog.this.patchPath.getText(),
							MainDialog.this.svnFilePath.getText(),
							MainDialog.this.myeclipseFilePath.getText());
					boolean flag = patch.creatPatch();
					if (flag) {
						JOptionPane.showMessageDialog(MainDialog.this,
								"���ɲ������ɹ�");
						// ���ɳɹ�֮�󻺴���Ϣ
						PatchUtil.cacheInfo(patch);
					} else {
						JOptionPane.showMessageDialog(MainDialog.this,
								"���ɲ�����ʧ�ܣ������log");
					}
				} else {
					JOptionPane.showMessageDialog(MainDialog.this, "��ѡ��·��");
				}
			} else {
				chooser = new JFileChooser();
				// ���õ�ǰĿ¼ - Ĭ��ָ��tomcatĬ��·��
				chooser.setCurrentDirectory(new java.io.File("."));
				String title = "";
				switch (index) {
				case 2:
					title = "������";
					if (bean != null) {
						chooser.setCurrentDirectory(new java.io.File(bean
								.getPatchPath()));
					} else {
						chooser.setCurrentDirectory(new java.io.File(
								"C:\\Users\\android\\Desktop"));
					}
					break;
				case 3:
					title = "svn�����ļ�";
					if (bean != null) {
						chooser.setCurrentDirectory(new java.io.File(bean
								.getSvnFilePath()));
					} else {
						chooser.setCurrentDirectory(new java.io.File(
								"C:\\Users\\android\\Desktop"));
					}
					break;
				case 4:
					if (bean != null) {
						chooser.setCurrentDirectory(new java.io.File(bean
								.getMyeclipseFilePath()));
					} else {
						title = "Դ����";
						chooser.setCurrentDirectory(new java.io.File(
								"E:\\workspace"));
					}
					break;
				default:
					title = "";
					break;
				}
				int result;
				chooser.setDialogTitle("��ѡ��" + title + "·��");
				// ���� JFileChooser���������û�ֻѡ���ļ���ֻѡ��Ŀ¼�����߿�ѡ���ļ���Ŀ¼(����ѡ��Ŀ¼)
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				// ȷ���Ƿ� AcceptAll FileFilter ������ѡ��������б���һ������ѡ�
				chooser.setAcceptAllFileFilterUsed(false);
				// ����һ�� "Open File" �ļ�ѡ�����Ի���APPROVE_OPTION��ѡ��ȷ�ϣ�yes��ok���󷵻ظ�ֵ
				if (chooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					// ���ص�ǰĿ¼
					System.out.println("getCurrentDirectory(): "
							+ chooser.getCurrentDirectory());
					// ����ѡ�е��ļ�
					File selectfile = chooser.getSelectedFile();
					System.out.println("getSelectedFile() : " + selectfile);
					// ���ı���ֵ
					switch (index) {
					case 2:
						MainDialog.this.patchPath.setText(selectfile
								.getAbsolutePath());
						break;
					case 3:
						MainDialog.this.svnFilePath.setText(selectfile
								.getAbsolutePath());
						break;
					case 4:
						MainDialog.this.myeclipseFilePath.setText(selectfile
								.getAbsolutePath());
						break;
					default:
						JOptionPane.showMessageDialog(MainDialog.this,
								"���棺��ǰ������Ч" + index);
						break;
					}
				} else {
					System.out.println("No Selection ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(MainDialog.this, "���ɲ�����ʧ��");
		}
		return null;
	}

	/**
	 * ��ʼ������
	 */
	public void init() {
		try {
			if (bean != null) {
				patchName.setText(bean.getPatchName());
				patchPath.setText(bean.getPatchPath());
				svnFilePath.setText(bean.getSvnFilePath());
				myeclipseFilePath.setText(bean.getMyeclipseFilePath());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("��ʼ��ʧ�ܣ�" + e.getMessage());
		}
	}

	/**
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

	public static PatchUtil bean = null;

	public MainDialog() {
		setTitle("������ tool");
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
		setHeader("���������ƣ�", 1);
		// �ڶ���ͷ��
		setHeader("������·����", 2);
		// ������ͷ��
		setHeader("svn����file��", 3);
		setHeader("Դ����·����", 4);

		// ��һ���ı�
		setText(this.patchName, 1, "");
		// �ڶ����ı�
		setText(this.patchPath, 2, "��λ���洢���������ļ���(����)");
		// �������ı�
		setText(this.svnFilePath, 3, "��λ��svn�����ļ��µ���Ŀ(hnszwfw-core)");
		setText(this.myeclipseFilePath, 4, "��λ��myeclipse�µ���Ŀ(hnszwfw-core)");

		// ��һ�а�ť
		setSelect("ѡ��", 2);
		// �ڶ��а�ť
		setSelect("ѡ��", 3);
		setSelect("ѡ��", 4);

		// ���ð�ť
		setButton("���ɲ�����");

		// made by
		setMadeBy();

		// ��ʼ��
		try {
			bean = JSON.parseObject(Util.read(PatchUtil.cacheFilePath),
					PatchUtil.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("��ʼ�쳣��" + e.getMessage());
		}
		init();
	}
}