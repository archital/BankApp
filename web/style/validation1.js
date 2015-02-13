function checkClient() {

 var city = $("#cityFind").val();
     var result = true;
            if (city.length < 2 ) {
                        $("#cityFindError").html("введите город клиента!");
                      result = false;
                    }


    var name = $("#nameFind").val();
    var nameArray = name.split(" ");
    if (nameArray.length < 2) {
        $("nameFindError").html("пожалуйста, укажите имя и фамилию!");
        result =  false;
    }
    for(var i = 0; i < nameArray.length; i++) {
        var arr = nameArray[i];
        if (arr.length < 2) {
          $("#nameFindError").html("имя или фамилия должны быть больше двух букв!");
         result =  false;
        }
    }
    return result;
    }
