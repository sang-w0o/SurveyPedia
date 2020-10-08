package com.surveypedia.choiceresults.exception;

public class ChoiceResultInsertException extends ChoiceResultException {

	private static final long serialVersionUID = 1L;
	private static final int ERRNO = 1;
	private static final String MESSAGE = "객관식 문항 응답을 저장하는데에 오류가 발생했습니다.";

	public ChoiceResultInsertException() {
		super(ERRNO, MESSAGE);
	}
}
