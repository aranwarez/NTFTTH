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
                <h4 class="modal-title" id="myModalLabel">New Content Provider Define</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="cpcode">Cp Code</label> 
                    <input type="text"  class="form-control" name="CP_CODE" id="CP_CODE"
                           placeholder="Enter Content Provider Name" readonly="true"> 
                    <Br/> 
                    <label for="spcode">Sp Code</label> 
                    <select style="width: 500px;" name="SP_CODE" id="SP_CODE" onchange="getSPServiceList()">
                        <option value=''>Select :</option>
                        <c:forEach var="SP" items="${Sp_list}">
                            <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                        </c:forEach>
                    </select> 
                    <br/> 
                    <br/> 

                    <label for="servicecode">Service</label> 
                    <select style="width: 500px;" name="SERVICE_CODE"  id="SERVICE_CODE"  onchange="getItemTariffList()"> </select> 
                    <Br> 
                    <label for="cpname">Cp Name</label> 
                    <input type="text"   class="form-control" name="CP_NAME" id="CP_NAME"
                           placeholder="Enter Content Provider"> 
                    <label  for="esmecode">ESME Code/Busy Code</label> 
                    <input type="text" class="form-control" name="ESME_CODE" id="ESME_CODE"
                           placeholder="Enter Esme Code"> 
                    <table>
                        <tr>
                            <td>
                                <label for="CategoryMap">Package Mapping ID</label> 
                                <input style="width: 280px;" class="form-control" name="CATEGORY_MAP" id="CATEGORY_MAP"
                                       placeholder="Enter Category Map ID">
                            </td>                             
                            <td>
                                <label for="esmecodemap">ESME/BUSY Map Code</label> 
                                <input style="width: 280px;" class="form-control" name="ESME_CODE_MAP" id="ESME_CODE_MAP"
                                       placeholder="Enter ESME CODE MAP"> 
                            </td>
                        </tr>
                    </table>    
                    <table>     
                        <tr>
                            <td>
                                <label for="Srvfor">Service Name</label> 
                                <input style="width: 560px;" class="form-control" name="SRV_FOR" id="SRV_FOR"
                                       placeholder="Enter service for">
                            </td>                             
                        </tr>
                    </table>
                    <table>
                        <tr>
                            <td>
                                <label for="Rentalitem">Rental Charge Code</label> 
                                <select style="width: 188px;" class="form-control" name="RENTAL_ITEM_CODE"  id="RENTAL_ITEM_CODE"> </select> 
                            </td>                             
                            <td>
                                <label for="VPNitem">VPN Charge Code</label> 
                                <select style="width: 188px;" class="form-control" name="VPN_ITEM_CODE"  id="VPN_ITEM_CODE"> </select> 
                            </td>                            
                            <td>
                                <label for="Space">Space Charge Code</label> 
                                <select style="width: 188px;" class="form-control" name="SPACE_ITEM_CODE"  id="SPACE_ITEM_CODE"> </select> 

                            </td>                             
                        </tr>
                    </table>    
                    <table>     
                        <tr>
                            <td>
                                <label for="Package">Package</label> 
                                <select style="width: 280px;" class="form-control" name="PACKAGE_TYPE" id="PACKAGE_TYPE">
                                    <option value=''>Select</option>
                                    <c:forEach var="PACKAGE" items="${Package_list}">
                                        <option value="${PACKAGE.PACKAGE_TYPE}">${PACKAGE.PACKAGE_TYPE} ${PACKAGE.DESCRIPTION}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                            <td>
                                <label for="Stream">Stream</label> 
                                <select style="width: 280px;" class="form-control" name="STREAM_TYPE" id="STREAM_TYPE">
                                    <option value=''>Select</option>
                                    <c:forEach var="STREAM" items="${Stream_list}">
                                        <option value="${STREAM.STREAM_TYPE}">${STREAM.STREAM_TYPE} ${STREAM.DESCRIPTION}</option>
                                    </c:forEach>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="startdt">Start Dt. / Service Live Dt</label> 
                                <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="START_DT" id="START_DT"
                                       placeholder="Enter start dt. / Service Live Dt"> 

                            </td>
                            <td>
                                <label for="enddt">End Dt.</label> 
                                <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="END_DT"
                                       id="END_DT" placeholder="Enter end dt."> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="sharingtype">Sharing</label> 
                                <select style="width: 280px;" class="form-control" name="SHARING_TYPE" id="SHARING_TYPE">
                                    <option value='Y'>Y</option>
                                    <option value='N'>N</option>
                                </select> 
                            </td>                             
                            <td>
                                <label for="sharentper">Sharing(%)</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="SHARE_NT_PER" id="SHARE_NT_PER"
                                       placeholder="Enter Sharing Per"> 
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="afsflag">AFS</label> 
                                <select style="width: 280px;" class="form-control" name="AFS_FLAG" id="AFS_FLAG">
                                    <option value='N'>N</option>
                                    <option value='Y'>Y</option>
                                </select> 
                            </td>                             
                            <td>
                                <label for="minqty">Min. Qty.</label> 
                                <input style="width: 280px;" class="form-control" type="number" name="MIN_QTY" id="MIN_QTY" placeholder="Enter Min Qty">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="saveCp()">Save
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
                        <label for="cpcode">Cp Code</label> 
                        <input type="text" class="form-control" name="CP_CODE" id="EDITCP_CODE"
                               placeholder="Enter Content Provider Name" readonly="true"> 
                        <br/> 
                        <label for="spcode">Sp Code</label> 
                        <select style="width: 500px;" name="EDITSP_CODE" id="EDITSP_CODE" onchange="getSPServiceList()">
                            <option value=''>Select :</option>
                            <c:forEach var="SP" items="${Sp_list}">
                                <option value="${SP.SP_CODE}">${SP.SP_NAME} ${SP.SP_CODE}</option>
                            </c:forEach>
                        </select> 
                        <br/> 
                        <br/> 

                        <label for="servicecode">Service</label> 
                        <select style="width: 500px;" name="EDITSERVICE_CODE"  id="EDITSERVICE_CODE" onchange="getItemTariffList()"> </select> 
                        <Br> 
                        <label for="cpname">Cp Name</label> 
                        <input type="text"   class="form-control" name="EDITCP_NAME" id="EDITCP_NAME"
                               placeholder="Enter Content Provider"> 
                        <label  for="esmecode">ESME Code/Busy Code</label> 
                        <input type="text" class="form-control" name="EDITESME_CODE" id="EDITESME_CODE"
                               placeholder="Enter Esme Code"> 
                        <table>
                            <tr>
                                <td>
                                    <label for="CategoryMap">Package Mapping ID</label> 
                                    <input style="width: 280px;" class="form-control" name="EDITCATEGORY_MAP" id="EDITCATEGORY_MAP"
                                           placeholder="Enter Category Map ID">
                                </td>                             
                                <td>
                                    <label for="esmecodemap">ESME/BUSY Map Code</label> 
                                    <input style="width: 280px;" class="form-control" name="ESME_CODE_MAP" id="EDITESME_CODE_MAP"
                                           placeholder="Enter ESME CODE MAP"> 
                                </td>
                            </tr>
                        </table>    
                        <table>
                            <tr>
                                <td>
                                    <label for="Srvfor">Service Name</label> 
                                    <input style="width: 560px;" class="form-control" name="EDITSRV_FOR" id="EDITSRV_FOR"
                                           placeholder="Enter service for">
                                </td>                             
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td>
                                    <label for="Rentalitem">Rental Charge Code</label> 
                                    <select style="width: 188px;" class="form-control" name="EDITRENTAL_ITEM_CODE"  id="EDITRENTAL_ITEM_CODE"> </select> 
                                </td>                             
                                <td>
                                    <label for="VPNitem">VPN Charge Code</label> 
                                    <select style="width: 188px;" class="form-control" name="EDITVPN_ITEM_CODE"  id="EDITVPN_ITEM_CODE"> </select> 
                                </td>                            
                                <td>
                                    <label for="Space">Space Charge Code</label> 
                                    <select style="width: 188px;" class="form-control" name="EDITSPACE_ITEM_CODE"  id="EDITSPACE_ITEM_CODE"> </select> 

                                </td>                             
                            </tr>
                        </table>    
                        <table>
                            <tr>
                                <td>
                                    <label for="Package">Package</label> 
                                    <select style="width: 280px;" class="form-control" name="EDITPACKAGE_TYPE" id="EDITPACKAGE_TYPE">
                                        <option value=''>Select</option>
                                        <c:forEach var="PACKAGE" items="${Package_list}">
                                            <option value="${PACKAGE.PACKAGE_TYPE}">${PACKAGE.PACKAGE_TYPE} ${PACKAGE.DESCRIPTION}</option>
                                        </c:forEach>
                                    </select> 
                                </td>
                                <td>
                                    <label for="Stream">Stream</label> 
                                    <select style="width: 280px;" class="form-control" name="EDITSTREAM_TYPE" id="EDITSTREAM_TYPE">
                                        <option value=''>Select</option>
                                        <c:forEach var="STREAM" items="${Stream_list}">
                                            <option value="${STREAM.STREAM_TYPE}">${STREAM.STREAM_TYPE} ${STREAM.DESCRIPTION}</option>
                                        </c:forEach>
                                    </select> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="startdt">Start Dt. / Service Live Dt</label> 
                                    <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EDITSTART_DT" id="EDITSTART_DT"
                                           placeholder="Enter start dt./ Service Live Dt"> 

                                </td>
                                <td>
                                    <label for="enddt">End Dt.</label> 
                                    <input type="text" style="width: 280px;" class="nepali-calendar form-control" name="EDITEND_DT"
                                           id="EDITEND_DT" placeholder="Enter end dt."> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="sharingtype">Sharing</label> 
                                    <select style="width: 280px;" class="form-control" name="EDITSHARING_TYPE" id="EDITSHARING_TYPE">
                                        <option value='Y'>Y</option>
                                        <option value='N'>N</option>
                                    </select> 
                                </td>                             
                                <td>
                                    <label for="sharentper">Sharing(%)</label> 
                                    <input style="width: 280px;" class="form-control" type="number"name="EDITSHARE_NT_PER" id="EDITSHARE_NT_PER"
                                           placeholder="Enter Sharing Per"> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="afsflag">AFS</label> 
                                    <select style="width: 280px;" class="form-control" name="EDITAFS_FLAG" id="EDITAFS_FLAG">
                                        <option value='N'>N</option>
                                        <option value='Y'>Y</option>
                                    </select> 
                                </td>                             
                                <td>
                                    <label for="minqty">Min. Qty.</label> 
                                    <input style="width: 280px;" class="form-control" type="number" name="EDITMIN_QTY" id="EDITMIN_QTY" placeholder="Enter Min Qty">
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
