/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ehr.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.FormatUtil;
import com.jeeplus.modules.ehr.entity.Posttrain;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.ehr.entity.Trainplan;
import com.jeeplus.modules.ehr.dao.TrainplanDao;
import com.jeeplus.modules.ehr.entity.Trainee;
import com.jeeplus.modules.ehr.dao.TraineeDao;

/**
 * 培训计划Service
 * @author cqj
 * @version 2018-05-25
 */
@Service
@Transactional(readOnly = true)
public class TrainplanService extends CrudService<TrainplanDao, Trainplan> {

	@Autowired
	private TraineeDao traineeDao;
	@Autowired
	private PosttrainService posttrainService;
	
	public Trainplan get(String id) {
		Trainplan trainplan = super.get(id);
		trainplan.setTraineeList(traineeDao.findList(new Trainee(trainplan)));
		return trainplan;
	}
	
	public List<Trainplan> findList(Trainplan trainplan) {
		return super.findList(trainplan);
	}
	
	public Page<Trainplan> findPage(Page<Trainplan> page, Trainplan trainplan) {
		return super.findPage(page, trainplan);
	}

	public Page<Trainee> find(Page<Trainee> page, Trainee trainee) {
		trainee.setPage(page);
		page.setList(traineeDao.findList(trainee));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Trainplan trainplan) {
		super.save(trainplan);
		// 更新发送接受人记录
		traineeDao.deleteByTrainplanId(trainplan.getId());
		if (trainplan.getTraineeList().size() > 0){
			for (Trainee tr:trainplan.getTraineeList()) {
				User usr=UserUtils.get(tr.getUserid());
				tr.setUsername(usr.getName());
				tr.setOfficename(usr.getOfficeName());
				tr.setOfficeid(usr.getOffice().getId());
				tr.setCreateDate(new Date());
			}
			traineeDao.insertAll(trainplan.getTraineeList());
		}
		/*//如果是已完成则添加培训记录
		if(trainplan.getStatus().equals("1")){//培训已完成
			Posttrain posttraintemp=new Posttrain();
			posttraintemp.setTrainplan(trainplan.getId());
			List<Posttrain> posttrainList=posttrainService.findList(posttraintemp);
			Posttrain posttrain=null;
			if(FormatUtil.isNoEmpty(posttrainList)&&posttrainList.size()>0){//修改
				posttrain=posttrainList.get(0);
				User usr=UserUtils.get(trainplan.getUserid());
				posttrain.setCompany(usr.getCompany().getId());
				posttrain.setDepart(trainplan.getOfficeid());
				posttrain.setOffice(trainplan.getOfficeid());
				posttrain.setTrainer(trainplan.getUsername());
				posttrain.setUpdateBy(usr);
				posttrain.setTitle(trainplan.getTitle());
				posttrain.setTraintime(trainplan.getTraindate());
				posttrain.setSummary(trainplan.getCompletesituation());
				posttrain.setOrganizer(usr.getOfficeName());
				posttrainService.savePosttrain(posttrain);//保存
			}else{//新增
				posttrain=new Posttrain();
				User usr=UserUtils.get(trainplan.getUserid());
				posttrain.setCompany(usr.getCompany().getId());
				posttrain.setDepart(trainplan.getOfficeid());
				posttrain.setOffice(trainplan.getOfficeid());
				posttrain.setTrainer(trainplan.getUsername());
				posttrain.setCreateBy(usr);
				posttrain.setTitle(trainplan.getTitle());
				posttrain.setTraintime(trainplan.getTraindate());
				posttrain.setSummary(trainplan.getCompletesituation());
				posttrain.setOrganizer(usr.getOfficeName());
				posttrain.setTrainplan(trainplan.getId());
				posttrainService.savePosttrain(posttrain);//保存
			}
		}else{
			Posttrain posttraintemp=new Posttrain();
			posttraintemp.setTrainplan(trainplan.getId());
			List<Posttrain> posttrainList=posttrainService.findList(posttraintemp);
			if(FormatUtil.isNoEmpty(posttrainList)&&posttrainList.size()>0){
				posttrainService.delete(posttrainList.get(0));
			}
		}*/
	}
	
	@Transactional(readOnly = false)
	public void delete(Trainplan trainplan) {
		/*Posttrain posttraintemp=new Posttrain();
		posttraintemp.setTrainplan(trainplan.getId());
		List<Posttrain> posttrainList=posttrainService.findList(posttraintemp);
		if(FormatUtil.isNoEmpty(posttrainList)&&posttrainList.size()>0){
			posttrainService.delete(posttrainList.get(0));
		}*/
		super.delete(trainplan);
		traineeDao.delete(new Trainee(trainplan));

	}
	
}