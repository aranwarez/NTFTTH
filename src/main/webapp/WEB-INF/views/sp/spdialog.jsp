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
                <h4 class="modal-title" id="myModalLabel">New Service Provider
                    Define</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="spcode">SP_CODE</label> <input type="text"
                                                               class="form-control" name="SP_CODE" id="SP_CODE"
                                                               placeholder="Enter Service Provider"> <label for="spname">SP_NAME</label>
                    <input type="text" class="form-control" name="SP_NAME" id="SP_NAME"
                           placeholder="Enter Service Provider Name"> <label
                           for="accoutno">Account No.</label> <input type="text"
                           class="form-control" name="SHORT_CODE" id="SHORT_CODE"
                           placeholder="Enter Account No."> <label for="address">Address</label>
                    <input type="text" class="form-control" name="ADDRESS" id="ADDRESS"
                           placeholder="Enter Address"> <label for="ContactPerson">Contact</label>
                    <input type="text" class="form-control" name="CONTACT_PERSON"
                           id="CONTACT_PERSON" placeholder="Enter Contact Person"> 
                           <label for="ContractDt">Contract Dt.</label> 
                    <input type="text" class="nepali-calendar" name="CONTRACT_DT" id="CONTRACT_DT" placeholder="Enter contract dt."> 
                    
                    <label for="TerminateDt">Terminate Dt.</label> 
                    <input type="text" class="nepali-calendar" name="CONTRACT_TER_DT" id="CONTRACT_TER_DT" placeholder="Enter termination dt.">
                           <br>
                           <label
                           for="TelNo">Tel No.</label> <input type="text"
                           class="form-control" name="TEL_NO" id="TEL_NO"
                           placeholder="Enter Tel No."> <label for="MobileNo">Mobile
                        No.</label> <input type="text" class="form-control" name="MOBILE_NO"
                                       id="MOBILE_NO" placeholder="Enter Mobile No."> <label
                                       for="Email">Email Id.</label> <input type="text"
                                       class="form-control" name="EMAIL" id="EMAIL"
                                       placeholder="Enter Email Id"> <label for="PanNo.">PAN
                        No.</label> <input type="text" class="form-control" name="PAN_NO"
                                       id="PAN_NO" placeholder="Enter Pan No."> <label
                                       for="SLDG_CODE">Sldg Code</label> <select name="SLDG_CODE"
                                       id="SLDG_CODE">
                        <c:forEach var="COA" items="${COA_list}">
                            <option value="${COA.SLDG_CODE}">${COA.SLDG_CODE}
                                ${COA.ENG_DESC}</option>
                            </c:forEach>
                    </select> 
                    
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="saveSp()">Save
                    changes</button>
            </div>
        </div>
    </div>
</div>

<!-- edit modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">

    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Edit Service Provider</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="name">SP_CODE</label> <input type="text"
                                                             class="form-control" name="SP_CODE" id="EDITSP_CODE"
                                                             placeholder="Enter Sp Code" readonly="readonly"> <label
                                                             for="name">SP_NAME</label> <input type="text"
                                                             class="form-control" name="SP_NAME" id="EDITSP_NAME"
                                                             placeholder="Enter Service Provider"> <label
                                                             for="accoutno">Account No.</label> <input type="text"
                                                             class="form-control" name="SHORT_CODE" id="EDITSHORT_CODE"
                                                             placeholder="Enter Account No."> <label for="address">Address</label>
                    <input type="text" class="form-control" name="ADDRESS"
                           id="EDITADDRESS" placeholder="Enter Address"> <label
                           for="ContactPerson">Contact</label> <input type="text"
                           class="form-control" name="CONTACT_PERSON"
                           id="EDITCONTACT_PERSON" placeholder="Enter Contact Person">
                      <label for="ContractDt">Contract Dt.</label> 
                    <input type="text" class="nepali-calendar" name="CONTRACT_DT" id="EDITCONTRACT_DT" placeholder="Enter contract dt."> 
                    <label for="TerminateDt">Terminate Dt.</label> 
                    <input type="text" class="nepali-calendar" name="CONTRACT_TER_DT" id="EDITCONTRACT_TER_DT" placeholder="Enter contract terminate dt.">
                
                    <br>
                    
                    <label for="TelNo">Tel No.</label> <input type="text"
                                                              class="form-control" name="TEL_NO" id="EDITTEL_NO"
                                                              placeholder="Enter Tel No."> <label for="MobileNo">Mobile
                        No.</label> <input type="text" class="form-control" name="MOBILE_NO"
                                       id="EDITMOBILE_NO" placeholder="Enter Mobile No."> <label
                                       for="Email">Email Id.</label> <input type="text"
                                       class="form-control" name="EMAIL" id="EDITEMAIL"
                                       placeholder="Enter Email Id"> <label for="PanNo.">PAN
                        No.</label> <input type="text" class="form-control" name="PAN_NO"
                                       id="EDITPAN_NO" placeholder="Enter Pan No."> <label
                                       for="SldgCode">Sldg Code</label> <select name="EDITSLDG_CODE"
                                       id="EDITSLDG_CODE">
                        <c:forEach var="COA" items="${COA_list}">
                            <option value="${COA.SLDG_CODE}">${COA.SLDG_CODE}
                                ${COA.ENG_DESC}</option>
                            </c:forEach>
                    </select> 
                    <Br>
                  </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" onclick="return updateSp();"
                        class="btn btn-primary">Update</button>
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
                <h4 class="modal-title" id="myModalLabel">Sp delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to delete this data. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-primary"
                        onclick="return del()">Yes</button>
            </div>
        </div>
    </div>
</div>
