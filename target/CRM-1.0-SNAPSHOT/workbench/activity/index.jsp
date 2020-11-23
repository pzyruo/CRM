<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<base href="<%=basePath%>">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function() {
		//日历控件
		 $(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});




		//为创建按钮绑定事件，打开添加操作的模态窗口
		let click = $("#addBtn").click(function () {
			/*
            * 	操作模态窗口的方式：
            * 	需要操作模态窗口的jquery对象，调用modal方法，为该方法传递参数 show，打开模态窗口，hide 隐藏模态窗口
            *
            *
            * */
			//alert(123)

			$.ajax({
				url: "workbench/activity/getUserList.do",
				type: "get",
				dataType: "json",
				success: function (data) {
					var html = "<option></option>"
					//遍历出来的每一个n就是一个user对象
					$.each(data, function (i, n) {
						html += "<option value='" + n.id + "'>" + n.name + "</option>";
					})
					$("#create-Owner").html(html);


					//取得当前登陆用户的id
					var id = "${user.id}";
					$("#create-Owner").val(id);

					//处理完下拉框之后打开模态窗口
					$("#createActivityModal").modal("show");


				}
			})
		})


		//当点击保存按钮时将写入的参数发送给controller层
		$("#saveBtn").click(function (){
			$.ajax({
				url:"workbench/activity/save.do",
				data:{
					"owner":$.trim($("#create-Owner").val()),
					"name":$.trim($("#create-name").val()),
					"startDate":$.trim($("#create-startDate").val()),
					"endDate":$.trim($("#create-endDate").val()),
					"cost":$.trim($("#create-cost").val()),
					"description":$.trim($("#create-description").val())

				},
				type:"post",
				dataType:"json",
				success:function (data){
					//添加成功，刷新市场信息列表，局部刷新
					//关闭模态窗口
					if (data.success){
						//刷新之前存在的属性值
						//$("#activityAddForm").submit()  提交表单
						//$("#activityAddForm").reset()  无法使用！！！！！
						// 原生js为我们提供了reset()方法
						/*
						* 	jQuery转化为dom对象
						* 		jQuery对象[下标]
						* 	dom对象转为jQuery对象
						* 		$(dom)
						* */

						$("#activityAddForm")[0].reset()
						$("#createActivityModal").modal("hide");
					}else
						alert("添加失败")
				}
			})
		})

		pageList(1,2);
/*
		页面加载完毕之后触发一个方法
		pageList(1,2)

    	对于非关系型数据库，做前端的分页相关操作的基础组件
    	就是pageNo（页码）和pageSize（每页展示的记录数）
    	pageList方法：就是ajax请求到后台，从后天取得最新的市场活动信息
		哪些地方需要刷新市场活动列表：(pageList)
		1.点击左侧市场活动 1
		2.创建.修改.删除 3
		3.查询按钮 1
		4.点击分页组件的时候 1
*/
		$("#searchBtn").click(function (){
			//点击查询之后后，将搜索框中的信息保存起来，保存到隐藏域整中
			$("#hidden-name").val($.trim($("#search-name").val()))
			$("#hidden-Owner").val($.trim($("#search-Owner").val()))
			$("#hidden-startDate").val($.trim($("#search-startDate").val()))
			$("#hidden-endDate").val($.trim($("#search-endDate").val()))

			pageList(1,2);
		})

		//为全选的复选框触发绑定事件，触发全选操作
		$("#qx").click(function (){
			$("input[name=xz]").prop("checked",this.checked)
		})
		//这种做法不可以，因为动态生成的元素是不能够以普通绑定事件
		// 的形式进行操作
				/*$("input[name=xz]").click(function (){
					alert(123);
				})
				*/
		//动态生成的元素，我们要以on的形式触发事件，
		/*
		* 	语法：
		* 		$(需要绑定元素的有效的外层元素).on(绑定事件的方式，需要绑定的元素的JQuery对象，回调函数)
		* */

		$("#activityBody").on("click",$("input[name=xz]"),function (){
			$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)
		})

		//为删除按钮绑定事件，执行市场活动删除操作
		$("#deleteBtn").click(function (){
			//找到所有复选框中有√的JQuery对象
			var $xz = $("input[name=xz]:checked");
			if ($xz.length==0){
				alert("请选择需要删除的记录")
			}
			else{
				//拼接参数
				var param="";
				//将$xz中的每一个对象遍历出来，取其value值，就相当于取得了需要删除记录的id值
				for (var i=0;i<$xz.length;i++){
					param+="id="+$($xz[i]).val();
					//如果不是最后一个元素需要在最后追加一个&符号
					if (i<$xz.length-1){
						param +="&";
					}
				}
				//alert(param);

				$.ajax({
					url:"workbench/activity/delete.do",
					data:{
						param
					},
					type:"post",
					dataType:"json",
					success:function (data){
						if (data.success){
							//删除成功
							pageList(1,2);
						}else
							alert("删除市场活动失败")
						}

				})

			}
		})
	});






	//在点击市场活动之后进行页面刷新
	function pageList(pageNo,pageSize){

		$("#qx").prop("checked",false);
		//查询前将隐藏域中储存的信息，重新赋给搜索框
		$("#search-name").val($.trim($("#hidden-name").val()))
		$("#search-Owner").val($.trim($("#hidden-Owner").val()))
		$("#search-startDate").val($.trim($("#hidden-startDate").val()))
		$("#search-endDate").val($.trim($("#hidden-endDate").val()))

		//alert("页面加载完毕")
		$.ajax({
			url:"workbench/activity/pageList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-name").val()),
				"owner":$.trim($("#search-Owner").val()),
				"startDate":$.trim($("#search-startTime").val()),
				"endDate":$.trim($("#search-endTime").val())
			},
			type:"get",
			dataType:"json",
			success:function (data){
				/*
					我们需要的：
						data{{查询市场活动信息列表1},{2},....} List<Activity> aList
					分页插件需要的：查询出来的总条数 int total
						{"total":100,"datalist":{{查询市场活动信息列表1},{2},....}}
				*/

				var html = "";
				$.each(data.dataList,function (i,n){
					//每一个n就是每一个市场活动对象
							html += '<tr class="active">';
							html += '<td><input type="checkbox" name="xz" value="'+n.id+'" /></td>';
							html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.jsp\';">'+n.name+'</a></td>';
                            html += '<td>'+n.owner+'</td>';
							html += '<td>'+n.startDate+'</td>';
							html += '<td>'+n.endDate+'</td>';
							html += '</tr>';
				})

				$("#activityBody").html(html);
				//计算总页数

				var totalPages = data.id % pageSize==0?data.id/pageSize:parseInt(data.id/pageSize)+1;
				//数据处理完毕会后，结合分页查询，对前端展现分页信息
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
		})
	}


</script>
</head>
<body>
<%--隐藏域保存属性	--%>
	<input type="hidden" id="hidden-">
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
				
					<form id="activityAddForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-Owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-Owner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
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
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
<%--
					data-dismiss="modal" 关闭模态窗口
--%>
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
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-Owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endTime">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default"id="searchBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
<%--
				data-toggle = "modal"
					表示触发该按钮，将要打开一个模态窗口
				data-target="#creatActivityModal":
					表示要打开哪个模态窗口，通过#id的形式

				现在我们是以属性和属性值的方式写在了button中，用来打开模态窗口
				但是这样做是有问题的
					问题在于没有办法对按钮的功能进行扩充
				所以在未来的实际项目开发中，对于模态窗口的操作，一定不要写死在元素中，
				应该由我们自己写js代码


--%>
				  <button type="button" class="btn btn-primary" id = "addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editActivityModal"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>