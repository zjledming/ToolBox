package cn.timer;

import java.util.TimerTask;

/**
 * 执行内容
 * 
 * @author ximing.fu
 * 
 */
public class Task extends TimerTask {
	public void run() {
		System.out.println("我有一头小毛驴!");
	}
}