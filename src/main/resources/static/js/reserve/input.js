var status = $("#reserveStatus").val(); // 入口を判断する、新規はcreate、更新はupdate
var preSelectFacilityId = $("#preSelectFacilityId").val();
var form = $("#form").val();

window.onload = function () {
  if (status == "create") {
    initFacilities();
  } else {
    checkStartTimeSelect();
  }
};

function doSubmitForm() {
  var form = document.getElementById('inputForm');
  var formData = new FormData(form);
  if (checkboxVarify(formData)) {
    form.submit();
  } else {
    confirm("時間帯を指定してください。");
  }
}

function checkboxVarify(formData) {
  for (let index = 9; index < 20; index++) {
    var timeData = formData.get("time" + index);
    if (timeData == "on") {
      return true;
    }
  }
  return false;
}

function initFacilities() {
  $.ajax({
    type: "get",
    url: "/reserve/create/getFacilities",
    data: {},
    success: function (data) {
      if (preSelectFacilityId != "null") {
        $("#hintOption").attr("selected");
      }
      var id = $("#formFacilityId").val();
      data.forEach((f) => {
        if (form != null) {
          // form优先
          if (id == f.facilityId) {
            $("#facilityId").append('<option value="' + f.facilityId + '" selected >' + f.facilityName + "</option>");
          } else {
            $("#facilityId").append('<option value="' + f.facilityId + '">' + f.facilityName + "</option>");
          }
        } else {
          if (preSelectFacilityId != "null") {
            if (preSelectFacilityId == f.facilityId) {
              // preSelect优先
              $("#facilityId").append('<option value="' + f.facilityId + '" selected >' + f.facilityName + "</option>");
            } else {
              $("#facilityId").append('<option value="' + f.facilityId + '">' + f.facilityName + "</option>");
            }
          } else {
            // default
            $("#facilityId").append('<option value="' + f.facilityId + '" ">' + f.facilityName + "</option>");
          }
        }
      });

      $(document).ready(function () {
        $("select").formSelect();
      });

      if (form == null) {
        change();
      }
    },
  });
}

function change() {
  var facilityName = $("#facilityId option:selected").text();
  $("#facilityName").attr("value", facilityName);
  var value = $("#facilityId").val();

  checkStartTimeSelect();

  $("#toolLoading").show();
  $("#toolLoading").attr("class", "progress col offset-s3 s6");
  $("#confirm").attr("class", "btn waves-effect waves-light disabled");
  $("#hintOption").attr("disabled", true);

  $(document).ready(function () {
    $("select").formSelect();
  });

  var tool = $("#toolField").empty();
  $.ajax({
    type: "get",
    url: "/reserve/create/getSpecTool",
    data: { id: value },
    success: function (data) {
      var str = "\n";
      if (data.length > 0) {
        $("#toolHint").hide();
        $("#toolLoading").hide();
        $("#confirm").attr("class", "btn waves-effect waves-light");
        for (var i = 0; i < data.length; i++) {
          str +=
            '<div class="row">\n' +
              '<div class="col offset-s3 s1">\n' +
                "<p>" + data[i].toolName + "</p>" +
              "</div>" +
              '<div class="col s5">\n' +
                '<div class="input-field">\n' +
                  '<input name="toolList[' + i + '].toolName" type="hidden" class="validate toolInfo" value="' + data[i].toolName + '"/>\n' +
                  '<input name="toolList[' + i + '].toolId" type="hidden" class="validate toolInfo" value="' + data[i].toolId + '"/>\n' +
                  '<input ' +
                    'placeholder="個数を入力してください" ' +
                    'name="toolList[' + i + '].toolNum" ' +
                    'type="number" ' +
                    'class="validate toolInfo" ' +
                    'minlength="1" ' +
                    'required ' +
                  '/>\n' +
                  '<span class="helper-text" data-error="半角英数字を正しく入力してください">半角英数字，いらない場合は0を入力してください</span>\n' +
                "</div>" +
              "</div>" +
            "</div>";
        }
      } else {
        $("#toolHint").show();
      }

      $("#toolField").html(str);
    },
  });
}

function checkStartTimeSelect() {
  // var back = $("#backHidden").val();
  var value = $("#facilityId").val();
  if (status == "update") {
    value = $("#hiddenUpdateFacilityId").val();
  }
  var date = $("#dateInput").val();

  // if (back == "true") {
  //新規/更新のConfirm画面から戻した
  // var form = $("#formHidden").val();
  // for (let index = 9; index < 20; index++) {
  //   if () {
  //     $("#time" + index).attr("disabled", true);
  //   }
  // }
  // }else{//初めて新規/更新に入る
  for (let index = 9; index < 20; index++) {
    $("#time" + index).attr("disabled", true);
  }
  // }

  if (value != "" && date != "") {
    changeStartTimeSelect(value, date);
  } else {
    $(".material-tooltip").show();
  }
}

function changeStartTimeSelect(facilityId, date) {
  $.ajax({
    type: "get",
    url: "/reserve/create/getReserveByFacilityIdAndDate",
    data: { facilityId: facilityId, date: date },
    success: function (data) {
      // if (data.size()>0) {
      // $("#hourTable").attr("class","timeTable centered col offset-m1 m10 s12")
      $(".material-tooltip").hide(); // "先に施設と日付を選択"のヒントを隠す
      for (const key in data) {
        var value = data[key];
        if (value == false) {
          $("#time" + key).attr("disabled", false); //利用されていない時間帯を選択可にする
        } else if (value == true) {
          $("#time" + key).attr("disabled", true); //利用されている時間帯を選択禁止にする
        }
      }
      if (status == "update") {
        var hourListString = $("#hourListHidden").val();
        for (let index = 9; index < 20; index++) {
          if (hourListString.indexOf(index.toString()) != -1) {
            $("#time" + index).attr("disabled", false); //この予約が選択された時間帯を選択可にする
            $("#time" + index).attr("checked", true); //この予約が選択された時間帯を選択にする
          }
        }
      }
      // }
    },
  });
}
