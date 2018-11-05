<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
 <head>
    
  <title>流程设计器</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="leipi.org">

 </head>
<body>
<style>
body { font-size:14px;}
</style>

	
<ul class="nav nav-tabs" role="tablist" id="attributeTab" style="margin-bottom:15px;">
  <li role="presentation" class="active"><a href="#attrBasic" role="tab" data-toggle="tab">常规</a></li>
  <!-- <li role="presentation"><a href="#attrForm" role="tab" data-toggle="tab">表单</a></li> -->
  <li role="presentation"><a href="#attrPower" role="tab" data-toggle="tab">权限</a></li>
  <%--<li role="presentation"><a href="#attrOperate" role="tab" data-toggle="tab">操作</a></li>--%>
  <li role="presentation" id="tab_attrJudge"><a href="#attrJudge" role="tab" data-toggle="tab">转出条件</a></li>
  <li role="presentation"><a href="#attrStyle" role="tab" data-toggle="tab">样式</a></li>
</ul>

<form class="form-horizontal" target="hiddeniframe" method="post" id="flow_attribute" name="flow_attribute" action="${ctx}/leipiflow/leipiFlowProcess/saveAttribute">
<input type="hidden" name="flow_id" value="${one.flowId }"/>
<input type="hidden" name="process_id" value="${one.id }"/>
  <div class="tab-content">
    <div class="tab-pane active" id="attrBasic" role="tabpanel">

          <div class="form-group">
            <label class="control-label col-sm-3" for="process_name">步骤名称</label>
            <div class="col-sm-7">
              <input type="text" id="process_name" class="form-control" placeholder="步骤名称" name="process_name" value="${one.processName}">
            </div>
          </div>

          <div class="form-group">
            <label class="control-label col-sm-3">步骤类型</label>
            <div class="col-sm-7">
              <label class="radio-inline">
                <input type="radio" name="process_type" value="is_step" <c:if test="${one.processType == 'is_step'}">checked="checked"</c:if>>正常步骤
              </label>
              <!-- <label class="radio-inline">
                <input type="radio" name="process_type" value="is_child" >转入子流程
              </label> -->
              <label class="radio-inline">
                <input type="radio" name="process_type" value="is_one" <c:if test="${one.processType == 'is_one'}">checked="checked"</c:if>>设为第一步
              </label>

            </div>
          </div>
<hr/>

        <div id="current_flow clearfix">
          <div class="offset1 clearfix">
          <!--未按顺序的bug 2012-12-12-->
         <select multiple="multiple" size="6" name="process_to" id="process_multiple" >
           <c:forEach items="${process_to_list_right}" var="proc" >
              <c:if test="${proc.id != one.id}"><option value="${proc.id}" >${proc.processName}</option></c:if>
           </c:forEach>
         </select>
          </div>
        </div><!-- current_flow end -->

        <div id="child_flow" class="hide">
           <div class="form-group">
            <label class="control-label" >子流程</label>
            <div class="controls">
              <select name="child_id" >
                <option value="0">--请选择--</option>
                <option value="1" >22</option><option value="2" >2222432231111</option><option value="3" >测试流程</option><option value="4" >请假流程</option>              </select>
            </div>
          </div>

           <div class="form-group">
            <label class="control-label" >子流程结束后动作</label>
            <div class="controls">
              <label class="radio inline">
                <input type="radio" name="child_after" value="1" checked="checked">
                同时结束父流程
              </label>
              <label class="radio inline">
                <input type="radio" name="child_after" value="2"  >
                返回父流程步骤
              </label>
            </div>
          </div>

          <div class="form-group hide" id="child_back_id">
            <label class="control-label" >返回步骤</label>
            <div class="controls">
              <select name="child_back_process" >
                <option value="0">--默认--</option>
                <option value="61" >发起申请</option><option value="62" >审批2</option><option value="63" >快捷审批</option><option value="64" >审批1</option><option value="65" >归档整理人</option>                <!--option value="1">步骤1</option>
                <option value="2">步骤2</option>
                <option value="3">步骤3</option-->
              </select>
              <span class="help-inline">默认为当前步骤下一步</span>
            </div>
          </div>

        </div><!-- child_flow end -->



    </div><!-- attrBasic end -->

    <div class="tab-pane " id="attrForm" role="tabpanel">



