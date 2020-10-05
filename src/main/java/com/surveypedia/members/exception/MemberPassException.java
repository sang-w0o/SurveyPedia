package com.surveypedia.members.exception;

public class MemberPassException extends MemberException {

	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 9;
	public static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

	public MemberPassException() {
		super(ERRNO, MESSAGE);
	}
}
