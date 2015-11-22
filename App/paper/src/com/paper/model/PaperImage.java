package com.paper.model;

import java.io.Serializable;

public class PaperImage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6554601408343257702L;
	private long id;
	private long pid;
	private String name;
	private int sort;
	private long datasize;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public long getPid()
	{
		return pid;
	}
	public void setPid(long pid)
	{
		this.pid = pid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getSort()
	{
		return sort;
	}
	public void setSort(int sort)
	{
		this.sort = sort;
	}
	public long getDatasize() {
		return datasize;
	}
	public void setDatasize(long datasize) {
		this.datasize = datasize;
	}

	@Override
	public String toString() {
		return "PaperImage{" +
				"datasize=" + datasize +
				", id=" + id +
				", pid=" + pid +
				", name='" + name + '\'' +
				", sort=" + sort +
				'}';
	}
}
