var status = $("#reserveStatus").val(); // 入口を判断する、新規はcreate、更新はupdate
var preSelectFacilityId = $("#preSelectFacilityId").val();
var form = $("#form").val();

window.onload = function () {
  if (status == "create") {
    initFacilities();
  }
};

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
  if (value == "") {
    return;
  }

  $("#toolLoading").show();
  $("#toolLoading").attr("class", "progress col offset-s3 s6");
  $("#confirm").attr("class", "btn waves-effect waves-light disabled");
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
            "<p>" +
            data[i].toolName +
            "</p>" +
            "</div>" +
            '<div class="col s5">\n' +
            '<div class="input-field">\n' +
            '<input name="toolList[' +
            i +
            '].toolName" type="hidden" class="validate toolInfo" value="' +
            data[i].toolName +
            '"/>\n' +
            '<input name="toolList[' +
            i +
            '].toolId" type="hidden" class="validate toolInfo" value="' +
            data[i].toolId +
            '"/>\n' +
            '<input placeholder="個数を入力してください" name="toolList[' +
            i +
            '].toolNum" type="number" class="validate toolInfo" />\n' +
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
