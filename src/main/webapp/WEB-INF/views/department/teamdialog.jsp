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
                <h4 class="modal-title" id="myModalLabel">Add new Department Information </h4>
            </div>

               <div class="modal-body">
                    
                    <div class="form-group">
                        <label for="TEAM_CODE">Department</label> <input type="text"
                                                                     class="form-control" name="DEPARTMENT" id="DEPARTMENT" placeholder="Enter Department Name">
                    </div>
                    
                    <div class="form-group">
                         <label for="DESCRIPTION">Contact No.</label> <input type="text"
                                                                     class="form-control"  id=CONTACT_NO placeholder="Enter Contact_No">
                         
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">OFFICE_NAME  </label> <input type="text"
                                                                     class="form-control" name="OFFICE_NAME" id="OFFICE_NAME" placeholder="Enter OFFICE   Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">AREA_NAME</label> <input type="text"
                                                                     class="form-control" name="AREA_NAME  " id="AREA_NAME" placeholder="Enter AREA   Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">Name  </label> <input type="text"
                                                                     class="form-control" name="SECTION  " id="SECTION" placeholder="Enter  Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">LANDLINE_NO  </label> <input type="text"
                                                                     class="form-control" name="LANDLINE_NO" id="LANDLINE_NO" placeholder="Enter LANDLINE_NO  Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">MOBILE_NO  </label> <input type="text"
                                                                     class="form-control" name="MOBILE_NO" id="MOBILE_NO" placeholder="Enter MOBILE_NO">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">REMARKS1  </label> <input type="text"
                                                                     class="form-control" name="REMARKS1" id="REMARKS1" placeholder="Enter REMARKS1">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">REMARKS2 </label> <input type="text"
                                                                     class="form-control" name="REMARKS2" id="REMARKS2" placeholder="Enter REMARKS2">
                    </div>
                    
                    
                    
                    
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="return saveDepartment()">Save Department</button>
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
                <h4 class="modal-title" id="myModalLabel">Edit Department Information</h4>
            </div>

            <form action="<c:url value="/service/update" />" method="post"
                  acceptCharset="UTF-8">

                 <div class="modal-body">
                      <div class="form-group">
                        <label for="TEAM_CODE">Department</label> <input type="text"
                                                                     class="form-control" name="eDEPARTMENT" id="eDEPARTMENT" placeholder="Enter Department" >
                    </div>
                    
                    <div class="form-group">
                         <label for="DESCRIPTION">Contact No</label> <input type="text"
                                                                     class="form-control" name="eCONTACTNO" id="eCONTACTNO" placeholder="Enter Contact NO">
                         
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">OFFICE_NAME  </label> <input type="text"
                                                                     class="form-control" name="eOFFICE_NAME" id="eOFFICE_NAME" placeholder="Enter OFFICE   Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">AREA_NAME</label> <input type="text"
                                                                     class="form-control" name="eAREA_NAME  " id="eAREA_NAME" placeholder="Enter AREA   Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">Name  </label> <input type="text"
                                                                     class="form-control" name="eSECTION  " id="eSECTION" placeholder="Enter    Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">LANDLINE_NO  </label> <input type="text"
                                                                     class="form-control" name="eLANDLINE_NO" id="eLANDLINE_NO" placeholder="Enter LANDLINE_NO  Name">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">MOBILE_NO  </label> <input type="text"
                                                                     class="form-control" name="eMOBILE_NO" id="eMOBILE_NO" placeholder="Enter MOBILE_NO">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">REMARKS1  </label> <input type="text"
                                                                     class="form-control" name="eREMARKS1" id="eREMARKS1" placeholder="Enter REMARKS1">
                    </div>
                    <div class="form-group">
                        <label for="TEAM_CODE">REMARKS2 </label> <input type="text"
                                                                     class="form-control" name="eREMARKS2" id="eREMARKS2" placeholder="Enter REMARKS2">
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
                <h4 class="modal-title" id="myModalLabel">Delete Department Information</h4>
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
