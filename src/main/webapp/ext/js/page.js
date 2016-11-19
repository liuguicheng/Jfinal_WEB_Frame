$("#page").ready(function(){
	var pageValue = $("#pageValue").val().split("_") ; ;
	var totalCount = parseInt(pageValue[0]) ;
	var pageSize = parseInt(pageValue[1] );
	var curPage = parseInt(pageValue[2]) ;
	var pageIndex = pageValue[3] -1 ;
	var pageGetUrl = pageValue[4] ;		
	var pageHtml = 
	'<form id="pagerForm" name="pagerForm" method="get" action="'+pageGetUrl+'">'+
		'<div class="am-fl">'+
			'<ul class="am-pagination" style="color:#000000;">'+
				'<li style="border-right-color:#fff;""><a href="javascript:;">共'+totalCount+'条记录</a></li>'+
				'<li>'+
					'<a href="javascript:;" style="margin-bottom:0px;padding-bottom:3px;" >'+
			          	'<select style="border-color:#fff;border:0px;" onchange="changePageSize(this.value)">'+
							'<option value="5" ';
							if( pageSize == 5 ) pageHtml += 'selected=true' ; 
							pageHtml += '>5</option>'+
							'<option value="10" ';
							if( pageSize == 10 ) pageHtml += 'selected=true' ; 
							pageHtml += '>10</option>'+
							'<option value="20" ';
							if( pageSize == 20 ) pageHtml += 'selected=true' ; 
							pageHtml += '>20</option>'+
							'<option value="30" ';
							if( pageSize == 30 ) pageHtml += 'selected=true' ; 
							pageHtml += '>30</option>'+
						'</select>'+
					'</a>'+
				'</li>'+
			'</ul>'+
		'</div>'+
		'<input type="hidden" name="pageNum" value="'+curPage+'" />'+
		'<input type="hidden" name="numPerPage" value="'+pageSize+'" />'+
		'<div class="am-fr">'+
		    '<ul class="am-pagination">'+
		    	'<li class="am-pagination-first" onclick="curPage(1)"><a href="javascript:;" class="">第一页</a></li>'+
		    	'<li ' ;
				if( curPage<=1 ) pageHtml += ' class="am-disabled" ' ;
				else pageHtml += ' onclick="upPage('+curPage+')" ' ;
		    	pageHtml +=' ><a href="javascript:;">«</a></li>';
	var pageNum = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : parseInt(totalCount / pageSize + 1);
	var pageEnd = pageNum ;
	var pageStart = curPage ;
	if ( pageNum - curPage > pageIndex ) {
		pageEnd = pageStart + pageIndex ;
	} else if ( pageNum > pageIndex) {
		pageStart = pageNum - pageIndex ;
	} else {
		pageStart = 1 ;
	}
	for (var i = pageStart; i <= pageEnd ; i++) {
		if ( curPage == i ) {
			pageHtml += '<li class="am-active" onclick="curPage('+i+')"><a href="javascript:;">'+i+'</a></li>' ;
		} else {
			pageHtml += '<li onclick="curPage('+i+')"><a href="javascript:;">'+i+'</a></li>' ;
		}
	}
	if ( curPage < pageNum) {
		pageHtml +='<li onclick="nextPage( '+curPage+' )"><a href="javascript:;">»</a></li>' ;
	} else {
		pageHtml +='<li class="am-disabled" ><a href="javascript:;">»</a></li>' ;
	}
	pageHtml +=	'<li><input type="text" class="am-form-field" style="width: 2.5em;height: 2.35em;border-color:#ddd #fff #ddd #ddd;" id="inputPageNum" ></li>'+
				'<li onclick="returnPage( '+pageNum+' )"><a href="javascript:;">跳转</a></li>'+
				'<li onclick="curPage('+pageNum+')" class="am-pagination-last "><a href="javascript:;" class="">最末页</a></li>'+
			'</ul>'+
		'</div>'+
	'</form>' ;
	$("#page").append( pageHtml ) ;
});
function curPage( curNum ){
	$("input[name='pageNum']").val( curNum ) ;
	$("#pagerForm").submit();
}
function upPage(curNum){
	curNum = curNum-1 ;
	if( curNum != 1 ){
		$("input[name='pageNum']").val( curNum ) ;
	}
	curPage( curNum );
}
function nextPage(curNum){
	curNum = curNum+1 ;
	if( curNum != 1 ){
		$("input[name='pageNum']").val( curNum ) ;
	}
	curPage( curNum );
}
function returnPage( pageNum ){
	var inputPageNum = $("#inputPageNum").val() ;
	if ( inputPageNum > pageNum ) {
		alert( "输入页数不能大于:"+ pageNum +"，请重新输入!" );
	} else {
		$("input[name='pageNum']").val( inputPageNum ) ;
		curPage( inputPageNum );
	}
}
function changePageSize( pageSize ){
	$("input[name='numPerPage']").val( pageSize ) ;
	curPage(1);
}
//全选。全部选
function checkbox( checked ,clazz ) {
	if( checked ) {
		$("."+clazz).attr( "checked", true );
	} else {
		$("."+clazz).attr( "checked", false );
	}
	
}