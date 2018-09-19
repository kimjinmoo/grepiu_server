package com.grepiu.test.process.develop;

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
 * <pre>
 *  Fail-fast :   순차 접근이 끝나기 전에 객체가 변경된 경우 ConcurrentModificationException 예외를 리턴 하는 방식
 * </pre>
 *
 */


enum days {
  SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class CollectionSummary {


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
     * java 1.5 추가 thread-safe 보장, 병렬처리
     * 특징 : 처리에 여분의 오버로드를 가져오지만 순회 작업의 수에 비해 수정 횟수가 최소일때 효과적 이다.
     *    - add는 ArrayList, LinkedList 보다 느리지만 get은 LinkedList보단 빠르고 ArrayList보단 살짝 느리다.
     */
    List<String> copyOnWriteArraylist = new CopyOnWriteArrayList<>();
  }

  @Test
  public void collectionsBySet() {
    /**
     *
     * 시간복잡도
     * add         :   O(1)
     * contains    :   O(1)
     * next        :   o(h/n) h는 테이블 용량
     * java 1.2 thread-safe 보장 안함
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
     * java 1.4 thread-safe 보장 안함
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
     * Java 1.5에서 나옴, 병렬처리, thread-safe 보장, 병렬 보장
     * 특징 : 중복을 허용하지 않는 thread-safe 보장하는 콜렉션인경우 용의
     */
    CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    /**
     *
     * 시간복잡도
     * add       : O(1)
     * contains  : O(1)
     * next      : O(1)
     * Java 1.5 에서 나옴
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
     * java 1.2 에서 나옴 thread-safe 보장 안함
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
     * java 1.6 병렬처리, thread-safe 보장, 병렬 보장
     * 특징 : Null을 허용하지 않는다.
     *  - Tree set 처럼 정렬을 한다.
     */
    ConcurrentSkipListSet<String> concurrentSkipListSet = new ConcurrentSkipListSet<>();
  }

  @Test
  public void collectionsByMap() {
    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) h는 테이블 용량
     * java 1.2 에서 나옴
     * 특징 : 순서에 상관없이 저장됨, Null을 허용한다. thread-safe 보장하지 않는다.
     */
    Map<String, Object> hashMap = new HashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(1)
     * java 1.4 에서 나옴
     * 특징 : 순서대로 등록한다. Null을 허용한다. thread-safe 보장하지 않는다.
     */
    Map<String, Object> linkedHashMap = new LinkedHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) H는 테이블
     * java 1.4 에서 나옴
     * 특징 : Map 형식에 부합되지 않음
     */
    Map<String, Object> IdentityHashMap = new IdentityHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(1)
     * java 1.5 에서 나옴
     * 특징 :
     */
    Map<days, Object>  enumMap = new EnumMap(days.class);

    /**
     *
     * 시간복잡도
     * get           : O(log n)
     * containsKey   : O(log n)
     * next          : O(log n)
     * java 1.2 에서 나옴
     * 특징 : 정렬이 되면서 추가가 됨
     *     -  null은 허용하지 않음
     *     -  thread-safe 보장하지 않는다.
     */
    Map<String, String> treeMap = new TreeMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(1)
     * containsKey   : O(1)
     * next          : O(h/n) h는 테이블
     * java 1.5 에서 나옴
     * 특징 :  thread-safe 보장하면서 SynchronizedMap 보다 속도가 빠르다
     *      - null을 허용하지 않음
     */
    Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     *
     * 시간복잡도
     * get           : O(log n)
     * containsKey   : O(log n)
     * next          : O(1)
     * java 1.6 에서 나옴
     * 특징 : thread-safe 보장하면서 SynchronizedMap 보다 속도가 빠르다
     *       - 메모리를 사용하여 O(log n)으로 데이터를 검색, 삽입, 삭제가 가능하다
     *       - lock이 적게 사용되어야 하는 병렬 처리 시스템에 용의
     */
    Map<String, Object> concurrentSkipListMap  = new ConcurrentSkipListMap<>();
  }

  @Test
  public void collectionsByQueue() {

    /**
     *
     * 시간복잡도
     * offer(입력)   : O(log n)
     * peek(get)     : O(1)
     * poll(반환)    : O(log n)
     * size          : O(1)
     * natural order : JVM에서 제공하는 일반적인거와 다를수 있음 순서 ex) 문자는 ASCII 순서로 정렬
     * java 1.5 에서 나옴
     * 특징 : 일반적은 큐는 FIFO의 구조를 가지지만 자연 네추럴 오더에 따라 정렬
     *       - Null을 허용하지 않는다.
     *
     */
    Queue<String> priorityQueue = new PriorityQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(n)
     * java 1.5 에서 나옴 thread-safe 보장(결과에 문제가 발생할 여지 있음)
     * 특징 : FIFO 방식 Queue
     *      -  데이터/추가/삭제가 빠름
     *      - size는 O(1)이 아니다.
     *      - null을 허용하지 않는다.
     */
    Queue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     * java 1.5에서 나옴
     * 특징 - 고정배열에 일반적인 Queue(FIFO)
     *      - 배열이 고정된 사이즈, 생성되면 변경 안됨
     */
    Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(5);

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     * java 1.5 에서 나옴
     * 특징 : FIFO 정렬
     *      - 크기를 지정하지 않을 경우 Integer.MAX_VALUE와 동일하게 생성됨
     *      - 삽입이 동적임
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
     * java 1.5 에서 나옴
     * 특징 : PriorityQueue 와 같은 정렬식으로 저장
     *      - 논리적으로 한대로 추가 가능
     *      - 자원이 고갈되면 OOM 발생
     */
    Queue<String> priorityBlockingQueue = new PriorityBlockingQueue<>();

    /**
     *
     * 시간복잡도
     * offer         : O(log n)
     * peek          : O(1)
     * poll          : O(log n)
     * size          : O(1)
     * java 1.5 에서 나옴
     * 특징 : 지연이 만료 되었을 때문 요소를 가져올수있다.
     *     -
     */
    Queue<String> delayQueue = new DelayQueue();

  /**
   *
   * 시간복잡도
   * offer         : O(1)
   * peek          : O(1)
   * poll          : O(1)
   * size          : O(1)
   * java 1.6에서 나옴
   * 특징 : 양 측면에서 요소를 추가하거나 제거 할수 있는 확장 가능한 배열의 특별한 종류
   *
   */
    Queue<String> arrayDeque = new ArrayDeque<>();

    /**
     *
     * 시간복잡도
     * offer         : O(1)
     * peek          : O(1)
     * poll          : O(1)
     * size          : O(1)
     * java 1.6
     * 특징 : 무제한으로 인스턴스화 할수 있음
     */
    Queue<String> linkedBlockingDeque = new LinkedBlockingDeque<>();
  }
}
