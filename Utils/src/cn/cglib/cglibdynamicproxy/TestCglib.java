package cn.cglib.cglibdynamicproxy;

/**
 * @ClassName: TestCglib
 * @Description: cglib��̬����
 * @author XiMing.Fu
 * @date 2014-3-17 ����02:33:47
 * 
 */
public class TestCglib {

	public static void main(String[] args) {
		BookFacadeCglib cglib = new BookFacadeCglib();
		BookFacadeImpl1 bookCglib = (BookFacadeImpl1) cglib
				.getInstance(new BookFacadeImpl1());
		bookCglib.addBook();
	}
}