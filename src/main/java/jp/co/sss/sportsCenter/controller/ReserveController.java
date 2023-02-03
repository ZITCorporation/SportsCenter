package jp.co.sss.sportsCenter.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import jp.co.sss.sportsCenter.entity.LendingFacility;
import jp.co.sss.sportsCenter.entity.LendingTool;
import jp.co.sss.sportsCenter.entity.ReserveManagement;
import jp.co.sss.sportsCenter.entity.ToolManagement;
import jp.co.sss.sportsCenter.entity.User;
import jp.co.sss.sportsCenter.form.ReserveForm;
import jp.co.sss.sportsCenter.form.ToolForm;
import jp.co.sss.sportsCenter.repository.LendingFacilityRepository;
import jp.co.sss.sportsCenter.repository.LendingToolRepository;
import jp.co.sss.sportsCenter.repository.ReserveManegementRepository;
import jp.co.sss.sportsCenter.repository.ToolManagementRepository;
import jp.co.sss.sportsCenter.repository.UserRepository;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReserveController {
    @Autowired
    ReserveManegementRepository reserveManegementRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LendingFacilityRepository lendingFacilityRepository;
    @Autowired
    LendingToolRepository lendingToolRepository;
    @Autowired
    ToolManagementRepository toolManagementRepository;

    // private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 予約ページからの予約情報を内部処理し、正しい形のReserveManagementに書き換え
     * 
     * @param session           sessionをページに適用するための
     * @param reserveManagement 処理前のreserveManagement
     * @param form              予約ページからのForm情報
     * @return 正しい形のReserveManagement
     */
    private ReserveManagement form2Reserve(HttpSession session, ReserveManagement reserveManagement,
            ReserveForm form) {

        // 外部参照の為のユーザーEntity準備
        User user = new User();
        if ((Boolean) (session.getAttribute("adminCreateFlag"))) { // 管理者として新規予約の場合はユーザーIDを指定
            int userId = Integer.parseInt(String.valueOf(session.getAttribute("adminCreateId")));
            Optional<User> op = userRepository.findById(userId);
            user = op.get();
        } else { // 更新の場合は、当ユーザーの情報を取る
            if (session.getAttribute("reserveStatus").equals("update")) {
                user = reserveManagement.getUserId();
            } else {
                // 他の場合は、今登録したユーザーのIDをSessionから取る
                int id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
                Optional<User> op = userRepository.findById(id);
                user = op.get();
            }
        }
        // System.out.println("ユーザーID:" + user.getUserId());

        // 外部参照の為の施設Entity準備
        Optional<LendingFacility> optionalEntity = lendingFacilityRepository.findById(form.getFacilityId());
        LendingFacility facility = optionalEntity.get();

        // 予約日付準備
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = null;
        try {
            date = new java.sql.Date(sdfDate.parse(form.getDate()).getTime());
        } catch (ParseException e) {
            System.out.println("SQLDate時間転換失敗");
        }

        // 予約時間準備
        String stringFromList = form2HourListString(form);

        reserveManagement.setUserId(user);
        reserveManagement.setFacilityId(facility);
        reserveManagement.setReserveDate(date);
        reserveManagement.setHourList(stringFromList);
        reserveManagement.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return reserveManagement;
    }

    private String form2HourListString(ReserveForm form) {
        List<String> times = new ArrayList<>();
        try {
            if (form.getTime9() != null) {
                times.add("9");
            }
            if (form.getTime10() != null) {
                times.add("10");
            }
            if (form.getTime11() != null) {
                times.add("11");
            }
            if (form.getTime12() != null) {
                times.add("12");
            }
            if (form.getTime13() != null) {
                times.add("13");
            }
            if (form.getTime14() != null) {
                times.add("14");
            }
            if (form.getTime15() != null) {
                times.add("15");
            }
            if (form.getTime16() != null) {
                times.add("16");
            }
            if (form.getTime17() != null) {
                times.add("17");
            }
            if (form.getTime18() != null) {
                times.add("18");
            }
            if (form.getTime19() != null) {
                times.add("19");
            }
        } catch (Exception e) {
        }
        String stringFromList = String.join(",", times);
        return stringFromList;
    }

    /**
     * 予約ページからのTool情報を内部処理し、正しい形のToolManagementのListに書き換え
     * 
     * @param list 処理前のToolManagementのList
     * @param form 予約ページからのForm情報
     * @return 正しい形のToolManagementのList
     */
    private List<ToolManagement> form2ToolManagementList(List<ToolManagement> list, ReserveForm form) {
        List<ToolManagement> returnList = new ArrayList<ToolManagement>();
        // 利用した道具のIDを取得、一つずつ道具予約Entityに登録
        for (ToolForm tf : form.getToolList()) {
            int toolId = tf.getToolId();
            int toolNum = tf.getToolNum();
            ToolManagement tm = null;
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    tm = list.get(i);
                    if (tm.getToolId().getToolId() == toolId) {
                        tm.setToolNumber(toolNum);
                        break;
                    }
                }
            } else {
                tm = new ToolManagement();
                LendingTool tool = lendingToolRepository.findById(toolId).get();
                tm.setToolId(tool);
                tm.setToolNumber(toolNum);
            }
            returnList.add(tm);
        }
        return returnList;
    }

    /**
     * AJAXを利用して、施設を選んだら、データベースから関連道具を検索
     * 
     * @param request ajaxからのデータ
     * @param session sessionをページに適用するための
     * @return
     */
    @ResponseBody
    @GetMapping("/reserve/create/getSpecTool")
    public List<LendingTool> getSpecTool(HttpServletRequest request, HttpSession session) {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<LendingFacility> optionalEntity = lendingFacilityRepository.findById(id);
        LendingFacility facility = optionalEntity.get();
        List<LendingTool> toolList = new ArrayList<>();
        try {
            toolList = lendingToolRepository.findByFacilityId(facility);
            session.setAttribute("toolList", toolList);
        } catch (Exception e) {
            System.out.println("failed to get toolList");
        }
        return toolList;
    }

    /**
     * AJAXを利用して、施設と日付を選んだら、データベースから関連のすべての予約を検索し、予約可能の時間帯を返す
     * 
     * @param request ajaxからのデータ
     * @param session sessionをページに適用するための
     * @return 予約可能の時間帯をMap(String,Boolean)として返す
     * @throws ParseException
     */
    @ResponseBody
    @GetMapping("/reserve/create/getReserveByFacilityIdAndDate")
    public Map<String, Boolean> getReserveByFacilityIdAndDate(HttpServletRequest request, HttpSession session)
            throws ParseException {
        Map<String, Boolean> hourMap = new HashMap<>();
        for (int i = 9; i < 20; i++) {
            hourMap.put(String.valueOf(i), false); // 9時から19時の判断MAP、デフォルトはfalse
        }

        int facilityId = Integer.parseInt(request.getParameter("facilityId")); // ページからの施設IDを取る
        String dateString = request.getParameter("date"); // ページからの日付を取る
        Timestamp dateStamp = Timestamp.valueOf(dateString + " 00:00:00");// 日付のStringをTimestampに変換

        Optional<LendingFacility> optionalEntity = lendingFacilityRepository.findById(facilityId);
        LendingFacility facility = optionalEntity.get(); // 施設IDを通じて、データベースから施設のEntityを探す

        List<ReserveManagement> reserveManagementList = reserveManegementRepository.findAllByFacilityIdAndReserveDate(
                facility,
                dateStamp, Sort.by("reserveManagementId").descending()); // 施設のEntityと日付を通じて、関連のすべての予約を検索する、Listに保存
        for (ReserveManagement reserveManagement : reserveManagementList) {// Listから一つ一つの予約を取る
            String hourString = reserveManagement.getHourList();// 予約の中のString形の時間帯を取る、例は"9,10,17,19"
            List<String> hourStringList = Arrays.asList(hourString.split(",")); // "9,10,17,19"＞{9,10,17,19}
            for (String hour : hourStringList) { // "9"
                hourMap.replace(hour, true); // Map<"9",true>
            }
        }

        return hourMap; // MAPをページに渡す
    }

    /**
     * AJAXを利用して、すべての施設を検索
     * 
     * @return すべての施設のList
     */
    @ResponseBody
    @GetMapping("/reserve/create/getFacilities")
    public List<LendingFacility> getFacilities() {
        List<LendingFacility> facilityList = null;
        try {
            facilityList = lendingFacilityRepository.findAll();
        } catch (Exception e) {
            System.out.println("failed to get facilityList");
        }
        return facilityList;
    }

    // 管理者・予約一覧
    @GetMapping("/reserve/findAll")
    public String usersReserve(HttpSession session, Model model) {

        // 権限チェック

        List<ReserveManagement> rm = reserveManegementRepository.findAll(Sort.by("reserveManagementId").descending());
        model.addAttribute("rm", rm);
        model.addAttribute("listStatus", "admin");
        session.setAttribute("adminCreateFlag", true);
        return "/reserve/reserve_list";
    }

    // 管理者・指定ユーザー・予約一覧
    @GetMapping("/reserve/findByUser/{id}")
    public String findByUserAdmin(@PathVariable("id") int userId, HttpSession session, Model model) {

        // 権限について、管理者はすべてのユーザーをアクセスができ

        Optional<User> op = userRepository.findById(userId);
        User user = op.get();
        List<ReserveManagement> rm = reserveManegementRepository.findAllByUserId(user,
                Sort.by("reserveManagementId").descending());
        model.addAttribute("rm", rm); // rmからuserListまで
        model.addAttribute("listStatus", "normal");
        session.setAttribute("adminCreateFlag", true);
        session.setAttribute("adminCreateId", userId);
        model.addAttribute("userName", user.getName());
        return "/reserve/reserve_list";
    }

    // 普通ユーザー・予約一覧
    @GetMapping("/reserve/findByUser")
    public String findByUser(HttpSession session, Model model) {

        // 権限チェック

        int id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        Optional<User> op =userRepository.findById(id);
        User user = op.get();
        List<ReserveManagement> rm = reserveManegementRepository.findAllByUserId(user,
                Sort.by("reserveManagementId").descending());
        model.addAttribute("rm", rm);
        model.addAttribute("listStatus", "normal");
        session.setAttribute("adminCreateFlag", false);
        return "/reserve/reserve_list";
    }

    // 予約詳細
    @GetMapping("/reserve/reserveDetail/{id}")
    public String usersReserveDetail(HttpSession session, Model model, @PathVariable("id") int id) {

        // 権限について、管理者はすべての予約をアクセスができ、普通ユーザーは自分の予約で検索できると考えばいい。

        Optional<ReserveManagement> op = reserveManegementRepository.findById(id);
        ReserveManagement rm = op.get();
        List<ToolManagement> toolList = toolManagementRepository.findByReserveManagementId(rm);
        User user = rm.getUserId();
        int userId = user.getUserId();
        session.setAttribute("adminCreateId", userId);
        model.addAttribute("rm", rm);
        model.addAttribute("toolList", toolList);
        model.addAttribute("hourList", rm.getHourList());
        model.addAttribute("reserveManagementId",id);
        return "/reserve/reserve_detail";
    }

    // 新規予約
    @GetMapping("/reserve/create")
    public String createInput(HttpSession session) {
        session.setAttribute("reserveStatus", "create");
        session.setAttribute("adminCreateFlag", false);
        return "/reserve/input";
    }

    // 管理者としての新規予約
    @GetMapping("/reserve/createAdmin/{id}")
    public String createInputAdmin(HttpSession session, @PathVariable("id") int id) {
        session.setAttribute("reserveStatus", "create");
        session.setAttribute("adminCreateFlag", true);
        session.setAttribute("adminCreateId", id);
        return "/reserve/input";
    }

    // 施設画面から新規予約画面へ進む場合は、施設IDを指定する
    @GetMapping("/reserve/create/{id}")
    public String createInputWithId(HttpSession session, Model model, @PathVariable("id") int facilityId) {
        session.setAttribute("reserveStatus", "create");
        model.addAttribute("preSelectFacilityId", facilityId);
        session.setAttribute("adminCreateFlag", false);
        return "/reserve/input";
    }

    // 新規予約確認
    @PostMapping("/reserve/create/confirm")
    public String createConfirm(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/reserve/confirm";
    }

    // 新規予約確認から、入力へ戻る
    @PostMapping("/reserve/create/back")
    public String backToCreateInput(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        model.addAttribute("back", true);
        return "/reserve/input";
    }

    // 予約新規完了
    @PostMapping("/reserve/create/complete")
    public String createComplete(@ModelAttribute("form") @Valid ReserveForm form, HttpSession session) {

        ReserveManagement reserveManagement = new ReserveManagement();
        List<ToolManagement> toolManagementList = new ArrayList<>();

        toolManagementList = form2ToolManagementList(toolManagementList, form);
        reserveManagement = form2Reserve(session, reserveManagement, form);

        reserveManegementRepository.save(reserveManagement);

        // 外部参照の為の準備
        reserveManagement = reserveManegementRepository.findByUserIdAndReserveDateAndHourList(
                reserveManagement.getUserId(), reserveManagement.getReserveDate(), reserveManagement.getHourList());

        // 道具情報に予約IDを登録
        for (ToolManagement tm : toolManagementList) {
            tm.setReserveManagementId(reserveManagement);
            toolManagementRepository.save(tm);
        }

        return "/reserve/complete";
    }

    // 予約変更
    @PostMapping("/reserve/update/input")
    public String reserveUpdate(@Valid ReserveForm form, HttpSession session, Model model) {
        session.setAttribute("reserveStatus", "update");
        String stringFromList = form2HourListString(form);
        model.addAttribute("hourList", stringFromList);
        model.addAttribute("form", form);
        model.addAttribute("reserveManagementId",form.getReserveManagementId());
        return "/reserve/input";
    }

    // 管理者としての予約変更
    @PostMapping("/reserve/update/input/{id}")
    public String reserveUpdateAdmin(@Valid ReserveForm form, HttpSession session, @PathVariable("id") int id,
            Model model) {
        session.setAttribute("reserveStatus", "update");
        session.setAttribute("adminCreateFlag", true);
        session.setAttribute("adminCreateId", id);
        String stringFromList = form2HourListString(form);
        model.addAttribute("hourList", stringFromList);
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    // 予約変更確認
    @PostMapping("/reserve/update/confirm")
    public String reserveUpdateConfirm(@Valid ReserveForm form, HttpSession session, Model model) {
        model.addAttribute("form", form);
        return "/reserve/confirm";
    }

    // 予約変更確認から戻る
    @PostMapping("/reserve/update/back")
    public String reserveUpdateConfirmBack(@Valid ReserveForm form, HttpSession session, Model model) {
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    // 予約変更完了
    @PostMapping("/reserve/update/complete")
    public String reserveUpdateComplete(@Valid ReserveForm form, HttpSession session, Model model) {
        int id = form.getReserveManagementId();
        Optional<ReserveManagement> op = reserveManegementRepository.findById(id);
        ReserveManagement reserveManagement = op.get();
        List<ToolManagement> toolManagementList = toolManagementRepository.findByReserveManagementId(reserveManagement);

        toolManagementList = form2ToolManagementList(toolManagementList, form);
        reserveManagement = form2Reserve(session, reserveManagement, form);

        reserveManegementRepository.save(reserveManagement);

        // 道具情報に予約IDを登録
        for (ToolManagement tm : toolManagementList) {
            tm.setReserveManagementId(reserveManagement);
            toolManagementRepository.save(tm);
        }
        return "/reserve/complete";
    }

    // 予約削除
    @PostMapping("/reserve/delete")
    public String reserveDelete(HttpSession session, HttpServletRequest request) {

        // 権限について、管理者はすべての予約を削除ができ、普通ユーザーは自分の予約で削除できると考えばいい。

        session.setAttribute("reserveStatus", "delete");
        String index = request.getParameter("index");
        reserveManegementRepository.deleteById(Integer.parseInt(index));
        return "/reserve/complete";
    }

    // 予約削除完了
    @GetMapping("/reserve/delete/complete")
    public String reserveDeleteComplete(HttpSession session) {
        session.setAttribute("reserveStatus", "delete");
        return "/reserve/complete";
    }
}