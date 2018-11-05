package com.jeeplus.common.sms;

import com.jeeplus.common.utils.DesUtil;
import com.jeeplus.common.utils.FormatUtil;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SignatureApiUtil {
	/*
	 * signature:签名 机器id_用户id_时间_加密字段
	 * date：时间戳
	 */
	public static String checkSignature(String signature,Date date){
		Map map=new HashMap<String, Object>();
		if(signature==null||signature==""){
			map.put("status", "n");
			map.put("info", "签名字段不能为空！");
			JSONObject json=JSONObject.fromObject(map);
			System.out.println("json:"+json.toString());
			return json.toString();
		}
		if(date==null){
			map.put("status", "n");
			map.put("info", "时间戳不能为空！");
			JSONObject json=JSONObject.fromObject(map);
			System.out.println("json:"+json.toString());
			return json.toString();
		}
		Date nowDate= new Date();
		long millsecond= FormatUtil.dayTimeBetween(date, nowDate);
		if(millsecond>3600000){//与当前时间相差1小时
			map.put("status", "n");
			map.put("info", "时间已过期！");
			JSONObject json=JSONObject.fromObject(map);
			System.out.println("json:"+json.toString());
			return json.toString();
		}
		
		//签名解密
		String designature=DesUtil.decrypt(signature);
		String[] keys=designature.split("_");
		
		//密文时间
		String encriptTime=keys[2];//yyyy-MM-dd HH:mm:ss
		String dateString=FormatUtil.DateToString(date, "yyyy-MM-dd HH:mm:ss");
		//验证时间戳
		if(!dateString.equals(encriptTime)){//不一致
			map.put("status", "n");
			map.put("info", "验证失败！");
			JSONObject json=JSONObject.fromObject(map);
			System.out.println("json:"+json.toString());
			return json.toString();
		}
		
		/*try {
			//验证token
			String machineid=keys[0];
			if(FormatUtil.isNoEmpty(machineid)){
				if(BaseConst.memcachedClient.get("token"+machineid) != null){
					String prevSignature=BaseConst.memcachedClient.get("token"+machineid);
					if(prevSignature.equals(signature)){
						//重复提交
						map.put("status", "n");
						map.put("info", "重复提交！");
						JSONObject json=JSONObject.fromObject(map);
						System.out.println("json:"+json.toString());
						return json.toString();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "n");
			map.put("info", "menmcache出错！");
			JSONObject json=JSONObject.fromObject(map);
			System.out.println("json:"+json.toString());
			return json.toString();
		}*/
		
		map.put("status", "y");
		map.put("info", "验证通过！");
		JSONObject json=JSONObject.fromObject(map);
		System.out.println("json:"+json.toString());
		return json.toString();
	}
	
	/**
	 * 生成签名字符串
	 * @param machineId 机器id
	 * @param userId 用户id
	 * @param keyStr 加密字段 jinying
	 * @return
	 */
	public static String createSignature(String machineId,String userId,String keyStr,String dateStr){
		Map map=new HashMap<String, Object>();
		String signatureStr=(machineId==null?"":machineId)+"_"+(userId==null?"":userId)+"_"+dateStr+"_"+(keyStr==null?"":keyStr);
		return DesUtil.encrypt(signatureStr);
	}
	
}
