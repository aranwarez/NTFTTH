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
                <h4 class="modal-title" id="myModalLabel">New Item Define</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="name">Item Code</label> 
                    <input type="text"
                           class="form-control" name="ITEM_CODE" id="ITEM_CODE" placeholder="Enter Item Code">
                </div>
                <div class="form-group">
                    <label for="name">DESCRIPTION</label> 
                    <input type="text"
                           class="form-control" name="DESCRIPTION" id="DESCRIPTION" placeholder="Enter description">
                </div>
                <div class="form-group">
                    <label for="name">Category Code</label> 
                    <select style="width: 280px;" class="form-control" name="CATEGORY_CODE" id="CATEGORY_CODE">
                        <option value=''>Select</option>
                        <c:forEach var="CAT" items="${ItemCategory_list}">
                            <option value="${CAT.CATEGORY_CODE}">${CAT.CATEGORY_CODE} ${CAT.DESCRIPTION}</option>
                        </c:forEach>
                    </select> 
                </div>
                <div class="form-group">
                    <label for="name">Recurring Flag</label> 
                    <select style="width: 280px;" class="form-control" name="IS_RECURRING" id="IS_RECURRING">
                        <option value='Y'>Y</option>
                        <option value='N'>N</option>
                    </select> 
                </div>
                <div class="form-group">
                    <label for="name">TSC</label> 
                    <input type="number"
                           class="form-control" name="TAXABLE_AMT" id="TAXABLE_AMT" placeholder="Enter TSC">
                </div>
                <div class="form-group">
                    <label for="name">VAT</label> 
                    <input type="number"
                           class="form-control" name="VATABLE_AMT" id="VATABLE_AMT" placeholder="Enter VAT">
                </div>
                <div class="form-group">
                    <label for="name">OWN</label> 
                    <input type="number"
                           class="form-control" name="OWN_AMT" id="OWN_AMT" placeholder="Enter OWN">
                </div>
                <div class="form-group">
                    <label for="name">Active Flag</label> 
                    <select style="width: 280px;" class="form-control" name="ACTIVE_FLAG" id="ACTIVE_FLAG">
                        <option value='Y'>Y</option>
                        <option value='N'>N</option>
                    </select> 
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="saveItem()">Save changes</button>
            </div>
            </form>
        </div>
    </div>
</div>

<!-- edit modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">

    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Edit Item</h4>
            </div>

            <form action="<c:url value="/item/update" />" method="post"
                  acceptCharset="UTF-8">

                <div class="modal-body">
                    <div class="form-group">
                        <label for="name">Item Code</label> 
                        <input type="text"
                               class="form-control" name="EDITITEM_CODE" id="EDITITEM_CODE" readonly="true" placeholder="Enter Item Code">
                    </div>
                    <div class="form-group">
                        <label for="name">DESCRIPTION</label> 
                        <input type="text"
                               class="form-control" name="EDITDESCRIPTION" id="EDITDESCRIPTION" placeholder="Enter description">
                    </div>
                    <div class="form-group">
                        <label for="name">Category Code</label> 
                        <select style="width: 280px;" class="form-control" name="EDITCATEGORY_CODE" id="EDITCATEGORY_CODE">
                            <option value=''>Select</option>
                            <c:forEach var="CAT" items="${ItemCategory_list}">
                                <option value="${CAT.CATEGORY_CODE}">${CAT.CATEGORY_CODE} ${CAT.DESCRIPTION}</option>
                            </c:forEach>
                        </select> 
                    </div>
                    <div class="form-group">
                        <label for="name">Recurring Flag</label> 
                        <select style="width: 280px;" class="form-control" name="EDITIS_RECURRING" id="EDITIS_RECURRING">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select> 
                    </div>
                    <div class="form-group">
                        <label for="name">TSC</label> 
                        <input type="number"
                               class="form-control" name="EDITTAXABLE_AMT" id="EDITTAXABLE_AMT" placeholder="Enter TSC">
                    </div>
                    <div class="form-group">
                        <label for="name">VAT</label> 
                        <input type="number"
                               class="form-control" name="EDITVATABLE_AMT" id="EDITVATABLE_AMT" placeholder="Enter VAT">
                    </div>
                    <div class="form-group">
                        <label for="name">OWN</label> 
                        <input type="number"
                               class="form-control" name="EDITOWN_AMT" id="EDITOWN_AMT" placeholder="Enter OWN">
                    </div>
                    <div class="form-group">
                        <label for="name">Active Flag</label> 
                        <select style="width: 280px;" class="form-control" name="EDITACTIVE_FLAG" id="EDITACTIVE_FLAG">
                            <option value='Y'>Y</option>
                            <option value='N'>N</option>
                        </select> 
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateItem();" class="btn btn-primary">Update</button>
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
                <h4 class="modal-title" id="myModalLabel">Item delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to delete this data. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/item/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary" onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
