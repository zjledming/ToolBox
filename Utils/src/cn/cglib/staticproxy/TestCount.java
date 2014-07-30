package cn.cglib.staticproxy;

/**
 * @ClassName: TestCount
 * @Description: ��̬��������Ա�������ض������Զ�����Դ���룬�ٶ�����롣�ڳ�������ǰ���������.class�ļ����Ѿ������ˡ�
 *               ȱ�㣺ÿһ��������ֻ��Ϊһ���ӿڷ���
 *               ������һ�����򿪷��б�Ȼ���������Ĵ������ң����еĴ���������˵��õķ�����һ��֮�⣬�����Ĳ�����һ�������ʱ�϶����ظ�����
 * @author XiMing.Fu
 * @date 2014-3-17 ����01:54:30
 * 
 */
public class TestCount {
	public static void main(String[] args) {
		CountImpl countImpl = new CountImpl();
		CountProxy countProxy = new CountProxy(countImpl);
		countProxy.updateCount();
		countProxy.queryCount();

	}
}