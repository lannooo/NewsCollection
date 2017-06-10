/**
 * Created by 51499 on 2017/6/3 0003.
 */
$(document).ready(function(){
    var page = 1;
    var type = "hot";
    var typeid = 0;

    getHotType();
    getHotNews();

    function afterTimeout(XMLHttpRequest, status){ //请求完成后最终执行参数
        if(status=='timeout'){//超时,status还有success,error等值的情况
            // ajaxTimeOut.abort(); //取消请求
            alert("超时");
        }else if(status=='error'){
            alert("error");
        }
    }

    //获取热门类别，根据用户是否登录有不同的算法
    function getHotType(){
        $.ajax({
            type: "GET",
            url: "news/hot/type",
            dataType: "json",
            timeout:3000,
            success: function (data) {
                if(data["success"]){
                    renderHotType(data["data"]);
                }else{
                    alertMessage(data);
                }
            },
        });
    }

    //获取热门新闻，根据用户是否登录有不同的结果
    //TODO:增加分页
    function getHotNews() {
        $.ajax({
            type:"GET",
            url: "news/hot/1/10",
            dataType: "json",
            timeout:3000,
            success: function (data) {
                if(data["success"]){
                    renderHotNews(data["data"]);
                }else{
                    alertMessage(data);
                }
            },
        });
    }

    
    //some click event
    //登出
    $("#logout").click(function () {
       $.ajax({
           type: "GET",
           url: "user/logout",
           dataType: "json",
           timeout:3000,
           success: function (data) {
               getHotType();
               getHotNews();
           },
       });
    });

    //切换登录或者注册
    $("#switchLR").change(function () {
        clearLoginOrRegister();
        if($(this).prop('checked')){
            $(".registerInput").show();
            $(".loginInput").hide();
        }else{
            $(".registerInput").hide();
            $(".loginInput").show();
        }
    });

    $("#next").click(function () {
        var url = "";
        page++;
        if(type=="hot")
            url = "news/hot/"+page+"/10";
        else
            url = "news/type/"+typeid+"/"+page+"/10";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            timeout:3000,
            success: function (data) {
                if(data["success"]){
                    renderHotNews(data["data"]);
                }else{
                    alertMessage(data);
                }
            },
            complete: afterTimeout
        });
        if(page>1)
            $(".previous").removeClass("disabled");
    });

    $("#previous").click(function () {
       if(page == 1)
           return;
       else{
           var url = "";
           page--;
           if(type=="hot")
               url = "news/hot/"+page+"/10";
           else
               url = "news/type/"+typeid+"/"+page+"/10";
           $.ajax({
               type: "GET",
               url: url,
               dataType: "json",
               timeout:3000,
               success: function (data) {
                   if(data["success"]){
                       renderHotNews(data["data"]);
                   }else{
                       alertMessage(data);
                   }
               },
               complete: afterTimeout
           });
           if(page==1)
               $(".previous").addClass("disabled");
       }
    });

    $("#settings").click(function () {
        $.ajax({
            type:"GET",
            url: "news/typeList",
            dataType: "json",
            timeout:3000,
            success: function (data1) {
                $.ajax({
                    type: "GET",
                    url: "favor/get",
                    dataType: "json",
                    timeout:3000,
                    success: function (data2) {
                        if(data2["success"]){
                            renderFavor(data1["data"], data2["data"]);
                            $("#setFavorModal").modal("show");
                        }
                        else{
                            alertMessage(data2);
                        }
                    },
                })
            }
        })
    });

    $("#loginOrRegisterBtn").click(function () {
        if($("#switchLR").prop('checked')){
            //register
            var email = $("#registerEmail").val();
            var userName = $("#registerUsername").val();
            var password = $("#registerPwd").val();
            $.ajax({
                type: "POST",
                url: "user/register",
                dataType: "json",
                data:{
                    username: userName,
                    password: password,
                    email:    email
                },
                timeout:3000,
                success:function (data) {
                    if(data["success"]){
                        alertMessage(data);
                        $("#switchLR").prop('checked', false);
                    }else{
                        alertMessage(data);
                    }
                },
            })
        }
        else{
            //login
            var userName = $("#loginUsername").val();
            var password = $("#loginPwd").val();
            $.ajax({
                type:"POST",
                url: "user/loginPost",
                dataType: "json",
                data:{
                    username:userName,
                    password: password
                },
                timeout:3000,
                success:function (data) {
                    if(data["success"]){
                        alertMessage(data);
                        //todo something update index by user favor
                        type="hot";
                        page=1;
                        getHotType();
                        getHotNews();
                    }else{
                        alertMessage(data);
                    }
                },
            });

            clearLoginOrRegister();
        }
        //some clear work
        $("#loginModal").modal("hide");
    });

    //查看一条新闻的详情
    function detailClick() {
        $.ajax({
            type: "GET",
            url: "news/article/"+parseInt($(this).attr("value")),
            dataType: "json",
            timeout:3000,
            success: function (data) {
                if(data["success"]){
                    renderNewsDetail(data["data"]);
                }else{
                    alertMessage(data);
                }
            },
        })
    };

    //点击了类别选项，更新热门新闻的简要内容
    function typeClick() {
        var typeId = $(this).attr("value");
        $.ajax({
            type: "GET",
            url:"news/type/"+typeId+"/1/10",
            dataType: "json",
            timeout:3000,
            success:function (data) {
                if(data["success"]){
                    type = "type";
                    typeid = typeId;
                    renderHotNews(data["data"]);
                }else{
                    alertMessage(data);
                }
            }

        })
    }



    
    // some render function
    //渲染favor 的 modal
    function renderFavor(allType, favoredType) {
        var lists = new Set();
        //do checked
        $.each(favoredType, function (i, n) {
            lists.add(n['type']['id']);
        });
        $.each(allType, function (i, n) {
            var btn = document.createElement("button");
            btn.innerHTML = n['name'];
            if(lists.has(n['id'])){
                btn.setAttribute("class","favorbtn btn btn-default active");
            }else{
                btn.setAttribute("class","favorbtn btn btn-default");
            }
            btn.setAttribute("type","button");
            btn.setAttribute("value", n['id']);
            $("#favors").append(btn);
        });
        $(".favorbtn").click(function () {
            var id = $(this).attr("value");
            if($(this).hasClass("active")){
                removeFavor(id);
                $(this).removeClass("active");
            }else{
                addFavor(id);
                $(this).addClass("active");
            }
        });
    }

    function addFavor(id) {
        $.ajax({
            type: "GET",
            url:  "favor/add/"+id,
            dataType: "json",
            timeout:3000,
            success: function (data) {
                if(data["success"]){

                }else{
                    alertMessage(data);
                }
            }

        })
    }

    function removeFavor(id) {
        $.ajax({
            type: "GET",
            url: "favor/delete/"+id,
            dataType: "json",
            timeout:3000,
            success: function (data) {
                if(data["success"]){

                }else{
                    alertMessage(data);
                }
            }
        })
    }


    //渲染新闻详情内容
    function renderNewsDetail(data) {
        $("#myLargeModalLabel").text("");
        $("#nsource").text("");
        $("#ntype").text("");
        $("#ntime").text("");
        $("#nclick").text("");
        $("#nurl").prop("href", "");
        $("#newsContent").children().remove();

        $("#myLargeModalLabel").text(data["title"]);
        $("#nsource").text(data["source"]);
        $("#ntype").text(data["type"]);
        $("#ntime").text(data["time"]);
        $("#nclick").text(data["click"]);
        $("#nurl").prop("href", data["url"]);
        $("#newsContent").append(data["content"]);

        $("#contentModal").modal("show");
    }

    //渲染页面上的新闻简要
    function renderHotNews(data) {
        //id, title, source, time, content, type
        $("#hotNews").children().remove();
        $.each(data, function (index, each) {
            var divOut = document.createElement("div");
            divOut.setAttribute("class", "col-xs-12 col-lg-6");

            var h2 = document.createElement("h2");
            h2.innerHTML = each["title"];

            var divInner = document.createElement("div");
            var sourceSpan = document.createElement("span");
            var typeSpan = document.createElement("span");
            var timeSpan = document.createElement("span");
            sourceSpan.setAttribute("class", "label label-default");
            sourceSpan.innerHTML = each["source"];
            typeSpan.setAttribute("class","label label-primary");
            typeSpan.innerHTML = each["type"];
            timeSpan.innerHTML = each["time"];
            divInner.appendChild(sourceSpan);
            divInner.appendChild(typeSpan);
            divInner.appendChild(timeSpan);

            var contentP = getSimpleContentP(each["content"]);

            var btn = document.createElement("button");
            btn.setAttribute("class", "btn btn-default");
            btn.setAttribute("value", each["id"]);
            btn.onclick = detailClick;
            btn.innerHTML = "查看详情";


            divOut.appendChild(h2);
            divOut.appendChild(divInner);
            divOut.appendChild(contentP);
            divOut.appendChild(btn);

            $("#hotNews").append(divOut);
        });

    }
    //渲染热门新闻类别
    function renderHotType(data) {
        $("#hotTypeList").children().remove();
        $.each(data, function (index, each) {
            var span = document.createElement("span");
            span.setAttribute("class", "badge");
            span.innerHTML = each["total"];
            var a = document.createElement("a");
            a.setAttribute("value", each["id"]);
            a.setAttribute("class","btn btn-raised btn-primary");
            a.setAttribute("href", "javascript:void(0)");
            a.innerHTML = each["name"];
            a.onclick = typeClick;
            a.appendChild(span);
            $("#hotTypeList").append(a);
        });
    }

    //对于简要新闻内容，生成p标签（可能是img或者文字）
    function getSimpleContentP(content) {
        var start = content.indexOf("</p>");
        var p = document.createElement("p");
        if(start>0){
            var first = content.substring(3, start);//获得第一个p的内容
            if(first.indexOf("<img")>=0){//第一个p是img， 那么返回一个包含img标签的p标签
                var src = first.substring(10, first.length-3);
                var img = document.createElement("img");
                img.setAttribute("src", src);
                p.appendChild(img);
            }
            else {//返回包含文字内容的p标签
                var text = "";
                while(text.length<100){
                    //todo bug dead lock
                    var begin = content.indexOf("<p>");
                    var end   = content.indexOf("</p>");
                    if(begin<0 || end<0)
                        break;
                    var t = content.substring(begin+3, end);
                    if(t.indexOf("<img")>=0){
                        break;
                    }
                    text = text + t;
                    content = content.substring(end+4);
                }
                p.innerHTML= text;
            }
        }else{
            p.innerHTML = "内容省略";
        }
        return p;
    }

    function clearLoginOrRegister() {
        $("#registerEmail").text("");
        $("#registerUsername").text("");
        $("#registerPwd").text("");
        $("#registerPwd2").text("");
        $("#loginUsername").text("");
        $("#loginPwd").text("");
    }

    function alertMessage(data) {
        if(data["success"])
            alert(data["message"]);
        else
            alert("获取数据异常，可能原因："+data["message"]);
    }
});