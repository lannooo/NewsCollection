/**
 * Created by 51499 on 2017/6/3 0003.
 */
$(document).ready(function(){
    var isTypeListHidden = false;
    $("#actionButton").click(function(){
        if(!isTypeListHidden){
            $("#typeList").fadeIn();
        }else{
            $("#typeList").fadeOut();
        }
        isTypeListHidden = !isTypeListHidden;
    });
});