package com.example.services.concretes;

import com.example.utilities.results.*;
import com.example.dataAcces.CandidateDao;
import com.example.dataAcces.JobAdDao;
import com.example.dataAcces.JobAdFavoritesDao;
import com.example.entities.concretes.JobAdFavorites;
import com.example.services.abstracts.JobAdFavoritesService;
import com.example.utilities.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;




@Service
public class JobAdFavoritesManager implements JobAdFavoritesService {

    private JobAdFavoritesDao jobAdFavoritesDao;
    private CandidateDao candidateDao;
    private JobAdDao jobAdDao;

    @Autowired
    public JobAdFavoritesManager(JobAdFavoritesDao jobAdFavoritesDao,CandidateDao candidateDao, JobAdDao jobAdDao) {
        this.jobAdFavoritesDao = jobAdFavoritesDao;
        this.candidateDao=candidateDao;
        this.jobAdDao=jobAdDao;
    }

   // @Override
    public DataResult<List<JobAdFavorites>> getByCandidateId(int candidateId) {
        if(!this.candidateDao.existsById(candidateId)){
            return new ErrorDataResult<List<JobAdFavorites>>("Böyle bir kullanıcı yok");
        }
        return new SuccessDataResult<List<JobAdFavorites>>(this.jobAdFavoritesDao.findByCandidateId(candidateId),"Data listelendi");
    }

    //@Override
    public Result addFavorite(int candidateId, int jobAdId) {

        if(!this.candidateDao.existsById(candidateId)){
            return new ErrorResult("Böyle bir kullanıcı yok");
        }else if(!this.jobAdDao.existsById(jobAdId)){
            return new ErrorResult("Böyle bir ilan yok");
        }else if(this.jobAdFavoritesDao.existsByCandidate_IdAndJobAd_Id(candidateId,jobAdId)){
            return new ErrorResult("Bu ilan zaten favorilerinizde");
        }

        JobAdFavorites jobAdFavorites=new JobAdFavorites();
        jobAdFavorites.setCandidate(this.candidateDao.getById(candidateId));
        jobAdFavorites.setJobAd(this.jobAdDao.getById(jobAdId));
        this.jobAdFavoritesDao.save(jobAdFavorites);
        return new SuccessResult("İlan facorilere eklendi");
    }

    //@Override
    public Result removeFavorite(int favoriteId) {
        if(!this.jobAdFavoritesDao.existsById(favoriteId)){
            return new ErrorResult("Böyle bir favori ilan yok");
        }
        this.jobAdFavoritesDao.deleteById(favoriteId);
        return new SuccessResult("İlan favorilerden kandırıldı");
    }
}