<table class="table table-condensed table-bordered table-hover" >
    <tr>
      <th style="text-align:center">字段名称</th>
      <th style="text-align:center">控件类型</th>
      <!--
        预留功能：
        1.是否必填
        2.锁定内容不能修改
      -->
      <th style="width:100px;"><label title="本步骤可写字段"  class="checkbox"><input type="checkbox" id="write">可写字段</label></th>
      <th style="width:100px;"><label title="保密字段对于本步骤主办人、经办人均为不可见"  class="checkbox"><input type="checkbox" id="secret">保密字段</label></th>
    </tr>
    <tbody>

<!-- 这里是表单设计器的字段 start -->
    <tr>
      <th>文本框</th>
      <td>文本框</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]" checked="checked" value="data_1" key="0" id="write_0"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"   value="data_1" key="0" id="secret_0"></label></td>
    </tr>    <tr>
      <th>下拉菜单</th>
      <td>下拉菜单</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]" checked="checked" value="data_2" key="1" id="write_1"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"   value="data_2" key="1" id="secret_1"></label></td>
    </tr>    <tr>
      <th>单选</th>
      <td>单选框</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]"  value="data_3" key="2" id="write_2"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"   value="data_3" key="2" id="secret_2"></label></td>
    </tr><tr>
      <th>复选</th>
      <td>复选框</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]"  value="checkboxs_0" key="3" id="write_3"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"  checked="checked" value="checkboxs_0" key="3" id="secret_3"></label></td>
    </tr>

    <tr>
      <th>宏控件</th>
      <td>宏控件</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]" checked="checked" value="data_7" key="4" id="write_4"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"   value="data_7" key="4" id="secret_4"></label></td>
    </tr>    <tr>
      <th>雷劈网</th>
      <td>二维码</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]" checked="checked" value="data_8" key="5" id="write_5"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"   value="data_8" key="5" id="secret_5"></label></td>
    </tr>    <tr>
      <th>多行文本</th>
      <td>多行文本</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]" checked="checked" value="data_9" key="6" id="write_6"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"   value="data_9" key="6" id="secret_6"></label></td>
    </tr>    <tr>
      <th>进度条</th>
      <td>进度条</td>
      <td><label class="checkbox"><input type="checkbox" name="write_fields[]" checked="checked" value="data_10" key="7" id="write_7"></label></td>
      <td><label class="checkbox"><input type="checkbox" name="secret_fields[]"   value="data_10" key="7" id="secret_7"></label></td>
    </tr><!--tr>
  <th>姓名</th>
  <td>文本框</td>
  <td><label style="display: block;" ><input type="checkbox" name="write[]" checked="true" value="data_2" key="1" id="write_1"></label></td>
  <td><label style="display: block;"><input type="checkbox" name="secret[]" disabled="true" value="data_2" key="1" id="secret_1"></label></td>
</tr-->
<!-- 这里是表单设计器的字段 end -->


    </tbody>
