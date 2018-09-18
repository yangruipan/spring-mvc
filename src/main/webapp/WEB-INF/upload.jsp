<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Upload</title>
</head>
<body>
<script type="text/javascript">
    var int = 0;
    function UpladFile() {

        int ++;
        var str = "<div class='pro_div'><progress id='progressBar_"+(int+1)+"' value='0' max='100'></progress>\n" +
            "    <span id='percentage_"+(int+1)+"'></span>\n" +
            "    <input type='file' id='file_"+(int+1)+"' name='myfile'/><br></div>";
        var divLength = $("div[class='pro_div']").length;
        if((int+1)>1){
            $(".pro_div").eq(divLength-1).append(str);
        }

        var fileObj = document.getElementById("file_"+int).files[0]; // js 获取文件对象
        var FileController = "http://localhost:8081/test/upload";                    // 接收上传文件的后台地址

        // FormData 对象
        var form = new FormData();
        form.append("file",fileObj);
        // XMLHttpRequest 对象
        var xhr = new XMLHttpRequest();
        xhr.open("post", FileController, true);
        xhr.onload = function () {
            // alert("上传完成!");
        };
        for (var i = 0; i < divLength; i++) {
            xhr.upload.addEventListener("progress", function (evt) {
                var progressBar = document.getElementById("progressBar_"+divLength);
                var percentageDiv = document.getElementById("percentage_"+divLength);
                if (evt.lengthComputable) {
                    progressBar.max = evt.total;
                    progressBar.value = evt.loaded;
                    percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
                    if(evt.loaded==evt.total){
                        //alert("上传完成100%");
                    }
                }
            }, false);
        }

        xhr.send(form);
    }

</script>
<div class="pro_div">
    <progress id="progressBar_1" value="0" max="100"></progress>
    <span id="percentage_1"></span>
    <input type="file" id="file_1" name="myfile"/>
    <br>
</div>

    <input type="button" onclick="UpladFile()" value="上传" />
<script src="../js/jquery.min.js"></script>
</body>
</html>
