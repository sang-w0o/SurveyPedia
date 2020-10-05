package com.surveypedia.members.exception;

public class MemberNickEmptyException extends MemberException {

	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 5;
	public static final String MESSAGE = "별명 입력란이 비었습니다.";

	public MemberNickEmptyException() {
		super(ERRNO, MESSAGE);
	}
}
