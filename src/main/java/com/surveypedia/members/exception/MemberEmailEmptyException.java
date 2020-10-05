package com.surveypedia.members.exception;

public class MemberEmailEmptyException extends MemberException {

	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 4;
	public static final String MESSAGE = "이메일 입력란이 비었습니다.";

	public MemberEmailEmptyException() {
		super(ERRNO, MESSAGE);
	}
}
