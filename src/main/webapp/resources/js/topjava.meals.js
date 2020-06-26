var userMealUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: userMealUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": userMealUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return data.substring(0, 10) + ' ' + data.substring(11, 16);
                        }
                        return data;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (!data.excess) {
                    $(row).attr("data-mealExcess", false);
                } else {
                    $(row).attr("data-mealExcess", true);
                }
            }
        }),
        updateTable: function () {
            $.get(userMealUrl, updateTableByData);
        }
    });
});


$('#startDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
});

$('#endDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
});

$('#startTime').datetimepicker({
    datepicker: false,
    format : 'H:i'
});

$('#endTime').datetimepicker({
    datepicker: false,
    format: 'H:i'
});