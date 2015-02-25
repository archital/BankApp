  function isNumeric(){
    var input = $("#input").val();
    var RE = new RegExp("^\\d{1,5}(.\\d{1,3})?$");
    if(!RE.test(input)) {
      $("#withdrawDepositError").html("Введите положительное значение!");
    return false;
    }
   return true;
    }

         function checkForm() {

                var checkResult = true;
                var name=$("#nameLogin").val();
                if (name.length<2) {
                    $("#nameLoginError").html("пожалуйста, укажите имя");
                    return false;
                }
                if (checkResult) {
                    return true;
                } else {
                    return false;
                }
            }

