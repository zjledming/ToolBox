package cn.cglib.staticproxy;

/**
 * ����һ�������ࣨ��ǿCountImplʵ���ࣩ
 * 
 * @author Administrator
 * 
 */
public class CountProxy implements Count {
	private CountImpl countImpl;

	/**
	 * ����Ĭ�Ϲ�����
	 * 
	 * @param countImpl
	 */
	public CountProxy(CountImpl countImpl) {
		this.countImpl = countImpl;
	}

	@Override
	public void queryCount() {
		System.out.println("������֮ǰ");
		// ����ί����ķ���;
		countImpl.queryCount();
		System.out.println("������֮��");
	}

	@Override
	public void updateCount() {
		System.out.println("������֮ǰ");
		// ����ί����ķ���;
		countImpl.updateCount();
		System.out.println("������֮��");

	}

}