</table>



    </div><!-- attrForm end -->
    

    <div class="tab-pane" id="attrPower" role="tabpanel">

        <div class="form-group">
            <label class="control-label" >自动选人</label>
            <div class="controls">
              <select name="auto_person" id="auto_person_id"  style="width: 250px;">
                <%-- <option value="0" <c:if test="${one.autoPerson == 0}">selected="selected"</c:if> >不自动选人</option> --%>
                <option value="1" <c:if test="${one.autoPerson == 1}">selected="selected"</c:if> >发起人</option>
                <%--<option value="2" <c:if test="${one.autoPerson == 2}">selected="selected"</c:if> >发起人的部门经理</option>
                <option value="3" <c:if test="${one.autoPerson == 3}">selected="selected"</c:if> >发起人的部门主管</option>--%>
                <option value="6" <c:if test="${one.autoPerson == 2||one.autoPerson == 3||one.autoPerson == 6}">selected="selected"</c:if> >发起人的部门负责人</option>
                <option value="5" <c:if test="${one.autoPerson == 5}">selected="selected"</c:if> >指定用户组</option>
                <option value="4" <c:if test="${one.autoPerson == 4}">selected="selected"</c:if> >指定人员</option>
                <option value="7" <c:if test="${one.autoPerson == 7}">selected="selected"</c:if> >执行时替换</option>
              </select>
              <span class="help-inline">预先设置自动选人，更方便转交工作</span>
            </div>
            <div class="controls " id="auto_unlock_id" style="display: none;">
              <label class="checkbox">
                <input type="checkbox" name="auto_unlock" value="1" checked="checked">允许更改
              </label>
            </div>

            <div id="auto_person_4" style="<c:if test="${one.autoPerson != 4}">display: none;</c:if> margin-top:12px;">
              <div class="">
                <label class="control-label">指定主办人</label>
                <div class="controls" style="width: 250px">
                   <!--  <input type="hidden" name="auto_sponsor_ids" id="auto_sponsor_ids" value=""> -->
                    <!-- <input class="input-xlarge" readonly type="text" placeholder="指定主办人" name="auto_sponsor_text" id="auto_user_text" value="">  -->
                    <!-- <a href="javascript:void(0);" class="btn" onclick="superDialog('/index.php?s=/demo/super_dialog/op/user.html','auto_user_text','auto_sponsor_ids');">选择</a> -->
                    <span class="pull-left" style="padding-bottom: 5px;"><sys:treeselect id="auto_sponsor_" name="auto_sponsor_ids" value="${one.autoSponsorIds}" labelName="auto_sponsor_text" labelValue="${one.autoSponsorText}"
							title="指定主办人" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></span>
                    
                </div>
              </div>
              <div class="form-group" style="display:none; margin-top:12px;">
                <label class="control-label">指定经办人</label>
                <div class="controls" style="width: 250px">
                    <!-- <input type="hidden" name="auto_respon_ids" id="auto_respon_ids" value=""> -->
                    <span class="pull-left" style="padding-bottom: 5px;"><sys:treeselect id="auto_respon_" name="auto_respon_ids" value="${one.autoResponIds}" labelName="auto_respon_text" labelValue="${one.autoResponText}"
							title="指定经办人" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></span>
                    <!-- <input class="input-xlarge" readonly type="text" placeholder="指定经办人" name="auto_respon_text" id="auto_userop_text" value=""> -->
                     <!-- <a href="javascript:void(0);" class="btn" onclick="superDialog('/index.php?s=/demo/super_dialog/op/user.html','auto_userop_text','auto_respon_ids');">选择</a> -->
                </div> 
              </div>
            </div>
            <div id="auto_person_5" style="<c:if test="${one.autoPerson != 5}">display: none;</c:if> margin-top:12px; ">
              <div>
                <label class="control-label">指定用户组</label>
                <div class="controls" style="width: 250px">
                    <!-- <input type="hidden" name="auto_role_ids" id="auto_role_value" value=""> -->
                    <!-- <input class="input-xlarge" readonly type="text" placeholder="指定角色" name="auto_role_text" id="auto_role_text" value="测试1,测试2"> --> <!-- <a href="javascript:void(0);" class="btn" onclick="superDialog('/index.php?s=/demo/super_dialog/op/role.html','auto_role_text','auto_role_value');">选择</a> -->
                    <%-- <span class="pull-left" style="padding-bottom: 5px;"><sys:treeselect id="juese" name="jueseIds" value="" labelName="jueseNames" labelValue=""
							title="指定角色" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></span> --%>
					<select id="auto_role_ids" name="auto_role_ids" style="    width: 250px;">
					    <option value="">无指定用户组</option>
					   <c:forEach items="${fns:getDictList('sys_user_type')}" var="item">
		                <option value="${item.value}" <c:if test="${one.autoRoleIds == item.value}">selected="selected"</c:if> >${item.label}</option>
		                </c:forEach>
		            </select>
                </div> 
              </div>
            </div>
			<div class="controls ">
              <label class="checkbox">
                <input type="checkbox" name="parallel" value="1" <c:if test="${one.parallel == 1}">checked="checked"</c:if>>是否并行
              </label>
            </div>
          </div>
