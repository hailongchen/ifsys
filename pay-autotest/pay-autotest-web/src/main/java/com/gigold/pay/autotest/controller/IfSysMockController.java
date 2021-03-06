/**
 * Title: IfSysMockController.java<br/>
 * Description: <br/>
 * Copyright: Copyright (c) 2015<br/>
 * Company: gigold<br/>
 *
 */
package com.gigold.pay.autotest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gigold.pay.autotest.bo.*;
import com.gigold.pay.autotest.datamaker.ConstField;
import com.gigold.pay.autotest.service.*;
import com.gigold.pay.framework.web.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gigold.pay.framework.base.SpringContextHolder;
import com.gigold.pay.framework.bootstrap.SystemPropertyConfigure;
import com.gigold.pay.framework.core.SysCode;
import com.gigold.pay.framework.core.exception.PendingException;
import com.gigold.pay.framework.web.BaseController;
import com.gigold.pay.framework.web.ResponseDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Title: IfSysMockController<br/>
 * Description: <br/>
 * Company: gigold<br/>
 * 
 * @author xiebin
 * @date 2015年11月30日上午11:37:39
 *
 */
@Controller
@RequestMapping("/autotest")
public class IfSysMockController extends BaseController {

	@Autowired
	IfSysMockService ifSysMockService;
	@Autowired
	InterFaceFieldService interFaceFieldService;
	@Autowired
	InterFaceService interFaceService;
	@Autowired
	InterFaceSysService interFaceSysService;
	@Autowired
	InterFaceProService interFaceProService;
	@Autowired
	private RetrunCodeService retrunCodeService;
	@Autowired
	IfSysReferService ifSysReferService;

	/**
	 * @return the retrunCodeService
	 */
	public RetrunCodeService getRetrunCodeService() {
		return retrunCodeService;
	}

	/**
	 * @param retrunCodeService the retrunCodeService to set
	 */
	public void setRetrunCodeService(RetrunCodeService retrunCodeService) {
		this.retrunCodeService = retrunCodeService;
	}

	/**
	 * @return the ifSysMockService
	 */
	public IfSysMockService getIfSysMockService() {
		return ifSysMockService;
	}

	/**
	 * @param ifSysMockService
	 *            the ifSysMockService to set
	 */
	public void setIfSysMockService(IfSysMockService ifSysMockService) {
		this.ifSysMockService = ifSysMockService;
	}

	/**
	 * @return the interFaceFieldService
	 */
	public InterFaceFieldService getInterFaceFieldService() {
		return interFaceFieldService;
	}

	/**
	 * @param interFaceFieldService
	 *            the interFaceFieldService to set
	 */
	public void setInterFaceFieldService(InterFaceFieldService interFaceFieldService) {
		this.interFaceFieldService = interFaceFieldService;
	}

	/**
	 * @return the interFaceService
	 */
	public InterFaceService getInterFaceService() {
		return interFaceService;
	}

	/**
	 * @param interFaceService
	 *            the interFaceService to set
	 */
	public void setInterFaceService(InterFaceService interFaceService) {
		this.interFaceService = interFaceService;
	}

