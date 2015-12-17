package com.gigold.pay.ifsys.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gigold.pay.framework.core.SysCode;
import com.gigold.pay.framework.web.BaseController;
import com.gigold.pay.ifsys.bo.InterFacePro;
import com.gigold.pay.ifsys.service.InterFaceProService;

@Controller
public class InterFaceProController extends BaseController{
    @Autowired
    InterFaceProService interFaceProService;
    @RequestMapping(value = "/getProInfoByIfId.do")
    public @ResponseBody InterFaceProResponseDto getProInfoByIfId( @RequestBody InterFaceProRequestDto qdto) {
        InterFacePro interFacePro = qdto.getInterFacePro();
        InterFaceProResponseDto dto = new InterFaceProResponseDto();
        List<InterFacePro> rlist = new ArrayList<InterFacePro>();
        interFacePro = interFaceProService.getProInfoById(interFacePro);
        if (interFacePro != null) {
            rlist.add(interFacePro);
            dto.setProList(rlist);
            dto.setRspCd(SysCode.SUCCESS);
        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }

        return dto;

    }
    
    @RequestMapping(value = "/getProInfoBySysId.do")
    public @ResponseBody InterFaceProResponseDto getProInfoBySysId( @RequestBody InterFaceProRequestDto qdto) {
        InterFaceProResponseDto dto = new InterFaceProResponseDto();
        List<InterFacePro> rlist = interFaceProService.getProInfoBySysId(qdto.getInterFacePro());
        if (rlist != null) {
            dto.setProList(rlist);
            dto.setRspCd(SysCode.SUCCESS);
        } else {
            dto.setRspCd(CodeItem.IF_FAILURE);
        }

        return dto;

    }

}
