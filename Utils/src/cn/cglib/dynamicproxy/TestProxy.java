package cn.cglib.dynamicproxy;

/**
 * @ClassName: TestProxy
 * @Description: jdk��̬���� ֻ��Ҫ��ί�ж���target��������࣬
 *               bookProxy.addBook();ִ�е�ʱ�򣬻��Զ�ִ�д�������invoke������ǰ��ҵ��
 * @author XiMing.Fu
 * @date 2014-3-17 ����02:20:56
 * 
 */
public class TestProxy {

	public static void main(String[] args) {
		BookFacadeProxy proxy = new BookFacadeProxy();
		BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
		bookProxy.addBook();
	}

}