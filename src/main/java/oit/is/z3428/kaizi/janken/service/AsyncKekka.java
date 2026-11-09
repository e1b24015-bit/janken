package oit.is.z3428.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3428.kaizi.janken.model.Match;
import oit.is.z3428.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  public boolean dbUpdated = false;

  @Autowired
  private MatchMapper matchMapper;

  @Async
  public void asyncShowResult(SseEmitter emitter) {
    logger.info("asyncShowResult start");
    try {
      while (true) {
        TimeUnit.MILLISECONDS.sleep(500);

        ArrayList<Match> activeMatches = this.matchMapper.selectActiveMatches();
        if (this.dbUpdated || !activeMatches.isEmpty()) {
          if (!activeMatches.isEmpty()) {
            Match match = activeMatches.get(0);
            logger.info("Sending match result: id=" + match.getId());
            emitter.send(match);
            this.dbUpdated = false;
            this.matchMapper.updateMatchActive(match.getId(), false);
            emitter.complete();
            break;
          }
        }
      }
    } catch (Exception e) {
      logger.warn("Exception in asyncShowResult: " + e.getMessage());
      emitter.complete();
    }
  }
}
