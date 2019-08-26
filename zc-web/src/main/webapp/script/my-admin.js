// 声明函数封装导航条初始化操作
function initPagination() {
    // 声明变量存储总记录数
    // 声明变量存储分页导航条显示时的属性设置
    var paginationProperties = {
        num_edge_entries: 3,			//边缘页数
        num_display_entries: 4,		//主体页数
        callback: pageselectCallback,	//回调函数
        items_per_page: window.pageSize,	//每页显示数据数量，就是pageSize
        current_page: window.pageNum - 1,//当前页页码
        prev_text: "上一页",			//上一页文本
        next_text: "下一页"			//下一页文本
    };

    // 显示分页导航条
    $("#Pagination").pagination(window.totalRecord, paginationProperties);
}

// 在每一次点击“上一页”、“下一页”、“页码”时执行这个函数跳转页面
function pageselectCallback(pageIndex) {
    // pageIndex从0开始，pageNum从1开始
    var pageNum = pageIndex + 1;
    // 跳转页面
    window.location.href = "admin/page/query?pageNum=" + (pageIndex + 1) + "&keyword=" + window.keyword;
    return false;
}

//封装执行批量删除的函数
function batchDelete(adminArray) {
    var requestBody = JSON.stringify(adminArray);
    $.ajax({
        "url": "admin/batch/delete",
        "type": "post",
        "contentType": "application/json;charset=UTF-8",
        "data": requestBody,
        "dataType": "json",
        "success": function (response) {
            console.log(response);
            var result = response.result;
            if ("SUCCESS" == result) {
                window.location.href = "admin/page/query?pageNum=" + window.pageNum + "&keyword=" + window.keyword;
            }
            if ("FAILID" == result) {
                alert(response.message + response.data);
                return;
            }
        },
        "error": function (response) {
            alert(response.message + response.data);
            return;
        }
    })
}