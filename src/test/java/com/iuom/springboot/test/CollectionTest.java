package com.iuom.springboot.test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * 콜렉션 특징/사용방법/Big O 시간복잡도 표현
 * 참고 URL :
 * https://gist.github.com/FedericoPonzi/8d5094dbae33cbb94536a73f62d1c1a0
 * http://bigocheatsheet.com/
 *
 * <pre>
 * 시간복잡도 대표 표현식 위일수록 빠르다.
 *   빠른 순서 ↑
 *   상수 시간    O(1)
 *   로그시간     O(log N)
 *   직선형 시간  O(N)
 *   2차 시간     O(n^2)
 *   지수 시간    O(C^n)
 *   느린 순서 ↓
 * </pre>
 */


enum days {
  SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class CollectionTest {


  /**
   *
   * List 인터페이스
   *
   */
  @Test
  public void collectionsByList() {
    /**
     *
     * 시간복잡도
     * add             : O(1)
     * remove          : O(n)
     * get             : O(1)
     * Contains        : O(n)
     * iterator.remove : O(n)
     * java 1.2에 추가 thread-safe 보장 안함
     * 특징 :  데이터 추가,삭제를 위해 임시 배열을 생성해 데이터를 복사
     *   - 대량의 자료를 추가/삭제시 복사가 일어 나게 되어 성능 저하를 일이킴
     *   - 데이터의 인덱스를 가지고 있어 데이터 검색시 빠름
     *   -
     */
    List<String> arrayList = new ArrayList<>();

    /**
     *
     * 시간복잡도
     * add             : O(1)
     * remove          : O(1)
     * get             : O(n)
     * Contains        : O(n)
     * iterator.remove : O(1)
     * java 1.2에 추가 thread-safe 보장 안함
     * 특징 : 데이터를 저장하는 각 노드가 이전 노드와 다음 노드의 상태만 알고 있다.
     *   - 데이터 추가/삭제시 빠름
     *   - 데이터 검색시 처음부터 노드를 순화해야 되기 때문에 느림
     */
    List<String> linkedList = new LinkedList<>();

    /**
     *
     * 시간복잡도
     * add             : O(n)
     * remove          : O(n)
     * get             : O(1)
     * Contains        : O(n)
     * iterator.remove : O(n)
     * java 5 추가 thread-safe 보장, 병렬처리
     * 특징 : 처리에 여분의 오버로드를 가져오지만 순회 작업의 수에 비해 수정 횟수가 최소일때 효과적 이다.
     *    - add는 ArrayList, LinkedList 보다 느리지만 get은 LinkedList보단 빠르고 ArrayList보단 살짝 느리다.
     */
    List<String> copyOnWriteArraylist = new CopyOnWriteArrayList<>();
  }

  public void collectionsBySet() {
    /**
     *
     * 시간복잡도
     * add         :   O(1)
     * contains    :   O(1)
     * next        :   o(h/n) h는 테이블 용량
     * thread-safe 보장 안함
     * 특징 : 객체들을 순서없이 저장하고 동일한 객체를 중복 저장하지 않는다.
     *    - 중복되지 않는 값을 등록할때 용의
     *    - 순서없이 저장되는것 주위
     *    - null을 허용한다.
     */
    Set<String> hashSet = new HashSet<>();

    /**
     *
     * 시간복잡도
     * add       : O(1)
     * contains  : O(1)
     * next      : O(1)
     * thread-safe 보장 안함
     * 특징 : 속도는 hashSet에 비해 느리지만 좋은 성능을 보장한다.
     *    - 등록한 순으로 정렬을 한다.
     *    - null을 허용한다.
     */
    Set<String> linkedHashSet = new LinkedHashSet<>();

    /**
     *
     * 시간복잡도
     * add       : O(n)
     * contains  : O(n)
     * next      : O(1)
     * Java5에서 나옴, 병렬처리, thread-safe 보장, 병렬 보장
     * 특징 : 중복을 허용하지 않는 thread-safe 보장하는 콜렉션인경우 용의
     */
    CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    /**
     *
     * 시간복잡도
     * add       : O(1)
     * contains  : O(1)
     * next      : O(1)
     * Java5에서 나옴
     * 특징 : 적은 메모리를 사용
     *      - 빠르다
     *      - null을 사용 할 수 없다.
     */
    Set<days> enumSet = EnumSet.of(days.FRIDAY);

    /**
     *
     * 시간복잡도
     * add       : O(log n)
     * contains  : O(log n)
     * next      : O(long n)
     * thread-safe 보장 안함
     * 특징 : 객체기준으로 정렬을 한다. 느리다.
     *    - null을 허용하지 않는다.
     */
    Set<String> treeSet = new TreeSet<>();

    /**
     *
     * 시간복잡도
     * add       : O(log n)
     * contains  : O(log n)
     * next      : O(1)
     * 병렬처리, thread-safe 보장, 병렬 보장
     * 특징 : Null을 허용하지 않는다.
     *  - Tree set 처럼 정렬을 한다.
     */
    ConcurrentSkipListSet<String> concurrentSkipListSet = new ConcurrentSkipListSet<>();
  }

  public void collectionsByMap() {
    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) h는 테이블 용량
     *
     */
    Map<String, Object> hashMap = new HashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(1)
     *
     */
    Map<String, Object> linkedHashMap = new LinkedHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) H는 테이블
     *
     */
    Map<String, Object> IdentityHashMap = new IdentityHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(1)
     *
     */
    Map<days, Object>  enumMap = new EnumMap(days.class);

    /**
     *
     * 시간복잡도
     * get           : O(log n)
     * containsKey   : O(log n)
     * next          : O(log n)
     *
     */
    Map<String, String> treeMap = new TreeMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) h는 테이블
     *
     */
    Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(log n)
     * containsKey   : O(log n)
     * next          : O(1)
     *
     */
    Map<String, Object> concurrentSkipListMap  = new ConcurrentSkipListMap<>();
  }

  public void collectionsByQueue() {

    /**
     *
     * 시간복잡도
     * offer         : O(log n)
     * peek          : O(1)
     * poll          : O(log n)
     * size          : O(1)
     */
    Queue<String> priorityQueue = new PriorityQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(n)
     *
     */
    Queue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(5);

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(log n)
     * peek          : O(1)
     * poll          : O(log n)
     * size          : O(1)
     *
     */
    Queue<String> priorityBlockingQueue = new PriorityBlockingQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(log n)
     * peek          : O(1)
     * poll          : O(log n)
     * size          : O(1)
     *
     */
    Queue<String> delayQueue = new DelayQueue();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> linkedList = new LinkedList<>();

  /**
   *
   * 시간복잡도
   * offer         : O(1)
   * peek          : O(1)
   * poll          : O(1)
   * size          : O(1)
   *
   */
    Queue<String> arrayList = new ArrayDeque<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     *
     */
    Queue<String> linkedBlockingDeque = new LinkedBlockingDeque<>();
  }

}
