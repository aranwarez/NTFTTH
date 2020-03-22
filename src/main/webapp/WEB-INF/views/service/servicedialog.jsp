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
                <h4 class="modal-title" id="myModalLabel">New FTTH Service Define</h4>
            </div>

               <div class="modal-body">
                    
                    <div class="form-group">
                        <label for="name">DESCRIPTION</label> <input type="text"
                                                                     class="form-control" name="DESCRIPTION" id="DESCRIPTION" placeholder="Enter description">
                    </div>
                    
                    <div class="form-group">
                         <label for="SHORT_CODE">SHORT_CODE</label> <input type="text"
                                                                     class="form-control" name="SHORT_CODE" id="SHORT_CODE" placeholder="Enter SHORT_CODE">
                         
                    </div>
                    <div class="form-group">
                         <label for="ACTIVE_DT">ACTIVE_DT</label> <input type="text"
                                                                     class="nepali-calendar form-control" value="2076/12/08" name="ACTIVE_DT" id="ACTIVE_DT" placeholder="Enter ACTIVE_DT">
                         
                    </div>
                    <div class="form-group">
                         <label for="DEACTIVE_DT">DEACTIVE_DT</label> <input type="text"
                                                                     class="nepali-calendar form-control" value="2076/12/08" name="DEACTIVE_DT" id="DEACTIVE_DT" placeholder="Enter DEACTIVE_DT">
                         
                    </div>
                    
                    
                    
                    <div class="form-group">
                        <label for="Active Flag">Active Status</label> 
                        <select  name="ACTIVE_STATUS" id="ACTIVE_STATUS">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select>        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="return saveService()">Save changes</button>
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
                <h4 class="modal-title" id="myModalLabel">Edit Service</h4>
            </div>

            <form action="<c:url value="/service/update" />" method="post"
                  acceptCharset="UTF-8">

                 <div class="modal-body">
                    
                    <div class="form-group">
                        <label for="name">DESCRIPTION</label> <input type="text"
                                                                     class="form-control" name="EDITDESCRIPTION" id="EDITDESCRIPTION" placeholder="Enter description">
                    </div>
                    
                    <div class="form-group">
                         <label for="SHORT_CODE">SHORT_CODE</label> <input type="text"
                                                                     class="form-control" name="EDITSHORT_CODE" id="EDITSHORT_CODE" placeholder="Enter SHORT_CODE">
                         
                    </div>
                    <div class="form-group">
                         <label for="ACTIVE_DT">ACTIVE_DT</label> <input type="text"
                                                                     class="nepali-calendar form-control" value="2076/12/08" name="EDITACTIVE_DT" id="EDITACTIVE_DT" placeholder="Enter ACTIVE_DT">
                         
                    </div>
                    <div class="form-group">
                         <label for="DEACTIVE_DT">DEACTIVE_DT</label> <input type="text"
                                                                     class="nepali-calendar form-control" value="2076/12/08" name="EDITDEACTIVE_DT" id="EDITDEACTIVE_DT" placeholder="Enter DEACTIVE_DT">
                         
                    </div>
                    
                    
                    
                    <div class="form-group">
                        <label for="Active Flag">Active Status</label> 
                        <select  name="EDITACTIVE_STATUS" id="EDITACTIVE_STATUS">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select>        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateService();" class="btn btn-primary">Update</button>
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
                <h4 class="modal-title" id="myModalLabel">Ftth service delete</h4>
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
