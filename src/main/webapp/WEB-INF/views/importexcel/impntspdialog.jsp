<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!-- import dialog box -->

<div class="modal fade" id="importdialog" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">

    <div class="modal-dialog" role="document">

        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Import Csv File </h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to import this data. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">

                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-primary"  onclick="importdata();">Yes</button>

            </div>
        </div>
    </div>
</div>
        
        
               