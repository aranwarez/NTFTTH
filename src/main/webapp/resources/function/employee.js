
 function officelist(){
	 
	    $.get('../getOfficeList', {getlist: "officelist"
	    }, function (response) {
	        var select = $('#LOCATION_CD,#EDITLOCATION_CD');
	        select.find('option').remove();
//	        $('<option>').val("").text("SELECT").appendTo(select);
	        $.each(response, function (index, value) {
	            $('<option>').val(value.OFFICE_CODE).text(value.DESCRIPTION+'('+value.OFFICE_CODE+')').appendTo(
	                    select);

	        });

	    });
 }

    function getlist() {


        var list;
        $.get('../employee/jsonlist', {getEmpList: "getlist"
        }, function (response) {
//            $.each(response, function (index, value) {
                list = response;

//            });


            /*
             * START SCRIPT
             */






            $('#example1').DataTable({
                "scrollX": true,
                "scrollY": true,
                "processing": true,
                serverSide: false,
                ordering: true,
                searching: true,
                paging: true,
                "lengthChange": true,
                ajax: function (data, callback, settings) {
                    var out = [];

                    $.each(response, function (index, value) {
                        list = response;
                        
                     //   console.log('javascipt '+value.EMPLOYEE_CODE);
                        
                        out.push([
                            value.EMPLOYEE_CODE, value.EMPLOYEE_NAME, value.ADDRESS, value.SEX, value.MARITAL_STATUS,
                            value.DOB, value.DOJ, value.QUALIFICATION, value.DISABLE_FLAG, value.TEL_NO,
                            value.MOBILE_NO, value.EMAIL, value.EMP_NO, value.TTC_NO, value.POST_CD,
                            value.DEPT_CD,
                            value.LOCATION_CD,
                            value.EMP_LEVEL, value.EMP_TYPE, value.EMP_TITLE,
                            "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editCC\" title=\"Edit\" onclick=\"return changeCC('" + value.EMPLOYEE_CODE + "')\">CC</a> " +
                                    "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editModal\" title=\"Edit\" onclick=\"return editEmployee('" + value.EMPLOYEE_CODE + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>"
                                    ,
                            "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('" + value.EMPLOYEE_CODE + "')\"<span class=\"fa fa-trash\"></span></a>"
                        ]);

                    });


                    setTimeout(function () {
                        callback({
                            draw: list.draw,
                            data: out,
                            recordsTotal: list.length,
                            recordsFiltered: list.length
                        });
                    }, 50);
                },
                scrollY: 400,
                        scroller: {
                            loadingIndicator: true
                        }
            });
            /*
             * /END SCRIPT
             */



        });
    }

    function getEmployeeFilterlist() {

        var REGION_CODE = $("#REGION_CODE").val();
        var ACC_CEN_CODE = $("#ACC_CEN_CODE").val();
        var CC_CODE = $("#CC_CODE").val();

        var list;
        $.get('../EmployeeServlet', {getEmpFilterList: "getEmpFilterList", REGION_CODE: REGION_CODE, ACC_CEN_CODE: ACC_CEN_CODE,
            CC_CODE: CC_CODE
        }, function (response) {
            $.each(response, function (index, value) {
                list = response;

            });


            /*
             * START SCRIPT
             */


            $('#example1').DataTable({
                "scrollX": true,
                "scrollY": true,
                "processing": false,
                serverSide: false,
                ordering: true,
                searching: true,
                paging: true,
                "lengthChange": true,
                ajax: function (data, callback, settings) {
                    var out = [];

                    $.each(response, function (index, value) {
                        list = response;
                        out.push([
                            value.EMPLOYEE_CODE, value.EMPLOYEE_NAME, value.ADDRESS, value.SEX, value.MARITAL_STATUS,
                            value.DOB, value.DOJ, value.QUALIFICATION, value.DISABLE_FLAG, value.TEL_NO,
                            value.MOBILE_NO, value.EMAIL, value.EMP_NO, value.TTC_NO, value.POST_CD,
                            value.DEPT_CD, value.LOCATION_CD, value.EMP_LEVEL, value.EMP_TYPE, value.EMP_TITLE,
                            "<a href = \"#\" data-toggle=\"modal\" data-target=\"#editModal\" title=\"Edit\" onclick=\"return editEmployee('" + value.EMPLOYEE_CODE + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>"
                                    ,
                            "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('" + value.EMPLOYEE_CODE + "')\"<span class=\"fa fa-trash\"></span></a>"
                        ]);

                    });


                    setTimeout(function () {
                        callback({
                            draw: list.draw,
                            data: out,
                            recordsTotal: list.length,
                            recordsFiltered: list.length
                        });
                    }, 50);
                },
                scrollY: 200,
                        scroller: {
                            loadingIndicator: true
                        }
            });
            /*
             * /END SCRIPT
             */



        });
    }


    function saveEmployee() {

        // alert('hello ');
        var y = new Boolean(false);
        if (y == false) {


            $.post('../employee/saveJS', {save: "saveemployee", EMPLOYEE_CODE: $("#EMPLOYEE_CODE").val(), EMPLOYEE_NAME: $("#EMPLOYEE_NAME").val(), ADDRESS: $("#ADDRESS").val(), SEX: $("#SEX").val(), MARITAL_STATUS: $('#MARITAL_STATUS').val(), DOB: $('#DOB').val(), DOJ: $('#DOJ').val(),
                QUALIFICATION: $("#QUALIFICATION").val(),
                DISABLE_FLAG: $("#DISABLE_FLAG").val(), TEL_NO: $("#TEL_NO").val(),
                MOBILE_NO: $("#MOBILE_NO").val(), EMAIL: $("#EMAIL").val(), EMP_NO: $("#EMP_NO").val(),
                TTC_NO: $("#TTC_NO").val(), POST_CD: $("#POST_CD").val(),
                DEPT_CD: $("#DEPT_CD").val(), LOCATION_CD: $("#LOCATION_CD").val(), EMP_LEVEL: $("#EMP_LEVEL").val(),
                EMP_TYPE: $("#EMP_TYPE").val(), EMP_TITLE: $("#EMP_TITLE").val()
            }, function (data) {
                y = true;
                alert(data);
                location.reload();
                
                getlist();

            });

        }
        //to close dialog box
        $('.modal').modal('hide');
    }


    function callReport() {
        var EMPLOYEE_CODE = $("#EMPLOYEE_CODE").val();
        var REGION_CODE = $("#REGION_CODE").val();
        var LOCATION_CODE = $("#LOCATION_CODE").val();
        var DEPT_CODE = $("#DEPT_CODE").val();

        var POST_CODE = $("#POST_CODE").val();
        var EMP_LEVEL = $("#EMP_LEVEL").val();
        var FROM_DT = $("#FROM_DT").val();
        var TO_DATE = $("#TO_DATE").val();
        var reporttype = $("#reporttype").val();

        window.open('Stream/employeeWiseTraining.jsp?reporttype=' +
                reporttype + '\&EMPLOYEE_CODE=' + EMPLOYEE_CODE + '\&REGION_CODE=' +
                REGION_CODE + '\&LOCATION_CODE=' + LOCATION_CODE + '\&DEPT_CODE=' + DEPT_CODE +
                '\&POST_CODE=' + POST_CODE + '\&EMP_LEVEL=' + EMP_LEVEL +
                '\&FROM_DT=' + FROM_DT + '\&TO_DATE=' + TO_DATE
                );
        return false;
    }
    var EMPLOYEE_CODE;
    function editEmployee(code) {
        EMPLOYEE_CODE = code;

        var row = $("#example1").dataTable().fnGetData();
        var l = row.length;
        for (var i = 0; i < l; i++) {
            if (row[i][0] == code) {
            	
                $("#EDITEMPLOYEE_CODE").val(row[i][0].toString());
                
                $("#EDITEMPLOYEE_NAME").val(row[i][1]);
                $("#EDITADDRESS").val(row[i][2]);
                
                $("#EDITSEX").val(row[i][3]);
                $("#EDITMARITAL_STATUS").val(row[i][4]);
                $("#EDITDOB").val(row[i][5]);
                $("#EDITDOJ").val(row[i][6]);
                $("#EDITQUALIFICATION").val(row[i][7]);
                
                $("#EDITTEL_NO").val(row[i][9]);
                $("#EDITMOBILE_NO").val(row[i][10]);

                $("#EDITEMAIL").val(row[i][11]);
                $("#EDITEMP_NO").val(row[i][12]);
                $("#EDITTTC_NO").val(row[i][13]);
                $("#EDITPOST_CD").val(row[i][14]);
                $("#EDITDEPT_CD").val(row[i][15]);
                $("#EDITLOCATION_CD").val(row[i][16]);
                $("#EDITEMP_LEVEL").val(row[i][17]);
                $("#EDITEMP_TYPE").val(row[i][18]);
                $("#EDITEMP_TITLE").val(row[i][19]);

            }

        }
    }
   
   

  

    function changeCC(emp_code) {
        EMPLOYEE_CODE = emp_code;

    }


    function updateCC() {

        $.post('../EmployeeServlet', {ChangeCC: "ChangeCC", EMPLOYEE_CODE: EMPLOYEE_CODE}, function (response) {

            alert(response);

        });


    }
    function updateEmployee() {


        var EMPLOYEE_NAME = $("#EDITEMPLOYEE_NAME").val();
        var ADDRESS = $("#EDITADDRESS").val();

        var TEL_NO = $("#EDITTEL_NO").val();
        var MOBILE_NO = $("#EDITMOBILE_NO").val();

        var EMAIL = $("#EDITEMAIL").val();
        var DEPT_CD = $("#EDITDEPT_CD").val();
        var LOCATION_CD = $("#EDITLOCATION_CD").val();
        
        var EMP_TITLE = $("#EDITEMP_TITLE").val();
        $.post('../employee/updateJS', {EMPLOYEE_CODE: EMPLOYEE_CODE, EMPLOYEE_NAME: EMPLOYEE_NAME,
            ADDRESS: ADDRESS, TEL_NO: TEL_NO, MOBILE_NO: MOBILE_NO, EMAIL: EMAIL, DEPT_CD: DEPT_CD, LOCATION_CD: LOCATION_CD,EMP_TITLE:EMP_TITLE}, function (response) {
            	alert(response);
            	location.reload();
            

        });
        $('.modal').modal('hide');

    }

    function delAccCenter(code) {
        acc_cen_code = code;

    }
    function del() {

        $.post('../employee/deleteJS', {delete: "deleteAccCenter", EMPLOYEE_CODE: acc_cen_code}, function (response) {


            temp = $('#example1').DataTable();
            temp.clear().draw();
            location.reload();
            alert(response);
            getlist();
            $('.modal').modal('hide');

        });
    }
    
    
    function isNumberKey(evt){
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }
    
   