	/**
	 * 
	 * Title: addIfSysMock<br/>
	 * Description: 新增接口测试数据<br/>
	 * 
	 * @author xiebin
	 * @date 2015年12月4日下午1:20:56
	 *
	 * @param dto
	 * @return
	 */
	@RequestMapping("/addifsysmock.do")
	public @ResponseBody IfSysMockAddRspDto addIfSysMock(@RequestBody IfSysMockAddReqDto dto) {
		IfSysMockAddRspDto reDto = new IfSysMockAddRspDto();
		// 验证请求参数合法性
		String code = dto.validation();
		// 没有通过则返回对应的返回码
		if (!"00000".equals(code)) {
			reDto.setRspCd(code);
			return reDto;
		}
		IfSysMock ifSysMock = null;
		try {
			ifSysMock = createBO(dto, IfSysMock.class);
		} catch (PendingException e) {
			reDto.setRspCd(CodeItem.CREATE_BO_FAILURE);
			return reDto;
		}
		boolean flag = ifSysMockService.addIfSysMock(ifSysMock);
		if (flag) {
			reDto.setRspCd(SysCode.SUCCESS);
			reDto.setIfSysMock(ifSysMock);
		} else {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}

	/**
	 * 
	 * Title: deleteIfSysMockById<br/>
	 * Description: 根据ID删除测试数据<br/>
	 * 
	 * @author xiebin
	 * @date 2015年12月4日下午1:22:36
	 *
	 * @param dto
	 * @return
	 */
	@RequestMapping("/deleteifsysmockbyid.do")
	public @ResponseBody ResponseDto deleteIfSysMockById(@RequestBody IfSysMockDelReqDto dto) {
		ResponseDto reDto = new ResponseDto();
		boolean flag = ifSysMockService.deleteIfSysMockById(dto.getId());
		if (flag) {
			reDto.setRspCd(SysCode.SUCCESS);
		} else {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}

	/**
	 * 
	 * Title: deleteIfSysMockByIfId<br/>
	 * Description: 根据接口ID删除测试数据<br/>
	 * 
	 * @author xiebin
	 * @date 2015年12月4日下午1:21:34
	 *
	 * @param dto
	 * @return
	 */
	@RequestMapping("/deleteifsysmockbyifId.do")
	public @ResponseBody ResponseDto deleteIfSysMockByIfId(@RequestBody IfSysMockDelReqDto dto) {
		ResponseDto reDto = new ResponseDto();
		boolean flag = ifSysMockService.deleteIfSysMockByIfId(dto.getIfId());
		if (flag) {
			reDto.setRspCd(SysCode.SUCCESS);
		} else {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}

	/**
	 * 
	 * Title: updateIfSysMock<br/>
	 * Description: 修改测试数据<br/>
	 * 
	 * @author xiebin
	 * @date 2015年12月4日下午1:21:54
	 *
	 * @param dto
	 * @return
	 */
	@RequestMapping("/updateifsysmock.do")
	public @ResponseBody IfSysMockAddRspDto updateIfSysMock(@RequestBody IfSysMockAddReqDto dto) {
		IfSysMockAddRspDto reDto = new IfSysMockAddRspDto();
		// 验证请求参数合法性
		String code = dto.validation();
		// 没有通过则返回对应的返回码
		if (!"00000".equals(code)) {
			reDto.setRspCd(code);
			return reDto;
		}
		IfSysMock ifSysMock = null;
		try {
			ifSysMock = createBO(dto, IfSysMock.class);
			ifSysMock.setRequestPath(dto.getRequestPath()); // 设置用例path
			ifSysMock.setRequestHead(dto.getRequestHead());
		} catch (PendingException e) {
			reDto.setRspCd(CodeItem.CREATE_BO_FAILURE);
			return reDto;
		}
		boolean flag = ifSysMockService.updateIfSysMock(ifSysMock);
		if (flag) {
			ifSysMock=ifSysMockService.getMockInfoById(ifSysMock);
			reDto.setIfSysMock(ifSysMock);
			reDto.setRspCd(SysCode.SUCCESS);
		} else {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}

	/**
	 * 
	 * Title: getAllIfSys<br/>
	 * Description: 分页获取接口基本信息 列表页<br/>
	 * 
	 * @author xiebin
	 * @date 2015年12月2日下午1:47:35
	 *
	 * @param dto
	 * @return
	 */
	@RequestMapping("/getallifsys.do")
	public @ResponseBody IfSysMockRspDto getAllIfSys(@RequestBody IfSysMockPageDto dto) {
		debug("getAllIfSys方法");
		IfSysMockRspDto reDto = new IfSysMockRspDto();
		int curPageNum = dto.getPageNum();
		PageInfo<InterFaceInfo> pageInfo = null;
		PageHelper.startPage(curPageNum, Integer.parseInt(SystemPropertyConfigure.getProperty("sys.pageSize")));
		InterFaceInfo interFaceInfo = null;
		try {
			interFaceInfo = createBO(dto, InterFaceInfo.class);
		} catch (PendingException e) {
			debug("转换bo异常");
			e.printStackTrace();
		}

		List<InterFaceInfo> list = interFaceService.getAllIfSys(interFaceInfo);
		for(InterFaceInfo interFaceInfo1 : list){

			// 加入重组的 mocklist,去掉一些无用信息
			List<Map> mockidlist  = ifSysMockService.getInterfaceMocksById(interFaceInfo1.getId());

			// 加入返回码
			List<ReturnCode> returnCodeList = retrunCodeService.getReturnCodeByIfId(interFaceInfo1.getId());
			interFaceInfo1.setMockidList(mockidlist);
			interFaceInfo1.setReturnCodeList(returnCodeList);
		}
		if (list != null) {
			pageInfo = new PageInfo<InterFaceInfo>(list);
			reDto.setPageInfo(pageInfo);

			reDto.setRspCd(SysCode.SUCCESS);
		} else {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}

	/**
	 * 
	 * Title: getIfSysMockByIfId<br/>
	 * Description: 根据接口ID获取测试数据信息 编辑页,新增页<br/>
	 * 
	 * @author xiebin
	 * @date 2015年12月2日下午3:08:21
	 *
	 * @param dto
	 * @return
	 */
	@RequestMapping("/getifsysmockbyifid.do")
	public @ResponseBody IfStsMockRspListDto getIfSysMockByIfId(@RequestBody IfSysMockAddReqDto dto) {
		IfStsMockRspListDto reDto = new IfStsMockRspListDto();
		try {
			int ifId = dto.getIfId();
			// 获取接口基本信息
			InterFaceInfo interFaceInfo = interFaceService.getInterFaceById(ifId);
			if (interFaceInfo == null) {
				reDto.setRspCd(CodeItem.FAILURE);
				return reDto;
			}
			// 初始化测试数据
			//initMockData(interFaceInfo);
			// 获取接口测试数据
			List<IfSysMock> list = ifSysMockService.getMockInfoByIfId(ifId);
			interFaceInfo.setMockList(list);
			reDto.setRspCd(SysCode.SUCCESS);
			reDto.setInterFaceInfo(interFaceInfo);
		} catch (Exception e) {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}

	/**
	 * 
	 * Title: initMockData<br/>
	 * Description: 初始化测试数据 <br/>
	 * 
	 * @author xiebin
	 * @date 2015年12月11日上午11:42:24
	 *
	 * @param interFaceInfo
	 */
	public void initMockData(InterFaceInfo interFaceInfo) {
		int ifId = interFaceInfo.getId();
		// 初始化测试数据
		List<ReturnCode> returnList = retrunCodeService.getReturnCodeByIfId(ifId);
		// 遍历返回码 更新测试数据
		for (ReturnCode rscdObj : returnList) {
			IfSysMock mock = (IfSysMock) SpringContextHolder.getBean(IfSysMock.class);
			mock.setIfId(ifId);
			mock.setRspCodeId(rscdObj.getId());
			//测试数据表中确认是否已经存在对应返回码的测试数据
			List<IfSysMock> ifMockList = ifSysMockService.getMockInfoByIfIdAndRspCdId(mock);
			if (ifMockList == null||ifMockList.size()==0) {
				// 获取接口请求字段的JSON展示字符串
				InterFaceField interFaceField = (InterFaceField) SpringContextHolder.getBean(InterFaceField.class);
				interFaceField.setIfId(ifId);
				interFaceField.setFieldFlag("1");
				String reqJson = interFaceFieldService.getJsonStr(interFaceField);
				// 获取接口响应字段的JSON展示字符串
				interFaceField.setFieldFlag("2");
				String rspJson = interFaceFieldService.getJsonStr(interFaceField);
				mock.setRequestJson(reqJson);
				mock.setResponseJson(rspJson);
				// 如果还没有测试数据 则默认添加一条
				ifSysMockService.addIfSysMock(mock);
			}
		}

	}
	
	
	/**
	 * 
	 * Title: getmockbypage<br/>
	 * Description: 分页获取测试用例数据列表<br/>
	 * @author xiebin
	 * @date 2015年12月24日上午10:33:18
	 *
	 * @param dto
	 * @return
	 */
	@RequestMapping("/getmockbypage.do")
	public @ResponseBody IfSysMockPageRspDto getmockbypage(@RequestBody IfSysMockPageReqDto dto) {
		IfSysMockPageRspDto reDto = new IfSysMockPageRspDto();

		int pageSize = dto.getPageSize();
		PageHelper.startPage(dto.getPageNum(),pageSize>0?pageSize:5);

		IfSysMock ifSysMock=null;
		try {
			ifSysMock=createBO(dto, IfSysMock.class);
		} catch (PendingException e) {
			debug("转换bo异常");
			e.printStackTrace();
		}
		List<IfSysMock> list=ifSysMockService.queryMockByPage(ifSysMock);
		if(list!=null){
			PageInfo pageInfo=new PageInfo(list);
			reDto.setPageInfo(pageInfo);
			reDto.setRspCd(SysCode.SUCCESS);
		}else{
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
		
	}
	
	
	
	@RequestMapping("/getrspcdbyifid.do")
	public @ResponseBody RetrunCodeRspDto getReturnCodeByIfId(@RequestBody ReturnCodeReqDto dto) {
		debug("调用getReturnCodeByIfId");
		RetrunCodeRspDto rdto = new RetrunCodeRspDto();
		int ifId = dto.getIfId();
		if (ifId == 0) {
			rdto.setRspCd(CodeItem.IF_ID_FAILURE);
			return rdto;
		}
		List<ReturnCode> list = retrunCodeService.getReturnCodeByIfId(ifId);
		if (list != null) {
			rdto.setList(list);
			rdto.setRspCd(SysCode.SUCCESS);
		} else {
			rdto.setRspCd(CodeItem.FAILURE);
		}
		return rdto;
	}


	@RequestMapping("/getMockById.do")
	public @ResponseBody IfSysMockInfoRspDto getMockByMockId(@RequestBody IfSysMockAddReqDto dto) {

		IfSysMockInfoRspDto reDto = new IfSysMockInfoRspDto();
		int mockid = dto.getId();
		if (mockid == 0) {
			reDto.setRspCd(CodeItem.IF_ID_FAILURE);
			return reDto;
		}

		IfSysMock ifSysMock = new IfSysMock();
		ifSysMock.setId(mockid);
		ifSysMock = ifSysMockService.getMockInfoById(ifSysMock);

		// 获取依赖,字段依赖关系
		List<IfSysRefer> refers= ifSysReferService.getDeeplyReferList(mockid);
		List<IfSysFeildRefer> fields = ifSysReferService.queryReferFields(mockid);
		// 获取sql
		// 获取报文头
		if (ifSysMock != null) {
			reDto.setMock(ifSysMock);
			reDto.setMockReferList(refers);
			reDto.setMockFieldReferList(fields);
			reDto.setRspCd(SysCode.SUCCESS);
		} else {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}


	/**
	 * 增加字段依赖数据
	 * {
	 * 	referList:[
	 * 		{mockid:xxx,ref_mock_id:xxx,ref_feild:xxx,alias:xxx,status:xxx},
	 * 		{mockid:xxx,ref_mock_id:xxx,ref_feild:xxx,alias:xxx,status:xxx}
	 * 	]
	 * }
	 *
	 * @param dto
	 * @return
     */
	@RequestMapping("/addFieldRefer.do")
	public @ResponseBody
	IfSysFieldReferListRspDto addFieldRefer(@RequestBody IfSysFieldReferListReqDto dto) {
		IfSysFieldReferListRspDto reDto = new IfSysFieldReferListRspDto();
		// 验证请求参数合法性
		String code = dto.validation();
		// 没有通过则返回对应的返回码
		if (!"00000".equals(code)) {
			reDto.setRspCd(code);
			return reDto;
		}

		List<IfSysFeildRefer> ifSysFeildReferList = dto.getReferList();
		reDto.setList(ifSysFeildReferList);
		boolean flag = ifSysReferService.updataReferFields(ifSysFeildReferList);

		if (flag) {
			reDto.setRspCd(SysCode.SUCCESS);
		} else {
			reDto.setRspCd(CodeItem.FAILURE);
		}
		return reDto;
	}

	/**
	 * 查询字段依赖关系
	 * @param dto
	 * @return
     */
	@RequestMapping("/queryFieldRefer.do")
	public @ResponseBody
	IfSysFieldReferListRspDto queryFieldRefer(@RequestBody IfSysFieldReferReqDto dto) {
		IfSysFieldReferListRspDto reDto = new IfSysFieldReferListRspDto();
		// 验证请求参数合法性
		String code = dto.validation();
		// 没有通过则返回对应的返回码
		if (!"00000".equals(code)) {
			reDto.setRspCd(code);
			return reDto;
		}

		try {
			List<IfSysFeildRefer> ifSysFeildReferList = ifSysReferService.queryReferFields(Integer.parseInt(dto.getMockid()));
			reDto.setList(ifSysFeildReferList);
			reDto.setRspCd(SysCode.SUCCESS);
		}catch (Exception e){
			reDto.setRspCd(SysCode.SYS_FAIL);
		}
		return reDto;
	}

	/**
	 * 删除字段依赖关系
	 * @param dto
	 * @return
     */
	@RequestMapping("/deleteFieldRefer.do")
	public @ResponseBody
	IfSysFieldReferListRspDto deleteReferField(@RequestBody IfSysFieldReferReqDto dto) {
		IfSysFieldReferListRspDto reDto = new IfSysFieldReferListRspDto();
		// 验证请求参数合法性
		String code = dto.validation();
		// 没有通过则返回对应的返回码
		if (!"00000".equals(code)) {
			reDto.setRspCd(code);
			return reDto;
		}

		boolean flag = false;
		try {
			flag = ifSysReferService.deleteReferField(Integer.parseInt(dto.getId()));
			reDto.setRspCd(SysCode.SUCCESS);
		}catch (Exception e){
			reDto.setRspCd(SysCode.SYS_FAIL);
		}

		return reDto;
	}

	/**
	 * 获取用例常量域
     */
	@RequestMapping("/getConstFields.do")
	public @ResponseBody
	ResponseDto getConstFields(){
		ResponseDto reDto = new ResponseDto();
		try {
			reDto.setDataes(ConstField.getAllConstFields());
		} catch (Exception e) {
			e.printStackTrace();
		}
		reDto.setRspCd(SysCode.SUCCESS);
		return reDto;
	}

	/**
	 * 获取接口信息
	 */
	@RequestMapping("/queryInterFaceById.do")
	public @ResponseBody
	IfSysInterFaceInfoRspDto getInterFaceInfo(@RequestBody IfSysInterFaceReqDto dto){

		IfSysInterFaceInfoRspDto reDto = new IfSysInterFaceInfoRspDto();


		// 获取接口信息
		InterFaceInfo interFaceInfo = interFaceService.getInterFaceById(dto.getIfId());
		if (interFaceInfo == null || interFaceInfo.getId() == 0) {
			reDto.setRspCd(CodeItem.FAILURE);
			return reDto;
		}


		// 获取所属系统信息
		InterFaceSysTem interFaceSysTem = (InterFaceSysTem) SpringContextHolder.getBean(InterFaceSysTem.class);
		interFaceSysTem.setId(interFaceInfo.getIfSysId());
		interFaceSysTem = interFaceSysService.getSysInfoById(interFaceSysTem);
		if (interFaceSysTem == null) {
			reDto.setRspCd(CodeItem.FAILURE);
			return reDto;
		}

		// 获取返回码列表
		List<ReturnCode> returnCodes = retrunCodeService.getReturnCodeByIfId(dto.getIfId());

		// 设置接口信息
		reDto.setInterFaceInfo(interFaceInfo);
		// 设置接口所属系统信息
		reDto.setSystem(interFaceSysTem);
		// 设置接口所属产品信息
		reDto.setReturnCodeList(returnCodes);

		reDto.setRspCd(SysCode.SUCCESS);
		return reDto;
	}

//	/**
//	 * 获取接口信息
//	 */
//	@RequestMapping("/getIndexTree.do")
//	public @ResponseBody IfSysMockRspDto getIndexTree(@RequestBody IfSysMockPageDto dto) {
//		debug("getAllIfSys方法");
//		IfSysMockRspDto reDto = new IfSysMockRspDto();
//		int curPageNum = dto.getPageNum();
//		PageInfo<InterFaceInfo> pageInfo = null;
//		PageHelper.startPage(curPageNum, Integer.parseInt(SystemPropertyConfigure.getProperty("sys.pageSize")));
//		InterFaceInfo interFaceInfo = null;
//		try {
//			interFaceInfo = createBO(dto, InterFaceInfo.class);
//		} catch (PendingException e) {
//			debug("转换bo异常");
//			e.printStackTrace();
//		}
//		List<InterFaceInfo> list = interFaceService.getAllIfSys(interFaceInfo);
//		if (list != null) {
//			pageInfo = new PageInfo<InterFaceInfo>(list);
//			reDto.setPageInfo(pageInfo);
//			reDto.setRspCd(SysCode.SUCCESS);
//		} else {
//			reDto.setRspCd(CodeItem.FAILURE);
//		}
//		return reDto;
//	}
}
