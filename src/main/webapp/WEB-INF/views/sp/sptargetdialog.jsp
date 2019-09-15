<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>


<!--------------------------------------------------->
<!-- This block is for Target Modal Dialog -->
<!--------------------------------------------------->



<!-- Action view modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">SP TARGET</h4>
            </div>

            <div class="modal-body">
                <button type="button" class="btn btn-primary" data-toggle="modal"
                        data-target="#addModal">Add</button>
                <button type="button" onclick="getsptargetList()"
                        class="btn btn-primary" data-toggle="modal"
                        data-target="#getSPTargetModal">Edit</button>
                <button type="button" class="btn btn-primary" data-toggle="modal"
                        data-target="#viewSPtargetModal">View</button>
            </div>

        </div>
    </div>
</div>

<!-- Add modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">New Service Provider
                    Target</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="spcode">SP_CODE</label> <input type="text"
                                                               class="form-control" name="SP_CODE" id="SP_CODE"
                                                               readonly="readonly" placeholder="Enter Service Provider">
                    <label for="SERVICE_CODE">Service</label> 
                    <select class="form-control" name="SERVICE_CODE" id="SERVICE_CODE">
                        <option value=''>Select :</option>
                        <c:forEach var="VASSER" items="${VASSer_list}">
                            <option value="${VASSER.SERVICE_CODE}">
                                ${VASSER.SERVICE_CODE}</option>
                            </c:forEach>
                    </select>
                    <label for="spname">Revenue Target</label> 
                    <input type="number"
                           class="form-control" name="REV_TARGET" id="REV_TARGET"
                           placeholder="Enter Revinue Target number"> 
                    <label
                        for="accoutno">Minimum Guarentee</label> <input type="number"
                        class="form-control" name="MINIMUM_GUARENTEE" min=0
                        id="MINIMUM_GUARENTEE"
                        placeholder="Enter MINIMUM GUARENTEE amount"> <label
                        for="TerminateDt">Effective Date </label> <input type="text"
                        class="nepali-calendar" name="EFFECTIVE_DT" id="EFFECTIVE_DT">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary"
                        onclick="savesptarget()">Save Target</button>
            </div>
        </div>
    </div>
</div>


<!-- Listing SPTarget modal -->
<!-- Get List modal -->
<div class="modal fade" id="getSPTargetModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
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
                <div class="col-xs-12 table-responsive">
                    <table class="table table-striped" id="sptarget">
                        <thead>
                            <tr>
                                <th>Vas Service</th>
                                <th>EFFECTIVE_DT</th>
                                <th>REVENUE_TARGET</th>
                                <th>MINIMUM_GUARENTEE</th>
                                <th>UPDATE_BY</th>
                                <th>UPDATE_DT</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<!-- Edit modal -->
<div class="modal fade" id="editSPTargetModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="editSPTargetModalLabel">New Service Provider Define</h4>
            </div>

            <div class="modal-body" >
                <div class="form-group">
                    <label for="spcode">SP_CODE</label> 
                    <input type="text"
                           class="form-control" name="SP_CODE" id="eSP_CODE"
                           readonly="readonly" placeholder="Enter Service Provider">
                    <label for="SERVICE_CODE">SERVICE_CODE</label> 
                    <select class="form-control"
                            name="eSERVICE_CODE" id="eSERVICE_CODE">
                        <option value=''>Select :</option>
                        <c:forEach var="VASSER" items="${VASSer_list}">
                            <option value="${VASSER.SERVICE_CODE}">
                                ${VASSER.SERVICE_CODE}</option>
                            </c:forEach>
                    </select>    
                    <label for="spname">Revenue Target</label> <input type="number"
                                                                      class="form-control" name="REV_TARGET" id="eREV_TARGET"
                                                                      placeholder="Enter Revinue Target number"> <label
                                                                      for="accoutno">Minimum Guarentee</label> <input type="number"
                                                                      class="form-control" name="MINIMUM_GUARENTEE" min=0
                                                                      id="eMINIMUM_GUARENTEE"
                                                                      placeholder="Enter MINIMUM GUARENTEE amount"> <label
                                                                      for="TerminateDt">Effective Date </label> <input type="text"
                                                                      class="nepali-calendar" name="EFFECTIVE_DT" id="eEFFECTIVE_DT">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary"
                        onclick="updateSPTarget()">Update Target</button>
            </div>
        </div>
    </div>
