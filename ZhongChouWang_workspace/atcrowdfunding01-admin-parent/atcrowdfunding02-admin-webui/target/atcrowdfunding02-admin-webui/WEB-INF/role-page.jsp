<%--
  Created by IntelliJ IDEA.
  User: Abyss
  Date: 2021/2/22
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<html>
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"  charset="UTF-8"></script>
<script type="text/javascript">

    $(function (){
       window.pageNum = 1;
       window.pageSize = 5;
       window.keyword = "";
       generatePage();

       $("#searchBtn").click(function (){
           window.keyword = $("#keywordInput").val();

           generatePage();
       });

       $("#showAddModalBtn").click(function (){
           $("#addModal").modal("show");
       });

       $("#saveRoleBtn").click(function (){
           var roleName = $.trim($("#addModal [name=roleName]").val());

           $.ajax({
              "url": "role/save.json",
              "type": "post",
              "data": {
                  "name": roleName
              },
               "dataType": "json",
               "success": function (response){
                  var result = response.result;
                  if(result == "SUCCESS"){
                      layer.msg("操作成功！")
                      window.pageNum = 99999999;
                      generatePage();
                  }

                  if (result == "FAILED"){
                      layer.msg("操作失败！" + response.message);
                  }
               },
               "error": function (response){
                  layer.msg(response.status + " " + response.statusText);
               }
           });
           $("#addModal").modal("hide");

           $("#addModal [name=roleName]").val("");
       });

        $("#rolePageBody").on("click", ".pencilBtn", function (){
            $("#editModal").modal("show");

            var roleName = $(this).parent().prev().text();

            window.roleId = this.id;

            $("#editModal [name=roleName]").val(roleName);
        });

        $("#updateRoleBtn").click(function (){
            var roleName = $("#editModal [name=roleName]").val();

            $.ajax({
                "url": "role/update.json",
                "type": "post",
                "data": {
                    "id":window.roleId,
                    "name":roleName
                },
                "dataType": "json",
                "success": function (response){
                    var result = response.result;
                    if(result == "SUCCESS"){
                        layer.msg("操作成功！")
                        generatePage();
                    }

                    if (result == "FAILED"){
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#editModal").modal("hide");
        });

        $("#removeRoleBtn").click(function (){
            var requestBody = JSON.stringify(window.roleIdArray);

            $.ajax({
                "url": "role/remove/by/role/id/array.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "json",
                "success": function (response){
                    var result = response.result;
                    if(result == "SUCCESS"){
                        layer.msg("操作成功！")
                        generatePage();
                    }

                    if (result == "FAILED"){
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#confirmModal").modal("hide");
        });

        $("#rolePageBody").on("click", ".removeBtn", function (){
            var roleName = $(this).parent().prev().text();

            var roleArray = [{
               roleId: this.id,
               roleName: roleName
            }];

            showConfirmModal(roleArray);

        });

        $("#summaryBox").click(function (){
            var currentStatus = this.checked;

            $(".itemBox").prop("checked", currentStatus);
        });

        $("#rolePageBody").on("click", ".itemBox", function (){
            var checkedBoxCount = $(".itemBox:checked").length;
            var totalBoxCount = $(".itemBox").length;
            // alert("checkedBoxCount = "+checkedBoxCount);
            // alert("totalBoxCount = "+totalBoxCount);
            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
        });


        $("#batchRemoveBtn").click(function (){

            var roleArray = [];

            $(".itemBox:checked").each(function (){

                var roleId = this.id;
                var roleName = $(this).parent().next().text();

                roleArray.push({
                   "roleId": roleId,
                   "roleName": roleName
                });

            });

            if (roleArray.length == 0){
                layer.msg("请至少选择一个执行删除！");
                return;
            }

            showConfirmModal(roleArray);

        });

        $("#rolePageBody").on("click", ".checkBtn", function (){
           window.roleId = this.id;
            $("#assignModal").modal("show");
            fillAuthTree();
        });

        $("#assignBtn").click(function (){
            var authIdArray = [];

            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

            var checkedNodes = zTreeObj.getCheckedNodes();

            for (var i = 0; i < checkedNodes.length; i++){
                var checkedNode = checkedNodes[i];

                var authId = checkedNodes.id;

                authIdArray.push(authId);
            }
            // alert(authIdArray);
            var requestBody = {
                "authIdArray": authIdArray,
                "roleId": [window.roleId]
            };

            requestBody = JSON.stringify(requestBody);

            $.ajax({
                "url": "assign/do/role/assign/auth.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "json",
                "success": function (response){
                    var result = response.result;

                    if(result == "SUCCESS"){
                        layer.msg("操作成功！");
                    }
                    if(result == "FAILED"){
                        layer.msg("操作失败！ " + response.statusText);
                    }

                },
                "error": function (response){
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            $("#assignModal").modal("hide");
        });

    });

</script>

<body>
<%@include file="/WEB-INF/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" id="showAddModalBtn" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="6" align="center">
                                        <div id="Pagination" class="pagination"></div>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>
<%@include file="/WEB-INF/modal-role-edit.jsp"%>
<%@include file="/WEB-INF/modal-role-confirm.jsp"%>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp"%>
</body>
</html>

