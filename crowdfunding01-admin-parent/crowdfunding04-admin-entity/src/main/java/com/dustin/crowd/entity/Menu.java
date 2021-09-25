package com.dustin.crowd.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * The persistent class for the t_menu database table.
 * 
 */
@Data
@Entity
@Table(name = "t_menu")
@NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 父节点的 id
	private Integer pid;
	
	// 节点名称
	private String name;
	
	// 节点附带的 URL 地址，是将来点击菜单项时要跳转的地址
	private String url;
	
	// 节点图标的样式
	private String icon;
	
	// 存储子节点的集合，初始化是为了避免空指针异常
//	@OneToMany(mappedBy = "id")
	@Transient
	private List<Menu> children = new ArrayList<>();
	
	// 控制节点是否默认为打开装，设置为 true 表示默认打开
	@Transient
	private Boolean open = true;
}