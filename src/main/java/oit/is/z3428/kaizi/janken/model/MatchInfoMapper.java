package oit.is.z3428.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT id, user1, user2, user1Hand, isActive FROM matchinfo WHERE isActive = TRUE")
  ArrayList<MatchInfo> selectActiveMatchInfo();

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Select("SELECT id, user1, user2, user1Hand, isActive FROM matchinfo WHERE user2 = #{user2} AND isActive = TRUE")
  ArrayList<MatchInfo> selectActiveMatchInfoByUser2(int user2);

  @Update("UPDATE matchinfo SET isActive = #{isActive} WHERE id = #{id}")
  void updateMatchInfoActive(int id, boolean isActive);
}
