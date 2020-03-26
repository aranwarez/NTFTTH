<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!-- new modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">New Sub Team Define</h4>
            </div>

               <div class="modal-body">
                    
                       
                    <div class="form-group">
                        <label for="SUB_TEAM_CODE">SUB_TEAM_CODE</label> <input type="text"
                                                                     class="form-control" name="SUB_TEAM_CODE" id="SUB_TEAM_CODE" placeholder="Enter SUB_TEAM_CODE">
                    </div>
                    
                    <div class="form-group">
                         <label for="DESCRIPTION">DESCRIPTION</label> <input type="text"
                                                                     class="form-control" name="DESCRIPTION" id="DESCRIPTION" placeholder="Enter SHORT_CODE">
                         
                    </div>
                 
                    
                    <div class="form-group">
                        <label for="TEAM_CODE">TEAM_CODE</label> 
                        <select  name="TEAM_CODE" id="TEAM_CODE" class="form-control chosen-select" style="width:350px;">
                                                
                        </select>        
                    </div>
                    
                    
                    
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="return saveSubTeam()">Save changes</button>
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
                <h4 class="modal-title" id="myModalLabel">Edit Sub Team</h4>
            </div>

            <form action="#" method="post"
                  acceptCharset="UTF-8">

                 <div class="modal-body">
                      
                       
                    <div class="form-group">
                        <label for="EDITSUB_TEAM_CODE">SUB_TEAM_CODE</label> <input type="text" readonly="readonly"
                                                                     class="form-control" name="EDITSUB_TEAM_CODE" id="EDITSUB_TEAM_CODE" placeholder="Enter SUB_TEAM_CODE">
                    </div>
                    
                    <div class="form-group">
                         <label for="EDITDESCRIPTION">DESCRIPTION</label> <input type="text"
                                                                     class="form-control" name="EDITDESCRIPTION" id="EDITDESCRIPTION" placeholder="Enter SHORT_CODE">
                         
                    </div>
                 
                    
                    <div class="form-group">
                        <label for="TEAM_CODE">TEAM_CODE</label> 
                        <select  name="EDITTEAM_CODE" id="EDITTEAM_CODE" class="form-control chosen-select" style="width:350px;">
                                                
                        </select>        
                    </div>
                 
                    
                   
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateSubTeam();" class="btn btn-primary">Update</button>
                </div>

            </form>

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
                <h4 class="modal-title" id="myModalLabel">Ftth team delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to delete this data. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/service/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary" onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
