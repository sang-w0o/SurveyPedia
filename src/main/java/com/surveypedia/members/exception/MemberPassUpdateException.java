package com.surveypedia.members.exception;

public class MemberPassUpdateException extends MemberException {
	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 13;
	public static final String MESSAGE = "비밀 번호 변경에 실패했습니다.";
	public MemberPassUpdateException() {
		super(ERRNO, MESSAGE);
	}

}
