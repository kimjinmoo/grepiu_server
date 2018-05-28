package com.grepiu.test.process.common;

import com.google.common.collect.Lists;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.domain.GrepIUMenuDetailVO;
import com.grepiu.www.process.common.domain.GrepIUMenuVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *
 * 파이어 베이스 TDD
 *
 */
@Slf4j
public class FirebaseDatabaseTDD extends LocalBaseConfig {

  @Override
  public void setUp() {

  }

  /**
   *
   * 데이터 저장
   *
   */
  @Test
  public void save() {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/grepiu/menuLists");

    List<GrepIUMenuDetailVO> menus = Lists.newArrayList();

    GrepIUMenuDetailVO menu1 = new GrepIUMenuDetailVO(1, "Lap1", "/lab");
    GrepIUMenuDetailVO menu2 = new GrepIUMenuDetailVO(2, "Post2", "/post");

    menus.add(menu1);
    menus.add(menu2);

    ref.setValueAsync(menus);
  }

  /**
   *
   * 데이터 가져오기
   *
   */
  @Test
  public  void select() {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/grepiu/menuLists");

  }
}
