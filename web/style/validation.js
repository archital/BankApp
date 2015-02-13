 function addClient() {
     var city = $("#addCity").val();
     var result = true;
            if (city.length < 2 ) {
                        $("#addCityError").html("введите город клиента!");
                      result = false;
                    }


    var name = $("#addName").val();
    var nameArray = name.split(" ");
    if (nameArray.length < 2) {
        $("addNameFindError").html("пожалуйста, укажите имя и фамилию!");
        result =  false;
    }
    for(var i = 0; i < nameArray.length; i++) {
        var arr = nameArray[i];
        if (arr.length < 2) {
          $("#addNameFindError").html("имя и фамилия должны быть больше двух букв!");
         result =  false;
        }
    }

          var email = $("#email").val();
        if (!email.match(".+@.+\.[a-zA-Z]{2,3}")) {
            $("#emailError").html("неверный формат e-mail");
           result =  false;
        }

    var startBalance = $("#startBalance").val();
   var RE3 = new RegExp("^\\d{1,5}(.\\d{1,3})?$");
    if(!RE3.test(startBalance)) {
      $("#startBalanceError").html("Введите положительное значение!");
    result =  false;
    }

    return result;

          }



