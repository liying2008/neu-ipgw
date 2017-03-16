package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/06 12:45
 * 版本：1.0
 * 描述：用户帐户实体类
 * 备注：
 * =======================================================
 */
public class AccountInfo {
	
	private String userName = null;
	private String psw = null;
    private String range = "2";
	
	public static final String USERNAME = "userName";
	public static final String PSW = "psw";
	public static final String RANGE = "range";

	/**
	 * AccountInfo的构造方法
	 * @param userName 用户名
	 * @param psw 密码
     * @param range 访问范围：1 国际 2 国内
	 */
	public AccountInfo(String userName, String psw, String range) {
		super();
		this.userName = userName;
		this.psw = psw;
        this.range = range;
	}
	
	/**
	 * 无参构造方法
	 */
	public AccountInfo() {
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
    public String getRange() {
        return range;
    }
    public void setRange(String range) {
        this.range = range;
    }
}
