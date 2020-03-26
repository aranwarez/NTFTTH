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
                <h4 class="modal-title" id="myModalLabel">New Team Define</h4>
            </div>

               <div class="modal-body">
                    
                    <div class="form-group">
                        <label for="TEAM_CODE">TEAM_CODE</label> <input type="text"
                                                                     class="form-control" name="TEAM_CODE" id="TEAM_CODE" placeholder="Enter TEAM_CODE">
                    </div>
                    
                    <div class="form-group">
                         <label for="DESCRIPTION">DESCRIPTION</label> <input type="text"
                                                                     class="form-control" name="DESCRIPTION" id="DESCRIPTION" placeholder="Enter SHORT_CODE">
                         
                    </div>
                 
                    
                    
                    
                    
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="return saveTeam()">Save changes</button>
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
                <h4 class="modal-title" id="myModalLabel">Edit Team</h4>
            </div>

            <form action="<c:url value="/service/update" />" method="post"
                  acceptCharset="UTF-8">

                 <div class="modal-body">
                      <div class="form-group">
                        <label for="TEAM_CODE">TEAM_CODE</label> <input type="text"
                                                                     class="form-control" name="EDITTEAM_CODE" id="EDITTEAM_CODE" placeholder="Enter TEAM_CODE" readonly="readonly">
                    </div>
                    
                    <div class="form-group">
                         <label for="DESCRIPTION">DESCRIPTION</label> <input type="text"
                                                                     class="form-control" name="EDITDESCRIPTION" id="EDITDESCRIPTION" placeholder="Enter DESCRIPTION">
                         
                    </div>
                 
                    
                   
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateTeam();" class="btn btn-primary">Update</button>
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
