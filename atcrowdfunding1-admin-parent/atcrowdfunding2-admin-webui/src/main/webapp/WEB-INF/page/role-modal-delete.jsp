<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="roleDeleteModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" id="deleteClose" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">是否要删除以下角色</h4>
            </div>
            <div method="post" class="form-signin clearfix" role="form">
                <div id="deleteRoleNames">

                </div>
                <button type="button" id="deleteAffirm" class="btn btn-primary " style="display: flow;float: right;">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>
