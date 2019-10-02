<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!-- new modal -->
<div class="modal fade" id="myModal" tabindex=-1 role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">New Payment</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <label for="Payment No ">Payment No.</label> 
                                <input type="text"  style="width: 380px;" class="form-control" name="PAYMENT_NO" id="PAYMENT_NO"
                                       placeholder="Auto generate Payment No." readonly="true"> 
                            </td>
                            <c:forEach var="DAT" items="${Date_list}">
                                <td>
                                    <label for="PAYMENTdt">Payment Dt.</label> 
                                    <input type="text" style="width: 380px;" value="${DAT.NEP_TODAY_DATE}" class="nepali-calendar form-control" name="PAYMENT_DT" id="PAYMENT_DT"
                                           placeholder="Select Payment Dt."> 
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <label for="spcode">Service Provider</label> 
                                <br>
                                <select class="form-control" style="width: 380px;" name="SP_CODE" id="SP_CODE" onchange="getSpDue(this.val);">
                                    <option value=''>Select :</option>
                                    <c:forEach var="SP" items="${Sp_list}">
                                        <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="Bankcd">Bank Code</label> 
                                <br>
                                <select  class="form-control" style="width: 380px;" name="BANK_CD" id="BANK_CD">
                                    <option value=''>Select</option>
                                    <c:forEach var="BANK" items="${Bank_list}">
                                        <option value="${BANK.BANK_CD}">${BANK.BANK_CD} ${BANK.ACCT_NO} ${BANK.ADDRESS}</option>
                                    </c:forEach>
                                </select> 
                            </td> 

                        </tr>
                        <tr>
                            <td>
                                <label for="ChequeNo">Cheque No.</label> 
                                <input style="width: 380px;" class="form-control" type="text" name="chequeno" id="CHEQUE_NO"
                                       placeholder="Enter Cheque number."> 
                            </td>
                            <td>
                                <label for="balamt">Bal Amount.</label> 
                                <label style="font-size: 30px"id="BALAMT"> </label>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                <label for="amt">Amount.</label> 
                                <input style="width: 380px;" class="form-control" type="number" name="AMT" id="AMT"
                                       placeholder="Enter Amount"> 
                            </td>
                            <td>
                                <label for="Createby">Create by</label> 
                                <input type="text" class="form-control" style="width: 380px;"  name="createby" id="CREATE_BY"
                                       placeholder="Payment By" readonly="true"> 
                            </td>
                        </tr>

                    </table>

                    <label for="Remarks">Remarks</label> 
                    <input type="text" class="nepali-calendar form-control" style="width: 760px;" name="REMARKS" id="REMARKS"
                           placeholder="Enter Remarks"> 
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="savePayment()">Save</button>
            </div>
            </form>
        </div>
    </div>
</div>

<!-- edit modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">

    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">View Payment</h4>
            </div>

            <form action="<c:url value="/payment/update" />" method="post"
                  acceptCharset="UTF-8">

                <div class="modal-body">
                    <div class="form-group">
                        <table>
                            <tr>
                                <td>
                                    <label for="Payment No ">Payment No.</label> 
                                    <input type="text"  style="width: 380px;" class="form-control" name="PAYMENT_NO" id="EDITPAYMENT_NO"
                                           placeholder="Auto generate Payment No." readonly="true"> 
                                </td>
                                <c:forEach var="DAT" items="${Date_list}">
                                    <td>
                                        <label for="PAYMENTdt">Payment Dt.</label> 
                                        <input type="text" style="width: 380px;" value="${DAT.NEP_TODAY_DATE}" class="nepali-calendar form-control" name="PAYMENT_DT" id="EDITPAYMENT_DT"
                                               placeholder="Select Payment Dt." readonly="true"> 
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td>
                                    <label for="spcode">Service Provider</label> 
                                    <br>
                                    <select style="width: 380px;" name="SP_CODE" id="EDITSP_CODE" readonly="true">
                                        <option value=''>Select :</option>
                                        <c:forEach var="SP" items="${Sp_list}">
                                            <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                        </c:forEach>
                                    </select> 
                                </td>
                                <td>
                                    <label for="Bankcd">Bank Code</label> 
                                    <select  class="form-control" style="width: 380px;" name="BANK_CD" id="EDITBANK_CD" readonly="true">
                                        <option value=''>Select</option>
                                        <c:forEach var="BANK" items="${Bank_list}">
                                            <option value="${BANK.BANK_CD}">${BANK.BANK_CD} ${BANK.ACCT_NO} ${BANK.ADDRESS}</option>
                                        </c:forEach>
                                    </select> 
                                </td> 
                            </tr>
                            <tr>
                                <td>
                                    <label for="ChequeNo">Cheque No.</label> 
                                    <input style="width: 380px;" class="form-control" type="text" name="chequeno" id="EDITCHEQUE_NO" readonly="true"
                                           placeholder="Enter Cheque number."> 
                                </td>
                                <td>
                                    <label for="amt">Amount.</label> 
                                    <input style="width: 380px;" class="form-control" type="number" name="AMT" id="EDITAMT"
                                           placeholder="Enter Amount" readonly="true"> 
                                </td>
                                
                            </tr>
                            <tr>
                                <td>
                                    <label for="Createby">Create by</label> 
                                    <input type="text" style="width: 380px;" class="form-control" name="createby" id="EDITCREATE_BY"
                                           placeholder="Payment By" readonly="true"> 
                                </td>
                            </tr>

                        </table>

                        <label for="Remarks">Remarks</label> 
                        <input type="text" class="nepali-calendar form-control" style="width: 760px;" name="REMARKS" id="EDITREMARKS"
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
                <h4 class="modal-title" id="myModalLabel">Cancel Payment</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to cancel this Payment. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/payment/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary"
                            onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
