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
                <h4 class="modal-title" id="myModalLabel">New Employee define</h4>
            </div>

               <div class="modal-body">
               
                     <div class="form-group">
                            <label for="name">EMPLOYEE_CODE</label>
                            <input type="text" class="form-control" id="EMPLOYEE_CODE" >
                        </div>

                        <div class="form-group">
                            <label for="name">EMPLOYEE_NAME</label>
                            <input type="text" class="form-control" id="EMPLOYEE_NAME" >
                        </div>
                        <div class="form-group">
                            <label for="name">ADDRESS</label>
                            <input type="text" class="form-control" id="ADDRESS" >
                        </div>
                        <div class="form-group">
                            <label>SEX</label>
                            <select class="form-control" name="SEX" id="SEX">
                                <option value="M">Male</option>
                                <option value="F">Female</option>                              
                            </select>
                        </div>



                        <div class="form-group">
                            <label for="MARITAL_STATUS">MARITAL_STATUS</label>
                            
                            <select name="MARITAL_STATUS" id="MARITAL_STATUS" class="form-control" >
    <option value="">-Select Marital Status-</option>
    <option value="M">M</option>
   <option value="U">U</option>
</select>

                        </div>

                           <!--<div class="form-group">
                            <label for="DOB">DOB</label>
                            <input type="text" class="form-control" id="DOB" >
                        </div> 
                      
                        <div class="form-group">
                            <label for="DOJ">DOJ</label>
                            <input type="text" class="form-control" id="DOJ" >
                        </div>
  -->

                        <div class="form-group">
                            <label for="QUALIFICATION">QUALIFICATION</label>
                            <input type="text" class="form-control" id="QUALIFICATION" >
                        </div>
                        <div class="form-group">
                            <label for="TEL_NO">TEL_NO</label>
                            <input type="text" class="form-control" id="TEL_NO" >
                        </div>

                        <div class="form-group">
                            <label for="QUALIFICATION">MOBILE_NO</label>
                            <input type="text" class="form-control" id="MOBILE_NO" >
                        </div>
                        <div class="form-group">
                            <label for="EMAIL">EMAIL</label>
                            <input type="email" class="form-control" id="EMAIL" required="required">
                        </div>

                        <div class="form-group">
                            <label for="EMP_NO">EMP_NO</label>
                            <input type="number" class="form-control" id="EMP_NO" required="required" onkeypress="return isNumberKey(event)">
                        </div>

                        <div class="form-group">
                            <label for="TTC_NO">TTC_NO</label>
                            <input type="number" class="form-control" id="TTC_NO" onkeypress="return isNumberKey(event)">
                        </div>

                        <div class="form-group">
                            <label for="POST_CD">POST_CD</label>
                            <input type="text" class="form-control" id="POST_CD">
                        </div>


                        <div class="form-group">
                            <label for="DEPT_CD">DEPT_CD</label>
                            <input type="text" class="form-control" id="DEPT_CD">
                        </div>


                        <div class="form-group">
                            <label for="LOCATION_CD">LOCATION_CD</label>
                            <select class="form-control" id="LOCATION_CD">
                            </select>
                        </div>


                        <div class="form-group">
                            <label for="EMP_LEVEL">EMP_LEVEL</label>
                            <input type="number" class="form-control" id="EMP_LEVEL" onkeypress="return isNumberKey(event)">
                        </div>


                        <div class="form-group">
                            <label for="EMP_LEVEL">EMP_TYPE</label>
                            <input type="text" class="form-control" id="EMP_TYPE"   maxlength="1">
                        </div>


                        <div class="form-group">
                   
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="return saveEmployee()">Save changes</button>
                </div>
           
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
                <h4 class="modal-title" id="myModalLabel">Edit Employee</h4>
            </div>

            

                 <div class="modal-body">
                 
                 
                 <div class="form-group">
                            <label for="name">EMPLOYEE_CODE</label>
                            <input type="text" class="form-control" id="EDITEMPLOYEE_CODE" disabled="true">
                        </div>

                        <div class="form-group">
                            <label for="name">EMPLOYEE_NAME</label>
                            <input type="text" class="form-control" id="EDITEMPLOYEE_NAME" >
                        </div>
                        <div class="form-group">
                            <label for="name">ADDRESS</label>
                            <input type="text" class="form-control" id="EDITADDRESS" >

                        </div>



                        <div class="form-group">
                            <label>SEX</label>
                            <select class="form-control" name="SEX" id="EDITSEX">
                                <option value="M">Male</option>
                                <option value="F">Female</option>                              
                            </select>
                        </div>



                        <div class="form-group">
                            <label for="MARITAL_STATUS">MARITAL_STATUS</label>
                                                       
                                 <select name="MARITAL_STATUS" id="EDITMARITAL_STATUS" class="form-control" >
    <option value="">-Select Marital Status-</option>

    <option value="M">M</option>
   <option value="U">U</option>
 
