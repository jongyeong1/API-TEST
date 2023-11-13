''' 
    강의명 : 빅데이터분석시각화 
    작성자 : 이종영
    학번 : 2016039029
    대상 : Mid-Term Project 
    주제 : 
    마감일 : 23/10/31 23:59:59 , 제출일 : 23/10/30 23:00
'''

'''
다이아 선정이유 : 버젯에 따라 구매가능한 최대한도의 다이아 
                 가격과 어느정도 비례관계를 가지는지
                 가성비라고 할만한 다이아 -> 그래프 분석
                 색이 가격에 주는 영향, 크기가 주는 영향 등 가장 가격에 영향을 주는 지표 분석
                 다이아 등급 분포
                 가장 희소성 높은 다이아
                 가장 비싼 다이아
                 예산설정에 따른 가성비 구매는 결국
                 다이아몬드 인덱스->시퀀스넘버, 캐럿 ->크기, 컷 -> 품질, 컬러 -> 색상, 클레어리티 -> 등급
                 뎁스,테이블 은 크기지표 -> 뎁스는 깊이 테이블은 위의 평면크기
                 x,y,z 또한 크기지표 -> 캐럿에 영향
                 각 지표별 상관관계
                 https://www.datamanim.com/dataset/03_dataq/diamondQuestion.html
                 https://seungjuitmemo.tistory.com/37
                 https://trivia-starage.tistory.com/102
                 https://teddylee777.github.io/visualization/seaborn-tutorial-1/
'''
# %%

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sb

prescription_full = pd.read_csv('국민건강보험공단_의약품처방정보_03_20211231.csv')
prescription = prescription_full.head()

population_full = pd.read_csv('행정구역_시군구_별_1세별_주민등록인구_20231031112928.csv')
population = population_full.head()

print('\n',population_full.shape)
# 데이터 크기 : (11053871, 15)
print('\n',population.columns)
# 활용할 속성 : AGE_GROUP, SEX, SIDO-시정보, GNL_NM_CD-의약품코드, UN_COST 가격, MDCN_EXEC_FRQ 배급량, AMT(MDCN_EXEC_FRQ * UN_COST) - 비용


print('\n',population_full.shape)
# 데이터 크기 : (1837, 5)
print('\n',population.columns)
# 활용할 속성 : 행정구역(시군구)별, 연령별, 2023.09 - 남녀합, 2023.09.01 - 남자, 2023.09.02 - 여자

# 사용할 속성 및 직관적이게 할 수 있도록 데이터 수정
prescription_full.drop(['STND_Y','STND_Y','SEQ_NO','RECU_FR_DT','DD_MQTY_FREQ','DD_EXEC_FREQ','DATA_STD_DT'], axis=1, inplace=True)
population_full.rename(columns = {'2023.09':'ALL','2023.09.1':'MALE','2023.09.2':'FEMALE'}, inplace=True)



sb.histplot(prescription_full['AGE_GROUP'])
print('\n',prescription_full['AGE_GROUP'].max(numeric_only = True))
print('\n',prescription_full['AGE_GROUP'].min(numeric_only = True))
# 나이구간 : 0~14=1 , 15~24=2, 25~29=3, 30~34=4 ... 95~99=17, (100<=)=18 
# 따라서 인구수 테이블을 해당 기준으로 묶어야 합니다.


sb.regplot(data=prescription_full, x='SIDO', y='AMT')
sb.regplot(data=prescription_full, x='AGE_GROUP', y='AMT')
sb.regplot(data=prescription_full, x='SEX', y='AMT')


# 인구수 테이블을 의약품 나이구간을 기준으로 다시 생성시킴

#

# 일단 영향 주는 속성과 데이터가 크니까 짤라서 볼 수 있는거 확인 

#sb.histplot(population_full['AGE_GROUP'])
#print('\n',population_full['AGE_GROUP'].max)

#sb.histplot(df) -> 쓸모없음
#sb.pairplot(data=df) -> 이거 너무 빡셈

#sb.violinplot(data=df, x='cut', y='table', hue='clarity')
#sb.violinplot(data=df, x='table')

#illoc_set = diamonds.iloc[:, 4:10]
#lloc_set_co = illoc_set.corr()
#sb.boxplot(data=diamonds, x='cut', y='table', hue='clarity')
#sb.heatmap(illoc_set_co, annot = True)

#interest_df = diamonds.loc[:, "depth": "z"].corr()

#sb.clustermap(interest_df)

#print(illoc_set)
#print(illoc_set_co)

# %%
# 1. Import necessary libraries

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import confusion_matrix
from sklearn import metrics


df = pd.read_csv('cancer.csv')

# 3. Exploratory data analysis: Data Information
print(df.head())
print(df.shape)
print(df.info())

# 3. Exploratory data analysis: Class Inbalance?
print(df['target'].value_counts())
print()
print(df['target'].value_counts()/np.float64(len(df)))

