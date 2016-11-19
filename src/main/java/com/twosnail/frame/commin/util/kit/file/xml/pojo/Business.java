/**
 * 
 */
package com.twosnail.frame.commin.util.kit.file.xml.pojo;

import java.util.List;

/**
 * 业务属性配置对象
 */
public class Business {

	/**
	 * 业务对象唯一标识
	 */
	private String id;
	/**
	 * 业务对象的名字
	 */
	private String name;
	
	/**
	 * 业务对象类型
	 */
	private String type;
	
	/**
	 * 业务对象列表数据
	 */
	private List<StringList> lists;
	
	/**
	 * 业务对象特性数据
	 */
	private List<Param> params;
	
	/**
	 * 业务对象复合特性数据
	 */
	private List<Business> complexs;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param lists the lists to set
	 */
	public void setLists(List<StringList> lists) {
		this.lists = lists;
	}
	
	/**
	 * @return the lists
	 */
	public List<StringList> getLists() {
		return lists;
	}

	/**
	 * @return the params
	 */
	public List<Param> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List<Param> params) {
		this.params = params;
	}

	/**
	 * @return the complexs
	 */
	public List<Business> getComplexs() {
		return complexs;
	}

	/**
	 * @param complexs the complexs to set
	 */
	public void setComplexs(List<Business> complexs) {
		this.complexs = complexs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
