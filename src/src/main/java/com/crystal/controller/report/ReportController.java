package com.crystal.controller.report;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.shared.ReportViewDto;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.catalog.SupervisoryEntityRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    GridService gridService;

    @Autowired
    SupervisoryEntityRepository supervisoryEntityRepository;


    @RequestMapping(value = "/report/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/report/index");
        List<String> lstSupervisorEntities = supervisoryEntityRepository.findNoObsoleteByBelongsTo();
        modelAndView.addObject("lstSupervisorEntities", new Gson().toJson(lstSupervisorEntities));
        return modelAndView;
    }

    @RequestMapping(value = "/report/getReportDataBySupervisor", method = RequestMethod.GET)
    public ResponseMessage getReportDataBySupervisor(@RequestParam(required = true) String id) {
        ResponseMessage response = new ResponseMessage();
        try {
            String belongsTo = new String (id.getBytes ("iso-8859-1"), "UTF-8");
            List<Object> listDataView = supervisoryEntityRepository.findDataReportBySupervisory(belongsTo);
            List<ReportViewDto> reportViewData = getReportData(listDataView);
            response.setReturnData(reportViewData);

        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/report/getReportDataBySupervisorYear", method = RequestMethod.GET)
    public ResponseMessage getReportDataBySupervisorYear(@RequestParam(required = true) String id, int year) {
        ResponseMessage response = new ResponseMessage();
        try {
            String belongsTo = new String (id.getBytes ("iso-8859-1"), "UTF-8");
            List<Object> listDataView = supervisoryEntityRepository.findDataReportBySupervisoryYear(belongsTo, year);
            List<ReportViewDto> reportViewData = getReportData(listDataView);
            response.setReturnData(reportViewData);

        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/report/getReportDataBySupervisorYearEntityType", method = RequestMethod.GET)
    public ResponseMessage getReportDataBySupervisorYearEntityType(@RequestParam(required = true) String id, int year, Long entityType) {
        ResponseMessage response = new ResponseMessage();
        try {
            String belongsTo = new String (id.getBytes ("iso-8859-1"), "UTF-8");
            List<Object> listDataView = supervisoryEntityRepository.findDataReportBySupervisoryYearEntityType(belongsTo, year, entityType);
            List<ReportViewDto> reportViewData = getReportData(listDataView);
            response.setReturnData(reportViewData);

        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/report/getReportDataBySupervisorYearEntity", method = RequestMethod.GET)
    public ResponseMessage getReportDataBySupervisorYearEntity(@RequestParam(required = true) String id, int year, Long entity) {
        ResponseMessage response = new ResponseMessage();
        try {
            String belongsTo = new String (id.getBytes ("iso-8859-1"), "UTF-8");
            List<Object> listDataView = supervisoryEntityRepository.findDataReportBySupervisoryYearEntity(belongsTo, year, entity);
            List<ReportViewDto> reportViewData = getReportData(listDataView);
            response.setReturnData(reportViewData);

        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }



    private List<ReportViewDto> getReportData(List<Object> objects){
        List<ReportViewDto> reportViewData = new ArrayList<>();

        for (int a = 0; a < objects.size(); a++) {
            Object[] obj = (Object[]) objects.get(a);
            if(obj.length == 9){
                ReportViewDto rv = new ReportViewDto(
                        obj[0].toString(),
                        ((BigInteger) obj[1]).intValue(),
                        ((BigInteger) obj[2]).intValue(),
                        ((BigDecimal) obj[3]).intValue(),
                        ((BigDecimal) obj[4]).intValue(),
                        ((BigDecimal) obj[5]).intValue(),
                        ((BigDecimal) obj[6]).intValue(),
                        ((BigDecimal) obj[7]).intValue(),
                        ((BigDecimal) obj[8]).floatValue());
                reportViewData.add(rv);
            }else{
                ReportViewDto rv = new ReportViewDto(
                        obj[0].toString(),
                        ((BigInteger) obj[1]).intValue(),
                        ((BigInteger) obj[2]).intValue(),
                        ((BigDecimal) obj[3]).intValue(),
                        ((BigDecimal) obj[4]).intValue(),
                        ((BigDecimal) obj[5]).intValue(),
                        ((BigDecimal) obj[6]).intValue(),
                        ((BigDecimal) obj[7]).intValue(),
                        ((BigDecimal) obj[8]).floatValue(),
                        ((BigInteger) obj[9]).longValue());
                reportViewData.add(rv);
            }
        }

        return reportViewData;
    }
}
