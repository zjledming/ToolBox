package cn.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.sax.StringUtil;

/**
 * @author ximing.fu
 * 
 */
public class BeanReflect {
	
	/**
	 * ͨ��ִ��set()��ִ��sql������bean���ֶ�set��Ӧ��ֵ
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
//	public LandInfoBean getLandByProjectid(String projectid) throws Exception {
//		String sql = "select * from td_hyxfc_land_info t where t.project_id = ?";
//		PreparedDBUtil pdb = new PreparedDBUtil();
//		pdb.preparedSelect(dsName, sql);
//		pdb.setString(1, projectid);
//		pdb.executePrepared();
//		LandInfoBean bean = new LandInfoBean();
//		if (pdb.size() > 0) {
//			Class c = Class
//					.forName("com.chinacreator.v2.xzsp.entity.houseintegration.LandInfoBean");// ��ȡ��ʵ���Ԫ����
//			Field[] fields = c.getDeclaredFields();
//			for (Field field : fields) {
//				String fieldName = field.getName();
//				String setter = "set"
//						+ field.getName().substring(0, 1).toUpperCase()
//						+ field.getName().substring(1);
//				// ��ȡ������ΪsetName�ķ�����field.getType())��ȡ���Ĳ�����������
//				Method method = c.getDeclaredMethod(setter, field.getType());
//				// ���ø÷�����ָ������ֵ
//				method.invoke(bean,
//						StringUtil.deNull(pdb.getString(0, fieldName)));
//			}
//		}
//		return bean;
//	}
	
	
	/**
	 * ִ��get��������ȡʵ��bean���ֶε�ֵ
	 * @throws Exception
	 */
	private void executeGet(Object bean) throws Exception {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			// ȡֵ
			String getter = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			Method method = bean.getClass().getMethod(getter,
					new Class[] {});
			Object value = method.invoke(bean, new Object[] {});
			if (value == null) {
				value = "";
			}
			if (value != null && !StringUtil.isBlank(value.toString())) {
				System.out.println(StringUtil.deNull(value.toString()));
			}
		}
	}
	

}
