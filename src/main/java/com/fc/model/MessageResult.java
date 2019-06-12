package com.fc.model;

import java.io.Serializable;


/**
 *
 * @param <T>
 */
public class MessageResult<T> implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1243558232803463830L;
	//整个业务是否执行成功
	protected boolean success=false;

	//返回业务数据
	protected T buessObj;

	//成功失败码
	protected int code;

	//成功失败信息
	protected String message;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getBuessObj() {
		return buessObj;
	}

	public void setBuessObj(T buessObj) {
		this.buessObj = buessObj;
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

	@Override
	public String toString() {
		return "[UserResult] [isSuccess="+this.success+"] [code="+this.code+"] [message="+this.message+"]";
		
	}
}
