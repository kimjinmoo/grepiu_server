package com.grepiu.test.ex;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/new-year-chaos/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
 */
public class Ex2 {

    // Complete the minimumBribes function below.
    static void minimumBribes(int[] q) {
        // 뇌물 카운트가 오버 된것을 체크 한다.
        boolean isChaotic = false;
        // 비교 배열을 만든다.
        int compare[] = new int[q.length];
        for(int i =0; i < compare.length; i++) {
            compare[i] = i+1;
        }
        // 뇌물 카운트 리미트
        final int bribeLimit = 2;
        // 배열 읽기 카운트
        int read = 0;
        // 뇌물 카운트
        int bribe = 0;

        while(read < q.length) {
            // 숫자가 compare 배열보다 보다 크면 뇌물을 준것이다. 시간 복잡도는 꽤 길다, N*N 정도 걸린다.
            while(q[read] > compare[read]) {
                // 위치 인덱스 찾기
                int findIndex = 0;
                for(int i = 0; i < q.length; i++) {
                    // compare에서 위치를 찾는다.
                    if(q[read]==compare[read+i]) {
                        findIndex = i;
                        break;
                    };
                }
                // 위치 익덱스가 2 이상이면 리미트 초과이다.
                if(findIndex > bribeLimit) {
                    isChaotic = true;
                    break;
                }
                // compare값을 이동시켜 값을 같게 한다. 이동시 뇌물 카운트를 1 시킨다.
                for(int i = findIndex; i > 0; i--) {
                    bribe+=1;
                    int tmpA = compare[read+i];
                    int tmpB = compare[read+i-1];
                    compare[read+i] = tmpB;
                    compare[read+i-1] = tmpA;
                }
            }
            // 읽기 카운트 증감
            read++;
        }
        // 최종 값 표출
        if(isChaotic) {
            System.out.println("Too chaotic");
        } else {
            System.out.println(bribe);
        }

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] q = new int[n];

            String[] qItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qItems[i]);
                q[i] = qItem;
            }

            minimumBribes(q);
        }

        scanner.close();
    }
}
