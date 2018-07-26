package com.lawbot.core.priv;

/**
 * 
 * @author Cloud Lau
 *
 */
public class PrivilegeManage {
	public final static int RECO_PRIV = 1; // 同案推荐权限
	
	public final static int AWARD_PRIV = RECO_PRIV << 1; // 裁决书
	
	public final static int ROBOT_PRIV = AWARD_PRIV << 1; //机器人
	
	public final static int CONTRACT_PRIV = ROBOT_PRIV << 1;//合同比对
	
	private int accessPriv = 0; //
	
	{
		accessPriv = RECO_PRIV + AWARD_PRIV + ROBOT_PRIV + CONTRACT_PRIV;
	}
	
	public PrivilegeManage(){}
	
	public PrivilegeManage(int priv){
		this.accessPriv = priv;
	}
	
	public void setPriv(int priv){
		this.accessPriv = priv;
	}
	
	
	/**
	 * 
	 * @param priv: the privilege you want to check
	 * @return
	 */
	public boolean has(int priv){
		return (accessPriv & priv) > 0;
	}
	

	public static void main(String[] args){
		PrivilegeManage p = new PrivilegeManage(3);
		
		System.out.print(p.has(RECO_PRIV));
		System.out.print(p.has(AWARD_PRIV));
		System.out.print(p.has(ROBOT_PRIV));
	}
	
}
