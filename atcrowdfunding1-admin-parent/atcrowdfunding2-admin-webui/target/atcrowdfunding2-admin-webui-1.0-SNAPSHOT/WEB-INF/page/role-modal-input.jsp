<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="roleInputModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">角色信息录入</h4>
            </div>
            <div method="post" class="form-signin clearfix" role="form">
                <div class="form-group has-success has-feedback">
                    <input type="text" name="roleName" class="form-control" id="inputSuccess4" placeholder="请输入角色名称"
                           style="margin-top:10px;">
                </div>
                <button type="button" id="saveBtn" class="btn btn-primary " style="display: flow;float: right;">保存
                </button>
            </div>
        </div>
    </div>
</div>
