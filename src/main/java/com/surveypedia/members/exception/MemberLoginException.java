package com.surveypedia.members.exception;

public class MemberLoginException extends MemberException {
    private static final long serialVersionUID = 1L;
    public static final int ERRNO = 18;
    public static final String MESSAGE = "이메일 또는 비밀번호가 잘못되었습니다.";

    public MemberLoginException() {
        super(ERRNO, MESSAGE);
    }
}
