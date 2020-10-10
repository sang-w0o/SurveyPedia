package com.surveypedia.surveys.exception;

public class SurveyResultNotViewableException extends SurveyException{

    final long serialVersionUID = 1L;
    private static final int ERRNO = 8;
    private static final String MESSAGE = "해당 설문을 구매한 후 결과를 볼 수 있습니다.";

    public SurveyResultNotViewableException() {
        super(ERRNO, MESSAGE);
    }
}
