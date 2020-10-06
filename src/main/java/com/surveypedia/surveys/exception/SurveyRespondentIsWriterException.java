package com.surveypedia.surveys.exception;

public class SurveyRespondentIsWriterException extends SurveyException {

	private static final long serialVersionUID = 1L;
	private static final int ERRNO = 7;
	private static final String MESSAGE = "�ڱ� �ڽ��� �������� ������ �� �����ϴ�.";

	public SurveyRespondentIsWriterException() {
		super(ERRNO, MESSAGE);
	}
}
