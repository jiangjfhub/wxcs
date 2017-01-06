<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/css/base/sweetalert.css">
<script src="${ctx}/static/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/static/js/ui/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${ctx}/static/js/ui/sweetalert.min.js"></script>
<title>都搜</title>
<style type="text/css">
.cl {


}
td{border:solid #add9c0; border-width:0px 1px 1px 0px; padding:10px 0px;}
table{border:solid #add9c0; border-width:1px 0px 0px 1px;width:90%;}
</style>
</head>
<body>
<form action="">
<input type="text" name="btCode" id="btCode" placeholder="请输入关键字">
<input type="text" name="btCode" id="btSerial" placeholder="请输入系列号">
<input type="button" id="submitByBtCode" value="搜索">
<input type="button" id="submitByBtSerial" value="搜索">
</form>
<div class="cl">
<table style="width:100%;border: 1px solid;" id="content">

</table>
<div id="detail">
成功数：<p id="successnum"></p>失败数：<p id="failnum"></p><br>
失败列表:<p id="list"></p>


</div>
</div>

<script type="text/javascript">
var count_success =0;
var count_fail=0;
var code="001";
$("#btCode").keydown(function(e){
	var key_code = e.keyCode;
	 if (key_code=='13'){
		 $("#submitByBtCode").click();
	}
});
$("#submitByBtCode").click(function(e){
	queryByCode();
});
$("#submitByBtSerial").click(function(e){
	queryBySerial(1);
})
function append(list){
	var header='<tr><td width="10%">片名</td><td width="10%">大小</td><td width="10%">创建时间</td><td width="70%" align="center">磁力链接地址</td></tr>';
	$("#content").append(header);
	$.each(list,function(i,obj){
		var tr ='<tr><td>'+obj.linkName+'</td><td>'+obj.size+'</td><td>'+obj.convertDate+'</td><td>'+obj.magnetLink+'</td></tr>'
		$("#content").append(tr);
	})
	
}
function queryByCode(){
	$("#content").html("请稍等。。。。");
	var btCode=$("#btCode").val();
	if(btCode.indexOf("-")==-1){
		var t1=btCode.match(/^[a-z|A-Z]+/gi);
		var t2=btCode.match(/\d+$/gi);
		btCode=t1+"-"+t2;
	}
	$.ajax({
		url: "${ctx}/myBtPage/search?btCode="+btCode,
		type:"post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		async:false,
		success: function(data){  
			if(data && data.CODE && data.CODE == "SUCCESS"){
				$("#content").html("");
				var list = data.RETURN_PARAM;
				append(list);
				
			}
        },  
        error: function(data){  
        	swal("OMG!", "查询失败", "error");
        }  
		
	})
}
function queryBySerial(i){
	var btSerial=$("#btSerial").val();
		btCode=btSerial+"-"+code;
	$("#content").html("正在查询"+btCode);
	$.ajax({
		url: "${ctx}/myBtPage/search?btCode="+btCode,
		type:"post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		async:false,
		success: function(data){  
			if(data && data.CODE && data.CODE == "SUCCESS"){
				$("#content").html(btSerial+"查询成功");
				count_success++;
				$("#successnum").html(count_success);
				i++;
				if(i<10){
					code="00"+i;
				}else if(i<100){
					code="0"+i;
				}else if(i<1000){
					code=i;
				}
				queryBySerial(i);
			}
        },  
        error: function(data){  
        	$("#content").html(btSerial+"查询失败");
        	count_fail++;
        	$("#failnum").html(count_fail);
        	$("#list").append(btCode+',');
        	i++;
			if(i<10){
				code="00"+i;
			}else if(i<100){
				code="0"+i;
			}else if(i<1000){
				code=i;
			}
			queryBySerial(i);
        }  
		
	})
	}
</script>
</body>
</html>