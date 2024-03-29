// document.addEventListener("DOMContentLoaded", function () {
//   var elems = document.querySelectorAll(".timepicker");
//   var instances = M.Timepicker.init(elems, {
//     showClearBtn: true,
//     i18n: {
//       clear: "消去",
//       cancel: "閉める",
//       done: "確認",
//     },
//     twelveHour: false,
//   });
// });

document.addEventListener("DOMContentLoaded", function () {
  var date = new Date();
  var elems = document.querySelectorAll(".datepicker");
  var instances = M.Datepicker.init(elems, {
    i18n: {
      months: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
      monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
      weekdays: ["日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"],
      weekdaysShort: ["日", "月", "火", "水", "木", "金", "土"],
      weekdaysAbbrev: ["日", "月", "火", "水", "木", "金", "土"],
      today: "今日",
      clear: "消去",
      cancel: "閉める",
      done: "確認",
      firstDay: 1,
    },
    format: "yyyy-mm-dd",
    formatSubmit: "yyyy-mm-dd",
    showClearBtn: true,
    minDate: date,
  });
});
