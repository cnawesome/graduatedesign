package com.zkl.secondhand.model;

import java.util.Date;
import java.util.List;

public class Order {
	private String id;
	private double money;
	private String receiverAddress;
	private String receiverName;
	private String receiverPhone;
	private int paystate;
	private Date ordertime;

	private List<OrderItem> items;
	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	// private int user_id;
	/*
	 * 如果数据库表中字段有外键关系，可把字段定义为对象类型
	 */
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getmoney() {
		return money;
	}

	public void setmoney(double money) {
		this.money = money;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public int getpaystate() {
		return paystate;
	}

	public void setpaystatu(int payState) {
		this.paystate = payState;
	}

	public Date getordertime() {
		return ordertime;
	}

	public void setOrderTime(Date orderTime) {
		this.ordertime = orderTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", money=" + money + ", receiveAddress=" + receiverAddress + ", receiveName="
				+ receiverName + ", receivePhone=" + receiverPhone + ", paySatatu=" + paystate + ", orderTime="
				+ ordertime + ", user=" + user + "]";
	}

}
