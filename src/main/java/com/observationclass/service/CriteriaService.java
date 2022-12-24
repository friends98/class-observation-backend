package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Criteria;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.CriteriaRequest;
import com.observationclass.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CriteriaService {
    @Autowired
    private CriteriaRepository criteriaRepository;

    public ApiResponse getListCriteria() {
        List<Criteria> listCriteria = criteriaRepository.findAllByDeleteFlag(Constants.DELETE_NONE);
        if (listCriteria == null) {
            throw new RecordNotFoundException("List criteria is empty!");
        }
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listCriteria);
    }

    public ApiResponse addNewCriteria(CriteriaRequest criteriaRequest) {
        Criteria criteria = new Criteria();
        setNewCritera(criteria, criteriaRequest);
        criteria.setCreate();
        criteriaRepository.save(criteria);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse updateCriteria(CriteriaRequest criteriaRequest) {
        Optional<Criteria> opCriteria = criteriaRepository.findByIdAndDeleteFlag(criteriaRequest.getId(), Constants.DELETE_NONE);
        if (opCriteria.isEmpty()) {
            throw new RecordNotFoundException("Criteria not found by Id");
        }
        opCriteria.get().setCriteriaName(criteriaRequest.getCriteriaName());
        opCriteria.get().setUpdate();
        criteriaRepository.save(opCriteria.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }

    public ApiResponse deleteCriteriaById(Integer id) {
        Optional<Criteria> opCriteria = criteriaRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opCriteria.isEmpty()) {
            throw new RecordNotFoundException("Criteria not found by Id");
        }
        opCriteria.get().setDeleteFlag(Constants.DELETE_TRUE);
        criteriaRepository.save(opCriteria.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null);
    }

    public void setNewCritera(Criteria criteria, CriteriaRequest criteriaRequest) {
        int sizeCriteria = criteriaRepository.findAllByDeleteFlag(Constants.DELETE_NONE).size();
        criteria.setCriteriaCode("TC" + (sizeCriteria + 1));
        criteria.setCriteriaName(criteriaRequest.getCriteriaName());
    }

}
