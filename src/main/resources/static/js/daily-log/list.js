$(function() {

    $('#searchConditionModal').dialog({
        autoOpen: false
        , modal: true
        , width: 600
        , height: "auto"
        , resizable: false
        , closeOnEscape: false
        , title: "検索条件"
    });

    $('#modalOpen').on('click', function() {
        $('#searchConditionModal').dialog('open');
    });


    $('#modalClose').on('click', function() {
        $('#searchConditionModal').dialog('close');
    });

});