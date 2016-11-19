package com.twosnail.frame.commin.util.kit.map;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Map转Obj
 */
public class MapToObjKit {

	/**
	 * Map<String,Object>转Obj对象
	 * @param <T>
	 * @param map
	 * @param obj
	 */
	public static <T> void map2Obj(Map<String, Object> map, T obj) {
		Class<?> clazz = obj.getClass();
		Set<Map.Entry<String, Object>> set = map.entrySet();
		Iterator<Map.Entry<String, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				String setMethod = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
				try {
					Field field = null;
					try {
						field = clazz.getDeclaredField(key);
					} catch (NoSuchFieldException e) {
						System.out.println("没有找到对应的属性:"+key);
					}
					if (field != null && value != null) {
						Method method = null;
						try {
							method = clazz.getDeclaredMethod(setMethod, new Class[] { field.getType() });
						} catch (NoSuchMethodException e) {
							System.out.println(obj + "中没有对应的方法：" + setMethod);
						}
						if (method != null)
							method.invoke(obj, toObject(value, field));
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 类型匹配
	 * 
	 * @param value
	 * @param field
	 * @return
	 */
	private static Object toObject(Object value, Field field) {
		if (field.getType() == String.class) {
			value = value + "";
		} else {
			if (value.equals("null") || "".equals(value)) {
				value = "0";
			}
			if (field.getType() == int.class || field.getType() == Integer.class) {
				value = Integer.valueOf("" + value);
			} else if (field.getType() == double.class || field.getType() == Double.class) {
				value = Double.valueOf("" + value);
			} else if (field.getType() == float.class || field.getType() == Float.class) {
				value = Float.valueOf("" + value);
			} else if (field.getType() == short.class || field.getType() == Short.class) {
				value = Short.valueOf("" + value);
			} else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
				value = Boolean.valueOf("" + value);
			} else if (field.getType() == byte.class || field.getType() == Byte.class) {
				value = Byte.valueOf("" + value);
			} else if (field.getType() == long.class || field.getType() == Long.class) {
				value = Long.valueOf("" + value);
			} else if (field.getType() == char.class || field.getType() == Character.class) {
				value = (Character) value;
			}
		}
		return value;
	}

	/**
	 * Map<String,Object>转Obj对象
	 * @param <T>
	 * @param map
	 * @param clazz
	 * @return 对应T的对象
	 * @throws ClassNotFoundException
	 */
	public static <T> T map2Obj(Map<String, Object> map, Class<T> clazz) throws ClassNotFoundException {
		T obj = null;
		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Map.Entry<String, Object>> iterator = set.iterator();
		String setMethod = "";
		String key = "";
		try {
			try {
				obj = clazz.getDeclaredConstructor(new Class[] {}).newInstance(new Object[] {});
			} catch (NoSuchMethodException e1) {
				System.out.println(obj + "中没有对应的构造方法方法");
			}
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				key = entry.getKey();
				Object value = entry.getValue();
				Field field = null;
				try {
					field = clazz.getDeclaredField(key);
				} catch (NoSuchFieldException e) {
				}
				if (field != null && value != null) {
					setMethod = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
					Method method = null;
					try {
						method = clazz.getDeclaredMethod(setMethod, new Class[] { field.getType() });
					} catch (NoSuchMethodException e) {
						System.out.println(obj + "中没有对应的方法：" + setMethod);
					}
					if (method != null)
						method.invoke(obj, toObject(value, field));
				}
			}
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return obj;
	}

	public static <T> List<T> listToObject(List<Map<String, Object>> list, Class<T> clazz) {
		List<T> resultList = null;
		if (list != null && list.size() > 0) {
			resultList = new ArrayList<T>();
			Iterator<Map<String, Object>> iterator = list.iterator();
			while (iterator.hasNext()) {
				T obj = null;
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				Map<String, Object> map = iterator.next();
				map2Obj(map, obj);
				resultList.add(obj);
			}
		}
		return resultList;
	}
	
	public static <T> void listToObject(List<Map<String, Object>> list, Class<T> clazz, List<T> resultList ) {
		
		if (list != null && list.size() > 0) {
			Iterator<Map<String, Object>> iterator = list.iterator();
			while (iterator.hasNext()) {
				T obj = null;
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				Map<String, Object> map = iterator.next();
				map2Obj(map, obj);
				resultList.add(obj);
			}
		}
	}
	
}
