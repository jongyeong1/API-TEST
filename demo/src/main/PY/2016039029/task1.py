''' 
    강의명 : 빅데이터분석시각화 
    작성자 : 이종영
    학번 : 2016039029
    주제 : HW#1_TASK1:10
    마감일 : 23/09/27 , 제출일 : 23/09/27
'''
import numpy as np

# TASK1 - Create an array filled with single value(55)

def Task_1() -> int :
    array_1 = np.full((3,3),55)
    print(array_1)

def Task_2() -> int :
    a = 0

def Task_3() -> int :
    a = 0
    
def Task_4() -> int :
    a = 0
    
def Task_5() -> int :
    a = 0
    
def Task_6() -> int :
    a = 0
    
def Task_7() -> int :
    a = 0
    
def Task_8() -> int :
    a = 0
    
def Task_9() -> int :
    a = 0
    
def Task_10() -> int :
    a = 0
    

Taskf_num = { '1' : Task_1(),
              2 : Task_2(),
              3 : Task_3(),
              4 : Task_4(),
              5 : Task_5(),
              6 : Task_6(),
              7 : Task_7(),
              8 : Task_8(),
              9 : Task_9(),
              10 : Task_10(),
}

def main() -> None:
    task_num = input("Choose Task Num : ")
    Taskf_num[task_num]

if __name__ == "__main__" :
    main()

    