package jp.co.sss.sportsCenter.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.sportsCenter.entity.LendingFacility;
import jp.co.sss.sportsCenter.entity.LendingTool;
import jp.co.sss.sportsCenter.entity.ReserveManagement;
import jp.co.sss.sportsCenter.entity.ToolManagement;
import jp.co.sss.sportsCenter.entity.User;
import jp.co.sss.sportsCenter.form.ReserveForm;
import jp.co.sss.sportsCenter.repository.LendingFacilityRepository;
import jp.co.sss.sportsCenter.repository.LendingToolRepository;
import jp.co.sss.sportsCenter.repository.ReserveManegementRepository;
import jp.co.sss.sportsCenter.repository.ToolManagementRepository;
import jp.co.sss.sportsCenter.repository.UserRepository;

import jp.co.sss.sportsCenter.form.ReserveForm;
import jp.co.sss.sportsCenter.repository.FacilityRepository;
import jp.co.sss.sportsCenter.repository.ReserveManegementRepository;
import jp.co.sss.sportsCenter.repository.ToolManegementRepository;
import jp.co.sss.sportsCenter.repository.ToolRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings("unchecked")
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
    @Autowired
    ToolRepository toolRepository;

    @RequestMapping(value = "/facilities", method = RequestMethod.GET)
    public String toFacility() {
        return "facility/listFacilities";
    }

    // アーチェリー
    @RequestMapping(value = "/facilities/archeryExplanation", method = RequestMethod.GET)
    public String toArcheryExplanation() {
        return "facility/archeryExplanation";
    }

    @RequestMapping(value = "/facilities/archery", method = RequestMethod.GET)
    public String toArchery() {
        return "facility/archery";
    }

    // ジム
    @RequestMapping(value = "/facilities/zymExplanation", method = RequestMethod.GET)
    public String toGymExplanation() {
        return "facility/gymExplanation";
    }

    @RequestMapping(value = "/facilities/gym", method = RequestMethod.GET)
    public String toGym() {
        return "facility/gym";
    }

    // テニス
    @RequestMapping(value = "/facilities/tennisExplanation", method = RequestMethod.GET)
    public String toTennisExplanation() {
        return "facility/tennisExplanation";
    }

    @RequestMapping(value = "/facilities/tennis", method = RequestMethod.GET)
    public String toTennis() {
        return "facility/tennis";
    }

    // バスケットボール
    @RequestMapping(value = "/facilities/basketballExplanation", method = RequestMethod.GET)
    public String toBasketballExplanation() {
        return "facility/basketballExplanation";
    }

    @RequestMapping(value = "/facilities/basketball", method = RequestMethod.GET)
    public String toBasketball() {
        return "facility/basketball";
    }

    // プール
    @RequestMapping(value = "/facilities/poolExplanation", method = RequestMethod.GET)
    public String toPoolExplanation() {
        return "facility/poolExplanation";
    }

    @RequestMapping(value = "/facilities/pool", method = RequestMethod.GET)
    public String toPool() {
        return "facility/pool";
    }

    // 予約
    @RequestMapping(value = "facility/archery", method = RequestMethod.POST)
    public String createConfirm(ReserveForm form, Model model, HttpSession session) {
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
        LendingTool tool = lendingToolRepository.getOne(form.getToolId());
        System.out.println("道具ID:" + tool.getToolId());

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
        toolManagement.setToolId(tool);
        toolManagement.setToolNumber(form.getToolNumber());

        // 登録
        model.addAttribute("toolManagement", toolManagement);
        toolManagementRepository.save(toolManagement);
        System.out.println("ok");

        return "y";
    }

    @RequestMapping(value = "y", method = RequestMethod.GET)
    public String y() {
        return "facility/y";
    }

    @ResponseBody
    @GetMapping(value = "/getFacility")
    public List<LendingFacility> getFacility(HttpSession session) {
        List<LendingFacility> facilityList = (List<LendingFacility>) session.getAttribute("facilityList");
        if (facilityList == null || facilityList.size() == 0) {
            facilityList = lendingFacilityRepository.findAll();
        }
        return facilityList;
    }

    @ResponseBody
    @GetMapping(value = "/getAllTool")
    public List<LendingTool> getAllTool(HttpSession session) {
        List<LendingTool> toolList = (List<LendingTool>) session.getAttribute("toolList");
        if (toolList == null || toolList.size() == 0) {
            toolList = toolRepository.findAll();
            session.setAttribute("toolList", toolList);
        }
        return toolList;
    }

    // @RequestMapping(value = "/getSpecTool/{id}", method = RequestMethod.GET)
    // @ResponseBody
    // public List<LendingTool> getSpecTool(@PathVariable("facilityId")
    // LendingFacility facilityId, HttpSession session) {
    // List<LendingTool> toolList = (List<LendingTool>)
    // session.getAttribute("toolList");
    // if (toolList == null || toolList.size() == 0) {
    // toolList = toolRepository.findByFacilityId(facilityId);
    // session.setAttribute("toolList", toolList);
    // }
    // System.out.println(toolList);
    // return toolList;
    // }

    @ResponseBody
    @RequestMapping(value = "/getSpecTool", method = RequestMethod.GET)
    public List<LendingTool> getSpecTool(HttpServletRequest request, HttpSession session) {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<LendingFacility> optionalEntity = lendingFacilityRepository.findById(id);
        LendingFacility facility = optionalEntity.get();

        // List<LendingTool> toolList = (List<LendingTool>)
        // session.getAttribute("toolList");
        // if (toolList == null || toolList.size() == 0) {
        List<LendingTool> toolList = toolRepository.findByFacilityId(facility);
        session.setAttribute("toolList", toolList);
        // }
        System.out.println(toolList);
        return toolList;
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public String toReserve() {
        return "/reserve/create/input";
    }

    @RequestMapping(value = "/reserve/input", method = RequestMethod.POST)
    public String createReserve(@ModelAttribute("form") @Valid ReserveForm form) {
        ReserveManagement reserve = new ReserveManagement(form.getUserId(), form.getDate(), form.getStartTime(),
                form.getEndingTime(), form.getFacilityId());
        reserveManegementRepository.save(reserve);

        for (ToolManagement tool : form.getToolList()) {
            toolManagementRepository.save(tool);
        }

        return "/reserve/complete";
    }

}