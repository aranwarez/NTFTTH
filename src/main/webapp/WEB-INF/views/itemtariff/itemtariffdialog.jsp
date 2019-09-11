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
                <h4 class="modal-title" id="myModalLabel">New Item Tariff Define</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="Transid">Trans ID</label> 
                    <input type="text"  class="form-control" name="ID_NO" id="ID_NO"
                           placeholder="Trans ID" readonly="true"> 
                    <Br/> 
                    <table>
                        <tr>
                            <td>
                                <label for="item">Item</label> 
                                <select style="width: 280px;" class="form-control" name="ITEM_CODE" id="ITEM_CODE">
                                    <option value=''>Select</option>
                                    <c:forEach var="ITEM" items="${Item_list}">
                                        <option value="${ITEM.ITEM_CODE}"> ${ITEM.ITEM_CODE} ${ITEM.DESCRIPTION} </option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="service">Service</label> 
                                <select style="width: 280px;" class="form-control" name="SERVICE_CODE" id="SERVICE_CODE">
                                    <option value=''>Select</option>
                                    <c:forEach var="SER" items="${VASSer_list}">
                                        <option value="${SER.SERVICE_CODE}">${SER.SERVICE_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="Rate">Rate</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="AMOUNT" id="AMOUNT" placeholder="Enter Rate">
                            </td>
                            <td>
                                <label for="effectivedt">Effective Dt.</label> 
                                <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EFFECTIVE_DT"
                                       id="EFFECTIVE_DT" placeholder="Enter Effective Date"> 
                            </td>
                        </tr>
                        <tr>
                        <label for="name">Active Flag</label> 
                        <select style="width: 280px;" class="form-control" name="ACTIVE_FLAG" id="ACTIVE_FLAG">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select> 
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="saveItemtariff()">Save
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
                <h4 class="modal-title" id="myModalLabel">Edit Item Tariff</h4>
            </div>

            <form action="<c:url value="/itemtariff/update" />" method="post"
                  acceptCharset="UTF-8">

                <div class="modal-body">
                    <div class="form-group">
                        <label for="Transid">Trans ID</label> 
                        <input type="text"  class="form-control" name="EDITID_NO" id="EDITID_NO"
                               placeholder="Trans ID" readonly="true"> 
                        <Br/> 
                        <table>
                            <tr>
                                <td>
                                    <label for="item">Item</label> 
                                    <select style="width: 280px;" class="form-control" name="EDITITEM_CODE" id="EDITITEM_CODE">
                                        <option value=''>Select</option>
                                        <c:forEach var="ITEM" items="${Item_list}">
                                            <option value="${ITEM.ITEM_CODE}"> ${ITEM.ITEM_CODE} ${ITEM.DESCRIPTION} </option>
                                        </c:forEach>
                                    </select> 
                                </td>
                                <td>
                                    <label for="service">Service</label> 
                                    <select style="width: 280px;" class="form-control" name="EDITSERVICE_CODE" id="EDITSERVICE_CODE">
                                        <option value=''>Select</option>
                                        <c:forEach var="SER" items="${VASSer_list}">
                                            <option value="${SER.SERVICE_CODE}">${SER.SERVICE_CODE}</option>
                                        </c:forEach>
                                    </select> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="Rate">Rate</label> 
                                    <input style="width: 280px;" class="form-control" type="number" name="EDITAMOUNT" id="EDITAMOUNT" placeholder="Enter Rate">
                                </td>
                                <td>
                                    <label for="effectivedt">Effective Dt.</label> 
                                    <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EDITEFFECTIVE_DT"
                                           id="EDITEFFECTIVE_DT" placeholder="Enter Effective Date"> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="name">Active Flag</label> 
                                    <select style="width: 280px;" class="form-control" name="EDITACTIVE_FLAG" id="EDITACTIVE_FLAG">
                                        <option value='Y'>Y</option>
                                        <option value='N'>N</option>
                                    </select> 
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateItemtariff();"
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
                <h4 class="modal-title" id="myModalLabel">Item Tariff delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to delete this data. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/itemtariff/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary"
                            onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