<%-- <hr/>
<h4>授权范围</h4>
          <div class="form-group">
            <label class="control-label">授权人员</label>
            <div class="controls" style="width: 250px">
                <!-- <input type="hidden" name="range_user_ids" id="range_user_ids" value="10"> -->
                <!-- <input class="input-xlarge" readonly type="text" placeholder="选择人员" name="range_user_text" id="range_user_text" value="测试1"> <a href="javascript:void(0);" class="btn" onclick="superDialog('/index.php?s=/demo/super_dialog/op/user.html','range_user_text','range_user_ids');">选择</a> -->
                <span class="pull-left" style="padding-bottom: 5px;"><sys:treeselect id="range_user_" name="range_user_ids" value="${one.rangeUserIds}" labelName="range_user_text" labelValue="${one.rangeUserText}"
							title="指定授权人员" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></span>
            </div> 
          </div>

          <div class="form-group">
            <label class="control-label">授权部门</label>
            <div class="controls" style="width: 250px">
                <!-- <input type="hidden" name="range_dept_ids" id="range_dept_ids" value="10,11"> -->
                <!-- <input class="input-xlarge" readonly type="text" placeholder="选择部门" name="range_dept_text" id="range_dept_text" value="测试1,测试2"> <a href="javascript:void(0);" class="btn" onclick="superDialog('/index.php?s=/demo/super_dialog/op/dept.html','range_dept_text','range_dept_ids');">选择</a> -->
                <span class="pull-left" style="padding-bottom: 5px;"><sys:treeselect id="range_dept_" name="range_dept_ids" value="${one.rangeDeptIds}" labelName="range_dept_text" labelValue="${one.rangeDeptText}"
					title="授权部门" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="false"/></span>
            </div> 
          </div>

          <div class="form-group">
            <label class="control-label">授权角色</label>
            <div class="controls" style="width: 250px">
                <!-- <input type="hidden" name="range_role_ids" id="range_role_ids" value="10,11,12"> -->
                <!-- <input class="input-xlarge" readonly type="text" placeholder="选择角色" name="range_role_text" id="range_role_text" value="测试1,测试2,测试3"> <a href="javascript:void(0);" class="btn" onclick="superDialog('/index.php?s=/demo/super_dialog/op/role.html','range_role_text','range_role_ids');">选择</a> -->
                <span class="pull-left" style="padding-bottom: 5px;"><sys:treeselect id="sqjuese" name="sqjueseId" value="" labelName="sqjueseName" labelValue=""
					title="授权角色" url="/sys/office/treeData?type=1" cssClass="form-control required" notAllowSelectParent="false"/></span>
					
					<select name="range_role_ids" id="range_role_ids" style="    width: 250px;">
					    <option value="">无授权角色</option>
					   <c:forEach items="${fns:getDictList('sys_user_type')}" var="item">
		                <option value="${item.value}" <c:if test="${one.rangeRoleIds == item.value}">selected="selected"</c:if>>${item.label}</option>
		                </c:forEach>
		            </select>
            </div> 
          </div>


          <div class="form-group">
            <div class="controls">
                <span class="help-block">当需要手动选人时，则授权范围生效</span>
            </div> 
          </div> --%>
          


    </div><!-- attrPower end -->


    <div class="tab-pane" id="attrOperate" role="tabpanel">

        <div class="form-group">
          <label class="control-label" >交接方式</label>
          <div class="controls">
            <select name="receive_type" >
              <option value="0" <c:if test="${one.receiveType == 0}">selected="selected"</c:if>>明确指定主办人</option>
              <option value="1" <c:if test="${one.receiveType == 1}">selected="selected"</c:if>>先接收为主办人</option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <div class="controls">
            <label class="checkbox">
                <input type="checkbox" name="is_user_end" value="1" <c:if test="${one.isUserEnd == 1}">checked="checked"</c:if> >允许主办人办结流程(最后步骤默认允许)
              </label>
            <label class="checkbox">
                <input type="checkbox" name="is_userop_pass" value="1" <c:if test="${one.isUseropPass == 1}">checked="checked"</c:if>>经办人可以转交下一步
              </label>
          </div>
        </div>
