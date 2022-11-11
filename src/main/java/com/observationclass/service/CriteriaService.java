package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Criteria;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.CriteriaRequest;
import com.observationclass.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;
import java.util.Optional;

@Service
public class CriteriaService {
    @Autowired
    private CriteriaRepository criteriaRepository;

    public ApiResponse listCriteriaByCampus(Integer campusId) {
        List<Criteria> listCriteriaByCampus = criteriaRepository.findAllByCampusIdAndDeleteFlag(campusId,Constants.DELETE_NONE);
        if (listCriteriaByCampus == null) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listCriteriaByCampus);
    }

    public ApiResponse addNewCriteria(CriteriaRequest criteriaRequest) {
        Criteria criteria = new Criteria();
        setNewCritera(criteria, criteriaRequest);
        criteria.setCreate();
        criteriaRepository.save(criteria);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse updateCriteria(CriteriaRequest criteriaRequest) {
        Optional<Criteria> opCriteria = criteriaRepository.findCriteriaByIdAndCampusIdAndDeleteFlag(criteriaRequest.getId(),
                criteriaRequest.getCampusId(), Constants.DELETE_NONE);
        if (opCriteria.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        opCriteria.get().setCriteriaName(criteriaRequest.getCriteriaName());
        opCriteria.get().setUpdate();
        criteriaRepository.save(opCriteria.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }

    public ApiResponse deleteCriteriaById(Integer id, Integer campusId) {
        Optional<Criteria> opCriteria = criteriaRepository.findCriteriaByIdAndCampusIdAndDeleteFlag(id, campusId,
                Constants.DELETE_NONE);
        if (!opCriteria.isEmpty()) {
            opCriteria.get().setDeleteFlag(Constants.DELETE_TRUE);
        }

        criteriaRepository.save(opCriteria.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null);
    }

    public void setNewCritera(Criteria criteria, CriteriaRequest criteriaRequest) {
        int sizeCriteria = criteriaRepository.findAllByCampusIdAndDeleteFlag(criteriaRequest.getCampusId(), Constants.DELETE_NONE).size();
        criteria.setCriteriaCode("TC" + (sizeCriteria + 1));
        criteria.setCriteriaName(criteriaRequest.getCriteriaName());
        criteria.setCampusId(criteriaRequest.getCampusId());
    }

}
