package cn.jxl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class ImportCA {

	// ���ϴ��ļ���������ʱ�趨����ʱ�ļ�λ��
	private String tempPath = "C:\\TEMP";

	// ��ȡ���ϴ�����
	private HttpServletRequest fileuploadRequest = null;

	// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
	private int sizeThreshold = 4096;

	// ���������û��ϴ��ļ���С,��λ:�ֽ�(��10M)
	private long sizeMax = 10485760;

	private InputStream is = null;

	private StringBuffer sb = new StringBuffer();

	public ImportCA() {
	}

	public ImportCA(String tempPath) {
		this.tempPath = tempPath;
	}

	public ImportCA(HttpServletRequest fileuploadRequest) {
		this.fileuploadRequest = fileuploadRequest;
	}

	public ImportCA(String tempPath, HttpServletRequest fileuploadRequest) {
		this.tempPath = tempPath;
		this.fileuploadRequest = fileuploadRequest;
	}

	/**
	 * @Title: getFile
	 * @Description: ��ȡ�ļ�
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public String getFile() throws Exception {
		// ���û����ʱĿ¼���򴴽���
		if (!(new File(tempPath).isDirectory())) {
			try {
				new File(tempPath).mkdirs();
			} catch (SecurityException e) {
				System.out.println("can not make security direcoty");
			}
		}

		DiskFileUpload fu = new DiskFileUpload();

		// �������ֻ�������ڴ��д洢������,��λ:�ֽ�,�������浽��ʱ�ļ�
		fu.setSizeThreshold(sizeThreshold);

		// ���������û��ϴ��ļ���С,��λ:�ֽڣ�10M��
		fu.setSizeMax(sizeMax);

		// ����һ���ļ���С����sizeThreshold��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
		fu.setRepositoryPath(tempPath);

		Iterator iter = null;
		// ��ȡ�ϴ���Ϣ
		try {
			List fileItems = fu.parseRequest(fileuploadRequest);
			// ����ÿ���ϴ����ļ�
			iter = fileItems.iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			// �������������ļ�������б���Ϣ
			if (!item.isFormField()) {
				// �ϴ��ļ���Ϣ
				String name = item.getName();
				if ((name == null) || name.equals("") && item.getSize() == 0) {
					continue;
				}
				try {
					is = item.getInputStream();
					item.delete();
					// ��������
					return doImport();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null) {
							is.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return "����ʧ��";
	}

	/**
	 * @Title: doImport
	 * @Description: ��������
	 * @return
	 * @return boolean
	 * @throws
	 */
	public String doImport() {
		try {
			// workbook��excel)
			Workbook wb = Workbook.getWorkbook(is);
			if (wb == null)
				return "�����ļ��쳣�������ļ�����";
			// sheet��������
			Sheet[] sheet = wb.getSheets();
			// ��������
			if (sheet != null && sheet.length > 0) {
				for (int i = 0; i < sheet.length; i++) {
					// ��½������,֤��������
					int m = 0, n = 0;
					// ȡ��һ��
					Cell[] fristcells = sheet[i].getRow(0);
					for (int j = 0; j < fristcells.length; j++) {
						if ("��¼��".equals(fristcells[j].getContents().trim())) {
							m = j;
						} else if ("֤����".equals(fristcells[j].getContents()
								.trim())) {
							n = j;
						}
					}
					if (m == 0 || n == 0) {
						sb.append("��һ�б������<��¼��>��<֤����>���У�����ϸ���");
					}
					// ��
					int lenght = sheet[i].getRows();
					if (lenght > 0) {
						int o = 0, p = 0, q = 0;
						String flag = "";
						for (int j = 1; j < lenght; j++) {
							Cell[] cells = sheet[i].getRow(j);
							for (int k = 0; k < cells.length; k++) {
								System.out.print(cells[k].getContents().trim()
										+ "--");
							}
							System.out.println();
						}
						return sb.toString();
					}
				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "����ʧ��";
	}

}
