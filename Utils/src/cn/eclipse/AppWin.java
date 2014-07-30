package cn.eclipse;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * @author huangfox
 *
 */
public class AppWin {
	protected Shell shell;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppWin window = new AppWin();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(500, 375);
		shell.setText("������");
		//����tabFolder
		final TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(10, 10, 464, 319);
		//ÿһ��tabItem�ڰ���һ��Composite�����࣬������ǿ��Խ�����������
		//����̳�composite�ͺ��ˡ�
		final TabItem comaTabItem = new TabItem(tabFolder, SWT.NONE);
		comaTabItem.setText("ComA");
		//ʵ����һ�����Ƕ��Ƶ�composite
		final Composite composite = new ComA(tabFolder, SWT.NONE);
		//�����Ƕ��Ƶ�composite���ӵ�tabItem�ϡ�
		comaTabItem.setControl(composite);
		//
		final TabItem combTabItem = new TabItem(tabFolder, SWT.NONE);
		combTabItem.setText("ComB");
		final Composite composite_1 = new ComB(tabFolder, SWT.NONE);
		combTabItem.setControl(composite_1);
		
		//
	}
}
