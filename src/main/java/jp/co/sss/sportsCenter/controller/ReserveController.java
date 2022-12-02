package jp.co.sss.sportsCenter.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    
    @RequestMapping(value="/facilities", method=RequestMethod.GET)
    public String toFacility(HttpSession session) {
        return "facility/listFacilities";
    }
    
    // アーチェリー
    @RequestMapping(value="/facilities/archeryExplanation", method=RequestMethod.GET)
    public String toArcheryExplanation(HttpSession session) {
        return "facility/archeryExplanation";
    }
    // 予約
    @RequestMapping(value = "/facility/archery", method = RequestMethod.POST)
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
       User user = userRepository.getOne((Integer)session.getAttribute("id"));
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
        reserveManagement = reserveManegementRepository.findByUserIdAndStartTimeAndEndingTime(reserveManagement.getUserId(), reserveManagement.getStartTime(), reserveManagement.getEndingTime());
        
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
    
    // ジム
    @RequestMapping(value="/facilities/zymExplanation", method=RequestMethod.GET)
    public String toGymExplanation(HttpSession session) {
        return "facility/gymExplanation";
    }
    @RequestMapping(value="/facilities/gym", method=RequestMethod.GET)
    public String toGym(HttpSession session) {
        return "facility/gym";
    }
    
    // テニス
    @RequestMapping(value="/facilities/tennisExplanation", method=RequestMethod.GET)
    public String toTennisExplanation(HttpSession session) {
        return "facility/tennisExplanation";
    }
    @RequestMapping(value="/facilities/tennis", method=RequestMethod.GET)
    public String toTennis(HttpSession session) {
        return "facility/tennis";
    }
    
    // バスケットボール
    @RequestMapping(value="/facilities/basketballExplanation", method=RequestMethod.GET)
    public String toBasketballExplanation(HttpSession session) {
        return "facility/basketballExplanation";
    }
    @RequestMapping(value="/facilities/basketball", method=RequestMethod.GET)
    public String toBasketball(HttpSession session) {
        return "facility/basketball";
    }
    
    // プール
    @RequestMapping(value="/facilities/poolExplanation", method=RequestMethod.GET)
    public String toPoolExplanation(HttpSession session) {
        return "facility/poolExplanation";
    }
    @RequestMapping(value="/facilities/pool", method=RequestMethod.GET)
    public String toPool(HttpSession session) {
        return "facility/pool";
    }
    
    @RequestMapping(value="y", method=RequestMethod.GET)
    public String y() {
        return "facility/y";
    }
}