package jp.co.sss.sportsCenter.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    // AJAXを利用して、施設を選んだら、データベースから関連道具を検索
    @ResponseBody
    @RequestMapping(value = "/reserve/create/getSpecTool", method = RequestMethod.GET)
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

    // AJAXを利用して、施設を検索
    @ResponseBody
    @RequestMapping(value = "/reserve/create/getFacilities", method = RequestMethod.GET)
    public List<LendingFacility> getFacilities(HttpSession session) {
        List<LendingFacility> facilityList = null;
        try {
            facilityList = lendingFacilityRepository.findAll();
        } catch (Exception e) {
            System.out.println("failed to get facilityList");
        }
        return facilityList;
    }

    // 新規予約
    @RequestMapping(value = "/reserve/create", method = RequestMethod.GET)
    public String createInput(HttpSession session) {
        session.setAttribute("reserveStatus", "create");
        return "/reserve/input";
    }

    // 施設画面から新規予約画面へ進む場合は、施設IDを指定する
    @RequestMapping(value = "/reserve/create/{id}", method = RequestMethod.GET)
    public String createInputWithId(HttpSession session, Model model, @PathVariable("id") int id) {
        session.setAttribute("reserveStatus", "create");
        model.addAttribute("preSelectFacilityId", id);
        return "/reserve/input";
    }

    @RequestMapping(value = "/reserve/create/confirm", method = RequestMethod.POST)
    public String createConfirm(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/reserve/confirm";
    }

    @RequestMapping(value = "/reserve/create/back", method = RequestMethod.POST)
    public String backToCreateInput(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    @RequestMapping(value = "/reserve/create/complete", method = RequestMethod.POST)
    public String createComplete(@ModelAttribute("form") @Valid ReserveForm form, HttpSession session) {

        ReserveManagement reserveManagement = new ReserveManagement();
        List<ToolManagement> toolManagementList = new ArrayList<>();

        toolManagementList = formToToolManagementList(toolManagementList, form);
        reserveManagement = formToReserve(session, reserveManagement, form);

        reserveManegementRepository.save(reserveManagement);

        // 外部参照の為の準備
        reserveManagement = reserveManegementRepository.findByUserIdAndStartTimeAndEndingTime(
                reserveManagement.getUserId(), reserveManagement.getStartTime(), reserveManagement.getEndingTime());

        // 道具情報に予約IDを登録
        for (ToolManagement tm : toolManagementList) {
            tm.setReserveManagementId(reserveManagement);
            toolManagementRepository.save(tm);
        }

        return "/reserve/complete";
    }

    // 管理者・予約一覧
    @RequestMapping("/reserve/findAll")
    public String usersReserve(HttpSession session, Model model) {
        List<ReserveManagement> rm = reserveManegementRepository.findAll(Sort.by("reserveManagementId").descending());
        model.addAttribute("rm", rm);
        return "/reserve/reserve_list";
    }

    // 予約一覧
    @RequestMapping("/reserve/findByUser")
    public String findByUser(HttpSession session, Model model) {
        User user = userRepository.getReferenceById(Integer.parseInt(String.valueOf(session.getAttribute("id"))));
        List<ReserveManagement> rm = reserveManegementRepository.findByUserId(user, Sort.by("reserveManagementId").descending());
        model.addAttribute("rm", rm);
        return "/reserve/reserve_list";
    }

    // 予約詳細
    @RequestMapping("/reserve/reserveDetail/{id}")
    public String usersReserveDetail(HttpSession session, Model model, @PathVariable("id") int id) {
        Optional<ReserveManagement> op = reserveManegementRepository.findById(id);
        ReserveManagement rm = op.get();
        List<ToolManagement> toolList = toolManagementRepository.findByReserveManagementId(rm);
        model.addAttribute("rm", rm);
        model.addAttribute("toolList", toolList);
        return "/reserve/reserve_detail";
    }

    // 予約変更
    @RequestMapping("/reserve/update/input")
    public String reserveUpdate(@Valid ReserveForm form, HttpSession session, Model model) {
        session.setAttribute("reserveStatus", "update");
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    // 予約変更確認
    @RequestMapping("/reserve/update/confirm")
    public String reserveUpdateConfirm(@Valid ReserveForm form, HttpSession session, Model model) {
        model.addAttribute("form", form);
        return "/reserve/confirm";
    }

    // 予約変更確認から戻る
    @RequestMapping("/reserve/update/back")
    public String reserveUpdateConfirmBack(@Valid ReserveForm form, HttpSession session, Model model) {
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    // 予約変更完了
    @RequestMapping("/reserve/update/complete")
    public String reserveUpdateComplete(@Valid ReserveForm form, HttpSession session, Model model) {
        int id = form.getReserveManagementId();
        Optional<ReserveManagement> op = reserveManegementRepository.findById(id);
        ReserveManagement reserveManagement = op.get();
        List<ToolManagement> toolManagementList = toolManagementRepository.findByReserveManagementId(reserveManagement);

        toolManagementList = formToToolManagementList(toolManagementList, form);
        reserveManagement = formToReserve(session, reserveManagement, form);

        reserveManegementRepository.save(reserveManagement);

        // 道具情報に予約IDを登録
        for (ToolManagement tm : toolManagementList) {
            tm.setReserveManagementId(reserveManagement);
            toolManagementRepository.save(tm);
        }
        return "/reserve/complete";
    }

    private ReserveManagement formToReserve(HttpSession session, ReserveManagement reserveManagement,
            ReserveForm form) {
        String st = form.getStartTime();
        String et = form.getEndingTime();
        System.out.println(st + " start1\nend" + et);

        // 日付と時刻を結合
        String d = form.getDate();
        st = d + " " + st + ":00";
        et = d + " " + et + ":00";

        System.out.println(st + " start2\nend" + et);

        // String型からTimestamp型に変換
        Timestamp timestamp1 = Timestamp.valueOf(st);
        Timestamp timestamp2 = Timestamp.valueOf(et);

        // 外部参照の為の準備
        User user = userRepository.getReferenceById(Integer.parseInt(String.valueOf(session.getAttribute("id"))));
        System.out.println("ユーザーID:" + user.getUserId());

        Optional<LendingFacility> optionalEntity = lendingFacilityRepository.findById(form.getFacilityId());
        LendingFacility facility = optionalEntity.get();

        reserveManagement.setUserId(user);
        reserveManagement.setFacilityId(facility);
        reserveManagement.setStartTime(timestamp1);
        reserveManagement.setEndingTime(timestamp2);

        return reserveManagement;
    }

    private List<ToolManagement> formToToolManagementList(List<ToolManagement> list, ReserveForm form) {
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
}