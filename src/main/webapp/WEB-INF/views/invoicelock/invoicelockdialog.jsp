<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!-- detail modal -->
<div class="modal fade" id="myDetailModal" tabindex=-1 role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="RatesLabel">Rates</h4>
			</div>

			<div class="modal-body">
				<div class="table-responsive">
					<table id="detailtable" class="table table-bordered table-striped">
						<thead>
							<tr>
                                                            
								<th>ID</th>
								<th>VAS Service</th>
								<th>Item</th>
								<th>Sharing Type</th>
								<th>Dr/Cr</th>
                                                                <th>Amt</th>
                                                                <th>Royalty</th>
                                                                <th>TSC</th>
                                                                <th>VAT</th>
                                                                <th>Total</th>
							</tr>
						</thead>
						<tfoot>
            <tr>
                <th colspan="5" style="text-align:right">Total Current</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
        </tfoot>

					</table>
				</div>



			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
                <h4 class="modal-title" id="myModalLabel">invoice delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to delete this invoice. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/invoicelock/delete" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary"
                            onclick="return del()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>

     <!-- unpost modal -->

<div class="modal fade" id="unpostModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">

    <div class="modal-dialog" role="document">

        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">invoice delete</h4>
            </div>

            <div class="modal-body">
                <p>Are you sure you want to unpost this invoice. This cannot be
                    undone</p>
            </div>

            <div class="modal-footer">
                <form action="<c:url value="/invoicelock/unpost" />" method="post"
                      acceptCharset="UTF-8">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary"
                            onclick="return unpost()">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>