</div>

<!-- End of Target Modal -->


<!--------------------------------------------------->
<!-- This block is for Service Dialog -->
<!--------------------------------------------------->

<!--Action modal -->
<div class="modal fade" id="ServiceSpActionModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">SP Service</h4>
            </div>

            <div class="modal-body">
                <button type="button" class="btn btn-primary" data-toggle="modal"
                        data-target="#addServiceModal">Add</button>
                <button type="button" onclick="getSPServiceList()"
                        class="btn btn-primary" data-toggle="modal"
                        data-target="#getSPServiceModal">Edit</button>
                <button type="button" class="btn btn-primary" data-toggle="modal"
                        data-target="#viewSPServiceModal">View</button>
            </div>

        </div>
    </div>
</div>


<!-- Add Service modal -->
<div class="modal fade" id="addServiceModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">New Service Provider
                    Service</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="spcode">SP_CODE</label> <input type="text"
                                                               class="form-control" name="SP_CODE" id="sSP_CODE"
                                                               readonly="readonly" placeholder="Enter Service Provider">
                    <label for="SERVICE_CODE">SERVICE_CODE</label> 
                    <select class="form-control"
                            name="sSERVICE_CODE" id="sSERVICE_CODE">
                        <option value=''>Select :</option>
                        <c:forEach var="VASSER" items="${VASSer_list}">
                            <option value="${VASSER.SERVICE_CODE}">
                                ${VASSER.SERVICE_CODE}</option>
                            </c:forEach>
                    </select> <label for="ACTIVE_FLAG">ACTIVE FLAG</label> 
                    <select class="form-control"
                            style="width: 50px;" name="ACTIVE_FLAG" id="sACTIVE_FLAG">
                        <option value='N'>N</option>
                        <option value='Y'>Y</option>
                    </select> 
                    <br> 
                    <label for="ACTIVE_DT">Contract Dt</label> 
                    <input
                        type="text" class="nepali-calendar" name="ACTIVE_DT"
                        id="sACTIVE_DT"> 
                    <label for=DEACTIVATE_DT>Effective/Commercial Dt </label> 
                    <input type="text" class="nepali-calendar"
                           name="DEACTIVATE_DT" id="sDEACTIVATE_DT">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary"
                        onclick="savespservice()">Save Service</button>
            </div>
        </div>
    </div>
</div>


<!-- Edit modal -->
<div class="modal fade" id="getSPServiceModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">New Service Provider
                </h4>
            </div>

            <div class="modal-body">
                <div class="col-xs-12 table-responsive">
                    <table class="table table-striped" id="spservice">
                        <thead>
                            <tr>
                                <th>SERVICE_CODE</th>
                                <th>ACTIVE_FLAG</th>
                                <th>Contract Dt</th>
                                <th>Effective/Commericial Dt</th>
                                <th>UPDATE_DT</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<!-- Edit modal -->
<div class="modal fade" id="editSPServiceModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="editSPTargetModalLabel">New Service
                    Provider</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label for="spcode">SP_CODE</label> <input type="text"
                                                               class="form-control" name="SP_CODE" id="esSP_CODE"
                                                               readonly="readonly" placeholder="Enter Service Provider">
                    <label for="SERVICE_CODE">SERVICE_CODE</label> <select
                        name="sSERVICE_CODE" id="esSERVICE_CODE">
                        <option value=''>Select :</option>
                        <c:forEach var="VASSER" items="${VASSer_list}">
                            <option value="${VASSER.SERVICE_CODE}">
                                ${VASSER.SERVICE_CODE}</option>
                            </c:forEach>
                    </select> <label for="ACTIVE_FLAG">ACTIVE FLAG</label> <select
                        style="width: 50px;" name="ACTIVE_FLAG" id="esACTIVE_FLAG">
                        <option value='N'>N</option>
                        <option value='Y'>Y</option>
                    </select> <br> <label for="ACTIVE_DT">Contract Dt</label> <input
                        type="text" class="nepali-calendar" name="ACTIVE_DT"
                        id="esACTIVE_DT"> <label for=DEACTIVATE_DT>Effective/Commercial Dt </label> <input type="text" class="nepali-calendar"
                                         name="DEACTIVATE_DT" id="esDEACTIVATE_DT">


                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary"
                        onclick="updateSPService()">Update SP Service</button>
            </div>
        </div>
    </div>
</div>




