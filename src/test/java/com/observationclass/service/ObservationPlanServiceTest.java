package com.observationclass.service;

import com.observationclass.entity.*;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationPlanRequest;
import com.observationclass.model.request.ObservationPlanUpdateRequest;
import com.observationclass.model.request.ObservationSlotRequest;
import com.observationclass.repository.*;
import com.observationclass.repository.dao.ObservationPlanDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ObservationPlanServiceTest {
    @MockBean
    private ObservationPlanRepository observationPlanRepository;

    @MockBean
    private CampusRepository campusRepository;
    @MockBean
    private SemesterRepository semesterRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private ObservationSlotRepository observationSlotRepository;

    @MockBean
    private SubjectRepository subjectRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private SlotRepository slotRepository;

    @InjectMocks
    private ObservationPlanService observationPlanService;

    @Mock
    private ObservationPlanDao observationPlanDao;

    @Mock
    private ObservationSlotService observationSlotService;

    @Test
    void testStatusPlanById_WhenPlanIdValid() throws Exception {
        ObservationPlan observationPlan = new ObservationPlan();
        observationPlan.setId(1);
        observationPlan.setPlanStatus(1);

        Mockito.when(observationPlanRepository.findByIdAndDeleteFlag(anyInt(), anyInt()))
                                                .thenReturn(Optional.of(observationPlan));
        ApiResponse apiResponseActual = observationPlanService.getStatusPlanById(5);
        System.out.println(apiResponseActual.toString());
        Integer planStatus = (Integer) apiResponseActual.getItems();
        System.out.println(apiResponseActual.getItems());
        assertEquals(0, planStatus);

    }

    @Test
    void testApproveObservationPlan_WhenPlanId_ExistInDB() {
        ObservationPlan observationPlan = new ObservationPlan();
        observationPlan.setId(1);
        observationPlan.setDeleteFlag(0);
        observationPlan.setPlanStatus(0);
        Mockito.when(observationPlanRepository.findByIdAndDeleteFlag(anyInt(), anyInt())).thenReturn(Optional.of(observationPlan));
        ApiResponse apiResponse = observationPlanService.approveObservationPlan(1, 1);
        assertThat(apiResponse.getItems()).isEqualTo(null);
    }

    @Test
    void testRejectObservationPlan_WhenPlanId_ExistInDB() {
        ObservationPlan observationPlan = new ObservationPlan();
        observationPlan.setId(1);
        observationPlan.setDeleteFlag(0);
        observationPlan.setPlanStatus(0);
        Mockito.when(observationPlanRepository.findByIdAndDeleteFlag(anyInt(), anyInt())).thenReturn(Optional.of(observationPlan));
        ApiResponse apiResponse = observationPlanService.approveObservationPlan(observationPlan.getId(), 2);
        assertThat(apiResponse.getItems()).isEqualTo(null);
    }

    @Test
    void testRejectObservationPlan_WhenPlanStatus_Notnull() {
        ObservationPlan observationPlan = new ObservationPlan();
        observationPlan.setId(1);
        observationPlan.setDeleteFlag(0);
        observationPlan.setPlanStatus(0);
        Mockito.when(observationPlanRepository.findByIdAndDeleteFlag(anyInt(), anyInt())).thenReturn(Optional.of(observationPlan));
        ApiResponse apiResponse = observationPlanService.approveObservationPlan(observationPlan.getId(), 8);
        assertThat(apiResponse.getItems()).isEqualTo(null);
    }


    @Test
    void testListPlanBySemesterAndCampus_WhenExitsInDB() {
        List<Object> listOfObject = new ArrayList<>();
        listOfObject.add(new Object());
        listOfObject.add(new Object());
        Mockito.when(observationPlanDao.listSearchObservationPlan(1, 1)).thenReturn(listOfObject);
        List<Object> listOfObjectActual =
                (ArrayList) observationPlanService.listPlanBySemesterAndCampus(1, 1).getItems();
        assertEquals(listOfObject.size(), listOfObjectActual.size());
    }

    @Test
    void testListPlanBySemesterAndCampus_WhenEmpty() {
        List<Object> listOfObject = new ArrayList<>();

        Mockito.when(observationPlanDao.listSearchObservationPlan(1, 1)).thenReturn(listOfObject);
        List<Object> listOfObjectActual =
                (ArrayList) observationPlanService.listPlanBySemesterAndCampus(1, 1).getItems();
        assertEquals(0, listOfObjectActual.size());
    }

    @Test
    void createNewObservationPlan() throws Exception {
        ObservationPlan observationPlan = new ObservationPlan();
        ObservationPlanRequest observationPlanRequest =new ObservationPlanRequest();
        Set<ObservationSlotRequest> setObservationSlotRequest = new HashSet<>();
        List<ObservationSlot> listOfObservationSlot = new ArrayList<>();
        observationPlanRequest.setCampusId(1);
        observationPlanRequest.setSemesterId(1);
        //observationPlanRequest.setDepartmentId(1);
        observationPlanRequest.setObservationSlotsRequest(setObservationSlotRequest);
        ObservationSlot observationSlot = new ObservationSlot();
        Semester semester = new Semester();
        Campus campus = new Campus();
        Department department = new Department();
        Account account = new Account();
        observationPlan.setCampus(campus);
        observationPlan.setSemester(semester);
        observationPlan.setDepartment(department);

        Mockito.when(semesterRepository.findById(anyInt())).thenReturn(Optional.ofNullable(semester));
        Mockito.when(campusRepository.findById(anyInt())).thenReturn(Optional.ofNullable(campus));
        Mockito.when(departmentRepository.findByIdAndCampusId(anyInt(),anyInt())).thenReturn(Optional.ofNullable(department));
        Mockito.when(accountRepository.findByIdAndCampusIdAndDeleteFlag(anyInt(),anyInt(),anyInt())).thenReturn(Optional.ofNullable(account));
        Mockito.when(observationPlanRepository.save(Mockito.any(ObservationPlan.class))).thenReturn(observationPlan);
        Mockito.when(observationSlotRepository.saveAll(Mockito.anySet())).thenReturn(listOfObservationSlot);
        System.out.println(Optional.ofNullable(department).isPresent()+"hhhhhhh");
        ApiResponse apiResponse=observationPlanService.createNewObservationPlan(observationPlanRequest);
        assertThat(apiResponse).isNotNull();
    }

    @Test
    void updateOnlyObservationPlan() {
        ObservationPlan observationPlan =new ObservationPlan();
        ObservationPlanUpdateRequest observationPlanUpdateRequest=new ObservationPlanUpdateRequest();
        observationPlanUpdateRequest.setId(1);
        observationPlanUpdateRequest.setCampusId(1);
        observationPlanUpdateRequest.setSemesterId(1);
        observationPlanUpdateRequest.setDepartmentId(1);
        Semester semester = new Semester();
        Campus campus = new Campus();
        Department department = new Department();


        Mockito.when(semesterRepository.findById(anyInt())).thenReturn(Optional.ofNullable(semester));
        Mockito.when(campusRepository.findById(anyInt())).thenReturn(Optional.ofNullable(campus));
        Mockito.when(departmentRepository.findById(anyInt())).thenReturn(Optional.ofNullable(department));
        Mockito.when(observationPlanRepository.findByIdAndDeleteFlag(anyInt(), anyInt())).thenReturn(Optional.ofNullable(observationPlan));
        Mockito.when(observationPlanRepository.save(Mockito.any(ObservationPlan.class))).thenReturn(observationPlan);
        ApiResponse apiResponse = observationPlanService.updateOnlyObservationPlan(observationPlanUpdateRequest);
        assertThat(apiResponse).isNotNull();
    }

    @Test
    void updateOnlyObservationSlot() {
    }

    @Test
    void testDeleteObservationPlan() {
        ObservationPlan observationPlanActual = new ObservationPlan();
        observationPlanActual.setId(1);
        observationPlanActual.setDeleteFlag(1);
        ObservationPlan observationPlanMock = new ObservationPlan();
        Mockito.when(observationPlanRepository.findById(anyInt())).thenReturn(Optional.ofNullable(observationPlanMock));
        Mockito.when(observationPlanRepository.save(Mockito.any(ObservationPlan.class))).thenReturn(observationPlanActual);
        ApiResponse apiResponseActual =observationPlanService.deleteObservationPlan(1);
        assertThat(apiResponseActual).isNotNull();

    }

    @Test
    void getAccountById() {

    }

    @Test
    void getSubjectById() {
        Subject subject = new Subject();
        Mockito.when(subjectRepository.findById(anyInt())).thenReturn(Optional.ofNullable(subject));
        Subject subjectActual=observationPlanService.getSubjectById(1);
        assertThat(subjectActual).isNotNull();
    }

    @Test
    void getSlotById() {
        Slot slot = new Slot();
        Mockito.when(slotRepository.findById(anyInt())).thenReturn(Optional.ofNullable(slot));
        Slot slotActual =observationPlanService.getSlotById(1);
        assertThat(slotActual).isNotNull();
    }

    @Test
    void getRoomById() {
        Room room =new Room();
        Mockito.when(roomRepository.findById(anyInt())).thenReturn(Optional.ofNullable(room));
        Room roomActual =observationPlanService.getRoomById(1);
        assertThat(roomActual).isNotNull();
    }

    public Integer getRandom() {
        return new Random().ints(1, 10).findFirst().getAsInt();
    }
}