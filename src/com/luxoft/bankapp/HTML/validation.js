
    function isNumeric(){
    var input = $("#input").val();
    var RE = /^-{0,1}\d*\.{0,1}\d+$/;
    if((input< 0) || (input == null)) {
      $("#withdrawDepositError").html("Введите положительное значение!");
    return false;
    }
   return (RE.test(input));
    }

         function checkForm() {
                var form = $("#form"); // find form by id
                var checkResult = true;
                var name=$("#name").val();
                if (name.length<2) {
                    $("#nameError").html("пожалуйста, укажите имя");
                    return false;
                }
                if (checkResult) {
                    return true;
                } else {
                    return false;
                }
            }
