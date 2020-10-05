package com.surveypedia.members.exception;

public class MemberPassEmptyException extends MemberException {

	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 8;
	public static final String MESSAGE = "비밀번호 입력란이 비어있습니다.";

	public MemberPassEmptyException() {
		super(ERRNO, MESSAGE);
	}
}
