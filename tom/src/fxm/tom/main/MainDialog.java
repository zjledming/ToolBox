package fxm.tom.main;

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
	// 主panel
	private JPanel contentPane;
	// 文本框
	private JTextField tomcatPath = new JTextField();
	private JTextField projectPath = new JTextField();

	// 选择器
	JFileChooser chooser;

	// x,y,w,h - 初始大小
	int ix = 400;
	int iy = 200;
	int iwidth = 480;
	int iheight = 230;

	// 第一行第一列表格头部位置25, 30, 110, 15
	int tx = 25;
	int ty = 30;
	int twidth = 80;
	int theight = 21;

	// 文本框宽度
	int inputwidth = 250;
	// select but 宽度
	int swidth = 60;

	/**
	 * 设置表格头部，每多一行，x不变，y+40，w,h不变
	 * 
	 * @param herdText
	 *            文本
	 * @param index
	 *            第几行头部，从index=1开始
	 */
	public void setHeader(String herdText, int index) {
		// 多了n行
		int n = index - 1;
		JLabel label = new JLabel(herdText);
		label.setBounds(tx, ty + 40 * n, twidth, theight);
		this.contentPane.add(label);
	}

	/**
	 * 设置表格文本框，每多一行，x不变，y+40，w,h不变
	 * 
	 * @param jTextField
	 *            操作控件
	 * @param index
	 *            第几行文本，从index=1开始
	 */
	public void setText(JTextField jTextField, int index, String text) {
		// 多了n行
		int n = index - 1;
		// jTextField = new JTextField();
		// x位置为边界+表头部宽度
		jTextField.setBounds(tx + twidth, ty + 40 * n, inputwidth, theight);
		// 设置此 TextField 中的列数，然后验证布局
		jTextField.setColumns(10);
		jTextField.setText(text);
		this.contentPane.add(jTextField);
	}

	/**
	 * 设置表格按钮，每多一行，x不变，y+40，w,h不变
	 * 
	 * @param butText
	 *            按钮名称
	 * @param index
	 *            第几行按钮，从index=1开始
	 */
	public void setSelect(String butText, final int index) {
		JButton jb = new JButton(butText);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invoke(index);
			}
		});
		// 多了n行
		int n = index - 1;
		// x位置为边界+表头部宽度+文本框宽度+10（隔开一定距离）
		jb.setBounds(tx + twidth + inputwidth + 10, ty + 40 * n, swidth,
				theight);
		this.contentPane.add(jb);
	}

	/**
	 * 点击"选择"按钮事件
	 * 
	 * @param index
	 */
	public String invoke(int index) {
		try {
			if (index == 10000) {
				// 部署
				if ((MainDialog.this.projectPath.getText().length() > 0)
						&& (MainDialog.this.tomcatPath.getText().length() > 0)) {
					new Tomcat().config(MainDialog.this.tomcatPath.getText(),
							MainDialog.this.projectPath.getText());
					JOptionPane.showMessageDialog(MainDialog.this, "部署成功！");
					invoke(10001);
				} else {
					// 提示框
					JOptionPane.showMessageDialog(MainDialog.this,
							"请选择tomcat路径和项目路径！");
				}
			} else if (index == 10001) {
				// 清理缓存
				if ((MainDialog.this.projectPath.getText().length() > 0)
						&& (MainDialog.this.tomcatPath.getText().length() > 0)) {
					try {
						new Tomcat()
								.clear(MainDialog.this.tomcatPath.getText());
						JOptionPane.showMessageDialog(MainDialog.this,
								"清理缓存成功！");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(MainDialog.this,
								"清理缓存失败，程序发生异常：" + e.getMessage());
					}
				} else {
					// 提示框
					JOptionPane.showMessageDialog(MainDialog.this,
							"请选择tomcat路径！");
				}
			} else {
				// 选择
				JTextField jField = null;
				String currentDirectory = "";
				String dialogTitle = "";
				if (index == 1) {
					jField = MainDialog.this.tomcatPath;
					currentDirectory = fxm.tom.util.Tomcat.tomcatPath;
					dialogTitle = "请选择tomcat路径";
				} else if (index == 2) {
					jField = MainDialog.this.projectPath;
					currentDirectory = fxm.tom.util.Tomcat.projectPath;
					dialogTitle = "请选择项目路径";
				}
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(currentDirectory));
				chooser.setDialogTitle(dialogTitle);
				// 设置 JFileChooser，以允许用户只选择文件、只选择目录，或者可选择文件和目录
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 这里选择目录
				// 确定是否将 AcceptAll FileFilter 用作可选择过滤器列表中一个可用选项。
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					// 返回选中的文件
					File selectfile = chooser.getSelectedFile();
					// 给tomcat路径赋值
					jField.setText(selectfile.getAbsolutePath());

					// 每次选择tomcat路径之后，更新项目路径
					if (index == 1) {
						MainDialog.this.projectPath.setText(new Tomcat()
								.getLocalCofingProject(selectfile
										.getAbsolutePath()));
					}
				} else {
					System.out.println("No Selection ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(MainDialog.this,
					"部署失败，程序发生异常：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 单个按钮<br>
	 * 设置按钮，倒数第二行，居中对齐
	 * 
	 * @param butText
	 */
	public void setButton(String butText) {
		// 设置按钮宽高,每2个字占60
		int bwidth = (butText.length() / 2) * 60;

		JButton button = new JButton(butText);
		/*
		 * 将一个 ActionListener 添加到按钮中;
		 * 用于接收操作事件的侦听器接口。对处理操作事件感兴趣的类可以实现此接口，而使用该类创建的对象可使用组件的 addActionListener
		 * 方法向该组件注册。在发生操作事件时，调用该对象的 actionPerformed 方法。
		 */
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invoke(10000);
			}
		});
		// button.setBounds(ix+iwidth/2-bwidth/2, iy+iheight-2*theight, bwidth,
		// theight);
		// 按钮的x.y都是相对窗体本身边界，不是显示屏的边界（减去title的宽度）
		button.setBounds(iwidth / 2 - bwidth / 2 - 5, iheight - 30 - 4
				* theight, bwidth, theight);
		this.contentPane.add(button);
	}

	int butWidth = 90;

	/**
	 * 多个按钮<br>
	 * 设置按钮，倒数第二行，居中对齐
	 * 
	 * @param butText
	 */
	public void setButton(String[] butText) {
		int butx0 = (iwidth - butText.length * butWidth - 30) / 2;
		for (int i = 0; i < butText.length; i++) {
			JButton button = new JButton(butText[i]);
			/*
			 * 将一个 ActionListener 添加到按钮中;
			 * 用于接收操作事件的侦听器接口。对处理操作事件感兴趣的类可以实现此接口，而使用该类创建的对象可使用组件的
			 * addActionListener 方法向该组件注册。在发生操作事件时，调用该对象的 actionPerformed 方法。
			 */
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("部	  署")) {
						invoke(10000);
					} else if (e.getActionCommand().equals("清理缓存")) {
						invoke(10001);
					}
				}
			});
			// 按钮的x.y都是相对窗体本身边界，不是显示屏的边界（减去title的宽度）
			button.setBounds(butx0 + i * (butWidth + 30), iheight - 30 - 4
					* theight, butWidth, theight);
			this.contentPane.add(button);

		}

	}

	/**
	 * 版权说明
	 */
	public void setMadeBy() {
		JLabel label = new JLabel("made by ximing.fu 版权所有.");
		label.setBounds((int) (iwidth * 0.57), iheight - 30 - 2 * theight, 185,
				theight);
		this.contentPane.add(label);
	}

	public static void main(String[] args) {
		/*
		 * 理解1：（很有道理，安全并且节省内存）
		 * 使用eventqueue.invokelater()好处是显而易见的，这个方法调用完毕后，它会被销毁，因为匿名内部类是作为临时变量存在的，
		 * 给它分配的内存在此时会被释放
		 * 。这个对于只需要在一个地方使用时可以节省内存，而且这个类是不可以被其它的方法或类使用的，只能被EventQueue
		 * .invokeLater(
		 * )来使用。但如果你需要一个在很多地方都能用到的类，而不是只在某一个类里面或者方法里用的话，定义成匿名内部类显然是不可取的。
		 * 是，runnable是跟线程相关的类。
		 * 
		 * 理解2： 把这个事件（new Runnable())添加到awt的事件处理线程当中去
		 * awt的事件处理线程会按照队列的顺序依次调用每个待处理的事件来运行
		 * 使用该方式的原因是：awt是单线程模式的，所有awt的组件只能在(推荐方式)事件处理线程中访问，从而保证组件状态的可确定性。
		 * 
		 * 注：awt:抽象窗口工具包 （Abstract Windowing Toolkit）
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
		setTitle("tomcat部署工具");
		// 设置此窗体是否可由用户调整大小。
		setResizable(true);
		// 设置用户在此窗体上发起 "close" 时默认执行的操作
		setDefaultCloseOperation(3);
		/*
		 * (int x, int y, int width, int height)移动组件并调整其大小，使其符合新的有界矩形 r。由 r.x 和
		 * r.y 指定组件的新位置，由 r.width 和 r.height 指定组件的新大小 如果 r.width 值或 r.height
		 * 值小于之前调用 setMinimumSize 指定的最小大小，则它的值将自动增加。
		 */
		setBounds(ix, iy, iwidth, iheight);
		this.contentPane = new JPanel();
		// setBorder
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// 设置 contentPane 属性
		setContentPane(this.contentPane);
		// 设置此容器的布局管理器
		this.contentPane.setLayout(null);

		// 第一行头部
		setHeader("tomcat路径：", 1);
		// 第二行头部
		setHeader("项目路径：", 2);

		// 第一行文本
		setText(this.tomcatPath, 1, Tomcat.tomcatPath);
		// 第二行文本
		setText(this.projectPath, 2, Tomcat.projectPath);

		// 第一行按钮
		setSelect("选择", 1);
		setSelect("选择", 2);

		// 设置按钮
		// setButton();
		setButton(new String[] { "部	  署", "清理缓存" });

		// made by
		setMadeBy();

		// 初始化
		try {
			MainDialog.this.projectPath
					.setText(new Tomcat()
							.getLocalCofingProject(MainDialog.this.tomcatPath
									.getText()));
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(MainDialog.this,
					"【项目路径】初始化异常：" + e.getMessage());
		}
	}
}