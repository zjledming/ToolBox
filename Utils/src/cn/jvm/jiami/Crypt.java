package cn.jvm.jiami;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * ��class���м���
 * @author ximing.fu
 *
 */
public class Crypt {

	public static void main(String[] args) throws Exception {

		/*
		 * �ṩǿ��������������� (RNG)
		 */
		SecureRandom sr = new SecureRandom();
		// ��ȡ��Ҫ
		FileInputStream fi = new FileInputStream(new File("d:/key.txt"));
		byte rawKeyData[] = new byte[fi.available()];
		fi.read(rawKeyData);
		fi.close();
		// ָ��һ�� DES ��Կ������һ�� DESKeySpec ����ʹ�� key �е�ǰ 8 ���ֽ���Ϊ DES ��Կ����Կ����
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		// ������Կ�Ĺ���������ת��ָ���㷨��������Կ�� SecretKeyFactory ���󣬸����ṩ����Կ�淶����Կ���ϣ����� SecretKey ����
		SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
		/*
		 * Ϊ���ܺͽ����ṩ���빦��
		 */
		// ����ʵ��ָ��ת���� Cipher ����
		Cipher cipher = Cipher.getInstance("DES");
		// ��ȡ�Ը���֤��Ĺ�Կ�����Դ��ʼ���� Cipher��
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		// Դ����
		FileInputStream fi2 = new FileInputStream(new File(
				"d:/HelloWorld.class"));
		byte data[] = new byte[fi2.available()];
		fi2.read(data);
		fi2.close();
		// ����Դ���룺�������ֲ������ܻ�������ݣ����߽���һ���ಿ�ֲ�����
		byte encryptedData[] = cipher.doFinal(data);
		FileOutputStream fo = new FileOutputStream(new File(
				"d:/HelloWorld.class"));
		fo.write(encryptedData);
		fo.close();
	}
}