<hr/>

        <div class="form-group">
          <label class="control-label" >会签方式</label>
          <div class="controls">
            <select name="is_sing" >
              <option value="1" <c:if test="${one.isSing == 1}">selected="selected"</c:if> >允许会签</option>
              <option value="2" <c:if test="${one.isSing == 2}">selected="selected"</c:if> >禁止会签</option>
              <option value="3" <c:if test="${one.isSing == 3}">selected="selected"</c:if> >强制会签</option>
            </select>
            <span class="help-inline">如果设置强制会签，则本步骤全部人都会签后才能转交或办结</span>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label" >可见性</label>
          <div class="controls">
            <select name="sign_look" >
              <option value="1" <c:if test="${one.signLook == 1}">selected="selected"</c:if>>总是可见</option>
              <option value="2" <c:if test="${one.signLook == 2}">selected="selected"</c:if>>本步骤之间经办人不可见</option>
              <option value="3" <c:if test="${one.signLook == 3}">selected="selected"</c:if>>其它步骤不可见</option>
            </select>
          </div>
        </div>


<hr/>

        <div class="form-group">
          <label class="control-label" >回退方式</label>
          <div class="controls">
            <select name="is_back" >
              <option value="1" <c:if test="${one.isBack == 1}">selected="selected"</c:if>>不允许</option>
              <option value="2" <c:if test="${one.isBack == 2}">selected="selected"</c:if>>允许回退上一步</option>
              <option value="3" <c:if test="${one.isBack == 3}">selected="selected"</c:if>>允许回退之前步骤</option>
            </select>
          </div>
        </div>


    </div><!-- attrOperate end -->



    <div class="tab-pane  " id="attrJudge" role="tabpanel">

       
    <table class="table" >
      <thead>
        <tr>
          <th style="width:100px;">转出步骤</th>
          <th>转出条件设置</th>
        </tr>
      </thead>
      <tbody>

<!--模板-->
<tr id="tpl" class="hide">    
<td style="width: 100px;">@text</td>
<td>
    <table class="table table-condensed">
    <tbody>
      <tr>
        <td style="width:75%">
            <select id="field_@a" class="input-medium">
              <option value="">选择字段</option>
              <!-- 表单字段 start -->
                                  <option value="data_1">文本框</option>                    <option value="data_2">下拉菜单</option>                    <option value="data_3">单选</option><option value="checkboxs_0">复选</option>
                                    <option value="data_7">宏控件</option>                    <option value="data_8">雷劈网</option>                    <option value="data_9">多行文本</option>                    <option value="data_10">进度条</option>              <!-- 表单字段 end -->  
            </select>
            <select id="condition_@a" class="input-small">
        <option value="=">等于</option>
        <option value="&lt;&gt;">不等于</option>
        <option value="&gt;">大于</option>
        <option value="&lt;">小于</option>
        <option value="&gt;=">大于等于</option>
        <option value="&lt;=">小于等于</option>
        <option value="include">包含</option>
        <option value="exclude">不包含</option>
            </select>
            <input type="text" id="item_value_@a" class="input-small">
            <select id="relation_@a" class="input-small">
        <option value="AND">与</option>
        <option value="OR">或者</option>
            </select>
        </td>
        <td>
            <div class="btn-group">
        <button type="button" class="btn btn-small" onclick="fnAddLeftParenthesis('@a')">（</button>
        <button type="button" class="btn btn-small" onclick="fnAddRightParenthesis('@a')">）</button>
        <button type="button" onclick="fnAddConditions('@a')" class="btn btn-small">新增</button>
            </div>
        </td>
       </tr>
       <tr>
        <td>
            <select id="conList_@a" multiple="" style="width: 100%;height: 80px;"></select>
        </td>
        <td>
            <div class="btn-group">
        <button type="button" onclick="fnDelCon('@a')" class="btn btn-small">删行</button>
        <button type="button" onclick="fnClearCon('@a')" class="btn btn-small">清空</button>
            </div>
        </td>
      </tr>
      <tr>
        <td>
            <input id="process_in_desc_@a" type="text" name="process_in_desc_@a" style="width:98%;">
            <input name="process_in_set_@a" id="process_in_set_@a" type="hidden">
        </td>
        <td>
            <span class="xc1">不符合条件时的提示</span>
        </td>
      </tr>
    </tbody>
    </table>
</td>
</tr>


  </tbody>
  <tbody id="ctbody">

  </tbody>
