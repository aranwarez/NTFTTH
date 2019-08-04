<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

  
 
<div class="col-xs-12 table-responsive">

    <table id="checkDatatable"   class="table table-striped example1" >
        <thead>        
            <tr>
                <th></th>
                <th></th>
                <th>Add</th>
                <th>Edit</th>
                <th>Delete</th>
                <th>Post</th>
                <th>UnPost</th>
            </tr>
        </thead>
        <tbody>
          <c:forEach var="pmanu" items="${parementmenu}">
            <tr>
                <td> ${pmanu.getSN()} 

                </td>
                <td>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" class="mastersetup${pmanu.getSN()}" value="${pmanu.getMENU_CODE()}">

                           ${pmanu.getMENU_CODE()}
                        </label>
                    </div>
                </td>
                <td>
                    <select class="add${pmanu.getSN()}"> 
                        <option value="Y">Y</option>
                        <option value="N">N</option>
                    </select>
                </td>

                <td>
                    <select class="editing${pmanu.getSN()}"> 
                        <option value="Y">Y</option>
                        <option value="N">N</option>
                    </select>
                </td>
                <td>
                    <select class="deleting${pmanu.getSN()}"> 
                        <option value="Y">Y</option>
                        <option value="N">N</option>
                    </select>
                </td>
                <td>
                    <select class="posting${pmanu.getSN()}"> 
                        <option value="Y">Y</option>
                        <option value="N">N</option>
                    </select>
                </td>
                <td>
                    <select class="cancel${pmanu.getSN()}"> 
                        <option value="Y">Y</option>
                        <option value="N">N</option>
                    </select>
                </td>
            </tr>

         </c:forEach>
         
        </tbody>
    </table>
</div>


<input type="submit" name="save" value="save" onclick="return saveMenuAccess()" class="btn btn-success">


 