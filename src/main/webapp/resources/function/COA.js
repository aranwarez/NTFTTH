/**
 * 
 */
$(document).ready(function () {
	getFolder();
	treeconfig();
	
	
});

function getFolder() {
    var table = $('#example1').DataTable();
    table
            .clear()
            .draw();
 

    $.get('../COA/getCOAList', {}, function (responseJson) {
        if (responseJson != null) {
           //   alert(JSON.stringify(responseJson));
            var modrow = [];
            $.each(responseJson, function (key, value) {
                var myobj = new Object();
                myobj.id = value.SLDG_CODE;
                if (value.PARENT_SLDG_CODE == null) {
                    myobj.parent = '#';

                    //code to draw the table for parent code null item
                    {
                        $("#example1").dataTable().fnAddData([
                            value.SLDG_CODE,
                            value.ENG_DESC,
                            value.NEP_DESC,
                            value.PARENT_SLDG_CODE,
                            value.STATEMENT_TYPE,
                            value.ACTIVE_FLAG,
                            value.DR_CR_FLAG,
                            value.AC_TYPE,
                            value.REMARKS,
                            "<a href=\"#\" data-toggle=\"modal\" data-target=\"#myModaleditFolder\"  title=\"Delete\" onclick=\"return edititem('" + value.SLDG_CODE + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
                            "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('" + value.SLDG_CODE + "')\"<span class=\"fa fa-trash\"></span></a>"]); //closing fnAddData()
                    }


                } else
                    myobj.parent = value.PARENT_SLDG_CODE;
                if(typeof value.NEP_DESC==='undefined' || (value.NEP_DESC).length>0){
                myobj.text = value.ENG_DESC + '('+value.NEP_DESC+')';
                }
                else{
                	myobj.text = value.ENG_DESC;
                    
                }
                modrow.push(myobj);


            });
            // alert(JSON.stringify(modrow));
            $('#html1').jstree({'core': {
            	"themes":{
                    "icons":false},
                    "dblclick_toggle" : false,
                    'check_callback': true,
                    'data': modrow
                }});
            // $('#html1').jstree(true).refresh();
            $("#html1").jstree(true).deselect_all(true);


        }
    });
}


function treeconfig() {

    $('#html1').on("select_node.jstree", function (e, data) {
//        alert("node_id: " + data.node.id);
        //       alert("node_id: " + data.node.parent);
      //  $('#fileuptopic').val(data.node.id);
        parentnode = data.node.parent;
        var table = $('#example1').DataTable();
        table
                .clear()
                .draw();

        $.get('../COA/getCOAList', {}, function (responseJson) {
        	        if (responseJson != null) {
                //  alert(JSON.stringify(responseJson));
                $.each(responseJson, function (key, value) {
                    if (value.PARENT_SLDG_CODE == data.node.id) {
                    	   
                               $("#example1").dataTable().fnAddData([
                                   value.SLDG_CODE,
                                   value.ENG_DESC,
                                   value.NEP_DESC,
                                   value.PARENT_SLDG_CODE,
                                   value.STATEMENT_TYPE,
                                   value.ACTIVE_FLAG,
                                   value.DR_CR_FLAG,
                                   value.AC_TYPE,
                                   value.REMARKS,
                                   "<a href=\"#\" data-toggle=\"modal\" data-target=\"#myModaleditFolder\"  title=\"Delete\" onclick=\"return edititem('" + value.SLDG_CODE + "')\"<span class=\"fa fa-pencil-square-o\"></span></a>",
                                   "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('" + value.SLDG_CODE + "')\"<span class=\"fa fa-trash\"></span></a>"]); //closing fnAddData()
                                                   }
                });
            }
        });


    });
}


