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
                <h4 class="modal-title" id="myModalLabel">New Vas Service Define</h4>
            </div>

               <div class="modal-body">
                    <div class="form-group">
                        <label for="name">SERVICE_CODE</label> <input type="text"
                                                                      class="form-control" name="SERVICE_CODE" id="SERVICE_CODE" placeholder="Enter Service">
                    </div>
                    <div class="form-group">
                        <label for="name">DESCRIPTION</label> <input type="text"
                                                                     class="form-control" name="DESCRIPTION" id="DESCRIPTION" placeholder="Enter description">
                    </div>
                    
                    <div class="form-group">
                        <label for="SLDG_CODE">SLDG_CODE</label> 
                        <select  name="SLDG_CODE" id="SLDG_CODE">
                    
                            <c:forEach var="COA" items="${COA_list}">
                                <option value="${COA.SLDG_CODE}">${COA.SLDG_CODE} ${COA.ENG_DESC}</option>
                            </c:forEach>
                        
                        </select>        
                    </div>
                    
                    
                    <div class="form-group">
                        <label for="Data Import">Data Import</label> 
                        <select  name="DATA_IMPORT" id="DATA_IMPORT">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select>        
                    </div>
                    <div class="form-group">
                        <label for="Active Flag">Active flag</label> 
                        <select  name="ACTIVE_FLAG" id="ACTIVE_FLAG">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select>        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="saveService()">Save changes</button>
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
                        <label for="name">SERVICE_CODE</label>
                        <input type="text" class="form-control" name="SERVICE_CODE" id="EDITSERVICE_CODE" placeholder="Enter Service" readonly="readonly">
                    </div>

                    <div class="form-group">
                        <label for="name">DESCRIPTION</label>
                        <input type="text" class="form-control"  name="DESCRIPTION" id="EDITDESCRIPTION" placeholder="Enter description">
                    </div>
                    <div class="form-group">
                        <label for="SLDG_CODE">SLDG_CODE</label> 
                        <select  name="EDITSLDG_CODE" id="EDITSLDG_CODE">
                    
                            <c:forEach var="COA" items="${COA_list}">
                                <option value="${COA.SLDG_CODE}">${COA.SLDG_CODE} ${COA.ENG_DESC}</option>
                            </c:forEach>
                        
                        </select>        
                    </div>
                    <div class="form-group">
                        <label for="Data Import">Data Import</label> 
                        <select  name="EDITDATA_IMPORT" id="EDITDATA_IMPORT">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select>        
                    </div>
                    <div class="form-group">
                        <label for="Active Flag">Active flag</label> 
                        <select  name="EDITACTIVE_FLAG" id="EDITACTIVE_FLAG">
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
                <h4 class="modal-title" id="myModalLabel">Vas service delete</h4>
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
