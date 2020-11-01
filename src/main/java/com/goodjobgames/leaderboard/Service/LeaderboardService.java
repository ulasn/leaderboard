package com.goodjobgames.leaderboard.Service;

import com.goodjobgames.leaderboard.DTO.CountryRankingDTO;
import com.goodjobgames.leaderboard.DTO.GlobalRankingDTO;
import com.goodjobgames.leaderboard.DTO.Request.RankingPageRequestDTO;
import com.goodjobgames.leaderboard.DTO.UserDTO;
import com.goodjobgames.leaderboard.Entity.User;
import com.goodjobgames.leaderboard.Exception.ServerErrorMessages;
import com.goodjobgames.leaderboard.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisZSet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
public class LeaderboardService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DefaultRedisZSet redisZSet;

    @Autowired
    UserRepository userRepository;
    
    public GlobalRankingDTO getGlobalRankings() {
        log.info("Global leaderboard request.");

        GlobalRankingDTO globalRankingDTO = new GlobalRankingDTO();
        Iterator<String> ranks = redisZSet.reverseRange(0, -1).iterator();
        Integer rank = 0;
        while(ranks.hasNext()){
            rank++;
            Optional<User> user= userRepository.findById(ranks.next());
            globalRankingDTO.addUser(new UserDTO(
                    rank,
                    user.get().getPoints().intValue(),
                    user.get().getName(),
                    user.get().getCountry()
                    )
            );
            ranks.remove();
        }
        log.info("Global leaderboard request - calculation complete");
        return globalRankingDTO;
    }

    public GlobalRankingDTO getGlobalRankingsByRange(RankingPageRequestDTO pageRequestDTO){
        log.info("Global Ranking request by range - range values start: {} end: {}",
                pageRequestDTO.getStart(), pageRequestDTO.getEnd());

        GlobalRankingDTO globalRankingDTO = new GlobalRankingDTO();

        Integer sizeOfTable = redisZSet.size();

        if(pageRequestDTO.getStart() == null || pageRequestDTO.getEnd() == null){
            log.error("Global Ranking request error, one of the parameters is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.BAD_PAGE_REQUEST.getErrorMessage());
        }

        if(pageRequestDTO.getStart() < 1 || pageRequestDTO.getEnd() >= sizeOfTable){
            log.error("Global Ranking request error, start or end is invalid");
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    ServerErrorMessages.BAD_PAGE_REQUEST_CONSTRAINTS.getErrorMessage() + sizeOfTable.toString());
        }
        log.info("Leaderboard page request - start of reverse range function");
        Iterator<String> ranks = redisZSet.reverseRange(pageRequestDTO.getStart().longValue() -1,
                pageRequestDTO.getEnd().longValue() -1).iterator();
        log.info("Leaderboard page request - end of reverse range function");

        Integer rank = pageRequestDTO.getStart();
        log.info("Leaderboard page request - start of getting user information");

        log.info("findbyid one by one start");
        while(ranks.hasNext()){
            Optional<User> user= userRepository.findById(ranks.next());
            globalRankingDTO.addUser(new UserDTO(
                            rank,
                            user.get().getPoints().intValue(),
                            user.get().getName(),
                            user.get().getCountry()
                    )
            );
            rank++;
            ranks.remove();
        }
        log.info("findbyid one by one end");
        log.info("Leaderboard page request - end of getting user information");
        return globalRankingDTO;
    }

    public GlobalRankingDTO getGlobalRankingsByPage(Integer pageNumber){
        GlobalRankingDTO globalRankingDTO = new GlobalRankingDTO();

        log.info("Leaderboard page request - page number: {}", pageNumber);

        Integer sizeOfTable = redisZSet.size();
        double noOfPages = Math.ceil((float)(sizeOfTable / 100));
        if(pageNumber < 1){
            log.error("Leaderboard page request - Page number cannot be less than 1");
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    ServerErrorMessages.PAGE_NUMBER_NEGATIVE.getErrorMessage());
        }

        if(pageNumber > noOfPages){
            log.error("Leaderboard page request - Page number cannot be bigger than number of pages");
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    ServerErrorMessages.NO_OF_PAGES_WRONG.getErrorMessage() + noOfPages);
        }

        long start = (pageNumber.longValue() - 1) * 100 ;
        long end = start + 99;

        Iterator<String> ranks = redisZSet.reverseRange(start, end).iterator();

        Integer rank = (int) start;
        while(ranks.hasNext()){
            rank++;
            Optional<User> user= userRepository.findById(ranks.next());
            globalRankingDTO.addUser(new UserDTO(
                            rank,
                            user.get().getPoints().intValue(),
                            user.get().getName(),
                            user.get().getCountry()
                    )
            );
            ranks.remove();
        }
        log.info("Leaderboard page request - calculation complete");
        return globalRankingDTO;
    }

    public CountryRankingDTO getCountryRankings(String isoCode) {
        log.info("Country Ranking request - service start");
        List<UserDTO> userList = new ArrayList<>();
        log.info("userRepository.findByCountry(isoCode) start");
        List<User> userListByCountry = userRepository.findByCountry(isoCode);
        log.info("userRepository.findByCountry(isoCode) end");
        for(User user : userListByCountry){
            Long rank = redisZSet.reverseRank(user.getId().toString()) + 1;
            userList.add(
                    new UserDTO(
                    rank.intValue(),
                    user.getPoints().intValue(),
                    user.getName(),
                    isoCode
                    )
            );
        }
        Collections.sort(userList);
        log.info("Country ranking request - calculation complete");
        return new CountryRankingDTO(userList);
    }

    public CountryRankingDTO getCountryRankingsByPage(String isoCode, Integer pageNumber) {
        log.info("Country Ranking request by page - service start");
        List<UserDTO> userList = new ArrayList<>();
        List<User> userListByCountry = userRepository.findByCountry(isoCode);
        Collections.sort(userListByCountry, Collections.reverseOrder());
        User user;
        int startingPosition = (pageNumber - 1) * 100;
        int endPosition = startingPosition + 100;
        for(int i = startingPosition; i<endPosition; i++){
            user = userListByCountry.get(i);
            if(user.getPoints().intValue() != 0){
                Long rank = redisZSet.reverseRank(user.getId()) + 1;
                userList.add(
                        new UserDTO(
                                rank.intValue(),
                                user.getPoints().intValue(),
                                user.getName(),
                                isoCode
                        )
                );
            }
        }
        Collections.sort(userList);
        log.info("Country ranking request by page - calculation complete");
        return new CountryRankingDTO(userList);
    }
}
