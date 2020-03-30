package com.lt.lxc.utils;

public class GlobalConstant {


	//下单开关
	public enum Switch{
		SWITCH(false);

		private boolean flag;

		private Switch(boolean flag) {
			this.flag = flag;
		}

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}
	}
	public enum LotteryResult{
		LOSE(0,"赢"),
		WIN(1,"输");

		private int code;

		private String message;

		LotteryResult(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	public enum Setting{
		MAX_RATIO(1),//大数据最大倍率
		MIN_RATIO(-1),//大数据最小倍率
		MIN_BET_MONEY(600.0);//最小下注金额
		private double value;
		Setting(double value){
			this.value = value;
		}
		public double getValue() {
			return value;
		}
		public void setValue(double value) {
			this.value = value;
		}
	}
	public enum NextIssueNumber{
		ISSUE_NUMBER("");
		private String data;
		NextIssueNumber(String data){
			this.data = data;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
	}
}
