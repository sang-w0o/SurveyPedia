package com.surveypedia.subjectiveresults.exception;

public class SubjectiveResultInsertException extends SubjectiveResultException {

	private static final long serialVersionUID = 1L;
	private static final int ERRNO = 1;
	private static final String MESSAGE = "주관식 문항 응답을 저장하는데에 오류가 발생했습니다.";

	public SubjectiveResultInsertException() {
		super(ERRNO, MESSAGE);
	}
}
