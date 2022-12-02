package jp.co.sss.sportsCenter.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/facilities", method = RequestMethod.GET)
    public String toFacility(HttpSession session) {
        return "facility/listFacilities";
    }

    // アーチェリー
    @RequestMapping(value = "/facilities/archeryExplanation", method = RequestMethod.GET)
    public String toArcheryExplanation(HttpSession session) {
        return "facility/archeryExplanation";
    }

    // 予約
    @RequestMapping(value = "/facility/archery", method = RequestMethod.POST)
    public String createConfirmOld(ReserveForm form, Model model, HttpSession session) {
        ReserveManagement reserveManagement = new ReserveManagement();
        ToolManagement toolManagement = new ToolManagement();

        String s = form.getStartTime();
        String e = form.getEndingTime();

        // 日付と時刻を結合
        String d = form.getDate();
        s = d + " " + s + ":00";
        e = d + " " + e + ":00";

        // String型からTimestamp型に変換
        Timestamp timestamp1 = Timestamp.valueOf(s);
        Timestamp timestamp2 = Timestamp.valueOf(e);
        System.out.println("時間:" + s);
        System.out.println("時間:" + e);

        // 外部参照の為の準備
        User user = userRepository.getOne((Integer) session.getAttribute("id"));
        System.out.println("ユーザーID:" + user.getUserId());
        LendingFacility facility = lendingFacilityRepository.getOne(form.getFacilityId());
        System.out.println("施設ID:" + facility.getFacilityId());
        // LendingTool tool = lendingToolRepository.getOne(form.getToolId());
        // System.out.println("道具ID:" + tool.getToolId());

        // 予約情報を登録
        reserveManagement.setReserveManagementId(form.getReserveManagementId());
        reserveManagement.setUserId(user);
        reserveManagement.setFacilityId(facility);
        reserveManagement.setStartTime(timestamp1);
        reserveManagement.setEndingTime(timestamp2);

        // 登録
        model.addAttribute("reserveManagement", reserveManagement);
        reserveManegementRepository.save(reserveManagement);
        System.out.println("ok");

        // 外部参照の為の準備
        reserveManagement = reserveManegementRepository.findByUserIdAndStartTimeAndEndingTime(
                reserveManagement.getUserId(), reserveManagement.getStartTime(), reserveManagement.getEndingTime());

        // 道具情報を登録
        toolManagement.setReserveManagementId(reserveManagement);
        // toolManagement.setToolId(tool);
        // toolManagement.setToolNumber(form.getToolNumber());

        // 登録
        model.addAttribute("toolManagement", toolManagement);
        toolManagementRepository.save(toolManagement);
        System.out.println("ok");

        return "y";
    }

    // ジム
    @RequestMapping(value = "/facilities/zymExplanation", method = RequestMethod.GET)
    public String toGymExplanation(HttpSession session) {
        return "facility/gymExplanation";
    }

    @RequestMapping(value = "/facilities/gym", method = RequestMethod.GET)
    public String toGym(HttpSession session) {
        return "facility/gym";
    }

    // テニス
    @RequestMapping(value = "/facilities/tennisExplanation", method = RequestMethod.GET)
    public String toTennisExplanation(HttpSession session) {
        return "facility/tennisExplanation";
    }

    @RequestMapping(value = "/facilities/tennis", method = RequestMethod.GET)
    public String toTennis(HttpSession session) {
        return "facility/tennis";
    }

    // バスケットボール
    @RequestMapping(value = "/facilities/basketballExplanation", method = RequestMethod.GET)
    public String toBasketballExplanation(HttpSession session) {
        return "facility/basketballExplanation";
    }

    @RequestMapping(value = "/facilities/basketball", method = RequestMethod.GET)
    public String toBasketball(HttpSession session) {
        return "facility/basketball";
    }

    // プール
    @RequestMapping(value = "/facilities/poolExplanation", method = RequestMethod.GET)
    public String toPoolExplanation(HttpSession session) {
        return "facility/poolExplanation";
    }

    @RequestMapping(value = "/facilities/pool", method = RequestMethod.GET)
    public String toPool(HttpSession session) {
        return "facility/pool";
    }

    @RequestMapping(value = "y", method = RequestMethod.GET)
    public String y() {
        return "facility/y";
    }

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
            // TODO: handle exception
            System.out.println("failed to get toolList");
        }
        return toolList;
    }

    @RequestMapping(value = "/reserve/create/input", method = RequestMethod.GET)
    public String createInput() {
        return "/reserve/create/input";
    }

    @RequestMapping(value = "/reserve/create/confirm", method = RequestMethod.POST)
    public String createConfirm(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/reserve/create/confirm";
    }

    @RequestMapping(value = "/reserve/create/back", method = RequestMethod.POST)
    public String backToCreateInput(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/reserve/create/input";
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

        return "/reserve/create/complete";
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
        // 利用した道具のIDを取得、一つずつ道具予約Entityに登録
        for (ToolForm tf : form.getToolList()) {
            int toolId = tf.getToolId();
            int toolNum = tf.getToolNum();
            ToolManagement tm = new ToolManagement();
            Optional<LendingTool> optionalEntity = lendingToolRepository.findById(toolId);
            LendingTool tool = optionalEntity.get();
            tm.setToolId(tool);
            tm.setToolNumber(toolNum);
            list.add(tm);
        }
        return list;
    }
}