<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
 <!-- new modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Menu Define</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="name">MENU_CODE</label>
                            <input type="text" class="form-control" id="MENU_CODE" placeholder="Enter menu code">
                        </div>

                        <div class="form-group">
                            <label for="name">MENU_DESC</label>
                            <input type="text" class="form-control" id="MENU_DESC" placeholder="Enter menu description">
                        </div>
                        <div class="form-group">
                            <label for="name">MENU_URL</label>
                            <input type="text" class="form-control" id="MENU_URL" placeholder="Enter menu url">
                        </div>
                        <div class="form-group">
                            <label>Parent Menu</label>
                            <select class="form-control" name="PARENT_MENU" id="PARENT_MENU">

                            </select>
                        </div>
                        <div class="form-group">
                            <label>Module</label>
                            <select class="form-control" name="MODULE_TYPE" id="MODULE_TYPE">
                                <option value="C">CRMS</option>
                                <option value="P">PBIM</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>STATUS_TYPE</label>
                            <input type="checkbox" id="STATUS_TYPE" checked="checked">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="return saveMenu();">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        


	<!-- delete modal -->

	<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">

		<div class="modal-dialog" role="document">

			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Menu delete</h4>
				</div>

				<div class="modal-body">
					<p>Are you sure you want to delete this data. This cannot be
						undone</p>
				</div>
                            
				<div class="modal-footer">
                                    <form action="<c:url value="/role/delete" />" method="post"
					acceptCharset="UTF-8">
					<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
					<button type="button" class="btn btn-primary" onclick="return del()">Yes</button>
                                    </form>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- edit modal -->      
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Menu Define</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="name">MENU_CODE</label>
                            <input type="text" class="form-control" id="EDITMENU_CODE"  readonly>
                        </div>

                        <div class="form-group">
                            <label for="name">MENU_DESC</label>
                            <input type="text" class="form-control" id="EDITMENU_DESC" placeholder="Enter menu description">
                        </div>
                        <div class="form-group">
                            <label for="name">MENU_URL</label>
                            <input type="text" class="form-control" id="EDITMENU_URL" placeholder="Enter menu url">
                        </div>
                        <div class="form-group">
                            <label>Parent Menu</label>
                            <select class="form-control" name="EDITPARENT_MENU" id="EDITPARENT_MENU">

                            </select>
                        </div>
                        <div class="form-group">
                            <label>Module</label>
                            <select class="form-control" name="EDITMODULE_TYPE" id="EDITMODULE_TYPE">
                                <option value="C">CRMS</option>
                                <option value="P">PBIM</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>STATUS_TYPE</label>
                            <input type="checkbox" id="EDITSTATUS_TYPE" checked="checked">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="return updateMenu();">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        
        
               