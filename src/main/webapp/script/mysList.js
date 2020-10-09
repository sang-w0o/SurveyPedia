var gPage = 0;
$.fn.bindGetByMySurveyType = function(){
	this.click(function(){
		var clicked_myType = $(this).val();
		$('.cateCont ul').remove();
		$.ajax({
			url:'../SurveyGetMySurveyInfo.do?myType='+clicked_myType+'&page='+gPage,
			type:'GET',
			success:function(data){
				for(let i = 0; i < data.surveys.length; i++){
					let survey = data.surveys[i];
					let obj = $('<ul>' + 
					'<li>' + survey.writer + '</li>' +
					'<li>' + survey.s_title + '</li>' +
					'<li>' + survey.interest_count + '</li>' +
					'<li>' + survey.written_date + '</li>' +
					'</ul>');
					obj.data('survey', survey);
					obj.data("respondent", data.respondent);
		
					if (clicked_myType == 'MYCURRENT' || clicked_myType == 'INTERESTCURRENT') {
						obj.bindListClick();
					}
					else if (clicked_myType =='MYENDED' || clicked_myType == 'INTERESTENDED' || clicked_myType == 'PURCHASED') {
						obj.bindCloseSurveyClick();
					}
					
					$('.cateCont').append(obj);
				}
				
				$('.cateBottom #btnNext').val(clicked_myType);
				$('.cateBottom #btnFirst').val(clicked_myType);
				$('.cateBottom #btnPrev').val(clicked_myType);
				$('.cateBottom #btnLast').val(clicked_myType);
				gPage = data.page;
			}
		})
	})
}

$.fn.bindGetNext = function(){
	this.click(function(){
		var clicked_myType = $('.cateBottom #btnNext').val();
		$('.cateCont ul').remove();
		let myType = $('.cateBottom #btnNext').val();
		$.ajax({
			url:'../SurveyGetMySurveyInfo.do?mode=next&myType='+myType+'&page='+gPage,
			type:'GET',
			success:function(data){
				for(let i = 0; i < data.surveys.length; i++){
					let survey = data.surveys[i];
					let obj = $('<ul>' + 
					'<li>' + survey.writer + '</li>' +
					'<li>' + survey.s_title + '</li>' +
					'<li>' + survey.interest_count + '</li>' +
					'<li>' + survey.written_date + '</li>' +
					'</ul>');
					obj.data('survey', survey);
					obj.data("respondent", data.respondent);
					if (clicked_myType == 'MY' || clicked_myType == 'inter') {
						obj.bindListClick();
					}
					else if (clicked_myType =='MY1' || clicked_myType == 'inter1' || clicked_myType == 'phar') {
						obj.bindCloseSurveyClick();
					}
					$('.cateCont').append(obj);
				}
				gPage = data.page;
			}
		})
	})
}

$.fn.bindGetPrev = function(){
	this.click(function(){
		var clicked_myType = $('.cateBottom #btnPrev').val();
		$('.cateCont ul').remove();
		let myType = $('.cateBottom #btnPrev').val();
		$.ajax({
			url:'../SurveyGetMySurveyInfo.do?mode=prev&myType='+myType+'&page='+gPage,
			type:'GET',
			success:function(data){
				for(let i = 0 ;i < data.surveys.length; i++){
					let survey = data.surveys[i];
					let obj = $('<ul>' + 
					'<li>' + survey.writer + '</li>' +
					'<li>' + survey.s_title + '</li>' +
					'<li>' + survey.interest_count + '</li>' +
					'<li>' + survey.written_date + '</li>' +
					'</ul>');
					obj.data('survey', survey);
					obj.data("respondent", data.respondent);
					if (clicked_myType == 'MY' || clicked_myType == 'inter') {
						obj.bindListClick();
					}
					else if (clicked_myType =='MY1' || clicked_myType == 'inter1' || clicked_myType == 'phar') {
						obj.bindCloseSurveyClick();
					}
					$('.cateCont').append(obj);
				}
				gPage = data.page;
			}
		})
	})
}

$.fn.bindGetFirst = function(){
	this.click(function(){
		var clicked_myType = $('.cateBottom #btnFirst').val();
		$('.cateCont ul').remove();
		let myType = $('.cateBottom #btnFirst').val();
		$.ajax({
			url:'../SurveyGetMySurveyInfo.do?mode=first&myType='+myType,
			type:'GET',
			success:function(data){
				for(let i = 0 ;i < data.surveys.length; i++){
					let survey = data.surveys[i];
					let obj = $('<ul>' + 
					'<li>' + survey.writer + '</li>' +
					'<li>' + survey.s_title + '</li>' +
					'<li>' + survey.interest_count + '</li>' +
					'<li>' + survey.written_date + '</li>' +
					'</ul>');
					obj.data('survey', survey);
					obj.data("respondent", data.respondent);
					if (clicked_myType == 'MY' || clicked_myType == 'inter') {
						obj.bindListClick();
					}
					else if (clicked_myType =='MY1' || clicked_myType == 'inter1' || clicked_myType == 'phar') {
						obj.bindCloseSurveyClick();
					}
					$('.cateCont').append(obj);
				}
				gPage = 1;
			}
		})
	})
}

$.fn.bindGetLast = function(){
	currentPage = 10000;
	this.click(function(){
		var clicked_myType = $('.cateBottom #btnLast').val();
		$('.cateCont ul').remove();
		let myType = $('.cateBottom #btnLast').val();
		$.ajax({
			url:'../SurveyGetMySurveyInfo.do?mode=last&myType='+myType,
			type:'GET',
			success:function(data){
				for(let i = 0 ;i < data.surveys.length; i++){
					let survey = data.surveys[i];
					let obj = $('<ul>' + 
					'<li>' + survey.writer + '</li>' +
					'<li>' + survey.s_title + '</li>' +
					'<li>' + survey.interest_count + '</li>' +
					'<li>' + survey.written_date + '</li>' +
					'</ul>');
					obj.data('survey', survey);
					obj.data("respondent", data.respondent);
					if (clicked_myType == 'MY' || clicked_myType == 'inter') {
						obj.bindListClick();
					}
					else if (clicked_myType =='MY1' || clicked_myType == 'inter1' || clicked_myType == 'phar') {
						obj.bindCloseSurveyClick();
					}
					$('.cateCont').append(obj);
				}
				gPage = data.page;
			}
		})
	})
}

$.fn.bindListClick = function() {
	this.click(function() {
		let ul = $(this);
		let s_code = ul.data('survey').s_code;
		let resp = ul.data('respondent');
		if (resp == null) {
			alert("로그인 시 참여가능합니다.");
			return;
		}
		checkResponse(s_code, resp); 
	});
}

$.fn.bindCloseSurveyClick = function(){
	$(this).click(function(){
		let ul = $(this);
		let s_code = ul.data('survey').s_code;
		location.href = 'surveyResult.jsp?s_code='+s_code;
	})
}

function checkResponse(s_code, respondent) {
	$.ajax({
		url: '../SurveyCheckResponse.do',
		type: 'post',
		data: JSON.stringify({
			s_code: s_code,
			respondent: respondent
		}),
		contentType: 'application/json',
		success: function(data) {
			if (!data.result) {
				alert(data.message);
			}
			else {
				location.href = 'responseSurvey.jsp?s_code=' + data.s_code + '&respondent=' + data.resp;
			}
		}
	});
}

