package jp.co.sss.sportsCenter.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
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

    private ReserveManagement form2Reserve(HttpSession session, ReserveManagement reserveManagement,
            ReserveForm form) {
        String st = form.getStartTime();
        String et = form.getEndingTime();
        System.out.println(st + " start1\nend" + et);

        // ????????????????????????
        String d = form.getDate();
        st = d + " " + st + ":00";
        et = d + " " + et + ":00";

        System.out.println(st + " start2\nend" + et);

        // String?????????Timestamp????????????
        Timestamp timestamp1 = Timestamp.valueOf(st);
        Timestamp timestamp2 = Timestamp.valueOf(et);

        // ???????????????????????????
        User user = userRepository.getReferenceById(Integer.parseInt(String.valueOf(session.getAttribute("id"))));
        System.out.println("????????????ID:" + user.getUserId());

        Optional<LendingFacility> optionalEntity = lendingFacilityRepository.findById(form.getFacilityId());
        LendingFacility facility = optionalEntity.get();

        reserveManagement.setUserId(user);
        reserveManagement.setFacilityId(facility);
        reserveManagement.setStartTime(timestamp1);
        reserveManagement.setEndingTime(timestamp2);

        return reserveManagement;
    }

    private List<ToolManagement> form2ToolManagementList(List<ToolManagement> list, ReserveForm form) {
        List<ToolManagement> returnList = new ArrayList<ToolManagement>();
        // ?????????????????????ID????????????????????????????????????Entity?????????
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

    // AJAX???????????????????????????????????????????????????????????????????????????????????????
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

    // AJAX?????????????????????????????????
    @ResponseBody
    @GetMapping("/reserve/create/getFacilities")
    public List<LendingFacility> getFacilities(HttpSession session) {
        List<LendingFacility> facilityList = null;
        try {
            facilityList = lendingFacilityRepository.findAll();
        } catch (Exception e) {
            System.out.println("failed to get facilityList");
        }
        return facilityList;
    }

    // ????????????????????????
    @GetMapping("/reserve/findAll")
    public String usersReserve(HttpSession session, Model model) {

        // ??????????????????

        List<ReserveManagement> rm = reserveManegementRepository.findAll(Sort.by("reserveManagementId").descending());
        model.addAttribute("rm", rm);
        return "/reserve/reserve_list";
    }

    // ?????????????????????????????????
    @GetMapping("/reserve/findByUser")
    public String findByUser(HttpSession session, Model model) {

        // ??????????????????

        User user = userRepository.getReferenceById(Integer.parseInt(String.valueOf(session.getAttribute("id"))));
        List<ReserveManagement> rm = reserveManegementRepository.findByUserId(user,
                Sort.by("reserveManagementId").descending());
        model.addAttribute("rm", rm);
        return "/reserve/reserve_list";
    }

    // ????????????
    @GetMapping("/reserve/reserveDetail/{id}")
    public String usersReserveDetail(HttpSession session, Model model, @PathVariable("id") int id) {

        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

        Optional<ReserveManagement> op = reserveManegementRepository.findById(id);
        ReserveManagement rm = op.get();
        List<ToolManagement> toolList = toolManagementRepository.findByReserveManagementId(rm);
        model.addAttribute("rm", rm);
        model.addAttribute("toolList", toolList);
        return "/reserve/reserve_detail";
    }

    // ????????????
    @GetMapping("/reserve/create")
    public String createInput(HttpSession session) {
        session.setAttribute("reserveStatus", "create");
        return "/reserve/input";
    }

    // ???????????????????????????????????????????????????????????????ID???????????????
    @GetMapping("/reserve/create/{id}")
    public String createInputWithId(HttpSession session, Model model, @PathVariable("id") int id) {
        session.setAttribute("reserveStatus", "create");
        model.addAttribute("preSelectFacilityId", id);
        return "/reserve/input";
    }

    // ??????????????????
    @PostMapping("/reserve/create/confirm")
    public String createConfirm(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/reserve/confirm";
    }

    // ??????????????????????????????????????????
    @PostMapping("/reserve/create/back")
    public String backToCreateInput(@ModelAttribute("form") @Valid ReserveForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    // ??????????????????
    @PostMapping("/reserve/create/complete")
    public String createComplete(@ModelAttribute("form") @Valid ReserveForm form, HttpSession session) {

        ReserveManagement reserveManagement = new ReserveManagement();
        List<ToolManagement> toolManagementList = new ArrayList<>();

        toolManagementList = form2ToolManagementList(toolManagementList, form);
        reserveManagement = form2Reserve(session, reserveManagement, form);

        reserveManegementRepository.save(reserveManagement);

        // ???????????????????????????
        reserveManagement = reserveManegementRepository.findByUserIdAndStartTimeAndEndingTime(
                reserveManagement.getUserId(), reserveManagement.getStartTime(), reserveManagement.getEndingTime());

        // ?????????????????????ID?????????
        for (ToolManagement tm : toolManagementList) {
            tm.setReserveManagementId(reserveManagement);
            toolManagementRepository.save(tm);
        }

        return "/reserve/complete";
    }

    // ????????????
    @PostMapping("/reserve/update/input")
    public String reserveUpdate(@Valid ReserveForm form, HttpSession session, Model model) {
        session.setAttribute("reserveStatus", "update");
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    // ??????????????????
    @PostMapping("/reserve/update/confirm")
    public String reserveUpdateConfirm(@Valid ReserveForm form, HttpSession session, Model model) {
        model.addAttribute("form", form);
        return "/reserve/confirm";
    }

    // ??????????????????????????????
    @PostMapping("/reserve/update/back")
    public String reserveUpdateConfirmBack(@Valid ReserveForm form, HttpSession session, Model model) {
        model.addAttribute("form", form);
        return "/reserve/input";
    }

    // ??????????????????
    @PostMapping("/reserve/update/complete")
    public String reserveUpdateComplete(@Valid ReserveForm form, HttpSession session, Model model) {
        int id = form.getReserveManagementId();
        Optional<ReserveManagement> op = reserveManegementRepository.findById(id);
        ReserveManagement reserveManagement = op.get();
        List<ToolManagement> toolManagementList = toolManagementRepository.findByReserveManagementId(reserveManagement);

        toolManagementList = form2ToolManagementList(toolManagementList, form);
        reserveManagement = form2Reserve(session, reserveManagement, form);

        reserveManegementRepository.save(reserveManagement);

        // ?????????????????????ID?????????
        for (ToolManagement tm : toolManagementList) {
            tm.setReserveManagementId(reserveManagement);
            toolManagementRepository.save(tm);
        }
        return "/reserve/complete";
    }

    // ????????????
    @PostMapping("/reserve/delete")
    public String reserveDelete(HttpSession session, HttpServletRequest request) {

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

        session.setAttribute("reserveStatus", "delete");
        String index = request.getParameter("index");
        reserveManegementRepository.deleteById(Integer.parseInt(index));
        return "/reserve/complete";
    }
}