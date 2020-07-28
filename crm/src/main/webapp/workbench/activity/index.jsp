<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://"+
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>"/>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<!--分页插件-->
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
		/**
		 * 对于模态窗口的打开操作，应该我们开发者自己进行控制，而不是前端固定写死
		 * jquery中提供了模态窗口的开关操作，model方法，open，hide两种属性
		 */
		// 获取添加操作中市场活动模态窗口
		$("#addBtn").click(function () {
			// 添加时间控件，年月日
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			// 打开模态窗口之前在后台中先获取所有用户数据，并使当前登录用户显示在默认下拉框中
			$.ajax({
				url: "workbench/activity/add.do",
				type:"get",
				dataType:"json",
				success:function (data) {
					// 对于这里的返回数据进行判断
					$.each(data ,function (i,n) {
						$("#create-owner").append("<option value="+n.id+">"+n.name+"</option>");
						$("#create-owner").val("${user.id}");
					})
				}
			});
			// 打开模态窗口
			$("#createActivityModal").modal("show");
		})

         $("#saveBtn").click(function () {
			 $.ajax({
				 url: "workbench/activity/save.do",
				 data:{
				 	"owner":$.trim($("#create-owner").val()),
					 "name":$.trim($("#create-name").val()),
					 "startDate":$.trim($("#create-startDate").val()),
					 "endDate":$.trim($("#create-endDate").val()),
					 "cost":$.trim($("#create-cost").val()),
					 "description":$.trim($("#create-description").val())
				 },
				 type:"post",
				 dataType:"json",
				 success:function (data) {
					 // 对于这里的返回数据进行判断
					 if (data.success){
					 	// 关闭窗口并清空，在jquery中没有reset方法的，要转成DOM对象
						 $("#createActivityModal").modal("hide");
						 $("#activityform")[0].reset();
						 pageList(1,4);
					 }else {
					 	alert("数据保存失败！");
					 }

				 }
			 });
		 })

		/*
		   在页面进行刷新时，应该执行pageList函数进行市场活动列表的刷新,需要进行ajax的请求
		   pageList方法中有两个属性，pageNo,pageSize,分别是在增加修改，删除，查询时进行刷新
		   pageNo:当前页码
		   pageSize：条数
		 */
		pageList(1,4);
		$("#searchBtn").click(function () {
		    // 将搜索框汇总的内容放入隐藏域中hidden
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));
			pageList(1,4);
		});

		// 对全选框进行事件的绑定
		$("#quanCheckbox").click(function () {
            $("#dancheckbox").prop("checked",this.checked)
		})
		// 对于表中的复选框进行事件的绑定，因为是动态的不能使用普通的方式
		//语法：$（需要绑定的元素的有效的外层元素）.on(绑定事件的方式，需要绑定的元素的jquery对象，回调函数)
		$("#activitybody").on("click",$("#dancheckbox"),function () {
              $("#quanCheckbox").prop("checked",$("#dancheckbox").length==$("#dancheckbox:checked").length);
		})

		/**
		 * 这里进行删除操作
		 * 删除操作会关联市场活动备注表，根据外键进行判断，这里因为可能会勾选很多，不能直接使用json数据传递
		 * url:"workbench/activity/delete.do"?id=xxxx&id=xxxxxx;
		 */
		$("#deleteBtn").click(function () {

			// 首先判断复选框是否选中
			var $checkbox = $("#dancheckbox:checked");
			if ($checkbox.length==0){
				alert("请选择要删除的对象！");
			}else {
				// 删除前进行判断
				if (confirm("是否确定删除？")){
					// 进行ajax请求
					var param = "";
					for (var i=0;i<$checkbox.length;i++){
						param += "id="+$checkbox[i].value;
						if (i<$checkbox.length-1){
							param += "&";
						}
					}
					$.ajax({
						url: "workbench/activity/delete.do",
						data:param,
						type:"get",
						dataType:"json",
						success:function (data) {
							// 删除成功显示，并进行页面的刷新；
							if (data.success){
								alert("删除成功！");
								pageList(1,4);
							}else {
								alert("删除失败！");
							}
						}
					});
				}
				//alert(param);
			}
		})



	});
	// 定义pageList函数，进行分页操作
	function pageList(pageNo,pageSize) {
		// 每次执行都会对全选框进行刷新
		$("#quanCheckbox").prop("checkd",false);
		//alert("pageList执行了");
		// 查询前将隐藏域中的值重新输入到搜索框
		/**
		 * 这里犯了一个错误，缺少一个括号，接下来就不执行了.....
		 * 还不会去报错误的信息！！！！
		 */

		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));
		// 进行ajax请求
		$.ajax({
			url: "workbench/activity/pageList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"owner":$.trim($("#search-owner").val()),
				"name":$.trim($("#search-name").val()),
				"startDate":$.trim($("#search-startDate").val()),
				"endDate":$.trim($("#search-endDate").val()),
			},
			type:"get",
			dataType:"json",
			success:function (data) {

				var html="";
				// 对于这里的返回数据进行判断
				$.each(data.activityList ,function (i,n) {

				    html +='<tr class="active">';
					html +='<td><input type="checkbox" id="dancheckbox" value="'+n.id+'"/></td>';
					html +='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'detail.html\'">'+n.name+'</a></td>';
					html +='<td>'+n.owner+'</td>';
					html +='<td>'+n.startDate+'</td>';
					html +='<td>'+n.endDate+'</td>';
					html +='</tr>';
				})
				// 市场活动表显示
				$("#activitybody").html(html);
				//$("#activitybody").html('<td>能不能显示？</td>');
				// 进行分页操作
				// 计算总页数
				var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		});

	}
	
</script>
</head>
<body>
    <input type="hidden" id="hidden-name"/>
    <input type="hidden" id="hidden-owner"/>
    <input type="hidden" id="hidden-startDate"/>
    <input type="hidden" id="hidden-endDate"/>

    <!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="activityform">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label ">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="quanCheckbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activitybody">

					</tbody>
				</table>
			</div>

			<!--分页插件的div-->
			<div  id="activityPage"></div>
			
		</div>
		
	</div>
</body>
</html>