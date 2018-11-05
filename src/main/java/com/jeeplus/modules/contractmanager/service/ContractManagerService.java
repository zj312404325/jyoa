/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contractmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.contractmanager.dao.ContractActualFundsDao;
import com.jeeplus.modules.contractmanager.dao.ContractFileDao;
import com.jeeplus.modules.contractmanager.dao.ContractInvoiceDao;
import com.jeeplus.modules.contractmanager.dao.ContractManagerDao;
import com.jeeplus.modules.contractmanager.dao.ContractNoteDao;
import com.jeeplus.modules.contractmanager.dao.ContractPlanfundDao;
import com.jeeplus.modules.contractmanager.dao.ContractSubjectDao;
import com.jeeplus.modules.contractmanager.dao.ContractTextDao;
import com.jeeplus.modules.contractmanager.entity.ContractActualFunds;
import com.jeeplus.modules.contractmanager.entity.ContractFile;
import com.jeeplus.modules.contractmanager.entity.ContractInvoice;
import com.jeeplus.modules.contractmanager.entity.ContractManager;
import com.jeeplus.modules.contractmanager.entity.ContractNote;
import com.jeeplus.modules.contractmanager.entity.ContractPlanfund;
import com.jeeplus.modules.contractmanager.entity.ContractSubject;
import com.jeeplus.modules.contractmanager.entity.ContractText;

/**
 * 合同管理-合同Service
 * @author yc
 * @version 2017-12-07
 */
@Service
@Transactional(readOnly = true)
public class ContractManagerService extends CrudService<ContractManagerDao, ContractManager> {

	@Autowired
	private ContractFileDao contractFileDao;
	@Autowired
	private ContractNoteDao contractNoteDao;
	@Autowired
	private ContractPlanfundDao contractPlanfundDao;
	@Autowired
	private ContractSubjectDao contractSubjectDao;
	@Autowired
	private ContractTextDao contractTextDao;
	@Autowired
	private ContractActualFundsDao contractActualFundsDao;
	@Autowired
	private ContractInvoiceDao contractInvoiceDao;
	
	public ContractManager get(String id) {
		ContractManager contractManager = super.get(id);
		contractManager.setContractActualFundsList(contractActualFundsDao.findList(new ContractActualFunds(contractManager)));
		contractManager.setContractFileList(contractFileDao.findList(new ContractFile(contractManager)));
		contractManager.setContractInvoiceList(contractInvoiceDao.findList(new ContractInvoice(contractManager)));
		contractManager.setContractNoteList(contractNoteDao.findList(new ContractNote(contractManager)));
		contractManager.setContractPlanfundList(contractPlanfundDao.findList(new ContractPlanfund(contractManager)));
		contractManager.setContractSubjectList(contractSubjectDao.findList(new ContractSubject(contractManager)));
		contractManager.setContractTextList(contractTextDao.findList(new ContractText(contractManager)));
		return contractManager;
	}
	
	public ContractManager get(ContractManager contractManager) {
		contractManager.setContractActualFundsList(contractActualFundsDao.findList(new ContractActualFunds(contractManager)));
		contractManager.setContractFileList(contractFileDao.findList(new ContractFile(contractManager)));
		contractManager.setContractInvoiceList(contractInvoiceDao.findList(new ContractInvoice(contractManager)));
		contractManager.setContractNoteList(contractNoteDao.findList(new ContractNote(contractManager)));
		contractManager.setContractPlanfundList(contractPlanfundDao.findList(new ContractPlanfund(contractManager)));
		contractManager.setContractSubjectList(contractSubjectDao.findList(new ContractSubject(contractManager)));
		contractManager.setContractTextList(contractTextDao.findList(new ContractText(contractManager)));
		return contractManager;
	}
	
	public List<ContractManager> findList(ContractManager contractManager) {
		return super.findList(contractManager);
	}
	
