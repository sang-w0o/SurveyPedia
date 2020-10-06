package com.surveypedia.surveys.exception;

public class SurveyInsertException extends SurveyException {

	private static final long serialVersionUID = 1L;
	public static final int ERRNO = 5;
	public static final String MESSAGE = "���� ��Ͽ� �����߽��ϴ�.";

	public SurveyInsertException() {
		super(ERRNO, MESSAGE);
	}
}
