package com.surveypedia.choices.exception;

public class ChoiceInsertException extends ChoiceException {

	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 100;
	public static final String MESSAGE = "선택지 등록에 실패했습니다.";

	public ChoiceInsertException() {
		super(ERRNO, MESSAGE);
	}
}