	public Page<ContractManager> findPage(Page<ContractManager> page, ContractManager contractManager) {
		return super.findPage(page, contractManager);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractManager contractManager) {
		super.save(contractManager);
		for (ContractFile contractFile : contractManager.getContractFileList()){
			if (contractFile.getId() == null){
				continue;
			}
			if (ContractFile.DEL_FLAG_NORMAL.equals(contractFile.getDelFlag())){
				if (StringUtils.isBlank(contractFile.getId())){
					contractFile.setContractmanager(contractManager);
					contractFile.preInsert();
					contractFileDao.insert(contractFile);
				}else{
					contractFile.preUpdate();
					contractFileDao.update(contractFile);
				}
			}else{
				contractFileDao.delete(contractFile);
			}
		}
		for (ContractNote contractNote : contractManager.getContractNoteList()){
			if (contractNote.getId() == null){
				continue;
			}
			if (ContractNote.DEL_FLAG_NORMAL.equals(contractNote.getDelFlag())){
				if (StringUtils.isBlank(contractNote.getId())){
					contractNote.setContractmanager(contractManager);
					contractNote.preInsert();
					contractNoteDao.insert(contractNote);
				}else{
					contractNote.preUpdate();
					contractNoteDao.update(contractNote);
				}
			}else{
				contractNoteDao.delete(contractNote);
			}
		}
		for (ContractPlanfund contractPlanfund : contractManager.getContractPlanfundList()){
			if (contractPlanfund.getId() == null){
				continue;
			}
			if (ContractPlanfund.DEL_FLAG_NORMAL.equals(contractPlanfund.getDelFlag())){
				if (StringUtils.isBlank(contractPlanfund.getId())){
					contractPlanfund.setContractmanager(contractManager);
					contractPlanfund.preInsert();
					contractPlanfundDao.insert(contractPlanfund);
				}else{
					contractPlanfund.preUpdate();
					contractPlanfundDao.update(contractPlanfund);
				}
			}else{
				contractPlanfundDao.delete(contractPlanfund);
			}
		}
		for (ContractSubject contractSubject : contractManager.getContractSubjectList()){
			if (contractSubject.getId() == null){
				continue;
			}
			if (ContractSubject.DEL_FLAG_NORMAL.equals(contractSubject.getDelFlag())){
				if (StringUtils.isBlank(contractSubject.getId())){
					contractSubject.setContractmanager(contractManager);
					contractSubject.preInsert();
					contractSubjectDao.insert(contractSubject);
				}else{
					contractSubject.preUpdate();
					contractSubjectDao.update(contractSubject);
				}
			}else{
				contractSubjectDao.delete(contractSubject);
			}
		}
		for (ContractText contractText : contractManager.getContractTextList()){
			if (contractText.getId() == null){
				continue;
			}
			if (ContractText.DEL_FLAG_NORMAL.equals(contractText.getDelFlag())){
				if (StringUtils.isBlank(contractText.getId())){
					contractText.setContractmanager(contractManager);
					contractText.preInsert();
					contractTextDao.insert(contractText);
				}else{
					contractText.preUpdate();
					contractTextDao.update(contractText);
				}
			}else{
				contractTextDao.delete(contractText);
			}
		}
		for (ContractActualFunds contractActualFunds : contractManager.getContractActualFundsList()){
			if (contractActualFunds.getId() == null){
				continue;
			}
			if (ContractActualFunds.DEL_FLAG_NORMAL.equals(contractActualFunds.getDelFlag())){
				if (StringUtils.isBlank(contractActualFunds.getId())){
					contractActualFunds.setContractmanager(contractManager);
					contractActualFunds.preInsert();
					contractActualFundsDao.insert(contractActualFunds);
				}else{
					contractActualFunds.preUpdate();
					contractActualFundsDao.update(contractActualFunds);
				}
			}else{
				contractActualFundsDao.delete(contractActualFunds);
			}
		}
		for (ContractInvoice contractInvoice : contractManager.getContractInvoiceList()){
			if (contractInvoice.getId() == null){
				continue;
			}
			if (ContractInvoice.DEL_FLAG_NORMAL.equals(contractInvoice.getDelFlag())){
				if (StringUtils.isBlank(contractInvoice.getId())){
					contractInvoice.setContractmanager(contractManager);
					contractInvoice.preInsert();
					contractInvoiceDao.insert(contractInvoice);
				}else{
					contractInvoice.preUpdate();
					contractInvoiceDao.update(contractInvoice);
				}
			}else{
				contractInvoiceDao.delete(contractInvoice);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractManager contractManager) {
		super.delete(contractManager);
		contractActualFundsDao.delete(new ContractActualFunds(contractManager));
		contractFileDao.delete(new ContractFile(contractManager));
		contractInvoiceDao.delete(new ContractInvoice(contractManager));
		contractNoteDao.delete(new ContractNote(contractManager));
		contractPlanfundDao.delete(new ContractPlanfund(contractManager));
		contractSubjectDao.delete(new ContractSubject(contractManager));
		contractTextDao.delete(new ContractText(contractManager));
	}
	
}