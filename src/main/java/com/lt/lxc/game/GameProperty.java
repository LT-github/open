package com.lt.lxc.game;


public class GameProperty {
	
	private int count;
	private double money;
	private double single;
	
	public GameProperty() {
		super();
	}
	public GameProperty(int count, double money, double single) {
		super();
		this.count = count;
		this.money = money;
		this.single = single;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getSingle() {
		return single;
	}
	public void setSingle(double single) {
		this.single = single;
	}
	
	
}
