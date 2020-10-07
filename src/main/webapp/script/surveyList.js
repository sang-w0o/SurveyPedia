function showList() {
	
	$.ajax({
		url: '../SurveyGetSurveyInfo.do?type=deadLine',
		type: 'get',
		success: function(data) {
			if (data.errno != 0) {
				console.log(data);
				return;
			}
			
			let resp = data.respondent;
			
			for (let i = 0; i < data.list.length; i++) {
				let sInfo = data.list[i];
				let obj = 
					$('<ul>' +
					'<li>' + sInfo.writer + '</li>' +
					'<li>' + sInfo.s_title + '</li>' +
					'<li>' + sInfo.interest_count + '</li>' +
					'<li>' + sInfo.written_date + '</li>' +
					'<li>' + sInfo.end_date + '</li>' +							
					'</ul>');
				
				$(obj).data('s_code', sInfo.s_code);
				$(obj).data('respondent', resp);
				$(obj).bindListClick();
				$(obj).appendTo('.deadLineCon');
			}
		}
	});

	$.ajax({
		url: '../SurveyGetSurveyInfo.do?type=spareSampleNum',
		type: 'get',
		success: function(data) {
			if (data.errno != 0) {
				console.log(data);
				return;
			}
			
			let resp = data.respondent;
			
			for (let i = 0; i < data.list.length; i++) {
				let sInfo = data.list[i];
				let obj = 
					$('<ul>' +
					'<li>' + sInfo.writer + '</li>' +
					'<li>' + sInfo.s_title + '</li>' +
					'<li>' + sInfo.interest_count + '</li>' +
					'<li>' + sInfo.end_date + '</li>' +
					'<li>' + sInfo.spare_sample_num + '</li>' +							
					'</ul>');
				
				$(obj).data('s_code', sInfo.s_code);
				$(obj).data('respondent', resp);
				$(obj).bindListClick();	
				$(obj).appendTo('.lowSampleCon');
			}
		}
	});

	$.ajax({
		url: '../SurveyGetSurveyInfo.do?type=endSurvey',
		type: 'get',
		success: function(data) {
			if (data.errno != 0) {
				console.log(data);
				return;
			}
	
			let resp = data.respondent;
			
			for (let i = 0; i < data.list.length; i++) {
				let sInfo = data.list[i];
				let obj = 
					$('<ul>' +
					'<li>' + sInfo.writer + '</li>' +
					'<li>' + sInfo.s_title + '</li>' +
					'<li>' + sInfo.interest_count + '</li>' +
					'<li>' + sInfo.end_date + '</li>' +
					'<li>' + sInfo.price + '</li>' +							
					'</ul>');
				
				$(obj).data('s_code', sInfo.s_code);
				$(obj).data('respondent', resp);
				$(obj).bindCloseSurveyClick();
				$(obj).appendTo('.closeSurveyCon');
			}
		}
	});
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
				console.log('해당 설문에 참여 가능합니다.');
				location.href = 'responseSurvey.jsp?s_code=' + data.s_code + '&respondent=' + data.resp;
			}
		}
	});
}

$.fn.bindListClick = function() {
	this.click(function() {
		let ul = $(this);
		let s_code = ul.data('s_code');
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
		let s_code = ul.data('s_code');
		location.href = 'surveyResult.jsp?s_code='+s_code;
	})
}

$(document).ready(function() {
	showList();
});


	