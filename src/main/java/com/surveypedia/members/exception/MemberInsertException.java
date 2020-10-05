package com.surveypedia.members.exception;

public class MemberInsertException extends MemberException {

	private static final long serialVersionUID = 1L;

	public static final int ERRNO = 1;
	public static final String MESSAGE = "알 수 없는 오류가 발생했습니다.";

	public MemberInsertException() {
		super(ERRNO, MESSAGE);
	}
}
