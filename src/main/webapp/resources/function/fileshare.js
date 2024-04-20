
var parentnode;
var UPLOAD_ID;

$(document).ready(function () {
    $.fn.dataTable.ext.errMode = 'none';
    $("#example1").dataTable();
//    getRegionData();
    getFolder();
    treeconfig();


    $("#p_scents").on("click", ".remScnt", function () {
        // items which need to be deleted are encapsulated within the parent item. You can use parent() to remove everything.
        $(this).parent().remove();
        i--;
    });


 //   getRecentUpload();
    // addbutton();
});





function validateForm() {
  var x = document.forms["myForm"]["fname"].value;
  if (x == "") {
    alert("Name must be filled out");
    return false;
  }
}

function editRegion(code,DISPLAY_FLAG) {
	UPLOAD_ID = code;
			$("#EDITDISPLAY_FLAG").val(DISPLAY_FLAG);
			
		}


function updateButton() {
	$.post('../update/uploadpath', {UPLOAD_ID:UPLOAD_ID,
		 DISPLAY_FLAG : $("#EDITDISPLAY_FLAG").val()
	}, function(data) {
		alert(data);
		location.reload();

	});
}
function delFILE(code) {
	UPLOAD_ID = code;

}

function del() {
	$.post('../upload/delete', {
		UPLOAD_ID : UPLOAD_ID
	}, function(data) {
		alert(data);
		location.reload();

	});
}


function getFolder() {

    $.get('../getAllFolder', {getAllFolder: "getAllFolder"}, function (responseJson) {
        if (responseJson != null) {
            // alert(JSON.stringify(responseJson));


            var modrow = [];
            $.each(responseJson, function (key, value) {
                var myobj = new Object();
                myobj.id = value.FOLDER_CODE;
                if (value.PARENT_CODE == null) {
                    myobj.parent = '#';
                } else
                    myobj.parent = value.PARENT_CODE;
                myobj.text = value.DESCRIPTION;
                modrow.push(myobj);
            });



            // alert(JSON.stringify(modrow));
            $('#html1').jstree({'core': {
                    'check_callback': true,
                    'data': modrow
                }});


            // $('#html1').jstree(true).refresh();


        }
    });


}


function treeconfig() {

    $('#html1').on("select_node.jstree", function (e, data) {
        //    alert("node_id: " + data.node.id);
        //   alert("node_id: " + data.node.parent);
        $('#fileuptopic').val(data.node.id);
        parentnode = data.node.parent;
        var table = $('#example1').DataTable();

        table
                .clear()
                .draw();

        $.get('../getAllFiles', {getAllFiles: "getAllFiles", pfolder: data.node.id}, function (responseJson) {
            if (responseJson != null) {
                // alert(JSON.stringify(responseJson));
                $.each(responseJson, function (key, value) {

                    $("#example1").dataTable().fnAddData([
                        value.FILE_PATH + '<a href="../sharedownloadFile?name=' + value.FILE_PATH + '&FileId=' + value.FILE_ID + '"> <i class="fa fa-download"></i></a>',
                        value.FILE_DES,
                        value.CREATED_BY,
                        value.CREATED_DATE,
                        value.TAG,
                        "<a href=\"#\" data-toggle=\"modal\" data-target=\"#deleteModal\"  title=\"Delete\" onclick=\"return delAccCenter('" + value.FILE_ID + "')\"<span class=\"fa fa-trash\"></span></a>"]); //closing fnAddData()

                });



                // $('#html1').jstree(true).refresh();


            }
        });





    });

}



function getOpen() {
    // alert('asdas');
    $("#myModalpath").html('Current Folder Path:\\' + $('#fileuptopic').val());


}


function getnewdir() {
    // alert('asdas');
    $("#myCurrPAth").html('Current Folder Path:\\' + $('#fileuptopic').val());


}

function Createfolder() {
    $.post('../createFolder', {save: "createFolder",
        topic: $('#fileuptopic').val(),

        DESCRIPTION: $("#foldername").val()

    }, function (data) {
        alert(data);


        $('.modal').modal('hide');
        location.reload();

    });
}

function editdir() {
    $.post('../editFolder', {edit: "editFolder",
        topic: $('#fileuptopic').val(),
        parentnode: parentnode,
        DESCRIPTION: $("#foldernewname").val()

    }, function (data) {
        alert(data);


        $('.modal').modal('hide');
        location.reload();

    });
}


function delAccCenter(id) {

    ACC_CEN_CODE = id
}

function deletes() {
    $.post('../FileServlet', {delfileid: ACC_CEN_CODE

    }, function (data) {
        alert(data);
        $('.modal').modal('hide');
    });

}
function getdeldir() {
    $.post('../delFolder', {del: "delFolder",
        topic: $('#fileuptopic').val(),
        parentnode: parentnode


    }, function (data) {
        alert(data);


        $('.modal').modal('hide');
        location.reload();

    });
}




