package com.lt.lxc.game;

public class GameEnum {

	public enum GameContent{
		BIG("大"),
		SMALL("小"),
		SINGLE("单"),
		DOUBLE("双"),
		AND("和"),
		DRAGON("龙"),
		TIGER("虎"),
		PEACE("合"),
		FRONT_LEOPARD("前豹"),
		FRONT_STRAIGHT("前顺"),
		FRONT_PAIR("前对"),
		FRONT_HALF_STRAIGHT("前半顺"),
		BEHIND_LEOPARD("后豹"),
		BEHIND_STRAIGHT("后顺"),
		BEHIND_PAIR("后对"),
		BEHIND_HALF_STRAIGHT("后半顺"),
		CLUTTER("散"),
		REPEAT("重"),
		FRONT_SUPER_COW("前超"),
		FRONT_ROLL_COW("前翻"),
		FRONT_FLAT_COW("前平"),
		BEHIND_SUPER_COW("后超"),
		BEHIND_ROLL_COW("后翻"),
		BEHIND_FLAT_COW("后平"),
		SINGLE_SUPER_COW("单超"),
		SINGLE_ROLL_COW("单翻"),
		SINGLE_FLAT_COW("单平"),
		DOUBLE_SUPER_COW("双超"),
		DOUBLE_ROLL_COW("双翻"),
		DOUBLE_FLAT_COW("双平"),
		FRONT_DISORDER("前杂"),
		BEHIND_DISORDER("后杂");
		private String message;
		
		GameContent(String message){
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
