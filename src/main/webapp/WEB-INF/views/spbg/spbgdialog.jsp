<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!-- new modal -->
<div class="modal fade" id="myModal" tabindex=-1 role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">New Bank Deposit/Guarentee/Refund</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <table>
                        <tr>
                            <td>
                                <label for="transid ">Trans No.</label> 
                                <input type="text"  style="width: 280px;" class="form-control" name="TRANS_ID" id="TRANS_ID"
                                       placeholder="Auto generate trans no." readonly="true"> 
                            </td>
                            <c:forEach var="DAT" items="${Date_list}">
                                <td>
                                    <label for="transdt">Trans Dt.</label> 
                                    <input type="text" style="width: 280px;" value="${DAT.NEP_TODAY_DATE}" class="nepali-calendar form-control" name="TRANS_DT" id="TRANS_DT"
                                           placeholder="Select trans dt."> 
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <label for="Transcd">Trans Cd</label> 
                                <select  class="form-control" style="width: 280px;" name="TRANS_CD" id="TRANS_CD" onchange="getbanklist(this)">
                                    <option value=''>Select</option>
                                    <c:forEach var="TRANS" items="${Trans_list}">
                                        <option value="${TRANS.TRANS_CD}">${TRANS.TRANS_CD}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="spcode">Service Provider</label> 
                                <select style="width: 280px;" name="SP_CODE" id="SP_CODE">
                                    <option value=''>Select :</option>
                                    <c:forEach var="SP" items="${Sp_list}">
                                        <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="Bankcd">Bank Code</label> 
                                <select  class="form-control" style="width: 280px;" name="BANK_CD" id="BANK_CD">
                                    <option value=''>Select</option>
                                    <c:forEach var="BANK" items="${Bank_list}">
                                        <option value="${BANK.BANK_CD}">${BANK.BANK_CD} ${BANK.ACCT_NO} ${BANK.ADDRESS}</option>
                                    </c:forEach>
                                </select> 
                            </td> 
                            <td>
                                <label for="amt">Amount.</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="AMT" id="AMT"
                                       placeholder="Enter Amount"> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="bankguar">Bank Guarentee Dt.</label> 
                                <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="BANK_GUARENTEE_DATE" id="BANK_GUARENTEE_DATE"
                                       placeholder="Select Bank Grarentee Date"> 
                            </td>
                            <td>
                                <label for="validitydate">Bank Validity Dt.</label> 
                                <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="BANK_VALIDITY_DATE" id="BANK_VALIDITY_DATE"
                                       placeholder="Select Bank Grarentee Date"> 
                            </td>
                        </tr>

                    </table>

                    <label for="Remarks">Remarks</label> 
                    <input type="text" class="nepali-calendar form-control" style="width: 560px;" name="REMARKS" id="REMARKS"
                           placeholder="Enter Remarks"> 
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="saveSpbg()">Save</button>
            </div>
            </form>
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
                <h4 class="modal-title" id="myModalLabel">Edit Bank Deposit/Refund/Guarentee Transaction</h4>
            </div>

            <form action="<c:url value="/spbg/update" />" method="post"
                  acceptCharset="UTF-8">

                <div class="modal-body">
                    <div class="form-group">
                        <table>
                            <tr>
                                <td>
                                    <label for="transid ">Trans No.</label> 
                                    <input type="text"  style="width: 280px;" class="form-control" name="EDITTRANS_ID" id="EDITTRANS_ID"
                                           placeholder="Auto generate trans no." readonly="true"> 
                                </td>
                                <td>
                                    <label for="transdt">Trans Dt.</label> 
                                    <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EDITTRANS_DT" id="EDITTRANS_DT"
                                           placeholder="Select trans dt."> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="Transcd">Trans Cd</label> 
                                    <select  class="form-control" style="width: 280px;" name="EDITTRANS_CD" id="EDITTRANS_CD" onchange="getbanklist(this)">
                                        <option value=''>Select</option>
                                        <c:forEach var="TRANS" items="${Trans_list}">
                                            <option value="${TRANS.TRANS_CD}">${TRANS.TRANS_CD}</option>
                                        </c:forEach>
                                    </select> 
                                </td>
                                <td>
                                    <label for="spcode">Service Provider</label> 
                                    <select style="width: 280px;" name="EDITSP_CODE" id="EDITSP_CODE">
                                        <option value=''>Select :</option>
                                        <c:forEach var="SP" items="${Sp_list}">
                                            <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                        </c:forEach>
                                    </select> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="Bankcd">Bank Code</label> 
                                    <select  class="form-control" style="width: 280px;" name="EDITBANK_CD" id="EDITBANK_CD">
                                        <option value=''>Select</option>
                                        <c:forEach var="BANK" items="${Bank_list}">
                                            <option value="${BANK.BANK_CD}">${BANK.BANK_CD} ${BANK.ACCT_NO} ${BANK.ADDRESS}</option>
                                        </c:forEach>
                                    </select> 
                                </td> 
                                <td>
                                    <label for="amt">Amount.</label> 
                                    <input style="width: 280px;" class="form-control" type="number" name="EDITAMT" id="EDITAMT"
                                           placeholder="Enter Amount"> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="bankguar">Bank Guarentee Dt.</label> 
                                    <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EDITBANK_GUARENTEE_DATE" id="EDITBANK_GUARENTEE_DATE"
                                           placeholder="Select Bank Grarentee Date"> 
                                </td>
                                <td>
                                    <label for="validitydate">Bank Validity Dt.</label> 
                                    <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EDITBANK_VALIDITY_DATE" id="EDITBANK_VALIDITY_DATE"
                                           placeholder="Select Bank Grarentee Date"> 
                                </td>
                            </tr>

                        </table>

                        <label for="Remarks">Remarks</label> 
                        <input type="text" class="nepali-calendar form-control" style="width: 560px;" name="REMARKS" id="EDITREMARKS"
                               placeholder="Enter Remarks"> 
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateSpbg();"
                            class="btn btn-primary">Update</button>
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
                <h4 class="modal-title" id="myModalLabel">Bank Deposit/Guarentee/Refund delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to delete this data. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/cp/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary"
                            onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