</table>
<input type="hidden" name="process_condition" id="process_condition">






    </div><!-- attrJudge end -->
    <div class="tab-pane" id="attrStyle" role="tabpanel">

        <div class="form-group">
          <label class="control-label" for="process_name">尺寸</label>
          <div class="controls">
            <input type="text" class="input-small" name="style_width" id="style_width" placeholder="宽度PX" <c:if test="${one.styleWidth != null}">value="${one.styleWidth}"</c:if> <c:if test="${one.styleWidth == null}">value="121"</c:if> > X <input type="text" class="input-small" name="style_height" id="style_height" placeholder="高度PX" <c:if test="${one.styleHeight != null}">value="${one.styleHeight}"</c:if> <c:if test="${one.styleWidth == null}">value="41"</c:if>  >
          </div>
        </div>

        <div class="form-group">
          <label class="control-label" for="process_name">字体颜色</label>
          <div class="controls">
            <input type="text" readonly="readonly" class="input-small" name="style_color" id="style_color" placeholder="#000000" <c:if test="${one.styleColor != null}">value="${one.styleColor}"</c:if> <c:if test="${one.styleColor == null}">value="#0e76a8"</c:if> >
            <div class="colors" org-bind="style_color">
                <ul>
                  <li class="Black <c:if test="${one.styleColor == '#000'}">active</c:if>" org-data="#000" title="Black">1</li>
                  <li class="red <c:if test="${one.styleColor == '#d54e21'}">active</c:if>" org-data="#d54e21" title="Red">2</li>
                  <li class="green <c:if test="${one.styleColor == '#78a300'}">active</c:if>" org-data="#78a300" title="Green">3</li>
                  <li class="blue <c:if test="${one.styleColor == '#0e76a8'}">active</c:if>" org-data="#0e76a8" title="Blue">4</li>
                  <li class="aero <c:if test="${one.styleColor == '#9cc2cb'}">active</c:if>" org-data="#9cc2cb" title="Aero">5</li>
                  <li class="grey <c:if test="${one.styleColor == '#73716e'}">active</c:if>" org-data="#73716e" title="Grey">6</li>
                  <li class="orange <c:if test="${one.styleColor == '#f70'}">active</c:if>" org-data="#f70" title="Orange">7</li>
                  <li class="yellow <c:if test="${one.styleColor == '#fc0'}">active</c:if>" org-data="#fc0" title="Yellow">8</li>
                  <li class="pink <c:if test="${one.styleColor == '#ff66b5'}">active</c:if>" org-data="#ff66b5" title="Pink">9</li>
                  <li class="purple <c:if test="${one.styleColor == '#6a5a8c'}">active</c:if>" org-data="#6a5a8c" title="Purple">10</li>
                </ul>
            </div>

          </div>
        </div>

 

        <div class="form-group">
          <label class="control-label" for="process_name"><span class="process-flag badge badge-inverse"><%-- <i class="<c:if test="${one.styleIcon != null}">${one.styleIcon}</c:if> <c:if test="${one.styleColor == null}">icon-ok</c:if> icon-white" id="style_icon_preview"></i> --%></span> 图标</label>
          <div class="controls">
            <input type="text" readonly="readonly" class="input-medium" name="style_icon" id="style_icon" placeholder="icon" <c:if test="${one.styleIcon != null}">value="${one.styleIcon}"</c:if> <c:if test="${one.styleColor == null}">value="icon-ok"</c:if> >
            <div class="colors" org-bind="style_icon">
                <ul>
                  <li class="Black <c:if test="${one.styleIcon == 'icon-star-empty'}">active</c:if>" org-data="icon-star-empty" title="Black"><i class="icon-star-empty icon-white"></i></li>
                  <li class="red <c:if test="${one.styleIcon == 'icon-ok'}">active</c:if>" org-data="icon-ok" title="Red"><i class="icon-ok icon-white"></i></li>
                  <li class="green <c:if test="${one.styleIcon == 'icon-remove'}">active</c:if>" org-data="icon-remove" title="Green"><i class="icon-remove icon-white"></i></li>
                  <li class="blue <c:if test="${one.styleIcon == 'icon-refresh'}">active</c:if>" org-data="icon-refresh" title="Blue"><i class="icon-refresh icon-white"></i></li>
                  <li class="aero <c:if test="${one.styleIcon == 'icon-plane'}">active</c:if>" org-data="icon-plane" title="Aero"><i class="icon-plane icon-white"></i></li>
                  <li class="grey <c:if test="${one.styleIcon == 'icon-play'}">active</c:if>" org-data="icon-play" title="Grey"><i class="icon-play icon-white"></i></li>
                  <li class="orange <c:if test="${one.styleIcon == 'icon-heart'}">active</c:if>" org-data="icon-heart" title="Orange"><i class="icon-heart icon-white"></i></li>
                  <li class="yellow <c:if test="${one.styleIcon == 'icon-random'}">active</c:if>" org-data="icon-random" title="Yellow"><i class="icon-random icon-white"></i></li>
                  <li class="pink <c:if test="${one.styleIcon == 'icon-home'}">active</c:if>" org-data="icon-home" title="Pink"><i class="icon-home icon-white"></i></li>
                  <li class="purple <c:if test="${one.styleIcon == 'icon-lock'}">active</c:if>" org-data="icon-lock" title="Purple"><i class="icon-lock icon-white"></i></li>
                </ul>
                <!-- <a href="http://v2.bootcss.com/base-css.html#icons" target="_blank">更多</a> -->
            </div>
          </div>
        </div>
        

        
        <!-- 不太完善，隐藏
         <div class="form-group">
          <label class="control-label">CSS3 图形</label>
          <div class="controls">
            <select name="style_graph" id="style_graph">
              <option value="">矩形</option>
              <option value="circle">圆形</option>
              <option value="oval">椭圆</option>
              <option value="hexagon">菱形</option>
            </select>
            <span class="help-inline">CSS3仅支持部分浏览器</span>
          </div>
        </div> -->


    </div><!-- attrStyle end -->
  </div>


