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
                <h4 class="modal-title" id="myModalLabel">Forward Trouble Ticket To Another Team</h4>
            </div>

               <div class="modal-body">
               
               
                <div class="form-group">
                        <label for="name">Remarks</label> 
                        
                        <textarea  id="remarks" class="form-control" rows="3" placeholder="Enter Remarks"></textarea>
                       </div>
                    
                   
                    
                    <div class="form-group">
                        <label for="jsonlist">Team</label> 
                        <select  name="TEAM_CODE" id="TEAM_CODE"  onchange="getSubTeamList()" class="form-control chosen-select" style="width:350px;">
                        <option value="">Select Team</option>
                         <c:forEach var="user" items="${teamlist}">                        
                            <option value='${user.TEAM_CODE}'>${user.DESCRIPTION}</option>
                          
                            
                            </c:forEach>
                            
                        </select>        
                    </div>
                    
                    <div class="form-group">
                        <label for="jsonlist">Sub Team</label> 
                        <select  name="SUB_TEAM_CODE" id="SUB_TEAM_CODE" class="form-control chosen-select" style="width:350px;">
                        
                       <option value="">Select Sub-Team</option>
                        </select>        
                    </div>
                    
                     
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" onclick="return ForwardTeam()">Forward Team</button>
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
                <h4 class="modal-title" id="myModalLabel">Closing Trouble Ticket</h4>
            </div>

            <div class="modal-body">
                     <div class="form-group">
                        <label for="name">Remarks</label> 
                        
                        <textarea  id="closeremarks" class="form-control" rows="3" placeholder="Enter Remarks"></textarea>
                       </div>
           
            </div>

            <div class="modal-footer">
                <form action="#" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary" onclick="CloseTicket()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- delete all modal -->

<div class="modal fade" id="deleteallModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">

    <div class="modal-dialog" role="document">

        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Closing All Solved Trouble Ticket</h4>
            </div>

            <div class="modal-body">
                     <div class="form-group">
                        <label for="name">Remarks</label> 
                        
                        <textarea  id="closeallremarks" class="form-control" rows="3" placeholder="Enter Remarks"></textarea>
                       </div>
           
            </div>

            <div class="modal-footer">
                
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary" onclick="Closealltickets()">Yes</button>
                
            </div>
        </div>
    </div>
</div>





<!-- View Detail  modal -->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Trouble Ticket History</h4>
            </div>

               <div class="modal-body">
               
               
              <div class="table">
              	<table id="viewdetailtable" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>From Team</th>
											<th>To Team</th>											
											<th>Solve Flag</th>
											<th>Remarks</th>
											<th>Create BY</th>
											<th>Create DT</th>
											
	
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
