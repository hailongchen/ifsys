/**
 * Title: WorkerThread.java<br/>
 * Description: <br/>
 * Copyright: Copyright (c) 2015<br/>
 * Company: gigold<br/>
 *
 */
package com.gigold.pay.autotest.threadpool;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gigold.pay.autotest.bo.IfSysMock;
import com.gigold.pay.autotest.bo.InterFaceInfo;
import com.gigold.pay.autotest.service.IfSysAutoTestService;
import com.gigold.pay.autotest.service.IfSysMockService;
import com.gigold.pay.framework.base.SpringContextHolder;

/**
 * Title: CheckThread<br/>
 * Description: 用来检测接口的线程<br/>
 * Company: gigold<br/>
 * 
 * @author xiebin
 * @date 2015年11月27日下午5:45:51
 *
 */
public class CheckThread implements Runnable {
	IfSysMockService ifSysMockService;
    IfSysAutoTestService ifSysAutoTestService;
	// 存放接口列表
	private List<InterFaceInfo> ifsyslist;

	public CheckThread(IfSysMockService ifSysMockService, List<InterFaceInfo> ifsyslist) {
		this.ifsyslist = ifsyslist;
		this.ifSysMockService = ifSysMockService;
		ifSysAutoTestService=(IfSysAutoTestService)SpringContextHolder.getBean(IfSysAutoTestService.class);
	}

	@Override
	public void run() {
		processCommand();
	}

	// 处理接口测试
	private void processCommand() {
		// 写检测接口的代码
		for (InterFaceInfo interFaceInfo : ifsyslist) {
			int ifId = interFaceInfo.getId();
			List<IfSysMock> mockList = ifSysMockService.getMockInfoByIfId(ifId);
			interFaceInfo.setMockList(mockList);
			ifSysAutoTestService.autoTest(interFaceInfo);
		}
	}

}