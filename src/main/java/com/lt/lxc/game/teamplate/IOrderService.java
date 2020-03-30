package com.lt.lxc.game.teamplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.GameEnum;
import com.lt.lxc.game.GameProperty;
import com.lt.lxc.game.GameUtil;
import com.lt.lxc.pojo.OrderDTO;
import com.lt.lxc.utils.Arith;

public abstract class IOrderService {

	public static final int MUTIL_MEDIANT = 27;
	public static final int SINGLE_MEDIANT = 5;

	public abstract OpenResultVo countLotteryResult(OrderDTO order,List<Integer> lotteryCode);

	/**
	 * 将结果封装成vo
	 * @param order
	 * @param lotteryCode
	 * @param lotteryResult
	 * @return
	 */
	protected OpenResultVo tovo(OrderDTO order, List<Integer> lotteryCode, List<String> lotteryResult) {
		OpenResultVo vo=new OpenResultVo();
		vo.setLotteryResult(lotteryResult);
		vo.setLotteryCode(lotteryCode);
		//如果orderDTO为null直接返回
		if(order == null) 
			return vo;
		//得出单注
		GameProperty gp = GameUtil.getTotalMoney(order.getBetsContent());
		//用户的下注与开奖结果作比较
		String betsContent = parseContent(order.getBetsContent());
		if(lotteryResult.contains(betsContent)) {
			vo.getBetResultMap().put(betsContent+"/"+gp.getSingle(), Arith.mul(gp.getSingle(), order.getOdds()));
		}else {
			vo.getBetResultMap().put(betsContent+"/"+gp.getSingle(), gp.getMoney()*(-1));
		}
		isWin(gp,vo);
		return vo;
	}
	
	/**
	 * 用户所有的选择号码判断完成后,计算出所有注的输赢的和,以此来判断该注单输赢
	 * @param vo
	 */
	protected void isWin(GameProperty gp,OpenResultVo vo) {
		//该注单的赢的金额
		double wonMoeny =0;
		for (Map.Entry<String, Double> entry : vo.getBetResultMap().entrySet()){
			if(entry.getValue()>0) {
				wonMoeny = Arith.add(wonMoeny,entry.getValue());
			}
		}
		if(wonMoeny>gp.getMoney()) {
			vo.setResult(true);
		}else {
			vo.setResult(false);
		}
		vo.setMoney(Arith.sub(wonMoeny,gp.getMoney()));
		vo.setWonMoney(wonMoeny);
	}

	/**
	 * 大小的判定
	 * @param count
	 * @return
	 */
	protected String bigAndSmall(int count,int num) {
		if(count > num) 
			return GameEnum.GameContent.BIG.getMessage();
		else
			return GameEnum.GameContent.SMALL.getMessage();
	}

	/**
	 * 单双的判断
	 * @param count
	 * @return
	 */
	protected String singleAndDouble(int count) {
		//单双的判定
		if(count%2==0) 
			return GameEnum.GameContent.DOUBLE.getMessage();
		else
			return GameEnum.GameContent.SINGLE.getMessage();
	}

	/**
	 * 合的判定
	 * @param count
	 * @return
	 */
	protected boolean isAnd(int count) {
		if(MUTIL_MEDIANT == count) 
			return true;
		return false;
	}

	/**
	 * 非组合龙虎和的判定
	 * @param lotteryCode
	 * @return
	 */
	protected String dragonAndTigerResult(List<Integer> lotteryCode) {
		//结果为龙
		if(lotteryCode.get(0) > lotteryCode.get(5))
			return GameEnum.GameContent.DRAGON.getMessage();
		//结果为和
		if(lotteryCode.get(0) ==  lotteryCode.get(5))
			return GameEnum.GameContent.PEACE.getMessage();
		//结果为虎
		return GameEnum.GameContent.TIGER.getMessage();
	}


	/**
	 * 解析下注的玩法
	 * @param betsContent
	 * @return
	 */
	protected String parseContent(String betsContent) {
		Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
		//[\u4E00-\u9FA5]是unicode2的中文区间
		Matcher matcher = pattern.matcher(betsContent);
		betsContent = matcher.replaceAll("");
		return betsContent;
	}

	//开奖结果的和值
	protected Integer sumLotteryResult(List<Integer> lotteryResult) {				
		int sum=0;
		for(int i=0;i<lotteryResult.size();i++) {			
			sum+=lotteryResult.get(i);
		}
		return sum;
	}

	//三区形态的结果集
	protected List<String> threeFormLotteryJudge(List<Integer> lotteryResult){

		List<String> list = new ArrayList<>();
		Integer[] numbers = {lotteryResult.get(0),lotteryResult.get(1),lotteryResult.get(2)};
		boolean flag = true;
		if(isPair(numbers)) {
			list.add(GameEnum.GameContent.FRONT_PAIR.getMessage());
			flag = false;
		}

		if(isLeopard(numbers))
			list.add(GameEnum.GameContent.FRONT_LEOPARD.getMessage());
		else if(isStraight(numbers))
			list.add(GameEnum.GameContent.FRONT_STRAIGHT.getMessage());
		else if(isHalfStraight(numbers))
			list.add(GameEnum.GameContent.FRONT_HALF_STRAIGHT.getMessage());
		else if(flag)
			list.add(GameEnum.GameContent.FRONT_DISORDER.getMessage());

		numbers = new Integer[]{lotteryResult.get(3),lotteryResult.get(4),lotteryResult.get(5)};
		flag = true;
		if(isPair(numbers)) {
			list.add(GameEnum.GameContent.BEHIND_PAIR.getMessage());
			flag = false;
		}

		if(isLeopard(numbers))
			list.add(GameEnum.GameContent.BEHIND_LEOPARD.getMessage());
		else if(isStraight(numbers))
			list.add(GameEnum.GameContent.BEHIND_STRAIGHT.getMessage());
		else if(isHalfStraight(numbers))
			list.add(GameEnum.GameContent.BEHIND_HALF_STRAIGHT.getMessage());
		else if(flag)
			list.add(GameEnum.GameContent.BEHIND_DISORDER.getMessage());
		return list;
	}
	/**
	 * 三位数豹子的判断
	 * @param stArr
	 * @return
	 */
	private boolean isLeopard(Integer[] numbers) {
		Set<Integer> stSet = new HashSet<>(Arrays.asList(numbers));
		return stSet.size()==1;
	}

	/**
	 * 三位数对子的判断
	 * @param stArr
	 * @return
	 */
	private boolean isPair(Integer[] numbers) {
		Set<Integer> stSet = new HashSet<>(Arrays.asList(numbers));
		return stSet.size()==2;
	}

	/**
	 * 三位数顺子的判断
	 * @param stArr
	 * @return
	 */
	private boolean isStraight(Integer[] numbers) {
		int sum=numbers[0]+numbers[1];
		int abs=Math.abs(numbers[0]-numbers[1]);
		return (abs==2&&numbers[2]*2==sum)||(abs==1&&(abs==1&&(Math.abs(numbers[2]*2-sum)==3)));
	}

	/**
	 * 三位数半顺的判断
	 * @param numbers
	 * @return
	 */
	public boolean isHalfStraight(Integer[] numbers) {
		if(!isStraight(numbers)) {
			if(Math.abs(numbers[0]-numbers[1]) == 1 || Math.abs(numbers[0]-numbers[2]) == 1 || Math.abs(numbers[1]-numbers[2]) == 1) {
				return true;
			}
		}
		return false;
	}


	//判断字符串是不是以数字开头
	public static boolean isStartWithNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str.charAt(0)+"");
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
