#include <iostream>
#include <time.h>
#include <cstdlib>
#include <cstring>

using namespace std;

// 사용자 정의 함수 목록
int check_s(char **text, int s_num, int s_size);
int check_pd(char *text, int size);

int main()
{

    // 변수 선언
    clock_t start, end;
    double r_time;
    char **text_arr;
    int q_size, s_size, s_num;
    int *max_q;

    // 시간체크 (제거필요)
    start = clock();

    // 입력 받음
    cin >> q_size >> s_num >> s_size;
    cout << "test" << q_size << s_num << s_size;

    // 할당
    text_arr = new char *[s_num];

    for (int z = 0; z < q_size; z++)
    {
        for (int i = 0; i < s_num; i++)
        {
            text_arr[i] = (char *)malloc(sizeof(char) * s_size);
            cin >> text_arr[i];
            cout << "할당 문자열 : " << text_arr[i] << "\n";
        }

        // 1. 최대값 찾는거니 위에서부터 시작하면 됨 컨비네이션으로 경우의 수 계산 함 iCn
        for (int i = s_num; i > 0; i--)
        {
            if (check_s(text_arr, i, s_size))
            {
                cout << "#" << z + 1 << " " << i * s_size;
            }
        }
    }

    // 종료
    end = clock();
    r_time = (double)end - start;
    cout << "running time : " << r_time << "ms\n";

    return 1;
}

int check_s(char **text, int s_num, int s_size)
{
    // 통짜로 만들기보단 걍 하나씩 바꿔가면서 해주는게 나은듯
    // int 2차원 배열 vs
    int next_text_front, next_text_back;
    int result = 1;
    for (int h = 0; h < s_size / 2; h++)
    {

        if (text[next_text_front][h] == text[next_text_back][s_size - h - 1])
        {
            continue;
        }
        else
        {
            result = 0;
            break;
        }
    }
    return result;
}
int check_pd(char *text, int size)
{ // 10개 a~a 9 9
    int result = 1;
    // 홀짝 둘 다 대응 되는듯? -> 홀수 일땐 마지막 한놈이 연산에 안들가니까
    for (int i = 0; i < size / 2; i++)
    {

        if (text[i] == text[size - i - 1])
        {
            continue;
        }
        else
        {
            result = 0;
            break;
        }
    }
    // if (size % 2){ //홀수
    //     for (int i = 0; i < size/2; i++)
    //     {

    //         if (text[i] == text[size - i - 1])
    //         {
    //             continue;
    //         }
    //         else
    //         {
    //             result = 0;
    //             break;
    //         }
    //     }
    // }else{ //짝수
    //     for (int i = 0; i < size/2; i++)
    //     {

    //         if (text[i] == text[size - i - 1])
    //         {
    //             continue;
    //         }
    //         else
    //         {
    //             result = 0;
    //             break;
    //         }
    //     }
    // }
    return result;
}