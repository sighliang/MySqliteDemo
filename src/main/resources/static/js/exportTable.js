//引入js，如没有jQuery请引入，引入js后在bootstrapTable配置里引入exportTableFun内的内容，参考cardinfo.js
//对应bootstrap的js引入时的type请用"text/babel"

$("body").append('<script src="/js/browser.min.js"></script>' +
    '<script src="/js/polyfill.min.js"></script><script src="/js/bootstrap-table-export.js"></script>' +
    '<script src="/js/tableExport.js"></script>' +
    '<script src="/js/xlsx.core.min.js"></script>' +
    '<script src="/js/FileSaver.min.js"></script>');

//fileName：表名(无后缀)；id,table的id
function exportTableFun(fileName,id){
    var options = {
        exportDataType:'all',//'basic':当前页的数据, 'all':全部的数据, 'selected':选中的数据
        showExport: true,  //是否显示导出按钮
        buttonsAlign:"right",  //按钮位置
        exportTypes:['excel'],//导出文件类型，[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf']
        exportOptions:{
            fileName: fileName,              //文件名称设置
            worksheetName: 'Sheet1',          //表格工作区名称
            tableName: fileName,
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
        }
    }
    return options

}
