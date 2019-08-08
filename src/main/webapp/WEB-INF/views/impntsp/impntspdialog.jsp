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
                <h4 class="modal-title" id="myModalLabel">New upload data Define</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="transno">Trans No.</label> 
                    <input type="text"  class="form-control" name="TRANS_NO" id="TRANS_NO"
                           placeholder="Auto genereate trans no" readonly="true"> 
                    <Br/> 
                    <table>
                        <tr>
                            <c:forEach var="DAT" items="${Date_list}">
                                <td>
                                    <label for="Year">Year</label> 
                                    <input style="width: 80px;" class="form-control"
                                           value="${DAT.CUR_YEAR}" type="number" name="IMP_YEAR"
                                           id="IMP_YEAR" placeholder="Enter Year">
                                </td>
                                <td>
                                    <label for="Period">Period</label> 
                                    <select style="width: 70px;" class="form-control"
                                            name="IMP_PERIOD" id="IMP_PERIOD">
                                        <c:forEach begin="1" end="4" varStatus="loop">
                                            <c:set var="indexcat" value="0${loop.index}" />
                                            <c:if test="${indexcat==DAT.CUR_PERIOD}">
                                                <option selected value='0${loop.index}'>${loop.index}</option>
                                            </c:if>
                                            <c:if test="${indexcat!=DAT.CUR_PERIOD}">
                                                <option value='0${loop.index}'>${loop.index}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </td>

                                <td>
                                    <label for="Month">Month</label> 
                                    <select style="width: 150px;" class="form-control" name="IMP_MONTH" 
                                            id="IMP_MONTH">
                                        <option value=''>Select :</option>
                                        <c:forEach var="MONTH" items="${Mon_list}">
                                            <c:if test="${DAT.CUR_MONTH==MONTH.MONTH_CD}">
                                                <option selected value="${MONTH.MONTH_CD}">
                                                    ${MONTH.NEP_MONTH}</option>
                                                </c:if>
                                                <c:if test="${DAT.CUR_MONTH!=MONTH.MONTH_CD}">
                                                <option value="${MONTH.MONTH_CD}">
                                                    ${MONTH.NEP_MONTH}</option>
                                                </c:if>
                                            </c:forEach>
                                    </select>
                                </td>
                            </c:forEach>
                            <td>
                                <label for="Vas Services">VAS Services</label> 
                                <select style="width: 180px;" class="form-control" name="SERVICE_CODE"
                                        id="SERVICE_CODE">
                                    <option value=''>Select :</option>
                                    <c:forEach var="VASSER" items="${VASSer_list}">
                                        <option value="${VASSER.SERVICE_CODE}">
                                            ${VASSER.SERVICE_CODE}</option>
                                        </c:forEach>
                                </select>
                            </td>    

                            <td>
                                <label for="NTSP">NT/SP</label> 
                                <select style="width: 80px;" class="form-control"
                                        name="NT_SP" id=NT_SP">
                                    <option value='NT'>NT</option>
                                    <option value='SP'>SP</option>
                                </select>
                            </td>    
                        </tr>
                    </table>
                    <table>
                        <tr>
                            <td>
                                <label for="category">Category</label> 
                                <input type="text" style="width: 190px;"  class="form-control" name="CATEGORY" id="CATEGORY"
                                       placeholder="Enter Category"> 
                            <td/>
                            <td>
                                <label  for="ESMEImp">ESME Imp.</label> 
                                <input type="text" style="width: 185px;" class="form-control" name="CP_DESC" id="CP_DESC"
                                       placeholder="Enter Esme Imp Code"> 
                            </td>
                            <td>
                                <label  for="esmecode">ESME Code</label> 
                                <input type="text" style="width: 185px;" class="form-control" name="ESME_CODE" id="ESME_CODE"
                                       placeholder="Enter ESME Code"> 
                            </td>
                        </tr>
                    </table>
                    <br/>

                    <table>
                        <tr>
                            <td>
                                <label  for="Spcode">SP Code</label> 
                                <select style="width: 500px;" class="form-control" name="SP_CODE" id="SP_CODE">
                                    <option value=''>Select :</option>
                                    <c:forEach var="SP" items="${Sp_list}">
                                        <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="saveImpntsp()">Save
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
                <h4 class="modal-title" id="myModalLabel">Edit Content Provider</h4>
            </div>

            <form action="<c:url value="/cp/update" />" method="post"
                  acceptCharset="UTF-8">

                <div class="modal-body">
                    <div class="form-group">
                        <label for="transno">Trans No.</label> 
                        <input type="text"  class="form-control" name="EDITTRANS_NO" id="EDITTRANS_NO"
                               placeholder="Auto genereate trans no" readonly="true"> 
                        <Br/> 
                        <table>
                            <tr>
                                <c:forEach var="DAT" items="${Date_list}">
                                    <td>
                                        <label for="Year">Year</label> 
                                        <input style="width: 80px;" class="form-control"
                                               value="${DAT.CUR_YEAR}" type="number" name="EDITIMP_YEAR"
                                               id="EDITIMP_YEAR" placeholder="Enter Year">
                                    </td>
                                    <td>
                                        <label for="Period">Period</label> 
                                        <select style="width: 70px;" class="form-control"
                                                name="EDITIMP_PERIOD" id="EDITIMP_PERIOD">
                                            <c:forEach begin="1" end="4" varStatus="loop">
                                                <c:set var="indexcat" value="0${loop.index}" />
                                                <c:if test="${indexcat==DAT.CUR_PERIOD}">
                                                    <option selected value='0${loop.index}'>${loop.index}</option>
                                                </c:if>
                                                <c:if test="${indexcat!=DAT.CUR_PERIOD}">
                                                    <option value='0${loop.index}'>${loop.index}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>

                                    <td>
                                        <label for="Month">Month</label> 
                                        <select style="width: 150px;" class="form-control" name="EDITIMP_MONTH" 
                                                id="EDITIMP_MONTH">
                                            <option value=''>Select :</option>
                                            <c:forEach var="MONTH" items="${Mon_list}">
                                                <c:if test="${DAT.CUR_MONTH==MONTH.MONTH_CD}">
                                                    <option selected value="${MONTH.MONTH_CD}">
                                                        ${MONTH.NEP_MONTH}</option>
                                                    </c:if>
                                                    <c:if test="${DAT.CUR_MONTH!=MONTH.MONTH_CD}">
                                                    <option value="${MONTH.MONTH_CD}">
                                                        ${MONTH.NEP_MONTH}</option>
                                                    </c:if>
                                                </c:forEach>
                                        </select>
                                    </td>
                                </c:forEach>
                                <td>
                                    <label for="Vas Services">VAS Services</label> 
                                    <select style="width: 180px;" class="form-control" name="EDITSERVICE_CODE"
                                            id="EDITSERVICE_CODE">
                                        <option value=''>Select :</option>
                                        <c:forEach var="VASSER" items="${VASSer_list}">
                                            <option value="${VASSER.SERVICE_CODE}">
                                                ${VASSER.SERVICE_CODE}</option>
                                            </c:forEach>
                                    </select>
                                </td>    

                                <td>
                                    <label for="NTSP">NT/SP</label> 
                                    <select style="width: 80px;" class="form-control"
                                            name="EDITNT_SP" id=EDITNT_SP">
                                        <option value='NT'>NT</option>
                                        <option value='SP'>SP</option>
                                    </select>
                                </td>    
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td>
                                    <label for="category">Category</label> 
                                    <input type="text" style="width: 190px;"  class="form-control" name="EDITCATEGORY" id="EDITCATEGORY"
                                           placeholder="Enter Category"> 
                                <td/>
                                <td>
                                    <label  for="ESMEImp">ESME Imp.</label> 
                                    <input type="text" style="width: 185px;" class="form-control" name="EDITCP_DESC" id="EDITCP_DESC"
                                           placeholder="Enter Esme Imp Code"> 
                                </td>
                                <td>
                                    <label  for="esmecode">ESME Code</label> 
                                    <input type="text" style="width: 185px;" class="form-control" name="EDITESME_CODE" id="EDITESME_CODE"
                                           placeholder="Enter ESME Code"> 
                                </td>
                            </tr>
                        </table>
                        <br/>

                        <table>
                            <tr>
                                <td>
                                    <label  for="Spcode">SP Code</label> 
                                    <select style="width: 500px;" class="form-control" name="EDITSP_CODE" id="EDITSP_CODE">
                                        <option value=''>Select :</option>
                                        <c:forEach var="SP" items="${Sp_list}">
                                            <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                                        </c:forEach>
                                    </select> 
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" onclick="return updateCp();"
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
                <h4 class="modal-title" id="myModalLabel">Cp delete</h4>
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