<div class="clearfix">
  <hr/>
  <span class="pull-right">
      <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">取消</a>
      <button class="btn btn-primary" type="button" id="attributeOK">确定保存</button>
  </span>
</div>
</form>
<iframe id="hiddeniframe" style="display: none;" name="hiddeniframe"></iframe>





    
<script type="text/javascript">
  /*var flow_id =  '4';//流程ID
  var process_id = '61';//步骤ID
  var get_con_url = "/index.php?s=/Flowdesign/get_con.html";//获取条件
  */
  var _out_condition_data = {"63":{"condition":"<option value=\"'data_1' = '1'  AND\">'\u6587\u672c\u6846' = '1'  AND<\/option><option value=\"( 'data_2' = '1' AND 'data_2' = '2' OR 'data_2' = '3' OR 'data_2' = '4' OR 'data_2' = '45')\">( '\u4e0b\u62c9\u83dc\u5355' = '1' AND '\u4e0b\u62c9\u83dc\u5355' = '2' OR '\u4e0b\u62c9\u83dc\u5355' = '3' OR '\u4e0b\u62c9\u83dc\u5355' = '4' OR '\u4e0b\u62c9\u83dc\u5355' = '45')<\/option>","condition_desc":"\u5feb\u6377\u5ba1\u6279\u6761\u4ef6\u4e0d\u6210\u7acb"},"64":{"condition":"<option value=\"'data_2' = '1'  AND\">'\u4e0b\u62c9\u83dc\u5355' = '1'  AND<\/option><option value=\"'data_2' = '2'\">'\u4e0b\u62c9\u83dc\u5355' = '2'<\/option>","condition_desc":"\u597d\u5427"}};
</script>

<script type="text/javascript" src="/static/flowdesign/js/attribute.js"></script>
<script type="text/javascript">
$(function(){
	
	 <c:forEach items="${process_to_list_left}" var="proc" >
	      $('#process_multiple').multiselect2side('addOption', {name: "${proc.processName}", value: "${proc.id}", selected: true});
     </c:forEach>
	
     $("#attributeOK").click(function(){
    	 console.log($("#flow_attribute").serialize());
         $.post($("#flow_attribute").attr("action"),$("#flow_attribute").serialize(),function(data){
         	 if(!data.status)
              {
                  layer.alert(data.msg,{icon:2},function(){
                      location.reload();
                  });
                  //mAlert(data.msg);
              }else{
            	  location.reload();
              }
            
         },'json');
     });
});
function callback(){
	
}
</script>

    

</body>
</html>