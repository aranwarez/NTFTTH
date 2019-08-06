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
                <h4 class="modal-title" id="myModalLabel">New VAS Service Tariff Define</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="Transid">Trans ID</label> 
                    <input type="text"  class="form-control" name="TRANS_ID" id="TRANS_ID"
                           placeholder="Trans ID" readonly="true"> 
                    <Br/> 
                    <table>
                        <tr>
                            <td>
                                <label for="service">Service</label> 
                                <select style="width: 280px;" class="form-control" name="SERVICE_CODE" id="SERVICE_CODE">
                                    <option value=''>Select</option>
                                    <c:forEach var="SER" items="${VASSer_list}">
                                        <option value="${SER.SERVICE_CODE}">${SER.SERVICE_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="Package">Package Type</label> 
                                <select style="width: 280px;" class="form-control" name="PACKAGE_TYPE" id="PACKAGE_TYPE">
                                    <option value=''>Select</option>
                                    <c:forEach var="PACKAGE" items="${Package_list}">
                                        <option value="${PACKAGE.PACKAGE_TYPE}">${PACKAGE.PACKAGE_TYPE} ${PACKAGE.DESCRIPTION}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="Rate">Rate</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="RATE" id="RATE" placeholder="Enter Rate">
                            </td>
                            <td>
                                <label for="effectivedt">Effective Dt.</label> 
                                <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EFFECTIVE_DT"
                                       id="EFFECTIVE_DT" placeholder="Enter Effective Date"> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="RangeFrom">Range From</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="RANGE_FROM" id="RANGE_FROM" placeholder="Enter Range From">
                            </td>
                            <td>
                                <label for="RangeTo">To</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="RANGE_TO" id="RANGE_TO" placeholder="Enter Range To">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="MoMtRatio">1MO:?MT</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="MO_MT_RATIO" id="MO_MT_RATIO" placeholder="Enter Range To">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="saveWapapptariff()">Save
                    changes</button>
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
                <h4 class="modal-title" id="myModalLabel">Edit VAS Service Tariff</h4>
            </div>

            <form action="<c:url value="/wapapptariff/update" />" method="post"
                  acceptCharset="UTF-8">

                <div class="modal-body">
                    <div class="form-group">
                        <label for="transid">Trans ID</label> 
                        <input type="text" class="form-control" name="TRANS_ID" id="EDITTRANS_ID"
                               placeholder="TransID" readonly="true"> 
                        <br/> 
                        <table>
                        <tr>
                            <td>
                                <label for="service">Service</label> 
                                <select style="width: 280px;" class="form-control" name="EDITSERVICE_CODE" id="EDITSERVICE_CODE">
                                    <option value=''>Select</option>
                                    <c:forEach var="SER" items="${VASSer_list}">
                                        <option value="${SER.SERVICE_CODE}">${SER.SERVICE_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="Package">Package Type</label> 
                                <select style="width: 280px;" class="form-control" name="EDITPACKAGE_TYPE" id="EDITPACKAGE_TYPE">
                                    <option value=''>Select</option>
                                    <c:forEach var="PACKAGE" items="${Package_list}">
                                        <option value="${PACKAGE.PACKAGE_TYPE}">${PACKAGE.PACKAGE_TYPE} ${PACKAGE.DESCRIPTION}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="Rate">Rate</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="EDITRATE" id="EDITRATE" placeholder="Enter Rate">
                            </td>
                            <td>
                                <label for="effectivedt">Effective Dt.</label> 
                                <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EDITEFFECTIVE_DT"
                                       id="EDITEFFECTIVE_DT" placeholder="Enter Effective Date"> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="RangeFrom">Range From</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="EDITRANGE_FROM" id="EDITRANGE_FROM" placeholder="Enter Range From">
                            </td>
                            <td>
                                <label for="RangeTo">To</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="EDITRANGE_TO" id="EDITRANGE_TO" placeholder="Enter Range To">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="MoMtRatio">1MO:?MT</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="EDITMO_MT_RATIO" id="EDITMO_MT_RATIO" placeholder="Enter Range To">
                            </td>
                        </tr>
                    </table>
                        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateWapapptariff();"
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
                <h4 class="modal-title" id="myModalLabel">Wap App Tariff delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to delete this data. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/wapapptariff/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary"
                            onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
