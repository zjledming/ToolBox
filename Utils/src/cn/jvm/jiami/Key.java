package cn.jvm.jiami;

import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * ����des�㷨�е�key
 * 
 * @author ximing.fu
 * 
 */
public class Key {

	private String keyName;

	public Key() {

	}

	public Key(String keyName) {
		this.keyName = keyName;
	}

	public void createKey(String keyName) throws Exception {

		SecureRandom sr = new SecureRandom();
		/*
		 * �ṩ���Գƣ���Կ�������Ĺ��ܡ� 
		 */
		//��������ָ���㷨��������Կ�� KeyGenerator ����
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		//  ��ʼ������Կ������
		kg.init(sr);
		// ����һ����Կ
		SecretKey key = kg.generateKey();
		System.out.println(key.toString());
		// ���ػ��������ʽ����Կ���������Կ��֧�ֱ��룬�򷵻� null��
		byte rawKeyData[] = key.getEncoded();
		// ����Կ���浽�����ļ���
		FileOutputStream fo = new FileOutputStream(new File(keyName));
		fo.write(rawKeyData);
		fo.close();
	}

	public static void main(String args[]) {
		try {
			new Key("").createKey("d:/key.txt");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}