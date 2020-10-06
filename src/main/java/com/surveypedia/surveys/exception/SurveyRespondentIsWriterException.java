package com.surveypedia.surveys.exception;

public class SurveyRespondentIsWriterException extends SurveyException {

	private static final long serialVersionUID = 1L;
	private static final int ERRNO = 7;
	private static final String MESSAGE = "자기 자신의 설문에는 참여(응답)할 수 없습니다.";

	public SurveyRespondentIsWriterException() {
		super(ERRNO, MESSAGE);
	}
}
