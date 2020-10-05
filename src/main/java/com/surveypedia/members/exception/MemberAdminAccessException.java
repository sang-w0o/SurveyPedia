package com.surveypedia.members.exception;

public class MemberAdminAccessException extends MemberException {

	private static final long serialVersionUID = 1L;
	private static final int ERRNO = 16;
	private static final String MESSAGE = "허용되지 않은 접근입니다.";

	public MemberAdminAccessException() {
		super(ERRNO, MESSAGE);
	}

}
