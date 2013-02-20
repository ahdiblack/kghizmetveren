package com.yaser.data.model;

// Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class TblCategory implements java.io.Serializable {

	@Id
	private Integer categoryId;
	private int parentId;
	private String categoryName;
	@ManyToMany
	@JoinColumn(name="parent_id")
	private Set<TblCategory> tblPoiCategories = new HashSet(0);
	private TblCategory parent;
	private int status;
	
	public TblCategory() {
	}

	public TblCategory(int parentId, String categoryName) {
		this.parentId = parentId;
		this.categoryName = categoryName;
	}

	public TblCategory(int parentId, String categoryName, Set tblPoiCategories) {
		this.parentId = parentId;
		this.categoryName = categoryName;
		this.tblPoiCategories = tblPoiCategories;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Set getTblPoiCategories() {
		return this.tblPoiCategories;
	}

	public void setTblPoiCategories(Set tblPoiCategories) {
		this.tblPoiCategories = tblPoiCategories;
	}

	public TblCategory getParent() {
		return parent;
	}

	public void setParent(TblCategory parent) {
		this.parent = parent;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
