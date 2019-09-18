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
								<th>VAS Provider</th>
								<th>Item</th>
								<th>Sharing Type</th>
								<th>Dr/Cr</th>
                                                                <th>Amt</th>
                                                                <th>Royalty</th>
                                                                <th>TSC</th>
                                                                <th>VAT</th>
							</tr>
						</thead>

					</table>
				</div>



			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>

		</div>
	</div>
</div>



