package com.surveypedia.questions.exception;

public class QuestionGetException extends QuestionException {

	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 102;
	public static final String MESSAGE = "문항 가져오기에 실패했습니다.";

	public QuestionGetException() {
		super(ERRNO, MESSAGE);
	}
}
