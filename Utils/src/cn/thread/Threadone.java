package cn.thread;

public class Threadone extends Thread {

	private String param;

	Threadone(String param) {
		this.param = param;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("�߳�" + this.getId() + "|" + param + "������ִ��...");
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