</select>

                        </div>

                       <!-- <div class="form-group">
                            <label for="DOB">DOB</label>
                            <input type="text" class="form-control" id="EDITDOB" >
                        </div>
                        
                        <div class="form-group">
                            <label for="DOJ">DOJ</label>
                            <input type="text" class="form-control" id="EDITDOJ" >
                        </div>
-->

                        <div class="form-group">
                            <label for="QUALIFICATION">QUALIFICATION</label>
                            <input type="text" class="form-control" id="EDITQUALIFICATION" >
                        </div>
                        <div class="form-group">
                            <label for="TEL_NO">TEL_NO</label>
                            <input type="text" class="form-control" id="EDITTEL_NO" >
                        </div>

                        <div class="form-group">
                            <label for="MOBILE_NO">MOBILE_NO</label>
                            <input type="text" class="form-control" id="EDITMOBILE_NO" >
                        </div>
                        <div class="form-group">
                            <label for="EMAIL">EMAIL</label>
                            <input type="email" class="form-control" id="EDITEMAIL" required="required">
                        </div>

                        <div class="form-group">
                            <label for="EMP_NO">EMP_NO</label>
                            <input type="number" class="form-control" id="EDITEMP_NO" onkeypress="return isNumberKey(event)" required="required">
                        </div>

                        <div class="form-group">
                            <label for="TTC_NO">TTC_NO</label>
                            <input type="number" class="form-control" id="EDITTTC_NO" onkeypress="return isNumberKey(event)">
                        </div>

                        <div class="form-group">
                            <label for="POST_CD">POST_CD</label>
                            <input type="text" class="form-control" id="EDITPOST_CD">
                        </div>


                        <div class="form-group">
                            <label for="DEPT_CD">DEPT_CD</label>
                            <input type="text" class="form-control" id="EDITDEPT_CD">
                        </div>


                        <div class="form-group">
                            <label for="LOCATION_CD">LOCATION_CD</label>                            
                             <select class="form-control" id="EDITLOCATION_CD">
                            </select>
                        </div>


                        <div class="form-group">
                            <label for="EMP_LEVEL">EMP_LEVEL</label>
                            <input type="number" class="form-control" id="EDITEMP_LEVEL" onkeypress="return isNumberKey(event)">
                        </div>


                        <div class="form-group">
                            <label for="EMP_LEVEL">EMP_TYPE</label>
                            <input type="text" class="form-control" id="EDITEMP_TYPE"  maxlength="1">
                        </div>


                         <div class="form-group">
                            <label for="EMP_TITLE">EMP_TITLE</label>
                            <input type="text" class="form-control" id="EDITEMP_TITLE ">
                        </div>
                       
                        
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateEmployee();" class="btn btn-primary">Update</button>
                </div>

          

        </div>
    </div>

 </div>
 
 <!-- delete modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Employee delete</h4>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this data?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                        <button type="button" class="btn btn-primary" onclick="return del()">Yes</button>
                    </div>
                </div>
            </div>
        </div>