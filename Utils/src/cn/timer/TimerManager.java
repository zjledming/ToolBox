package cn.timer;

import java.util.Calendar;  
import java.util.Date;  
import java.util.Timer;  
  
/** 
 * ������� 
 * @author admin_Hzw 
 * 
 */  
public class TimerManager {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        new TimerManager();    
    }  
  
    //ʱ����(һ��)  
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;  
    public TimerManager() {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, 1); //�賿1��  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        Date date=calendar.getTime(); //��һ��ִ�ж�ʱ�����ʱ��  
        //�����һ��ִ�ж�ʱ�����ʱ�� С�ڵ�ǰ��ʱ��  
        //��ʱҪ�� ��һ��ִ�ж�ʱ�����ʱ���һ�죬�Ա���������¸�ʱ���ִ�С��������һ�죬���������ִ�С�  
        if (date.before(new Date())) {  
            date = this.addDay(date, 1);  
        }  
        Timer timer = new Timer();  
        Task task = new Task();  
        //����ָ����������ָ����ʱ�俪ʼ�����ظ��Ĺ̶��ӳ�ִ�С�  
        timer.schedule(task,date,PERIOD_DAY);    
    }  
    // ���ӻ��������  
    public Date addDay(Date date, int num) {  
        Calendar startDT = Calendar.getInstance();  
        startDT.setTime(date);  
        startDT.add(Calendar.DAY_OF_MONTH, num);  
        return startDT.getTime();  
    }  
  
} 