function saveItemCategory() {
    //debugger;
    if ($('#CATEGORY_CODE') == null || $('#CATEGORY_CODE') == "" || $('#CATEGORY_CODE').val().length > 10) {
        alert('Invalid ItemCategory Type!!!');
        return;
    }
    var CODE = $("#CATEGORY_CODE").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    
    $.post('../itemcategory/saveJS', {
        CATEGORY_CODE: CODE,
        DESCRIPTION: DESCRIPTION
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });



}

var CODE;
function editItemCategory(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {

            $("#EDITCATEGORY_CODE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
        }
    }
}

function updateItemCategory() {

    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    
    $.post('../itemcategory/update', {
        CATEGORY_CODE: CODE,
        DESCRIPTION: DESCRIPTION
    }, function (data) {
        // location.reload();
        alert(data);
//        temp = $('#example1').DataTable();
//        temp.clear().draw();
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }


    });
}

var CODE;

function deleteItemCategory(code) {
    CODE = code;
}
function del() {

    $.post('../itemcategory/delete', {
        CATEGORY_CODE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

