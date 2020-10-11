$.fn.bindLogout = function() {
		
		this.click(function(){
			$.ajax({
				url: '../Logout.do',
				type: 'POST',
				success: function(data) {
				if (data.errno == 0) {
					alert(data.message);
					location.href = 'index.jsp';
				}
				else {
					alert(data.message);
				}
			}
		});
	});		
}
	
$.fn.bindWithdraw = function(){
		
	this.click(function(){
		if(confirm('탈퇴 후 30일동안은 재가입 하실 수 없습니다.\n정말 탈퇴 하시겠습니까?')){		
			let password = prompt("비밀번호를 입력해 주세요.");
			$.ajax({
				url:'../MemberWithdrawInsert.do',
				type:'post',
				data:JSON.stringify({
					pass:password
				}),
				contentType: 'application/json',
			}).done(function(data){
				alert(data.message);
				if(data.result) {
					location.href = "index.jsp";
				}
				location.reload();
			});
		}
	});	
}
	
$.fn.bindEnterMyPage = function() {
	this.click(function() {
		location.href = 'mySurveyList.jsp';
	});
}

$.Update = function(){
	$.ajax({
		url:'../MemberMyPageUpdate.do',
		type:'get',
		success:function(data){
			if(data.result){
				$('.myPage > ul > li:nth-child(3)').remove();
				let li = "<li id='point'>보유 포인트 : " + data.point + "<a id='btnMyPoint' href='myPointHistory.jsp'>내역보기</a></li>";
				$('.myPage > ul > li:nth-child(2)').after(li);
			}
		}
	})
}
	
$(document).ready(function(){
	$.Update();
	$('.myPage #changePass').click(function(){
		$('.mid > #side > #changePass').css('display' ,'block');
	});
})
	