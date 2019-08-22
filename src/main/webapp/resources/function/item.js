function saveItem() {
    //debugger;
    if ($('#ITEM_CODE') == null || $('#ITEM_CODE') == "" || $('#ITEM_CODE').val().length > 10) {
        alert('Invalid Item Type!!!');
        return;
    }
    var CODE = $("#ITEM_CODE").val();
    var DESCRIPTION = $("#DESCRIPTION").val();
    var CATEGORY_CODE = $("#CATEGORY_CODE").val();
    var IS_RECURRING = $("#IS_RECURRING").val();
    var TAXABLE_AMT = $("#TAXABLE_AMT").val();
    var VATABLE_AMT = $("#VATABLE_AMT").val();
    var OWN_AMT = $("#OWN_AMT").val();
    var ACTIVE_FLAG = $("#ACTIVE_FLAG").val();
        
    $.post('../item/saveJS', {
        ITEM_CODE: CODE,
        DESCRIPTION: DESCRIPTION,
        CATEGORY_CODE: CATEGORY_CODE,
        IS_RECURRING: IS_RECURRING,
        TAXABLE_AMT: TAXABLE_AMT,
        VATABLE_AMT: VATABLE_AMT,
        OWN_AMT: OWN_AMT,
        ACTIVE_FLAG: ACTIVE_FLAG
    }, function (data) {
        alert(data);
        if (data.substring(0, 6) === "Succes") {
            location.reload();
        }
    });
}

var CODE;
function editItem(code) {
    CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][0] == code) {
            $("#EDITITEM_CODE").val(row[i][0]);
            $("#EDITDESCRIPTION").val(row[i][1]);
            $("#EDITCATEGORY_CODE").val(row[i][2]);
            $("#EDITIS_RECURRING").val(row[i][3]);
            $("#EDITTAXABLE_AMT").val(row[i][4]);
            $("#EDITVATABLE_AMT").val(row[i][5]);
            $("#EDITOWN_AMT").val(row[i][6]);
            $("#EDITACTIVE_FLAG").val(row[i][7]);
        }
    }
}

function updateItem() {
    var DESCRIPTION = $("#EDITDESCRIPTION").val();
    var CATEGORY_CODE = $("#EDITCATEGORY_CODE").val();
    var IS_RECURRING = $("#EDITIS_RECURRING").val();
    var TAXABLE_AMT = $("#EDITTAXABLE_AMT").val();
    var VATABLE_AMT = $("#EDITVATABLE_AMT").val();
    var OWN_AMT = $("#EDITOWN_AMT").val();
    var ACTIVE_FLAG = $("#EDITACTIVE_FLAG").val();
    
    $.post('../item/update', {
        ITEM_CODE: CODE,
        DESCRIPTION: DESCRIPTION,
        CATEGORY_CODE: CATEGORY_CODE,
        IS_RECURRING: IS_RECURRING,
        TAXABLE_AMT: TAXABLE_AMT,
        VATABLE_AMT: VATABLE_AMT,
        OWN_AMT: OWN_AMT,
        ACTIVE_FLAG: ACTIVE_FLAG
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

function deleteItem(code) {
    CODE = code;
}
function del() {

    $.post('../item/delete', {
        ITEM_CODE: CODE
    }, function (data) {
        location.reload();
        alert(data);
    });
}