# 3. Exploratory data analysis: Missing Data?
print(df.isnull().sum())

# 3. Exploratory data analysis: Outliers?
data_to_boxplot = [df['age'], df['trestbps'], df['chol'], df['thalach'], df['oldpeak']]
plt.boxplot(data_to_boxplot)
plt.xlabel('Attributes')
plt.ylabel('Value')
plt.show()

# 4. Split data into separate training and test set
training_points = df.drop(columns=['target'])
training_labels = df['target']

X_train, X_test, y_train, y_test = train_test_split(
	training_points, 
	training_labels, 
	test_size=0.3, 
	random_state=4)

#print(X_train)
#print(y_train)
#print(X_test)
#print(y_test)

#print(X_train.shape)
#print(y_train.shape)
#print(X_test.shape)
#print(y_test.shape)

# 5. Fit K Neighbours Classifier to the training set
classifier = KNeighborsClassifier(n_neighbors = 5)
classifier.fit(X_train, y_train)
guesses = classifier.predict(X_test)

print(guesses)

# 6. Check Accuracy Score
print(confusion_matrix(y_test, guesses))
print(metrics.accuracy_score(y_test, guesses))

# 7. Rebuild kNN Classification model using different values of k 
classifier = KNeighborsClassifier(n_neighbors = 10)
classifier.fit(X_train, y_train)
guesses = classifier.predict(X_test)

print(guesses)
print(confusion_matrix(y_test, guesses))
print(metrics.accuracy_score(y_test, guesses))

# 7. Rebuild kNN Classification model using different values of k 
classifier = KNeighborsClassifier(n_neighbors = 15)
classifier.fit(X_train, y_train)
guesses = classifier.predict(X_test)

print(guesses)
print(confusion_matrix(y_test, guesses))
print(metrics.accuracy_score(y_test, guesses))

# 7. Improving Accuracy: Tuning k parameter 
k_range = range(1, 50)

accuracy_scores = []

for k in k_range:
    classifier = KNeighborsClassifier(n_neighbors = k)
    classifier.fit(X_train, y_train)
    guesses = classifier.predict(X_test)
    accuracy_scores.append(metrics.accuracy_score(y_test, guesses))
print(accuracy_scores)

plt.plot(k_range, accuracy_scores)
plt.xlabel('Value of K for KNN')
plt.ylabel('Testing Accuracy')
plt.show()

# 8. Improving Accuracy: Changing split ratio
training_points = df.drop(columns=['target'])
training_labels = df['target']

X_train, X_test, y_train, y_test = train_test_split(
	training_points, 
	training_labels, 
	test_size=0.2, 
	random_state=4)

print(X_train.shape)
print(y_train.shape)
print(X_test.shape)
print(y_test.shape)

# 8. Improving Accuracy: Changing split ratio
# Tuning k parameter 
k_range = range(1, 50)

accuracy_scores = []

for k in k_range:
    classifier = KNeighborsClassifier(n_neighbors = k)
    classifier.fit(X_train, y_train)
    guesses = classifier.predict(X_test)
    accuracy_scores.append(metrics.accuracy_score(y_test, guesses))
print(accuracy_scores)

plt.plot(k_range, accuracy_scores)
plt.xlabel('Value of K for KNN')
plt.ylabel('Testing Accuracy')
plt.show()


# 9. Improving Accuracy: Feature Engineering

dfcorr = df.corr()
plt.figure(figsize=(16,5))
sns.heatmap(data = dfcorr, annot=True)

df = df.drop(columns=['trestbps', 'chol', 'fbs', 'restecg'])


# 11. Split data into separate training and test set
training_points = df.drop(columns=['target'])
training_labels = df['target']

X_train, X_test, y_train, y_test = train_test_split(
	training_points, 
	training_labels, 
	test_size=0.2, 
	random_state=4)

print(X_train.shape)
print(y_train.shape)
print(X_test.shape)
print(y_test.shape)

# 11. Tuning k parameter 
k_range = range(1, 50)

accuracy_scores = []

for k in k_range:
    classifier = KNeighborsClassifier(n_neighbors = k)
    classifier.fit(X_train, y_train)
    guesses = classifier.predict(X_test)
    accuracy_scores.append(metrics.accuracy_score(y_test, guesses))
print(accuracy_scores)

plt.plot(k_range, accuracy_scores)
plt.xlabel('Value of K for KNN')
plt.ylabel('Testing Accuracy')
plt.show()

df.describe()


# 12. Improving Accuracy: Feature Scaling
from sklearn.preprocessing import MinMaxScaler

#Create copy of dataset.
df = df.copy()

#Rescaling features age, thalach, oldpeak.
scaler = MinMaxScaler()

features = [['age', 'thalach', 'oldpeak']]
for feature in features:
    df[feature] = scaler.fit_transform(df[feature])