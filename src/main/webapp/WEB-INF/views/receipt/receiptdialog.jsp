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
                <h4 class="modal-title" id="myModalLabel">New Receipt</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <table>
                        <tr>
                            <td>
                                <label for="Receipt No ">Receipt No.</label> 
                                <input type="text"  style="width: 280px;" class="form-control" name="RECEIPT_NO" id="RECEIPT_NO"
                                       placeholder="Auto generate Receipt No." readonly="true"> 
                            </td>
                            <c:forEach var="DAT" items="${Date_list}">
                                <td>
                                    <label for="receiptdt">Receipt Dt.</label> 
                                    <input type="text" style="width: 280px;" value="${DAT.NEP_TODAY_DATE}" class="nepali-calendar form-control" name="RECEIPT_DT" id="RECEIPT_DT"
                                           placeholder="Select Receipt Dt."> 
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <label for="spcode">Service Provider</label> 
                                <select style="width: 280px;" name="SP_CODE" id="SP_CODE">
                                    <option value=''>Select :</option>
                                    <c:forEach var="SP" items="${Sp_list}">
                                        <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="Bankcd">Bank Code</label> 
                                <select  class="form-control" style="width: 280px;" name="BANK_CD" id="BANK_CD">
                                    <option value=''>Select</option>
                                    <c:forEach var="BANK" items="${Bank_list}">
                                        <option value="${BANK.BANK_CD}">${BANK.BANK_CD} ${BANK.ACCT_NO} ${BANK.ADDRESS}</option>
                                    </c:forEach>
                                </select> 
                            </td> 
                            
                        </tr>
                        <tr>
                            <td>
                                <label for="BankName">Bank Name</label> 
                                <select  class="form-control" style="width: 280px;" name="BANK_NAME" id="BANK_NAME">
                                    <option value=''>Select</option>
                                    <c:forEach var="OBANK" items="${OBank_list}">
                                        <option value="${OBANK.BANK_CODE}">${OBANK.BANK_CODE} ${OBANK.BANK_NAME}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="ChequeNo">Cheque No.</label> 
                                <input style="width: 280px;" class="form-control" type="text" name="chequeno" id="CHEQUE_NO"
                                       placeholder="Enter Cheque number."> 
                            </td>
                            
                        </tr>
                        <tr>
                            <td>
                                <label for="amt">Amount.</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="AMT" id="AMT"
                                       placeholder="Enter Amount"> 
                            </td>
                            <td>
                                <label for="Createby">Create by</label> 
                                <input type="text" class="form-control" style="width: 280px;"  name="createby" id="CREATE_BY"
                                       placeholder="Receipt By" readonly="true"> 
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
                <button type="submit" class="btn btn-primary" onclick="saveReceipt()">Save</button>
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
                <h4 class="modal-title" id="myModalLabel">View Receipt</h4>
            </div>

            <form action="<c:url value="/receipt/update" />" method="post"
                  acceptCharset="UTF-8">

                <div class="modal-body">
                    <div class="form-group">
                        <table>
                            <tr>
                            <td>
                                <label for="Receipt No ">Receipt No.</label> 
                                <input type="text"  style="width: 280px;" class="form-control" name="RECEIPT_NO" id="EDITRECEIPT_NO"
                                       placeholder="Auto generate Receipt No." readonly="true"> 
                            </td>
                            <c:forEach var="DAT" items="${Date_list}">
                                <td>
                                    <label for="receiptdt">Receipt Dt.</label> 
                                    <input type="text" style="width: 280px;" value="${DAT.NEP_TODAY_DATE}" class="nepali-calendar form-control" name="RECEIPT_DT" id="EDITRECEIPT_DT"
                                           placeholder="Select Receipt Dt." readonly="true"> 
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <label for="spcode">Service Provider</label> 
                                <select style="width: 280px;" name="SP_CODE" id="EDITSP_CODE" readonly="true">
                                    <option value=''>Select :</option>
                                    <c:forEach var="SP" items="${Sp_list}">
                                        <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="Bankcd">Bank Code</label> 
                                <select  class="form-control" style="width: 280px;" name="BANK_CD" id="EDITBANK_CD" readonly="true">
                                    <option value=''>Select</option>
                                    <c:forEach var="BANK" items="${Bank_list}">
                                        <option value="${BANK.BANK_CD}">${BANK.BANK_CD} ${BANK.ACCT_NO} ${BANK.ADDRESS}</option>
                                    </c:forEach>
                                </select> 
                            </td> 
                            
                        </tr>
                        <tr>
                            <td>
                                <label for="BankName">Bank Name</label> 
                                <select  class="form-control" style="width: 280px;" name="BANK_NAME" id="EDITBANK_NAME" readonly="true">
                                    <option value=''>Select</option>
                                    <c:forEach var="OBANK" items="${OBank_list}">
                                        <option value="${OBANK.BANK_CODE}">${OBANK.BANK_CODE} ${OBANK.BANK_NAME}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="ChequeNo">Cheque No.</label> 
                                <input style="width: 280px;" class="form-control" type="text" name="chequeno" id="EDITCHEQUE_NO" readonly="true"
                                       placeholder="Enter Cheque number."> 
                            </td>
                            
                        </tr>
                        <tr>
                            <td>
                                <label for="amt">Amount.</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="AMT" id="EDITAMT"
                                       placeholder="Enter Amount" readonly="true"> 
                            </td>
                            <td>
                                <label for="Createby">Create by</label> 
                                <input type="text" style="width: 280px;" class="form-control" name="createby" id="EDITCREATE_BY"
                                       placeholder="Receipt By" readonly="true"> 
                            </td>
                        </tr>

                        </table>

                        <label for="Remarks">Remarks</label> 
                        <input type="text" class="nepali-calendar form-control" style="width: 560px;" name="REMARKS" id="EDITREMARKS"
                               placeholder="Enter Remarks" readonly="true"> 
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
                <h4 class="modal-title" id="myModalLabel">Cancel Receipt</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to cancel this receipt. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/receipt/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary"
                            onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
