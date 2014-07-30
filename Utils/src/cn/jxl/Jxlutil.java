package cn.jxl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;

public class Jxlutil {

	private static final Logger LOG = Logger.getLogger(Jxlutil.class);

	/**
	 * ����������excel
	 * 
	 * @param os
	 *            ݔ���� content ������������ columnData ���ݺϲ���̖���� columnName ���^�ϲ���̖����
	 * @return void
	 */
	public static void export(OutputStream os, Vector content,
			Integer[] columnData, Map<String, Integer[]> columnName) {
		WritableWorkbook workbook = null;
		Vector inner = null;
		String value = "";// �����cell�е��ı�ֵ
		try {
			workbook = Workbook.createWorkbook(os);
			// workbook = Workbook.createWorkbook(new File("D://a.xls"));
			// ����������
			WritableSheet worksheet = workbook.createSheet("record", 0);// ������һ��������name:����������
			Label label = null;// ����д���ı����ݵ���������ȥ
			// ��ʼд������
			for (int i = 0; i < content.size(); i++) {
				inner = (Vector) content.get(i);// ��ȡһ����¼
				for (int j = 0; j < inner.size(); j++) {
					value = (String) inner.get(j);
					label = new Label(j, i, value);
					worksheet.addCell(label);
				}
			}
			// mergerColumn(worksheet, columnName);
			// mergerRows(worksheet, columnData);
			workbook.write();
			// workbook.close();
		} catch (Exception e) {
			LOG.error(e.toString());
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (Exception e) {
					LOG.error(e.toString());
				}
			}
		}
	}

	/**
	 * �ϲ�excel sheet�е�ͬ����ͬ���ݵ���
	 * 
	 * @param worksheet
	 *            �����팦�� columnData ���ݺϲ���̖����
	 * @return void
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public static void mergerRows(WritableSheet worksheet, Integer[] columnData)
			throws RowsExceededException, WriteException {
		if (worksheet == null || columnData == null || columnData.length <= 0) {
			return;
		}
		int rows = worksheet.getRows(); // �@ȡ�������е��Д�
		int columns = worksheet.getColumns(); // �@ȡ�������е��б�
		if (rows <= 1 || columns <= 0) {
			return;
		}
		for (Integer column : columnData) {
			int col = column.intValue();
			// ȥ���oЧ����
			if (col >= columns) {
				continue;
			}
			int beginRow = 0;
			while (beginRow < rows) {
				int row = getMaxSameContent(worksheet, beginRow, col, rows);
				if (row != -1) {
					worksheet.mergeCells(col, beginRow, col, row);
					beginRow = row + 1;
				} else {
					beginRow++;
				}
			}
		}

	}

	/**
	 * �@ȡexcel sheet�е�ͬ������ͬ���ݵ������λ��
	 * 
	 * @param worksheet
	 *            �����팦�� columnData ���ݺϲ���̖����
	 * @return void
	 */
	public static int getMaxSameContent(WritableSheet worksheet, int beginRow,
			int beginColumn, int rows) {
		String cellValue = worksheet.getCell(beginColumn, beginRow)
				.getContents().toString();
		int i = beginRow + 1;
		for (; i < rows; i++) {
			String value = worksheet.getCell(beginColumn, i).getContents()
					.toString();
			if (value == null || "".equals(value) || !value.equals(cellValue)) {
				break;
			}
		}
		if (i == beginRow + 1) {
			return -1;
		} else {
			return i - 1;
		}
	}

	/**
	 * �ϲ����^
	 * 
	 * @param worksheet
	 *            �����팦�� columnName ���^�ϲ���̖����
	 * @return void
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public static void mergerColumn(WritableSheet worksheet,
			Map<String, Integer[]> columnName) throws RowsExceededException,
			WriteException {
		if (worksheet == null || columnName == null || columnName.size() <= 0) {
			return;
		}
		int rows = worksheet.getRows(); // �@ȡ�������е��Д�
		int columns = worksheet.getColumns(); // �@ȡ�������е��б�
		if (rows <= 0 || columns <= 1) {
			return;
		}
		/**
		 * ���Ʊ��^
		 */
		worksheet.insertRow(0);
		copyRow(worksheet, 1, 0);
		/**
		 * ӛ䛲���Ҫ�M�кϲ��ı��^
		 */
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < columns; i++) {
			list.add(i);
		}
		/**
		 * �ϲ��I�Ռӵı��^
		 */
		Iterator<String> it = columnName.keySet().iterator();
		while (it.hasNext()) {
			String name = it.next();
			Integer[] column = columnName.get(name);
			if (column == null || column.length <= 1) {
				return;
			}
			int beginColumn = column[0];
			int endColumn = column[0];
			list.remove(column[0]);
			for (int i = 1; i < column.length; i++) {
				if (column[i] < beginColumn) {
					beginColumn = column[i];
				}
				if (column[i] > endColumn) {
					endColumn = column[i];
				}
				list.remove(column[i]);
			}
			Label label = new Label(beginColumn, 0, name);
			worksheet.addCell(label);
			worksheet.mergeCells(beginColumn, 0, endColumn, 0);
		}
		/**
		 * ���P���б��^�����Qһ�µı��^
		 */
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			worksheet.mergeCells(list.get(i), 0, list.get(i), 1);
		}
	}

	/**
	 * ������
	 * 
	 * @param worksheet
	 *            �����팦�� fromRow �������� toRow ������
	 * @return void
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	public static void copyRow(WritableSheet worksheet, int fromRow, int toRow)
			throws RowsExceededException, WriteException {
		if (worksheet == null) {
			return;
		}
		int columns = worksheet.getColumns(); // �@ȡ�������е��б�
		if (columns <= 0) {
			return;
		}
		for (int i = 0; i < columns; i++) {
			String value = worksheet.getCell(i, fromRow).getContents()
					.toString();
			Label label = new Label(i, toRow, value);
			worksheet.addCell(label);
		}
	}

	/** 
	 * @Title: getSheets 
	 * @Description: ���ļ���ȡ������
	 * @param file
	 * @return 
	 * @return Sheet[] 
	 * @throws 
	 * @author XiMing.Fu
	 */
	public Sheet[] getSheets(File file) {
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet[] sheet = wb.getSheets();
		return sheet;
	}

}
