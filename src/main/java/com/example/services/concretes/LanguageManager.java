package com.example.services.concretes;

import com.example.utilities.results.*;
import com.example.dataAcces.CvDao;
import com.example.dataAcces.LanguageDao;
import com.example.entities.concretes.Language;
import com.example.entities.dtos.LanguageForSetDto;
import com.example.services.abstracts.LanguageService;
import com.example.utilities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class LanguageManager implements LanguageService {

    private LanguageDao languageDao;
    private CvDao cvDao;

    @Autowired
    public LanguageManager(LanguageDao languageDao,CvDao cvDao) {
        this.languageDao = languageDao;
        this.cvDao=cvDao;
    }

    //@Override
    public Result addLanguage(LanguageForSetDto languageForSetDto) {

        if(!this.cvDao.existsById(languageForSetDto.getCvId())){
            return new ErrorResult("Böyle bir cv yok");
        }else if(languageForSetDto.getName().length()<=2){
            return new ErrorResult("Dil ismi 2 karakterden uzun olmalıdır");
        }else if(Integer.parseInt(languageForSetDto.getLevel()) <=0 || Integer.parseInt(languageForSetDto.getLevel()) >=6){
            return new ErrorResult("Dil seviyesi 1-5 arası bir değer olmalıdır");
        }

        Language language=new Language();
        language.setCv(this.cvDao.getById(languageForSetDto.getCvId()));
        language.setName(languageForSetDto.getName());
        language.setLevel(languageForSetDto.getLevel());

        this.languageDao.save(language);
        return new SuccessResult("Dil kaydedildi");
    }

    //@Override
    public Result deleteLanguage(int languageId) {
        if(!this.languageDao.existsById(languageId)){
            return new ErrorResult("Böyle bir dil bulunamadı");
        }
        this.languageDao.deleteById(languageId);
        return new SuccessResult("Silindi");
    }

    //@Override
    public DataResult<List<Language>> getByCvId(int cvId) {
        if(!this.cvDao.existsById(cvId)){
            return new ErrorDataResult<List<Language>>("Böyle bir cv yok");
        }
        return new SuccessDataResult<List<Language>>(this.languageDao.findByCvId(cvId),"Listelendi");
    }
}

