package cn.com.bean;

import java.util.Map;

public class NoteProterties {

	private boolean noteUsed;// ���ſ���

	private String noteAttemptTimes;// �����ط��Ĵ���

	private Map<String, Object> map;// ������Ӧ���ŷ��ͻ��Ƶ�������Ϣ

	public boolean isNoteUsed() {
		return noteUsed;
	}

	public void setNoteUsed(boolean noteUsed) {
		this.noteUsed = noteUsed;
	}

	public String getNoteAttemptTimes() {
		return noteAttemptTimes;
	}

	public void setNoteAttemptTimes(String noteAttemptTimes) {
		this.noteAttemptTimes = noteAttemptTimes;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
