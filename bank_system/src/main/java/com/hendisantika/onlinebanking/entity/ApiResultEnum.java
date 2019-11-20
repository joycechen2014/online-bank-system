package com.hendisantika.onlinebanking.entity;

public enum ApiResultEnum {
	SUCCESS("200","ok"),
	FAILED("400","Request Failed!"),
	ERROR("500","Unknown Error!"),
	ERROR_NULL("501","Null Pointer Exception!"),
	ERROR_CLASS_CAST("502","Type Cast Exception!"),
	ERROR_RUNTION("503","Runtime Exception!"),
	ERROR_IO("504","Upload File Exception!"),
	ERROR_MOTHODNOTSUPPORT("505","Rquest Method Not Allowed!"),


	//参数
	PARAMETER_NULL("10001","Lack Of Parameter Error!"),
	TRIGGER_GROUP_AND_NAME_SAME("10002","组名和名称已存在"),



  CREATING_RECURRING_JOB_ERROR("20000","Creating Recurring Job Error!"),
	SHUTTING_RECURRING_JOB_ERROR("20001","Shutting Recurring Job Error!"),
	INSUFFICIENT_BALANCE_ERROR("20005","Insufficient Balance Error!"),
	ACCOUNT_NOT_FOUND("20002","找不到账户信息"),
	ACCOUNT_PASSWARD_ERROR("20003","用户名密码错误"),
	ACCOUNT_EXIST("20004","账号已存在"),

	AUTH_NOT_HAVE("30001","没有权限"),



	FILE_IS_NULL("40001","文件为空"),

	TASK_IS_RUNING("50001","任务已启动，无法再起启动"),
	TASK_IS_PAUSE("50002","任务暂停，只可继续执行"),
	TASK_NOT_RUNING("50003","任务未执行，无法暂停"),
	;

	private String message;
	private String status;

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}
	private ApiResultEnum(String status,String message) {
		this.message = message;
		this.status = status;
	}


}
