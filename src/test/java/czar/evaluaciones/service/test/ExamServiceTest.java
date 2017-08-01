package czar.evaluaciones.service.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.dtos.ComboDto;
import czar.evaluaciones.dtos.ExamDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.entities.Exam;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.ExamRepository;
import czar.evaluaciones.services.ComboService;
import czar.evaluaciones.services.ExamService;
import czar.evaluaciones.services.impl.ExamServiceImpl;

@RunWith(SpringRunner.class)
public class ExamServiceTest {
    
    @TestConfiguration
    static class ExamServiceTestConfiguration {
        @Bean
        public ExamService getExamService() {
            return new ExamServiceImpl();
        }
    }
    
    @Autowired
    private ExamService examService;
    
    @MockBean
    private ExamRepository examRepository;
    
    @MockBean
    private ConfigurationRepository configurationRepository;
    
    @MockBean
    private ComboService comboService;
    
    @Before
    public void init() {
        Configuration config = new Configuration();
        config.setValue("10");
        
        when(configurationRepository.findByKey(Config.ROWS_X_PAGE.toString())).thenReturn(config);
        
        Page<Exam> page = new Page<Exam>() {
            @Override public List<Exam> getContent() { return null; }
            @Override public int getNumber() { return 0; }
            @Override public int getNumberOfElements() { return 0; }
            @Override public int getSize() { return 0; }
            @Override public Sort getSort() { return null; }
            @Override public boolean hasContent() { return false; }
            @Override public boolean hasNext() { return false; }
            @Override public boolean hasPrevious() { return false; }
            @Override public boolean isFirst() { return false; }
            @Override public boolean isLast() { return false; }
            @Override public Pageable nextPageable() { return null; }
            @Override public Pageable previousPageable() { return null; }
            @Override public Iterator<Exam> iterator() { return null; }
            @Override public long getTotalElements() { return 0; }
            @Override public int getTotalPages() { return 0; }
            @Override public <S> Page<S> map(Converter<? super Exam, ? extends S> arg0) { return null; }
        };
        
        when(examRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(examRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(page);
        
        ComboDto combo = new ComboDto(2L, "comboExam");
        when(comboService.getComboCategories()).thenReturn(Arrays.asList(combo));
        
        Answer<Void> answer = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Exam exam = invocation.getArgumentAt(0, Exam.class);
                Long idCategoryException = 2L;
                if (idCategoryException.equals(exam.getIdExam())) {
                    throw new DataIntegrityViolationException("");
                }
                return null;
            }
            
        };
        
        doAnswer(answer).when(examRepository).save(any(Exam.class));
        
        Answer<Void> answerDelete = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Long idExam = invocation.getArgumentAt(0, Long.class);
                if (idExam.equals(2L)) {
                    throw new DataIntegrityViolationException("");
                }
                return null;
            }
            
        };
        
        doAnswer(answerDelete).when(examRepository).delete(anyLong());
        
        Exam exam = new Exam();
        Set<Category> categories = new HashSet<>();
        categories.add(new Category());
        exam.setCategories(categories);
        exam.setPassPercent(new BigDecimal("0.60"));
        when(examRepository.findOne(anyLong())).thenReturn(exam);
    }
    
    @Test
    public void findTest() {
        ExamDto examDto = new ExamDto();
        
        examService.find(examDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.ROWS_X_PAGE.toString()));
        verify(examRepository, atLeastOnce()).findAll(any(Pageable.class));
    }
    
    @Test
    public void findEa1Test() {
        ExamDto examDto = new ExamDto();
        examDto.setName("");
        
        examService.find(examDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.ROWS_X_PAGE.toString()));
        verify(examRepository, atLeastOnce()).findAll(any(Pageable.class));
    }
    
    @Test
    public void findEa2Test() {
        ExamDto examDto = new ExamDto();
        examDto.setDirection("ASC");
        examDto.setName("name");
        examDto.setPage(1);
        examDto.setSize(10);
        
        examService.find(examDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.ROWS_X_PAGE.toString()));
        verify(examRepository, atLeastOnce()).findByNameContainingIgnoreCase(anyString(), any(Pageable.class));
    }
    
    @Test
    public void saveTest() throws Exception {
        ExamDto exam = new ExamDto();
        exam.setIdExam(1L);
        exam.setPassPercent(BigDecimal.TEN);
        exam.setIdsCategories(Arrays.asList(1L));
        
        examService.save(exam);
        
        verify(examRepository, atLeastOnce()).save(any(Exam.class));
        
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa1Test() throws Exception {
        ExamDto exam = new ExamDto();
        exam.setIdExam(2L);
        exam.setPassPercent(BigDecimal.TEN);
        exam.setIdsCategories(Arrays.asList(1L));

        examService.save(exam);
    }
    
    @Test
    public void getByIdTest() {
        Long idExam = 1L;
        
        examService.getById(idExam);
        
        verify(examRepository, atLeastOnce()).findOne(anyLong());
        verify(comboService, atLeastOnce()).getComboCategories();
    }
    
    @Test
    public void deleteTest() throws ServiceException {
        Long idExam = 1L;
        
        examService.delete(idExam);
        
        verify(examRepository, atLeastOnce()).delete(anyLong());
    }
    
    @Test(expected = ServiceException.class)
    public void deleteEa1Test() throws ServiceException {
        Long idExam = 2L;
        
        examService.delete(idExam);
    }
}
