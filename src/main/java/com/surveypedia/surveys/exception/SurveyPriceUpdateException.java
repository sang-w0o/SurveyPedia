package com.surveypedia.surveys.exception;

public class SurveyPriceUpdateException extends SurveyException {

	private static final long serialVersionUID = 1L;
	private static final int ERRNO = 3;
	private static final String MESSAGE = "���� ����Ʈ ���� �� ������ �߻��߽��ϴ�.";

	public SurveyPriceUpdateException() {
		super(ERRNO, MESSAGE);
	}
}
