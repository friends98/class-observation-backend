package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Criteria;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.CriteriaRequest;
import com.observationclass.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CriteriaService {
    @Autowired
    private CriteriaRepository criteriaRepository;


    public ApiResponse addNewCriteria(CriteriaRequest criteriaRequest) {
        Criteria criteria = new Criteria();
        setCritera(criteria,criteriaRequest);
        criteria.setCreate();
        criteriaRepository.save(criteria);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }
    public ApiResponse updateCriteria(CriteriaRequest criteriaRequest){
        Optional<Criteria> opCriteria = criteriaRepository.findCriteriaByIdAndDeleteFlag(criteriaRequest.getId(), Constants.DELETE_NONE);
        if (!opCriteria.isEmpty()) {
            opCriteria.get().setCriteriaName(criteriaRequest.getCriteriaName());
        }
        opCriteria.get().setUpdate();
        criteriaRepository.save(opCriteria.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }

    public ApiResponse deleteCriteriaById(Integer id) {
        Optional<Criteria> opCriteria = criteriaRepository.findCriteriaByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (!opCriteria.isEmpty()) {
            opCriteria.get().setDeleteFlag(Constants.DELETE_TRUE);
        }
       
        criteriaRepository.save(opCriteria.get());
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.DELETE_SUCCESS,null);
    }

    public void setCritera(Criteria criteria, CriteriaRequest criteriaRequest) {
        int sizeCriteria =criteriaRepository.findAll().size();
        criteria.setCriteriaCode("TC"+(sizeCriteria+1));
        criteria.setCriteriaName(criteriaRequest.getCriteriaName());
    }

}
