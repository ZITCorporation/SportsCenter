package jp.co.sss.sportsCenter.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.sss.sportsCenter.entity.BulletinBoard;
import jp.co.sss.sportsCenter.repository.BulletinBoardRepository;

@Controller
public class BulletinBoardController {

    @Autowired
    BulletinBoardRepository bulletinBoardRepository;

    @RequestMapping(value = "/bbs/create", method = RequestMethod.POST)
    public String recordNewBBS(HttpServletRequest request) {
        BulletinBoard bb = new BulletinBoard();
        bb.setBbsAuthor(request.getParameter("bbsAuthor"));
        bb.setBbsContent(request.getParameter("bbsContent"));
        bb.setBbsTitle(request.getParameter("bbsTitle"));
        bb.setCreateTime(new Timestamp(new Date().getTime()) );
        bulletinBoardRepository.save(bb);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/bbs/listAllBBS", method = RequestMethod.GET)
    public List<BulletinBoard> listAllBBS() {
        List<BulletinBoard> list = bulletinBoardRepository.findAll(Sort.by("createTime").descending());
        return list;
    }

}
