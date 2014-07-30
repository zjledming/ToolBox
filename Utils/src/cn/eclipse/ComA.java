package cn.eclipse;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
/**
 * @author huangfox
 * ����һ��composite������򵥵ľͷ�һ��label��
 */
public class ComA extends Composite {
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public ComA(Composite parent, int style) {
		super(parent, style);
		final Label compositeLabel = new Label(this, SWT.NONE);
		compositeLabel.setText("composite a");
		compositeLabel.setBounds(178, 142, 172, 17);
		//
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
