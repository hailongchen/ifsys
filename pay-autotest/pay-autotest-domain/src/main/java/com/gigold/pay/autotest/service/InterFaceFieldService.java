package com.gigold.pay.autotest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.gigold.pay.autotest.bo.InterFaceField;
import com.gigold.pay.autotest.dao.InterFaceFieldDao;

@Service
public class InterFaceFieldService {

    @Autowired
    InterFaceFieldDao interFaceFieldDao;

    public List<InterFaceField>  getFirstReqFieldByIfId(InterFaceField interFaceField) {
    	 StringBuilder ss=new StringBuilder();
         ss.append("{");
    	List<InterFaceField> rlist=interFaceFieldDao.getFirstReqFieldByIfId(interFaceField);
    	proJSON(ss,rlist,interFaceField.getFieldCheck());
        String jsonStr=ss.toString().replaceAll(",\\}", "}").replaceAll(",\\]", "]");
        jsonStr=jsonStr.substring(0, jsonStr.length()-1);
    	return null;
    }
    /**
     * 
     * Title: proJSON<br/>
     * 将接口请求部分解析成JSON字符串: <br/>
     * @author xb
     * @date 2015年10月12日上午9:32:51
     *
     * @param ss
     * @param list
     */
    public void proJSON(StringBuilder ss,List<InterFaceField> list,String parentFieldCheck){
       int i=0;
       
       InterFaceField ff=null;
        for(i=0;i<list.size();i++){
            ff =list.get(i);
          List<InterFaceField> clist=interFaceFieldDao.getFieldByparentId(ff);
            ss.append("\""+ff.getFieldName()+"\":");
            if(clist!=null&&clist.size()>0){
               if("4".equals(ff.getFieldCheck())){
                   ss.append("[{"); 
               }else{
                ss.append("{\n"); 
               }
               proJSON(ss,clist,ff.getFieldCheck());
            }else{
                ss.append("\""+ff.getFieldReferValue()+"\"");
                if(i<list.size()-1){
                    ss.append(",");
                  } 
            }
        }
        
        if(i>0){
           if(!StringUtils.isEmpty(parentFieldCheck)&&parentFieldCheck.equals("4")){
               ss.append("}]");
           }else{
               ss.append("}");
           }
            ss.append(",");
        }
       
    }
    
}
