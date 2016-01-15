package com.crystal.controller.catalog;

import com.crystal.service.catalog.InsertCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InsertCatalogController {

	@Autowired
	InsertCatalogService service;

	@RequestMapping(value = "/catalogs/insertRole", method = RequestMethod.GET)
	public String insertRole() {
		service.role();
		return "insertRole OK!";
	}

	@RequestMapping(value = "/catalogs/insertUser", method = RequestMethod.GET)
	public String insertUser() {
		service.user();
		return "insertUser OK!";
	}

	@RequestMapping(value = "/catalogs/insertAuditedEntityType", method = RequestMethod.GET)
	public String auditedEntityType() {
		service.auditedEntityType();
		return "auditedEntityType OK!";
	}

	@RequestMapping(value = "/catalogs/insertAuditedEntity", method = RequestMethod.GET)
	public String auditedEntity() {
		service.auditedEntity();
		return "auditedEntity OK!";
	}

	@RequestMapping(value = "/catalogs/insertAuditType", method = RequestMethod.GET)
	public String auditType() {
		service.auditType();
		return "auditType OK!";
	}

	@RequestMapping(value = "/catalogs/insertEventType", method = RequestMethod.GET)
	public String eventType() {
		service.eventType();
		return "eventType OK!";
	}

	@RequestMapping(value = "/catalogs/insertMeetingType", method = RequestMethod.GET)
	public String meetingType() {
		service.meetingType();
		return "meetingType OK!";
	}

	@RequestMapping(value = "/catalogs/insertSupervisoryEntity", method = RequestMethod.GET)
	public String supervisoryEntity() {
		service.supervisoryEntity();
		return "supervisoryEntity OK!";
	}


	@RequestMapping(value = "/catalogs/insertFileType", method = RequestMethod.GET)
	public String fileType() {
		service.fileType();
		return "fileType OK!";
	}

	@RequestMapping(value = "/catalogs/observationType", method = RequestMethod.GET)
	public String observationType() {
		service.observationType();
		return "observationType OK!";
	}

	@RequestMapping(value = "/catalogs/systemSetting", method = RequestMethod.GET)
	public String systemSetting() {
		service.systemSettings();
		return "systemSetting OK!";
	}

	@RequestMapping(value = "/catalogs/insertCatalogAll", method = RequestMethod.GET)
	public String insertCatalogAll() {
		service.role();
		service.auditedEntityType();
		service.auditedEntity();
		service.user();
		service.auditType();
		service.eventType();
		service.meetingType();
		service.supervisoryEntity();
		service.fileType();
		service.observationType();
		service.systemSettings();
		return "insertCatalogAll OK!!";
	}

}
