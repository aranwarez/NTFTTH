<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<!-- new modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>

                        <h4 class="modal-title" id="myModalpath">Upload File here</h4>
                    </div>
                       <form name="myForm1" action = "../shareuploadFile" method="post" enctype="multipart/form-data">
                     
                    <div class="modal-body">

                            <input type="hidden" id="fileuptopic" name="folder"/>
                            Tag : <input type="text" id="filetag" name="tag" required="required" value="All"/></br>
                           
                            <input type="file" id='inputfile' class="form-control"  name="file" onChange='getoutput()' >
                            Description : <input type="text" id="desc" name="desc" required="required"/></br>
                            
                            
                        
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <input type="submit" class="btn btn-primary" value="Upload">
                    </div>
                    </form>
                    
                </div>
            </div>
        </div>
        <!-- create modal -->
        <div class="modal fade" id="myModalNewFolder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myCurrPAth">Current Directory</h4>
                    </div>
                    <div class="modal-body">


                        <div class="form-group">
                            <label for="Folder">Folder Name</label>
                            <input type="text" class="form-control" id="foldername" >
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="return Createfolder()">Create</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- edit modal -->
        <div class="modal fade" id="myModaleditFolder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myCurrPAth">Change Directory Name to</h4>
                    </div>
                    <div class="modal-body">


                        <div class="form-group">
                            <label for="Folder">Folder Name</label>
                            <input type="text" class="form-control" id="foldernewname" >
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="return editdir()">Update</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- delete folder -->
        <div class="modal fade" id="deleteFolder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Document Manager</h4>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this folder. This cannot be undone</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                        <button type="button" class="btn btn-primary" onclick="return getdeldir()">Yes</button>
                    </div>
                </div>
            </div>
        </div>



        <!-- delete modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Document Manager</h4>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this file. This cannot be undone</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                        <button type="button" class="btn btn-primary" onclick="return deletes()">Yes</button>
                    </div>
                </div>
            </div>
        </div>