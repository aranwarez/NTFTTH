$(document).ready(function () {
    getParentMenu();
});
var MENU_CODE;
function getParentMenu() {
    //jQuery.ajaxSetup({ async: false });
    $.get('../menu/menulist', {
        getMenuList: "getlist"
    }, function (response) {
        var select = $('#PARENT_MENU,#EDITPARENT_MENU');
        select.find('option').remove();
        $('<option>').val("").text("ALL").appendTo(select);
        $.each(response, function (index, value) {

            if (value.parent_MENU == null) {

                $('<option>').val(value.menu_CODE).text(
                        value.menu_DESC + " ( " + value.menu_CODE + " )")
                        .appendTo(select);
            }
        });
    });

}
function saveMenu() {
    var STATUS_TYPE;
    if ($("#STATUS_TYPE").is(':checked'))
        STATUS_TYPE = 'Y';
    else
        STATUS_TYPE = 'N';

    $.post('../menu/save', {
        MENU_CODE: $("#MENU_CODE").val(),
        MENU_DESC: $("#MENU_DESC").val(),
        MENU_URL: $("#MENU_URL").val(),
        PARENT_MENU: $("#PARENT_MENU").val(),
        MODULE_TYPE: $("#MODULE_TYPE").val(),
        STATUS_TYPE: STATUS_TYPE
    }, function (data) {
        alert(data);
        //$('.modal').modal('hide');
		location.reload();
    });
}

function editMenu(code) {
    MENU_CODE = code;
    var row = $("#example1").dataTable().fnGetData();
    var l = row.length;
    for (var i = 0; i < l; i++) {
        if (row[i][1].trim() == code.trim()) {

            $("#EDITMENU_CODE").val(row[i][1]);
            $("#EDITMENU_DESC").val(row[i][2]);
            $("#EDITMENU_URL").val(row[i][3]);
            $("#EDITPARENT_MENU").val(row[i][4]);
            if (row[i][5] == 'Y') {
                $('#EDITSTATUS_TYPE').prop('checked', true);
            } else {
                $('#EDITSTATUS_TYPE').prop('checked', false);
            }
            $("#EDITMODULE_TYPE").val(row[i][6]);

        }
    }

}
function updateMenu() {
    var STATUS_TYPE;
    if ($("#EDITSTATUS_TYPE").is(':checked'))
        STATUS_TYPE = 'Y';
    else
        STATUS_TYPE = 'N';
    $.post('../menu/update', {
        update: "updatemenu",
        MENU_CODE: MENU_CODE,
        MENU_DESC: $("#EDITMENU_DESC").val(),
        MENU_URL: $("#EDITMENU_URL").val(),
        PARENT_MENU: $("#EDITPARENT_MENU").val(),
        MODULE_TYPE: $("#EDITMODULE_TYPE").val(),
        STATUS_TYPE: STATUS_TYPE
    }, function (data) {
        alert(data);
        location.reload();
    });
}

function deleteMenu(code) {
    MENU_CODE = code;
}



function del() {

    $.post('../menu/delete', {delete: "deletemenu", MENU_CODE: MENU_CODE}, function (data) {
        alert(data);
        location.reload();
    });
}